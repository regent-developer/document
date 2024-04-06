# Springboot打包成war包

1. pom.xml配置修改

```xml
<packaging>war</packaging>
```

	
2. 添加依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <!--排除SpringBoot内置的Tomcat干扰-->
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!--添加servlet-api依赖,用来打war包-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
```

        
3. 启动类修改
```java
 //继承SpringBootServletInitializer类
 //重写configure方法
@SpringBootApplication
public class SpringtestDemoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SpringtestDemoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringtestDemoApplication.class);
    }
}
```