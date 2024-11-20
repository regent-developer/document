# Linux系统中无网络状态下安装nginx

## 安装openssl
### 解压压缩包&编译&安装命令
```shell
tar -zxvf openssl-3.0.3.tar.gz
cd openssl-3.0.3/
./config && make && make install
```

## 安装zlib
### 解压压缩包&编译&安装命令
```shell
tar -zxvf zlib-1.2.12.tar.gz
cd zlib-1.2.12/
./configure && make && make install
```

## 安装pcre
### 解压压缩包&编译&安装命令
```shell
tar -zxvf pcre2-10.40.tar.gz
cd pcre2-10.40/
./configure && make && make install
```

## 安装nginx
### 解压压缩包&编译&安装命令
```shell
tar -zxvf nginx-1.21.6.tar.gz
cd nginx-1.21.6/
./configure && make && make install
```

## 确认安装是否正确并启动
```shell
cd /usr/local/nginx/sbin
./nginx -t
./nginx
```