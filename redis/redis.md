# redis

## 配置redis

* cmd中cd到redis目录下，输入下面的命令

```shell
redis-server redis.windows.conf
```

* 输入下面命令注册为windows服务
```shell
redis-server --service-install redis.windows.conf
```

* 在windows服务中启动redis服务


## Redis常用的指令

* 卸载服务：redis-server --service-uninstall
* 开启服务：redis-server --service-start
* 停止服务：redis-server --service-stop

## 测试

命令行输入redis-cli


## geoadd

geoadd 用于存储指定的地理空间位置，可以将一个或多个经度(longitude)、纬度(latitude)、位置名称(member)添加到指定的 key 中 

GEOADD [key] [longitude] [latitude] [member]


## georadiusbymember


GEORADIUSBYMEMBER key member radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count] [ASC|DESC] [STORE key] [STOREDIST key]

## geopos

查找指定key的经纬度信息  

## geodist

返回两个地方的距离，可以指定单位，比如米m，千米km，英里mi，英尺ft

## georadius

根据给定的经纬度，返回半径不超过指定距离的元素  

## geohash

返回的是geohash值  



