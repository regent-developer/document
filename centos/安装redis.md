# centos8中安装redis

## yum安装redis
 yum install redis

## 启动redis服务
service redis start

## 查看redis服务
service redis status

## 设置开机启动
systemctl enable redis

## 配置环境变量
修改 /etc/redis.conf 文件
### 检查是否有[port 6379]，如没有添加

### 安装成功后有可能会导致远程连接不上的解决方案
* protected-mode yes -> protected-mode no（在没有密码的情况下，关闭保护模式）
* 注释掉bind 127.0.0.1     （取消绑定本地地址）
* daemonize no -> daemonize yes   （是否为进程守护，关闭ssh窗口后即是否在后台继续运行）