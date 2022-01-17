# docker redis

## 获取redis镜像
```shell
docker pull redis
```

## 查看本地镜像
```shell
docker images
```

## redis.conf文件下载

http://download.redis.io/redis-stable/redis.conf

## redis.conf相关配置

* bind 127.0.0.1 #注释掉这部分，这是限制redis只能本地访问
* protected-mode no #默认yes，开启保护模式，限制为本地访问
* daemonize no#默认no，改为yes意为以守护进程方式启动，可后台运行，除非kill进程，改为yes会使配置文件方式启动redis失败
* databases 16 #数据库个数（可选）。
* dir  ./ #输入本地redis数据库存放文件夹（可选）
* appendonly yes #redis持久化（可选）
* requirepass  密码 #配置redis访问密码

## 创建并启动redis容器
```shell
docker run -p 6379:6379 --name redis -v /usr/local/docker/redis/redis.conf:/etc/redis/redis.conf -v /usr/local/docker/redis/data:/data -d redis redis-server /etc/redis/redis.conf --appendonly yes
```

## 查看redis容器
```shell
docker container ls -a
```

## 查看运行的容器
```shell
docker ps
```

## 通过 redis-cli 连接测试使用 redis 服务
```shell
docker exec -it redis /bin/bash

redis-cli
```

## 查看日志
```shell
docker logs -f -t --tail 100 redis
```

## 报错：Error response from daemon: Conflict. The container name “***” is already in use
```shell
docker container rm ***
```

## 报错：docker: Error response from daemon: cannot start a stopped process: unknown.
```shell
yum install -y libseccomp-devel
```