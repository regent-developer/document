# SpringBoot中的实用工具类

## 获取进程ID
```java
ApplicationPid pid = new ApplicationPid();
System.out.printf("进程ID: %s%n", pid.toString());
```

```java
// 在 META-INF/spring.factories 中注册监听器，将进程ID写入到一个文件中。通过配置 spring.pid.file 属性，我们可以指定文件路径
spring.pid.file=d:/app.pid
```

```java
String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
```

## 应用运行主目录
```java
ApplicationHome home = new ApplicationHome();
System.out.printf("dir: %s, source: %s%n", home.getDir(), home.getSource());
```

## 获取 Java 版本
```java
System.out.printf("Java Version: %s%n", JavaVersion.getJavaVersion());
```

## 应用临时目录
```java
ApplicationTemp temp = new ApplicationTemp();
System.out.printf("临时目录: %s%n", temp.getDir());
```

## 系统属性/环境变量访问
```java
System.out.printf("java.home=%s%n", SystemProperties.get("java.home"));
```

## 实例化对象
```java
public interface DAO {}
public class A implements DAO {}
public class B implements DAO {}

Instantiator<DAO> instant = new Instantiator<>(DAO.class, p -> {});
List<DAO> ret = instant.instantiate(List.of("com.pack.A", "com.pack.B"));
System.out.printf("%s%n", ret);
```

## 资源加载
```java
//  PropertiesPropertySourceLoader 和 YamlPropertySourceLoader 可以用来加载 .properties、.xml、.yaml 资源文件
PropertiesPropertySourceLoader propertyLoader = new PropertiesPropertySourceLoader();
List<PropertySource<?>> list = propertyLoader.load("pack", new ClassPathResource("pack.properties"));
System.out.printf("pack.*: %s%n", list.get(0).getSource());

YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();
List<PropertySource<?>> yamls = yamlLoader.load("pack", new ClassPathResource("pack.yml"));
System.out.printf("pack.*: %s%n", yamls.get(0).getSource());
```

## 获取 basePackages
```java
private ConfigurableApplicationContext context;
System.out.printf("basePackages: %s%n", AutoConfigurationPackages.get(context));
```

