# golang echo

## 简介
echo web框架是go语言开发的一种高性能，可扩展，轻量级的web框架。  
echo框架默认其实只包含了MVC框架的C部分，就是负责url路由和控制器部分。至于V视图部分和M数据操作部分我们可以随意使用自己喜欢的工具库来操作。

## 安装
```go
go get github.com/labstack/echo/...
```

## 简单实例代码
```go
package main

import (
	"net/http"
	//导入echo包
	"github.com/labstack/echo"
)

func main() {
    //实例化echo对象。
	e := echo.New()
	
	//注册一个Get请求, 路由地址为: /test  并且绑定一个控制器函数, 这里使用的是闭包函数。 
	e.GET("/test", func(c echo.Context) error {
	    //控制器函数直接返回一个字符串，http响应状态为http.StatusOK，就是200状态。
		return c.String(http.StatusOK, "OK")
	})
	
	//启动http server, 并监听8080端口
    e.Start(":8080")
}
```

## 路由&控制器

### 路由
```go
//定义post请求, url为：/users, 绑定saveUser控制器函数
e.POST("/users", saveUser)

//定义get请求，url模式为：/users/:id  （:id是参数，例如: /users/10, 会匹配这个url模式），绑定getUser控制器函数
e.GET("/users/:id", getUser)

//定义put请求
e.PUT("/users/:id", updateUser)

//定义delete请求
e.DELETE("/users/:id", deleteUser)
```

### 控制器
```go
//控制器函数只接受一个echo.Context上下文参数
//参数：c 是上下文参数，关联了当前请求和响应，通过c参数我们可以获取请求参数，向客户端响应结果。
func HandlerFunc(c echo.Context) error

c.Param()
c.QueryParam()
```