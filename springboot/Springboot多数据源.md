# Springboot多数据源

## 使用Spring Boot官方支持的多数据源配置


在application.properties文件中分别添加多个数据源的配置，并通过@Primary注解指定默认数据源。然后，通过@Configuration注解创建一个DataSourceConfig类，将多个数据源注入到该类中，并通过@Bean注解将其注册为Spring Bean。最后，在需要访问某个数据源时，直接使用@Qualifier注解指定具体的数据源即可。  

### application.properties文件配置
```
# Primary DataSource
spring.datasource.url=jdbc:mysql://localhost:3306/db1
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 
# Secondary DataSource
spring.second-datasource.url=jdbc:mysql://localhost:3306/db2
spring.second-datasource.username=root
spring.second-datasource.password=root
spring.second-datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### DataSourceConfig类配置
```java
@Configuration
public class DataSourceConfig {
    @Primary
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }
 
    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix="spring.second-datasource")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }
}
```

### 测试代码
```java
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;
 
    @Autowired
    @Qualifier("secondDataSource")
    private DataSource secondDataSource;
 
    // ...
}
```




## 使用第三方库实现多数据源（Druid连接池）

### application.properties文件配置
```
# Primary DataSource
jdbc.primary.url=jdbc:mysql://localhost:3306/db1
jdbc.primary.username=root
jdbc.primary.password=root
jdbc.primary.driver-class-name=com.mysql.cj.jdbc.Driver
 
# Secondary DataSource
jdbc.second.url=jdbc:mysql://localhost:3306/db2
jdbc.second.username=root
jdbc.second.password=root
jdbc.second.driver-class-name=com.mysql.cj.jdbc.Driver
```

### DataSourceConfig类配置
```java
@Configuration
public class DataSourceConfig {
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix="jdbc.primary")
    public DataSource primaryDataSource() {
        return new DruidDataSource();
    }
 
    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix="jdbc.second")
    public DataSource secondDataSource() {
        return new DruidDataSource();
    }
}
```

### 测试代码
```java
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;
 
    @Autowired
    @Qualifier("secondDataSource")
    private DataSource secondDataSource;
 
    // ...
}
```
