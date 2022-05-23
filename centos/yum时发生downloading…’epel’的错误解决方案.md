# CentOS 8安装软件出现Errors during downloading…’epel’ Status code: 404的解决方案

* 原因：第三方的镜像站中均已移除CentOS 8的源，Centos 8版本已停止更新相应依赖导致的。

## 备份之前的repo文件
```shell
mv /etc/yum.repos.d /etc/yum.repos.d.bak
```

## 创建源文件目录
```shell
mkdir -p /etc/yum.repos.d
```

## 下载新的yum源
```shell
curl https://mirrors.aliyun.com/repo/Centos-vault-8.5.2111.repo > /etc/yum.repos.d/Centos-vault-8.5.2111.repo
curl https://mirrors.aliyun.com/repo/epel-archive-8.repo > /etc/yum.repos.d/epel-archive-8.repo
```

