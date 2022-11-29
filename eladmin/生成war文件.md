# eladmin生成war文件的设置

## 父pom文件的设置
```xml

<!--增加-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>
<dependency> 
        <groupId>org.apache.tomcat</groupId> 
        <artifactId>tomcat-servlet-api</artifactId> 
        <version>8.0.36</version> 
        <scope>provided</scope> 
</dependency>

<!--Spring boot Web容器-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions> 
        <!-- 去除内嵌tomcat -->
        <exclusion> 
            <groupId>org.springframework.boot</groupId> 
            <artifactId>spring-boot-starter-tomcat</artifactId> 
        </exclusion> 
    </exclusions> 
</dependency>
```

## AppRun.java的设置
```java
// 增加
protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(new Class[] { AppRun.class });
}
```

## xxx-system的pom文件设置
```xml
<!-- 增加 -->
<packaging>war</packaging>

<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>repackage</goal>
            </goals>
        </execution>
    </executions>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-war-plugin</artifactId>
    <version>3.2.2</version>
    <configuration>
        <webResources>
            <webResource>
                <!-- 拷贝第三方jar文件 -->
                <directory>${pom.basedir}/src/main/resources/lib/</directory>
                <targetPath>WEB-INF/lib/</targetPath>
                <includes>
                    <include>**/*.jar</include>
                </includes>
            </webResource>
        </webResources>
    </configuration>
</plugin>
```