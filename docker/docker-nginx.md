# 一，安装docker（nginx）镜像 
## 1，查找 Docker Hub 上的 nginx 镜像
```bash
docker search nginx
```
## 2.拉取官方的Nginx镜像
```bash
docker pull nginx
```
## 3.在本地镜像列表里查到 REPOSITORY 为 nginx 的镜像
```bash
docker images nginx
```
## 4.以下命令使用 NGINX 默认的配置来启动一个 Nginx 容器实例
```bash
docker run --rm --name nginx-test -p 8080:80 -d nginx
```
## 5.查看启动的docker容器
```bash
docker container ps
```


# 二，nginx服务部署,映射本地目录到nginx容器

## 1.创建本地目录，用于存放Nginx的相关文件信息.
```bash
mkdir -p /home/nginx/www /home/nginx/logs /home/nginx/conf

# www: 目录将映射为 nginx 容器配置的虚拟目录。
# logs: 目录将映射为 nginx 容器的日志目录。
# conf: 目录里的配置文件将映射为 nginx 容器的配置文件。
```

## 2.拷贝容器内 Nginx 默认配置文件到本地当前目录下的 conf 目录
```bash
docker cp xxxxID:/etc/nginx/nginx.conf /home/nginx/conf/
```

## 3.部署命令

```bash
docker run --rm -d -p 8181:80 --name nginx-docker \
  -v /home/nginx/www:/usr/share/nginx/html \
  -v /home/nginx/conf/nginx.conf:/etc/nginx/nginx.conf \
  -v /home/nginx/logs:/var/log/nginx \
  nginx


 # --rm：容器终止运行后，自动删除容器文件。
 # -p 8181:80： 将容器的 80 端口映射到主机的 8181 端口.
 # --name nginx-docker：将容器命名为 nginx-docker 
 # -v /home/nginx/www:/usr/share/nginx/html：将我们自己创建的 www 目录挂载到容器的 /usr/share/nginx/html。
 # -v /home/nginx/conf/nginx.conf:/etc/nginx/nginx.conf：将我们自己创建的 nginx.conf 挂载到容器的 /etc/nginx/nginx.conf。
 # -v /home/nginx/logs:/var/log/nginx：将我们自己创建的 logs 挂载到容器的 /var/log/nginx。
```