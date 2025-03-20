# SpringSecurity获取当前登录用户的信息

## 直接注入 Principal

```java
@GetMapping("/welcome")
@ResponseBody
public Object toLoginInfo(Principal principal){
    return principal;
}
```
## 直接注入 Authentication
```java
@GetMapping("/welcome2")
@ResponseBody
public Object toLoginInfo2(Authentication authentication) {
    return authentication;
}
```
## 注入 UsernamePasswordAuthenticationToken
```java
@GetMapping("/welcome3")
@ResponseBody
public Object toLoginInfo3(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    return usernamePasswordAuthenticationToken;
}
```
## 通过 SecurityContextHolder 获取
```java
@GetMapping("/welcome4")
@ResponseBody
public Object toLoginInfo4() {
    return SecurityContextHolder.getContext().getAuthentication();
}
```

https://blog.csdn.net/gege_0606/article/details/146327967?spm=1001.2100.3001.7377&utm_medium=distribute.pc_feed_blog.none-task-blog-hot-11-146327967-null-null.nonecase&depth_1-utm_source=distribute.pc_feed_blog.none-task-blog-hot-11-146327967-null-null.nonecase