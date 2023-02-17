# Springboot集成spring-retry

## 依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.retry</groupId>
    <artifactId>spring-retry</artifactId>
</dependency>
```

## 开启spring-retry

启动类上增加注解 @EnableRetry

```java
@EnableRetry
@SpringBootApplication
public class AsurplusApplication {
    public static void main(String[] args) {
        SpringApplication.run(AsurplusApplication.class, args);
    }
}

```


## 重试实现类

在需要重试的方法上增加注解 @Retryable，表示该方法需要重试

```java
@Component
public class TestRetry {

    int a = 0;

    @Retryable(value = {RuntimeException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 2))
    public String test() {
        a++;
        System.out.println(a + " - " + System.currentTimeMillis());
        if (a < 10) {
            throw new RuntimeException("未满足条件");
        }
        return "执行成功";
    }

}

```

### @Retryable 注解

* value：可重试的异常类型。含义同include。默认为空(如果excludes也为空，则重试所有异常)
* include：可重试的异常类型。默认为空(如果excludes也为空，则重试所有异常)
* exclude：无需重试的异常类型。默认为空(如果includes也为空，则重试所有异常)
* maxAttempts：最大重试次数(包括第一次失败)，默认为3次
* backoff：重试等待策略,下面会在@Backoff中介绍
* recover：表示重试次数到达最大重试次数后的回调方法

### @Backoff 注解

* delay：重试之间的等待时间(以毫秒为单位)
* maxDelay：重试之间的最大等待时间(以毫秒为单位)
* multiplier：指定延迟的倍数
* delayExpression：重试之间的等待时间表达式
* maxDelayExpression：重试之间的最大等待时间表达式
* multiplierExpression：指定延迟的倍数表达式
* random：随机指定延迟时间

当重试耗尽时，RetryOperations 可以将控制传递给另一个回调，即 RecoveryCallback。Spring-Retry 还提供了 @Recover 注解，用于 @Retryable 重试失败后处理方法。若不需要重试失败后的处理方法，则不写回调方法，重试耗尽后抛出异常。  

```java
@Recover
public String recoverTest(RuntimeException e) {
    return "回调方法-" + e.getMessage();
}

```

若同一个实现类中有多个回调方法，我们需要使用 recover 属性指定回调的方法名。
