# Linux系统中无网络状态下安装redis

## 解压redis压缩包
```shell
tar -zvxf redis-5.0.7.tar.gz
```

## 编译redis代码
```shell
make
```

## 安装
```shell
make PREFIX=/redis-5.0.7 install
```

## 设置redis.conf
```shell
daemonize设置为yes（守护进程）
```

## 启动redis
```shell
./redis-server& ../redis.conf
```