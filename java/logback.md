# 使用Logback各自输出sql log

## 问题
利用logback输出mybatis的sql语句的log，根据需求两个不同的包分别输出各自的sql log，并且这两个不同的包有共同的DAO，这样就不能分别输出各自的sql log。

## 调查步骤
1，利用interface的继承的特性，分别给两个包，继承了公共的DAO的interface，实验失败。  
2，分别复制两套共同的DAO为两个包使用，可以分别输出sql log，但方案的代码冗余大，方案否定。  
3，利用logback的filter和MDC来实现，方案成功。

## 代码
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <File>C:/log/sql.log</File>
        <!--追加filter的节点-->
        <filter class="com.filter.AlogFilter"></filter>
        <encoder>
            <pattern>%date [%level] [%thread] %logger{60} [%file : %line] %msg%n</pattern>
        </encoder>
    </appender>

    <logger name="com.dao" level="DEBUG" />

    <root level="DEBUG">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>
</configuration>
```

自定义Filter  
```java
/**
 * @Author: huyd
 * @Description:
 * @Date Created in 23:37 2020/9/18.
 */
public class ALogFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        // 根据MDC取得的过滤类型进行过滤（放行与否）
        String type = MDC.get("type");
        if (type.equals("a")){
            // 通过
            return FilterReply.ACCEPT;
        } else {
            // 不通过
            return  FilterReply.DENY;
        }
        
    }
}
```

调用时的处理  
```java
// 在调用的位置追加下面代码，在MDC中追加需过滤的类型
MDC.put("type", "a");
```

说明：分别定义两个包的自定义过滤器ALogFilter和BLogFilter，制定放行规则，在logback.xml中设置sql log输出的位置添加A和B的过滤器，依据MDC的设置来控制log输出是否放行。  
  
  
2020/9/18
