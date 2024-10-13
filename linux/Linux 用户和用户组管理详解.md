# Linux 用户和用户组管理详解

* 用户（user）：每个登录 Linux 系统的用户都有一个独立的身份。用户不仅可以操作自己的文件，还能基于权限访问其他资源。

* 用户组（group）：用户组是多个用户的集合，组内用户拥有相同的权限。例如，你可以为多个用户分配同一组以便共享某些资源。

## 添加和删除用户

### 添加用户
```shell
adduser username  # 创建新用户
# 该命令会自动为用户创建主目录，并生成一些初始化文件
```

### 删除用户
```shell
userdel username  # 删除用户

# 使用 userdel 仅删除用户，用户的主目录和文件仍会保留。若要删除用户并同时删除其主目录，使用以下命令：
userdel -r username  # 删除用户及其主目录

```

## 用户组管理

### 添加用户组

```shell
groupadd groupname  # 创建新用户组

```

### 删除用户组
```shell
groupdel groupname  # 删除用户组

```

### 将用户加入用户组
```shell
useradd -g groupname username

```

## 禁用和启用用户


### 临时禁用用户
```shell
passwd username -l  # 锁定用户

```

### 恢复用户
```shell
passwd username -u  # 解锁用户

```

## 显示用户和组信息
```shell
id username  # 显示用户ID、组ID及所属组
cat /etc/passwd  # 查看用户信息
cat /etc/group  # 查看组信息

```

## 高级用户管理

### 批量删除组内用户
```shell
gpasswd -d username groupname  # 将用户从组中删除

```

### 配置 sudo 权限
```shell
# 要允许某组用户无密码执行 sudo 命令，可以在 /etc/sudoers.d/ 目录下创建文件，内容如下：
%groupname ALL=(ALL) NOPASSWD: ALL

```

## 用户和用户组的配置文件
* /etc/passwd：存放系统中所有用户的基本信息。
* /etc/shadow：存储用户的加密密码及密码过期时间等信息。
* /etc/group：存储用户组信息。
* /etc/gshadow：存储用户组的加密信息。

## 用户的 UID 和 GID
* UID：用户 ID，用于标识系统中的用户，通常从 1000 开始分配给普通用户。
* GID：组 ID，用于标识用户所属的主组。默认情况下，用户的 GID 与其 UID 相同。

```shell
# 查看 UID 和 GID
id username

# 手动指定 UID 或 GID
useradd -u 1500 -g 1500 username  # 指定 UID 和 GID
```

## 用户密码过期设置
```shell
chage -l username  # 查看用户密码的过期信息


chage -M 90 username  # 设置密码有效期为 90 天
chage -E "2024-12-31" username  # 设置用户账号到期时间
```
## 创建系统用户
```shell
adduser -r systemuser

```

## 限制用户的资源使用
```shell
# 修改 limits.conf
username soft nproc 1000  # 限制最大进程数为 1000
username hard nproc 2000  # 硬性限制最大进程数为 2000
username soft nofile 1024  # 限制最大文件描述符数

```

## 用户登录时间限制
```shell
# 如果需要限制某个用户只能在特定时间内登录系统，可以通过 pam_time.so 模块来实现，配置 /etc/security/time.conf 文件：
login ; * ; username ; Al0800-1800  # 限制用户只能在早上 8 点到晚上 6 点之间登录

```