# 再centos8中安装mysql

## 卸载之前安装的mysql
yum remove -y mysql
find / -name mysql //找到残留的文件，再通过rm -rf去删除对应的文件

## 创建目录，用于存放mysql8
mkdir /usr/local/mysql8
cd /usr/local/mysql8

## 下载mysql安装包
wget https://dev.mysql.com/get/mysql80-community-release-el7-2.noarch.rpm

## 安装mysql
yum -y install mysql80-community-release-el7-2.noarch.rpm
yum -y install mysql-community-server

* 执行yum -y install mysql-community-server报错的解决方案
原因：centos8由于本地有MySQL模块，需先禁用自带MYSQL模块
yum module disable mysql
完成再执行yum -y install mysql-community-server

* yum -y install mysql-community-server下载完毕时安装报错的解决方案
原因：一般是由于源key错误导致的dnf或者yum安装软件失败。尝试添加--nogpgcheck后缀来忽略，重新执行安装
yum -y install mysql-community-server --nogpgcheck

## 启动数据库
systemctl start  mysqld.service

## 获取随机密码
grep "password" /var/log/mysqld.log

## 登录mysql
mysql -u root -p

## 修改密码
ALTER USER 'root'@'localhost' IDENTIFIED BY '新密码';

## 设置Mysql数据库可以远程连接
use mysql;
update user set host = '%' where user = 'root';
select host, user from user;
flush privileges;
