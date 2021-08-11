# flowable
Flowable is a light-weight business process engine written in Java.

## 官方网站
https://www.flowable.org/docs/userguide/index.html

## Flowable与springBoot项目整合

### 添加依赖
```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--flowable工作流依赖-->
        <dependency>
            <groupId>org.flowable</groupId>
            <artifactId>flowable-spring-boot-starter</artifactId>
            <version>6.3.0</version>
        </dependency>
        <!--mysql依赖-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.45</version>
        </dependency>
</dependencies>
```

### flowable配置
```yml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/flowable-spring-boot?characterEncoding=UTF-8
    username: root
    password: root
flowable:
#关闭定时任务JOB
  async-executor-activate: false
```

### 定义流程文件
The Flowable engine expects processes to be defined in the BPMN 2.0 format, which is an XML standard that is widely accepted in the industry. 
Typically, such a process definition is modeled with a visual modeling tool, such as the Flowable Designer (Eclipse) or the Flowable Modeler (web application).


### 参照站点
https://blog.csdn.net/puhaiyang/article/details/79845248
