# SpringBoot接口防重复提交

## 前言
在开发中，我们经常会遇到重复提交的问题，比如用户在短时间内多次点击提交按钮，或者在网络延迟的情况下多次点击提交按钮，导致数据重复提交。为了防止这种情况，我们可以使用防重复提交技术。

## 实现

1，防止重复提交注解(@RepeatSubmit)
```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复提交的注解
 * 单位：毫秒
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RepeatSubmit {

    /**
     * 重复提交间隔时间/毫秒
     *
     * @return
     */
    int value() default 300;

    /**
     * 最长间隔30s
     */
    int MAX_INTERVAL = 30000;
}
```

2，凭证，用于校验重复提交的 ticket
```java
import java.util.function.Function;

/**
 * 凭证
 *
 */
public abstract class AbstractRepeatSubmitTicket {

    private Function<String, String> ticketFunction;

    public AbstractRepeatSubmitTicket(Function<String, String> ticketFunction) {
        this.ticketFunction = ticketFunction;
    }

    /**
     * 获取凭证
     *
     * @param ticketToken
     * @return
     */
    public String getTicket(String ticketToken) {
        return this.ticketFunction.apply(ticketToken);
    }

    /**
     * 获取凭证 时间戳
     *
     * @param ticket
     * @return
     */
    public abstract Long getTicketTimestamp(String ticket);

    /**
     * 设置本次请求时间
     *
     * @param ticket
     */
    public abstract void putTicket(String ticket);

    /**
     * 移除凭证
     *
     * @param ticket
     */
    public abstract void removeTicket(String ticket);
}
```

2.1，使用 caffine 作为存储的实现方式
```java
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.demo.annoation.RepeatSubmit;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 凭证（内存实现）
 *
 */
public class RepeatSubmitCaffeineTicket extends AbstractRepeatSubmitTicket {

    /**
     * 限制缓存最大数量 超过后先放入的会自动移除
     * 默认缓存时间
     * 初始大小为：100万
     */
    private static Cache<String, Long> cache = Caffeine.newBuilder()
            .maximumSize(100 * 10000)
            .expireAfterWrite(RepeatSubmit.MAX_INTERVAL, TimeUnit.MILLISECONDS).build();

    public RepeatSubmitCaffeineTicket(Function<String, String> ticketFunction) {
        super(ticketFunction);
    }

    @Override
    public Long getTicketTimestamp(String ticket) {
        return cache.getIfPresent(ticket);
    }

    @Override
    public void putTicket(String ticket) {
        cache.put(ticket, System.currentTimeMillis());
    }

    @Override
    public void removeTicket(String ticket) {
        cache.invalidate(ticket);
    }
}
```

2.2，使用 redis 作为存储的实现方式
```java
import com.demo.annoation.RepeatSubmit;
import org.springframework.data.redis.core.ValueOperations;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 凭证（redis实现）
 *
 */
public class RepeatSubmitRedisTicket extends AbstractRepeatSubmitTicket {

    private ValueOperations<String, String> redisValueOperations;

    public RepeatSubmitRedisTicket(ValueOperations<String, String> redisValueOperations, Function<String, String> ticketFunction) {
        super(ticketFunction);
        this.redisValueOperations = redisValueOperations;
    }

    @Override
    public Long getTicketTimestamp(String ticket) {
        Long timeStamp = System.currentTimeMillis();
        boolean setFlag = redisValueOperations.setIfAbsent(ticket, String.valueOf(timeStamp), RepeatSubmit.MAX_INTERVAL, TimeUnit.MILLISECONDS);
        if (!setFlag) {
            timeStamp = Long.valueOf(redisValueOperations.get(ticket));
        }
        return timeStamp;
    }

    @Override
    public void putTicket(String ticket) {
        redisValueOperations.getOperations().delete(ticket);
        this.getTicketTimestamp(ticket);
    }

    @Override
    public void removeTicket(String ticket) {
        redisValueOperations.getOperations().delete(ticket);
    }
}
```

3，切面
```java
import lombok.extern.slf4j.Slf4j;
import com.demo.annoation.RepeatSubmit;
import com.demo.ticket.AbstractRepeatSubmitTicket;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * 重复提交 aop 切口
 *
 */
@Aspect
@Slf4j
public class RepeatSubmitAspect {

    private AbstractRepeatSubmitTicket repeatSubmitTicket;

    /**
     * 获取凭证信息
     *
     * @param repeatSubmitTicket
     */
    public RepeatSubmitAspect(AbstractRepeatSubmitTicket repeatSubmitTicket) {
        this.repeatSubmitTicket = repeatSubmitTicket;
    }

    /**
     * 定义切入点
     *
     */
    @Around("@annotation(com.demo.annoation.RepeatSubmit)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String ticketToken = attributes.getRequest().getServletPath();
        String ticket = this.repeatSubmitTicket.getTicket(ticketToken);
        if (StringUtils.isEmpty(ticket)) {
            return point.proceed();
        }
        Long lastRequestTime = this.repeatSubmitTicket.getTicketTimestamp(ticket);
        if (lastRequestTime != null) {
            Method method= ((MethodSignature) point.getSignature()).getMethod();
            RepeatSubmit annotation= method.getAnnotation(RepeatSubmit.class);
            int interval = Math.min(annotation.value(), RepeatSubmit.MAX_INTERVAL);
            if (System.currentTimeMillis() < lastRequestTime + interval) {
                // 提交频繁
                return ResponseDTO.error(UserErrorCode.REPEAT_SUBMIT);
            }
        }
        Object obj = null;
        try {
            // 先给 ticket 设置在执行中
            this.repeatSubmitTicket.putTicket(ticket);
            obj = point.proceed();
        } catch (Throwable throwable) {
            log.error("", throwable);
            throw throwable;
        }
        return obj;
    }

}
```

4，配置 ticket 存储方式
使用 caffine 作为存储，具体凭证使用的是 [url + userid]，我这里用 sa-token 鉴权的，可自行修改。
```java
import cn.dev33.satoken.stp.StpUtil;
import com.demo.RepeatSubmitAspect;
import com.demo.ticket.RepeatSubmitCaffeineTicket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 重复提交配置
 */
@Configuration
public class RepeatSubmitConfig {

    @Bean
    public RepeatSubmitAspect repeatSubmitAspect() {
        RepeatSubmitCaffeineTicket caffeineTicket = new RepeatSubmitCaffeineTicket(this::ticket);
        return new RepeatSubmitAspect(caffeineTicket);
    }

    /**
     * 获取指明某个用户的凭证
     */
    private String ticket(String servletPath) {
        // 获取用户ID：用 sa-token 鉴权的，可自行修改
        String userId = StpUtil.getLoginId();
        if (null == userId) {
            return "";
        }
        return servletPath + "_" + userId;
    }
}
```

5，使用
```java
@RestController
public class TestController{

    @GetMapping("/test1")
    @RepeatSubmit
    public String test1(String str) {
        return "防止重复提交：" + str;
    }

    @GetMapping("test2")
    public String test2(String str) {
        return "正常提交：" + str;
    }
}
```