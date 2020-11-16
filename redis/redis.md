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

