# SpringSecurity获取当前登录用户的信息

## 直接注入 Principal

```java
@GetMapping("/welcome")
@ResponseBody
public Object toLoginInfo(Principal principal){
    return principal;
}
```
* 当你在 Controller 方法中直接声明一个 Principal 类型的参数时，Spring Security 会自动注入当前登录用户对应的 Principal 对象。通常，Principal 只包含最基本的用户标识（例如用户名），但如果需要更多信息，就需要使用 Authentication。


## 直接注入 Authentication
```java
@GetMapping("/welcome2")
@ResponseBody
public Object toLoginInfo2(Authentication authentication) {
    return authentication;
}
```
* Authentication 接口继承自 Principal，除了包含用户名外，还包含了用户的权限信息（Authorities）、认证凭证（Credentials）、认证状态等。直接注入 Authentication 可以获取更丰富的信息，是实际开发中常用的方式之一。

## 注入 UsernamePasswordAuthenticationToken
```java
@GetMapping("/welcome3")
@ResponseBody
public Object toLoginInfo3(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    return usernamePasswordAuthenticationToken;
}
```
* UsernamePasswordAuthenticationToken 是 Authentication 的一个常见实现，通常在基于表单登录时使用。它除了保存用户名和密码，还保存了用户权限等信息。直接注入这种类型的参数，与直接注入 Authentication 类似，只是类型更加具体


## 通过 SecurityContextHolder 获取
```java
@GetMapping("/welcome4")
@ResponseBody
public Object toLoginInfo4() {
    return SecurityContextHolder.getContext().getAuthentication();
}
```
* 从静态的 SecurityContextHolder 中获取当前线程绑定的 SecurityContext，再从中取出 Authentication 对象。使用这种方式可以在非 Controller 的地方（例如在业务逻辑或工具类中）获取当前登录用户的信息。

