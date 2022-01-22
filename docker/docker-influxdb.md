# docker influxdb

## 获取influxdb镜像
```shell
docker pull influxdb:2.1.1
```

## 创建并启动influxdb容器
```shell
docker run -d --name influxdb --restart always -p 8086:8086 -v /usr/local/docker/influxdb/data:/var/lib/influxdb2 influxdb:2.1.1
```

|参数|作用|
|-|-|
|-d|启动后在后台运行，不打印日志|
|–name 容器名|给容器命名，方便管理，也可以不写此参数|
|–restart always|如果容器死掉，就自动将其拉起，也可以不写此参数|
|-v 宿主机路径:容器内路径|将容器内指定路径挂载出来到宿主机中，这里是把数据库本地存储的目录挂出来，保证容器销毁以后数据还在|
|-p 宿主机端口:容器内端口|将宿主机端口与容器内端口进行映射，influxdb默认的容器内端口是8086，容器外端口可以根据需要自己调整|

## 