# 在centos8下安装docker

## 前言
从 2017 年 3 月开始 docker 在原来的基础上分为两个分支版本: Docker CE 和 Docker EE。
Docker CE 即社区免费版，Docker EE 即企业版，强调安全，但需付费使用。



## 1，安装依赖
```bash
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
```

## 2，设置stable镜像仓库
```bash
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```

## 3，安装docker
```bash
sudo yum install docker-ce docker-ce-cli containerd.io
```


* 如果提示：Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?
原因可能是上一次没有正常退出docker，所以docker没有正常启动，在相应的/var/run/路径下找不到docker进程，执行下面命令。

```bash
sudo service docker restart
```

* 如果报错：Problem: package docker-ce-3:19.03.4-3.el7.x86_64 requires containerd.io >= 1.2.2-3 那就先装新版的 containerd.io，执行下面命令。
```bash
dnf install https://download.docker.com/linux/centos/7/x86_64/stable/Packages/containerd.io-1.2.6-3.3.el7.x86_64.rpm
```

https://download.docker.com/linux/centos/7/x86_64/stable/Packages/

再次执行下面安装的命令：
```bash
sudo yum install docker-ce docker-ce-cli
sudo systemctl start docker
docker --version
```


* 如果国外的Docker镜像很慢的话，则可以基于阿里云镜像来安装Docker。  
```bash
yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
dnf install https://mirrors.aliyun.com/docker-ce/linux/centos/7/x86_64/stable/Packages/containerd.io-1.2.13-3.1.el7.x86_64.rpm
dnf install docker-ce
```

注意，下面给出的第二条命令，实际下载时，最好去检查一下是否有更新。

查看指定版本  
```bash
yum list docker-ce.x86_64  --showduplicates | sort -r    #从高到低列出Docker-ce的版本
```

## 4，开机启动
```bash
sudo systemctl enable docker
```

## 5，docker命令
```bash
# 查看docker的信息
docker info 
# 搜索名为img_name的镜像
docker search img_name 
# 将名为img_name的镜像下载到本地
docker pull img_name 
# 查看本地已有的镜像
docker images 
# 删除名为img_name的镜像
docker rmi img_name 
# 列出正在运行的容器
docker ps 
# 列出所有的容器
docker ps -a 
# 使用img_name以交互模式在后台运行分配了伪终端的名为container_name的镜像
docker run -itd --name=container_name img_name 
# 通过容器名字或ID启动容器
docker start container_name/container_id 
# 通过容器名字或ID停止容器
docker stop container_name/container_id 
# 通过容器名字或ID重启容器
docker restart container_name/container_id 
# 通过容器名字或ID删除容器
docker rm container_name/container_id 
# 通过容器名字或ID进入容器
docker exec -it container_name/container_id /bin/bash 
# 查看docker日志
cat /var/log/docker
# 查询删除docker
sudo yum list  installed | grep docker
sudo yum -y remove x1 x2 x3
# 直接删除docker
sudo yum remove docker \
                  docker-io \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
# 清除镜像和容器文件
sudo rm -rf /var/lib/docker
# 重启加速配置文件
systemctl daemon-reload
# 重启docker后台服务
systemctl restart docker 

```

## 参考link
1，<https://blog.csdn.net/zhi1314/article/details/102928915>  
2，<https://blog.csdn.net/l1028386804/article/details/105480007/>  
3，<https://blog.csdn.net/weixin_43939128/article/details/105554773?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase>  
4，<https://www.cnblogs.com/caoweixiong/p/12186736.html>

## 目标主机
QINGCloud（主机：centos8）