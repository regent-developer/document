# Springboot使用AOP统一结果封装

## 依赖
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

## 自定义注解（NoResult.java 使用此注解的method，将不会封装返回结果）

```java
/**
 * @interface自定义注解
 * @Target: 注解的作用目标  PARAMETER:方法参数   METHOD:方法 TYPE:类、接口
 *
 * @Retention：注解的保留位置  RUNTIME  种类型的Annotations将被JVM保留,
 *
 * 能在运行时被JVM或其他使用反射机制的代码所读取和使用
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoResult {
}


```

## Reponses返回码类

```java
public enum ResultCode {

    SUCCESS(0, "操作成功", ""),
    ERROR(1, "操作失败", "");

    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    ResultCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
```

## 通用返回类

```java
/**
 * 通用返回类
 *
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(ResultCode resultCode) {
        this(resultCode.getCode(), null, resultCode.getMessage(), resultCode.getDescription());
    }
    public BaseResponse(ResultCode resultCode, T data) {
        this(resultCode.getCode(), data, resultCode.getMessage(), resultCode.getDescription());
    }
}


```

## 切面实现
```java
@Slf4j
@Aspect
@Component
public class ResulyAspect {
    @Pointcut("execution(* com.demo.project.controller.*..*(..))")
    public void pointAspect() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     */
    @Around("pointAspect()")
    public Object doAfter(ProceedingJoinPoint joinPoint) throws Throwable {
        // 转换为method
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 包装结果
        return packageResult(joinPoint, method);
    }

    public Object packageResult(ProceedingJoinPoint joinPoint, Method method) throws Throwable {
        Class<?> returnType = method.getReturnType();
        Object result = joinPoint.proceed();
        // void不需要包装
        if (returnType.equals(void.class) || returnType.equals(Void.class)) {
            return result;
        }
        // 设置了不需要包装的接口
        NoResult noResult = method.getAnnotation(NoResult.class);
        if (noResult == null) {
            noResult = method.getDeclaringClass().getAnnotation(NoResult.class);
        }
        if (noResult != null) {
            return result;
        }
        // 非restful风格接口不需要包装
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        PatchMapping patchMapping = method.getAnnotation(PatchMapping.class);
        if (requestMapping != null || getMapping != null || postMapping != null || deleteMapping != null || putMapping != null || patchMapping != null) {
            if (result == null) {
                return new BaseResponse(ResultCode.ERROR);
            } else {
                if (result instanceof BaseResponse) {
                    BaseResponse baseResponse = (BaseResponse) result;
                    return baseResponse;
                } else {
                    return new BaseResponse(ResultCode.SUCCESS, result);
                }
            }
        } else {
            return result;
        }
    }
}


```