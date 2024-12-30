package main

import (
	"github.com/SimonWang00/goeureka"
	"github.com/gin-gonic/gin"
	"wbGo/controller"
	"wbGo/task"
)

func main() {
	//注册到 Eureka
	registerToEurekaLocal()
	registerToEureka()
	//下面是请求方法
	router := gin.Default()

	v1 := router.Group("/costing")
	{
		v1.POST("/select", controller.CostingSelect)
	}

	//调用定时任务
	task.StartsTask()

	router.Run("0.0.0.0:8000")
}

// 本地
func registerToEurekaLocal() {
	// 实现注册到 Eureka 的逻辑,30秒一次心跳
	//fmt.Println("注册到 Eureka")
	opt := make(map[string]string)
	goeureka.RegisterClient("http://127.0.0.1:6868", "", "myGoApp", "8000", "43", opt)
}

// 线上
func registerToEureka() {
	// 实现注册到 Eureka 的逻辑,30秒一次心跳
	//fmt.Println("注册到 Eureka")
	opt := make(map[string]string)
	goeureka.RegisterClient("http://192.168.0.1:6868", "", "myGoApp", "8000", "43", opt)
}

