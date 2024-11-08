# Spring AOP 和 MyBatis插件组合替代触发器

## 实现步骤
* 1.定义MyBatis拦截器：首先使用MyBatis插件来拦截SQL的执行，获取到SQL语句和参数。
* 2.定义Spring AOP的环绕通知：在AOP切面中，使用环绕通知拦截特定的Mapper方法。
* 3.在环绕通知中调用MyBatis拦截器：在SQL执行前通过MyBatis拦截器获取SQL和参数，在SQL执行成功后执行额外的业务逻辑。


## 代码实现

### 定义MyBatis拦截器获取SQL和参数

定义一个MyBatis拦截器，拦截并缓存最近执行的SQL和参数，这样可以在AOP通知中访问这些信息。
```java
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.RowBounds;
import java.sql.Connection;
import java.util.Properties;

@Intercepts({
    @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class SqlInterceptor implements Interceptor {
    
    // 定义一个线程安全的变量用于存储最近的SQL和参数
    private static final ThreadLocal<String> currentSql = new ThreadLocal<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取StatementHandler并解析SQL语句
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        String sql = statementHandler.getBoundSql().getSql();
        
        // 缓存SQL
        currentSql.set(sql);

        // 执行原方法
        return invocation.proceed();
    }

    public static String getCurrentSql() {
        return currentSql.get();
    }
    
    // 移除数据，防止内存溢出
    public static void removeSql() {
        currentSql.remove();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}
}

```

#### 说明
* 它是 MyBatis 插件，拦截 MyBatis 在生成并准备执行 SQL 语句时的行为。
* 调用 invocation.proceed() 是为了让 MyBatis 继续执行原本的逻辑，否则拦截器会阻止原方法的执行，导致 SQL 无法被正常执行。
* 此拦截器仅用于捕获 SQL 语句及其参数，不会实际改变 SQL 执行流程或执行 SQL。



### 配置拦截器
```xml
<plugins>
    <plugin interceptor="com.example.interceptor.SqlInterceptor"/>
</plugins>

```

### 定义Spring AOP的环绕通知
在Spring AOP切面中，使用环绕通知来拦截Mapper方法，获取SQL语句并在SQL执行后调用额外的业务逻辑。

```java
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SqlExecutionAopInterceptor {

    @Autowired
    private SomeOtherService someOtherService; // 依赖注入业务服务

    // 环绕通知拦截特定的Mapper方法
    @Around("execution(* com.example.mapper.revoke(..))")
    public Object aroundExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;

        try {
            System.out.println("开始执行SQL操作...");
            
            // 在SQL执行之前，通过拦截器获取SQL语句
            String sql = SqlInterceptor.getCurrentSql();
            SqlInterceptor.removeSql();
            System.out.println("即将执行的SQL语句：" + sql);

            // 执行Mapper方法，获取SQL执行结果
            result = joinPoint.proceed();

            // 检查SQL执行结果，执行后续业务逻辑
            if (result != null && result instanceof Integer && (Integer) result > 0) {
                System.out.println("SQL执行成功，执行额外操作...");
                
                // 调用业务方法，传入所需的SQL参数（根据需求从方法参数中获取）
                someOtherService.addRevokeCount(sql);
            } else {
                System.out.println("SQL未成功执行，未执行额外操作");
            }

        } catch (Exception e) {
            System.out.println("SQL执行过程中出现异常：" + e.getMessage());
            throw e; // 抛出异常，确保事务一致性
        }

        return result;
    }
}

```
#### 说明
* 它是 Spring AOP 切面，用于拦截特定的 Java 方法调用，例如 DAO 层的方法。
* 调用 joinPoint.proceed() 是为了继续执行被拦截的方法，如果不调用 proceed()，原方法逻辑将不会被执行。
* 它负责在方法执行前后添加逻辑，比如在执行后根据执行结果进行附加操作。


### 定义业务服务类
在业务服务SomeOtherService中，可以利用SQL中包含的参数来执行额外的更新逻辑。
```java
import org.springframework.stereotype.Service;

@Service
public class SomeOtherService {

    public void addRevokeCount(String sql) {
        // 解析SQL获取必要的参数（根据实际业务情况解析SQL中的参数）
       
        System.out.println("SQL信息：" + sql);
        
        // 在这里调用Mapper方法，执行更新操作
    }
}

```

### 总结
* MyBatis拦截器用于捕获即将执行的SQL，将SQL语句暂存至ThreadLocal中。
* 环绕通知在SQL执行前后分别获取SQL和执行结果，确保在SQL执行成功后执行附加业务逻辑。
* 业务方法调用中可以使用SQL语句的内容来执行特定的操作。

这样可以确保每次SQL执行前后在Spring AOP和MyBatis拦截器的配合下获取并使用SQL语句，完成业务需求。

### 补充
SqlInterceptor 和 SqlExecutionAopInterceptor 都是拦截器，但它们作用在不同层次上：

* SqlInterceptor 仅拦截 MyBatis 内部的 SQL 执行操作，不涉及具体的 Java 方法调用。
* SqlExecutionAopInterceptor 仅拦截由 Spring 管理的 DAO 层方法调用，与 MyBatis 拦截器是独立的，执行 joinPoint.proceed() 是为了确保 DAO 方法继续执行。

两者结合使用时：

SqlExecutionAopInterceptor 拦截方法时会调用 joinPoint.proceed() 执行 DAO 方法，这会触发 MyBatis 执行 SQL。
SqlInterceptor 在 MyBatis 执行 SQL 时捕获 SQL 语句并保存。
最终，SQL 只会被执行一次，不会重复执行。


