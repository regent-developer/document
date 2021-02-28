# docker-compose

## 安装

```shell
curl -L https://github.com/docker/compose/releases/download/1.25.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
curl -L https://github.com/docker/compose/releases/download/1.25.0/docker-compose-Linux-x86_64 -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
```

## 查看版本

```shell
docker-compose --version
```

## 常用命令

- docker-compose -h # 查看帮助
- docker-compose up # 创建并运行所有容器
- docker-compose up -d # 创建并后台运行所有容器
- docker-compose -f docker-compose.yml up -d # 指定模板
- docker-compose down # 停止并删除容器、网络、卷、镜像。
- docker-compose logs # 查看容器输出日志
- docker-compose pull # 拉取依赖镜像
- dokcer-compose config # 检查配置
- dokcer-compose config -q # 检查配置，有问题才有输出
- docker-compose restart # 重启服务
- docker-compose start # 启动服务
- docker-compose stop # 停止服务
