# 修改nacos配置后不需要重启服务

@RefreshScope是Spring Cloud提供的一种属性刷新机制，它可以应用于需要动态刷新的配置类或方法上。当Nacos上的属性值发生变化时，通过调用/actuator/refresh端点（如果Spring Boot Actuator已集成到项目中）来刷新被@RefreshScope注解的类或方法。这样，应用就能获取到最新的配置信息，而无需重启服务。
@RefreshScope注解可以应用于类或方法上，具体使用方式如下：

1. 在需要动态刷新的配置类或方法上添加@RefreshScope注解，例如：

```java
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${my.property}")
    private String myProperty;

    @GetMapping("/property")
    public String getProperty() {
        return myProperty;
    }
}
```

2. 在Spring Boot应用的启动类上添加@EnableConfigurationProperties注解，以启用属性刷新功能，例如：

```java
@SpringBootApplication
@EnableConfigurationProperties
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

3. 在Nacos控制台上修改配置后，调用/actuator/refresh端点来刷新配置，例如：

```bash
curl -X POST http://localhost:8080/actuator/refresh
```