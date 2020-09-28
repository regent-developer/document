# go mod

go Modules—Modules是Go 1.11中新增的实验性功能，是一种新型的包管理工具。  

## go升级到1.11

```
export GO111MODULE=on //此时go会使用modules，而不会去GOPATH目录下查找。
```

GO111MODULE=auto(默认)：go命令行将会根据当前目录来决定是否启用module功能，当前目录在GOPATH/src之外且该目录包含go.mod文件，或者当前文件在包含go.mod文件的目录下面。  

GO111MODULE=off：go命令行将不会支持module功能，寻找依赖包的方式将会沿用旧版本那种通过vendor目录或者GOPATH模式来查找。  

O111MODULE=on：GOPATH在项目构建过程中不再担当import的角色，但它仍然存储下载的依赖包，具体位置在$GOPATH/pkg/mod。  

## 常用命令
```
go mod init //初始化mod
```

```
go mod tidy //拉取缺少的模块，移除不用的模块。
```

```
go mod download //下载依赖包
```

```
go mod vendor //将依赖复制到vendor下
```

```
go mod verify //校验依赖
```

```
go list -m -json all //依赖详情
```

```
go mod graph //打印模块依赖图
```
 
```
//有一些包被墙了，拉不到，可以使用如下命令  
export GOPROXY=https://goproxy.cn
or
export GOPROXY=https://goproxy.io
or
export GOPROXY=https://mirrors.aliyun.com/goproxy
```