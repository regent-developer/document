# docker mysql

## 获取mysql镜像
```shell
docker pull mysql
```

## 创建并启动mysql容器
```shell
 docker run -d -p 3306:3306 -v /usr/local/docker/mysql/conf:/etc/mysql/conf.d -v /usr/local/docker/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 --name mysql mysql
```