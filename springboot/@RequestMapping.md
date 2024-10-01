# @RequestMapping

## 简介
@RequestMapping是Spring MVC中用于处理请求地址映射的注解，它可以将HTTP请求映射到相应的处理器类或处理器方法上。通过指定请求路径、请求方法（如GET、POST）等属性，@RequestMapping使得开发者能够灵活地定义请求的路由规则。@RequestMapping可以应用在类或方法级别，类级别的注解用于限定该类中所有方法的请求基础路径，而方法级别的注解则具体定义请求的映射细节。

## 使用示例

### 定义路径
```java
// 单个路径
@RequestMapping(value = "/api/path")
public String path() {
  return "define path" ;
}

// 路径数组
@RequestMapping(value = {"/api/path1", "/api/path2"}, method = RequestMethod.GET)
public String path() {
  return "define path" ;
}
```

### 设置请求方法
```java
// 请求方法：GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
@RequestMapping(value = "/api/method", method = RequestMethod.POST)
public String method() {
  return "request method" ;
}
```

### 设置请求Header
```java
@RequestMapping(value = "/api/header", headers = "x-pack=xxxooo")
public String header() {
  return "define Header" ;
}

// 多个header
@RequestMapping(
  value = "/api/header", 
  headers = {
    "x-pack=xxxooo",
    "x-key=aabbcc"
  }
)
public String header() {
    
}
```


### 指定produces
```java
// 通过指定produces，来限制请求的Accept请求header必须是指定的类型
@GetMapping(value = "/api/produces", produces = "application/json")
public String produces() {
  return "define produces";
}

```

### 指定consumes
```java
// 通过指定consumes，来限制请求的Content-Type请求header必须是指定的类型
@GetMapping(value = "/api/consumes", consumes = {"text/plain"})
public String consumes() {
  return "define consumes";
}

```

### 使用路径变量
```java
@RequestMapping(value = "/api/var/{id}")
public String pathVar(
  @PathVariable("id") long id) {
  return String.format("id = %d", id);
}

```
### 设置请求参数
```java
@RequestMapping(value = "/api/params", params = "id")
public String params(Long id) {
  return "params id =" + id ;
}

// 多个参数

@RequestMapping(value = "/api/params", params = {"id", "name"})
public String params(Long id) {
    
}

```
### 接口回退Fallback
```java
// 当没有具体的URI被匹配时，它会用来处理请求。
@RequestMapping(value = "*")
public String fallback() {
  return "Fallback for GET Requests";
}

```

### 方法可以私有private
```java
// Controller中的请求方法可以使用private声明，内部是通过反射调用
@RequestMapping(value = "/api/params", params = "id")
private String params(Long id) {
  return "params id =" + id ;
}
```