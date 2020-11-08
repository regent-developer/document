# Go安装OpenCV库

gocv是OpenCV4在Go中实现。

## 安装gocv

```shell
go get -u -d gocv.io/x/gocv
```

安装到%GOPATH%\src下。

## 编译OpenCV

需要MinGW-W64和CMake

### 安装MinGW-W64
https://sourceforge.net/projects/mingw-w64/files/mingw-w64/

追加环境变量。


### 安装CMake
https://cmake.org/download/

### 编译OpenCV

```shell
cd %GOPATH%\src\gocv.io\x\gocv
win_build_opencv.cmd
```

编译好后，添加C:\opencv\build\install\x64\mingw\bin到环境变量。

## 测试
```shell
cd %GOPATH%\src\gocv.io\x\gocv
go run cmd\version\main.go
```


