# springboot内置Health Indicator



## **DiskSpaceHealthIndicator**

用于检查磁盘空间是否足够。如果磁盘空间不足，应用程序的健康状态将被标记为DOWN。



```properties
management.endpoint.health.show-details=always
management.endpoint.health.diskspace.threshold=1MB
```



## **DataSourceHealthIndicator**

用于检查数据源的连接状态。如果数据源无法连接，应用程序的健康状态将被标记为DOWN。



```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=password
management.endpoint.health.show-details=always
```



## **LdapHealthIndicator**

用于检查LDAP服务器的连接状态。



```properties
spring.ldap.urls=ldap://ldap.example.com
management.endpoint.health.show-details=always
```



## **RabbitHealthIndicator**

用于检查RabbitMQ消息代理的连接状态。



```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
management.endpoint.health.show-details=always
```



## **RedisHealthIndicator**

用于检查Redis服务器的连接状态。



```properties
spring.redis.host=localhost
spring.redis.port=6379
management.endpoint.health.show-details=always
```



## **MongoHealthIndicator**

用于检查MongoDB的连接状态。



```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
management.endpoint.health.show-details=always
```



## **Custom Health Indicator**

可以创建自定义的Health Indicator。为此，你需要实现`HealthIndicator`接口，并在自定义健康检查的逻辑中返回适当的`Health`对象。



```java
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
@Component
public class CustomHealthIndicator implements HealthIndicator {
   @Override
   public Health health() {
       // 实现自定义的健康检查逻辑
       if (isCustomServiceUp()) {
           return Health.up().withDetail("CustomService", "Up and running").build();
       } else {
           return Health.down().withDetail("CustomService", "Not available").build();
       }
   }
   private boolean isCustomServiceUp() {
       // 实现自定义服务的检查逻辑
       return true;
   }
}
```







