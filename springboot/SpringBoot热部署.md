# SpringBoot开启热部署

## 依赖
```xml
<!-- 开启热部署 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

## 配置文件
```
# 热部署相关devtools配置
# 热部署生效
spring.devtools.restart.enabled=true
# 设置重启目录
spring.devtools.restart.additional-paths=src/main/java
# 排除静态资源变化不用重启
spring.devtools.restart.exclude=static/**
```