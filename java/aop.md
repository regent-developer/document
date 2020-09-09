# AOP
## AOP-log

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
    String value() default "";
}
```

```java
@Aspect
@Component
public class AnnotationAop {

    @Pointcut(value = "@annotation(log)", argNames = "log")
    public void pointcut(Log log) {
    }

    // around(环绕通知)： 在方法执行前和执行后都会执行
    @Around(value = "pointcut(log)", argNames = "joinPoint,log")
    public Object around(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        try {
            System.out.println(log.value());
            System.out.println("around");
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            System.out.println("around");
        }
    }

    // before(前置通知)： 在方法开始执行前执行
    @Before("@annotation(com.demo.annotation.Log)")
    public void before(JoinPoint joinPoint) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Log log = method.getAnnotation(Log.class);
            System.out.println("注解式拦截 " + log.value());
    }

    // after(后置通知)： 在方法执行后执行
    @After("pointcut()")
    public void commit() {
        System.out.println("after commit");
    }

    // afterReturning(返回后通知)： 在方法返回后执行
    @AfterReturning("pointcut()", returning = "returnObject")
    public void afterReturning(JoinPoint joinPoint, Object returnObject) {
        // 输出Log
        System.out.println("afterReturning");
    }

    // afterThrowing(异常通知)： 在抛出异常时执行
    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        // 输出Log
        System.out.println("afterThrowing afterThrowing  rollback");
    }
}
```

### 执行顺序：around > before > around > after > afterReturning