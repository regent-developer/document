# HttpServletRequest、HttpServletResponse获取方式

## 封装为静态方法
```java
ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
HttpServletRequest request = servletRequestAttributes.getRequest();
HttpServletResponse response = servletRequestAttributes.getResponse();

```


## controller方法
```java
@GetMapping(value = "")
public String doSomething(HttpServletRequest request,HttpServletResponse response) {
    //...
}

```


## 直接注入
```java
@Autowired
private HttpServletRequest request;
@Autowired
private HttpServletResponse response;

```

