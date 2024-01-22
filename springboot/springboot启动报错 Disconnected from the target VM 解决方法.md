# Springboot启动报错 Disconnected from the target VM 解决方法



## 现象

springboot工程启动时，报错，报错信息：【Disconnected from the target VM, address: ‘127.0.0.1:xxxxx’, transport: 'sock】



## 解决方法

* 查找端口是否被占用

```shell
netstat –ano|findstr xxxx
```



* 没有引入**spring-boot-starter-web**时，也会报上面的错误

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

