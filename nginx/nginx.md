# centos下安装nginx

## 安装各种依赖

```bash
#gcc安装，nginx源码编译需要
yum install gcc-c++

#PCRE pcre-devel 安装，nginx 的 http 模块使用 pcre 来解析正则表达式
yum install -y pcre pcre-devel

#zlib安装，nginx 使用zlib对http包的内容进行gzip
yum install -y zlib zlib-devel

#OpenSSL 安装，强大的安全套接字层密码库，nginx 不仅支持 http 协议，还支持 https（即在ssl协议上传输http）
yum install -y openssl openssl-devel
```

## 安装wget
```bash
yum install wget
```
## 安装make
```bash
yum install make -y
```

## 下载nginx
```bash
wget -c https://nginx.org/download/nginx-1.18.0.tar.gz
```

## 安装nginx

```bash
#根目录使用ls命令可以看到下载的nginx压缩包，然后解压
tar -zxvf nginx-1.18.0.tar.gz

#解压后进入目录
cd nginx-1.18.0

#使用默认配置
./configure

#编译安装
make
make install

```

## 启动 停止 
```bash
#查找安装路径，默认都是这个路径
whereis nginx

#启动、停止nginx
cd /usr/local/nginx/sbin/

./nginx     #启动
./nginx -s stop  #停止，直接查找nginx进程id再使用kill命令强制杀掉进程
./nginx -s quit  #退出停止，等待nginx进程处理完任务再进行停止
./nginx -s reload  #重新加载配置文件，修改nginx.conf后使用该命令，新配置即可生效

#重启nginx，建议先停止，再启动
./nginx -s stop
./nginx


#查看nginx进程
ps aux|grep nginx
```

# 开机自启动
```bash
#在rc.local增加启动代码即可
vi /etc/rc.local
#增加一行 /usr/local/nginx/sbin/nginx，增加后保存
#设置执行权限
cd /etc
chmod 755 rc.local
```

# 配置域名映射
```bash
#进入nginx配置文件目录，找到nginx的配置文件nginx.conf
cd /usr/local/nginx/conf/

#直接修改
vi nginx.conf

#修改：server_name  域名;
#修改：proxy_pass http://localhost:8080;（location）
```