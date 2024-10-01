# @PathVariable

## 简介
@PathVariable是Spring MVC中的一个非常重要的注解，作用在于将URL中的模板变量（即路径变量）绑定到控制器方法的参数上。这一功能特别适用于处理RESTful风格的请求，使得开发者能够直接从URL中提取参数值，并将其传递给方法进行处理。通过使用@PathVariable注解，可以设计出更加灵活和动态的URL映射，同时简化参数传递的过程，提高代码的可读性和可维护性。

## 使用示例

### 基本使用
```java
// 为使映射正确工作，捕获 URI 变量 {id} 的名称必须与 @PathVariable 成员参数 String id 相同。
@GetMapping("/{id}")
public Object id(@PathVariable Long id) {
  return id ;
}
```

### 不同参数名
```java

@GetMapping("/{id}")
public Object id(@PathVariable("id") Long key) {
  return key ;
}
```

### 类级别的路径变量
```java
@RestController
@RequestMapping("/pv/{type}")
public class PathVariableController {

  @GetMapping("/{id}")
  public Object id(@PathVariable Integer type, @PathVariable("id") Long key) {
    return type + "@" +key ;
  }
}
```

### 多个URI变量
```java
@GetMapping("/{cate}/{id}")
public Object category(@PathVariable String cate, @PathVariable Long id) {
  return cate + "@" + id ;
}
```

### Map接收路径变量
```java
@GetMapping("/api/{tag}/query/{name}")
public String getByTagAndName(@PathVariable Map<String, String> paths) {
  String tag = paths.get("tag");
  String name = paths.get("name");
  return tag + "@" + name ;
}
```

### 正则路径变量
```java
@GetMapping("/vk/api/{name:[a-z]+}")
public String getJarFile(@PathVariable String name) {
  return name ;
}
```

### 可选的路径变量
```java
// 默认情况下@PathVariable路径变量是必须，否则服务端将MethodArgumentTypeMismatchException异常。我们除了可以通过设置PathVariable注解的required属性为false外，还可以通过Optional类型接收值

@GetMapping({"/users/{id}", "/users/"})
public Object byId(@PathVariable Optional<Long> id) {
  return id.orElseGet(() -> -1L) ;
}
```

### 路径后缀
```java
@GetMapping("/ext/api/{file:.+}")
public Object fileExt(@PathVariable String file) {
  return new R(file) ;
}
```

### 在非Controller中取得路径变量
```java
// 通常请求的路径变量是在Controller层被捕获并处理的。如果你希望在Service层或其他非Controller组件中获取这些路径变量，而不是通过参数传递的方式
private HttpServletRequest request ;
public void uriVar() {
  Map<String, String> vars = (Map<String, String>)request
    .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) ;
}
```
