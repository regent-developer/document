# hadoop(docker)

## 环境搭建

### 下载 centos 镜像

```shell
docker pull java
```

### 镜像重命名

```shell
docker tag [image_id] hadoop:latest
docker rmi java
```

### 建立容器

```shell
docker run -it hadoop /bin/bash
```

### 安装 yum

```shell
apt-get update
apt-get install yum
```

### SSH 的安装以及配置（todo）

```shell
yum install openssh-server
yum install openssh-clients
```

### 安装 hadoop2.6

```shell
curl -o hadoop-2.6.tar.gz  http://archive.apache.org/dist/hadoop/common/hadoop-2.6.0/hadoop-2.6.0.tar.gz
```
