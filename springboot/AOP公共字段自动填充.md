# AOP公共字段自动填充

## 场景

在开发中，我们经常需要在实体类中添加一些公共字段，比如创建时间、创建人、更新时间、更新人等，这些字段在新增和修改数据时需要自动填充，如果手动填充，代码会显得冗余，我们可以使用AOP来实现自动填充。

## 实现思路
* 自定义注解 AutoFill，用于标识需要进行公共字段自动填充的方法
* 自定义切面类 AutoFillAspect，统一拦截加入了 AutoFill 注解的方法，通过反射为公共字段赋值
* 在 Mapper 的方法上加入 AutoFill 注解

## 实现

### 自定义注解
```java
/**
 * 自定义注解，用于标识某个方法需要进行功能字段自动填充处理
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //数据库操作类型：UPDATE INSERT
    OperationType value();
}
```

```java
/**
 * 数据库操作类型
 */
public enum OperationType {
 
    /**
     * 更新操作
     */
    UPDATE,
 
    /**
     * 插入操作
     */
    INSERT
}
```
#### 说明
##### 自定义注解 @AutoFill
* 注解作用：标记在某个方法上，用来自动填充一些特定的功能字段，类似于插入或更新操作时自动填充创建时间、修改时间等字段。
* 注解属性：这个注解有一个属性 value，它表示当前方法执行时是进行什么类型的数据库操作（如 INSERT 或 UPDATE），具体通过 OperationType 枚举值来指定。
* 注解目标 @Target(ElementType.METHOD)：表示这个注解只能用于方法上。
* 注解保留策略 @Retention(RetentionPolicy.RUNTIME)：表示这个注解将在运行时保留，也就是说你可以通过反射获取这个注解，并在程序运行时处理它。


##### 枚举类 OperationType
* 作用：用于定义数据库操作的类型，包括 UPDATE 和 INSERT。
* 枚举常量：
UPDATE：表示更新操作（例如修改数据库中的某些记录时）。
INSERT：表示插入操作（例如向数据库中添加新的记录时）。

### 自定义切面类
自定义切面类 AutoFillAspect，统一拦截加入了 AutoFill 注解的方法，通过反射为公共字段赋值。

```java
/**
 * 自定义切面类，用于拦截带有 @AutoFill 注解的方法，进行公共字段（如创建时间、创建人、更新时间、更新人）的自动填充
 */
@Aspect // 表示这是一个切面类，专门用于处理某些特定操作的拦截和逻辑执行
@Component // 将该类声明为 Spring 容器中的一个组件，便于自动扫描和管理
@Slf4j // 用于记录日志的注解，简化日志记录操作
public class AutoFillAspect {
 
    /**
     * 切入点的定义：拦截 com.xxx.mapper 包下的所有方法，并且这些方法必须带有 @AutoFill 注解。
     * 也就是说，只有那些需要自动填充公共字段的方法才会被拦截。
     */
    @Pointcut("execution(* com.xxx.mapper.*.*(..)) && @annotation(com.xxx.annotation.AutoFill)")
    public void autoFillPointCut(){}
 
    /**
     * 前置通知：在目标方法执行之前进行操作，即在方法被实际执行前，先执行这个逻辑。
     * 在这里，我们会在方法执行前为其自动填充公共字段（如时间和用户 ID）。
     * 
     * @param joinPoint 这是连接点，表示当前拦截到的方法的上下文信息，如方法名、参数等。
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始进行公共字段自动填充...");
 
        // 1. 获取当前被拦截的方法上的数据库操作类型（是插入 INSERT 还是更新 UPDATE）
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 获取方法签名，用于获取方法的详细信息
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); // 获取当前方法上的 @AutoFill 注解
        OperationType operationType = autoFill.value(); // 从注解中获取数据库操作类型（INSERT 或 UPDATE）
 
        // 2. 获取当前拦截到的方法的参数列表，通常第一个参数是实体对象（即要保存或更新的数据）
        Object[] args = joinPoint.getArgs(); // 获取方法的参数
        if(args == null || args.length == 0){
            return; // 如果方法没有参数，直接返回，不做任何处理
        }
 
        Object entity = args[0]; // 获取第一个参数，即实体对象。通常数据库操作的第一个参数就是要处理的数据对象
 
        // 3. 准备要填充的公共字段数据：当前时间和当前操作用户的 ID
        LocalDateTime now = LocalDateTime.now(); // 获取当前的时间，用于填充创建时间和更新时间
        Long currentId = BaseContext.getCurrentId(); // 获取当前操作用户的 ID，从 ThreadLocal 中获取到当前用户的上下文信息
 
        // 4. 根据不同的数据库操作类型，选择填充对应的字段
        if(operationType == OperationType.INSERT){
            // 如果是插入操作（INSERT），需要填充四个公共字段：创建时间、创建人、更新时间、更新人
            try {
                // 通过反射获取实体类中的 set 方法，用于动态设置字段值
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class); 
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
 
                // 通过反射调用实体对象的这些 set 方法，为其字段赋值
                setCreateTime.invoke(entity, now); // 为创建时间字段赋值当前时间
                setCreateUser.invoke(entity, currentId); // 为创建人字段赋值当前用户的 ID
                setUpdateTime.invoke(entity, now); // 为更新时间字段赋值当前时间
                setUpdateUser.invoke(entity, currentId); // 为更新人字段赋值当前用户的 ID
            } catch (Exception e) {
                // 如果反射过程中出现异常，打印异常堆栈信息，便于排查问题
                e.printStackTrace();
            }
        } else if(operationType == OperationType.UPDATE){
            // 如果是更新操作（UPDATE），只需要填充两个字段：更新时间、更新人
            try {
                // 通过反射获取实体类中的 set 方法，用于动态设置字段值
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
 
                // 通过反射调用实体对象的这些 set 方法，为其字段赋值
                setUpdateTime.invoke(entity, now); // 为更新时间字段赋值当前时间
                setUpdateUser.invoke(entity, currentId); // 为更新人字段赋值当前用户的 ID
            } catch (Exception e) {
                // 如果反射过程中出现异常，打印异常堆栈信息，便于排查问题
                e.printStackTrace();
            }
        }
    }
}
```

### 方法上加自定义注解
```java
@Mapper
public interface Mapper {
    /**
     * 插入数据
     * @param 
     */
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)" +
            " VALUES" +
            " (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);
}
```
