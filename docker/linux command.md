# linux命令
## 查看CentOS版本方法
```bash
rpm -q centos-release
// 这个命令适用于所有的linux，包括Redhat、SuSE、Debian、Centos等发行版

uname
uname -r
uname -a


cat /etc/redhat-release

cat /proc/version

cat /etc/issue

```

## 后台执行

```bash
nohup [命令] > nohup.log 2>&1 &
```


## 实时刷新查看日志

```bash
tail -f nohup.log
```

## 环境变量
### export var=...

直接在 bash 中执行 export 命令, 这种方法在当前 shell 中临时设置环境变量, 当 shell 退出后环境变量失效

```bash

```

### 修改文件 /etc/environment

Linux 系统加载时生效

一般不需要动, 如果改了得话需要重启系统

```bash

```

### 修改文件 /etc/profile

用户初始化配置.
对所有用户有效.
对所有 shell 有效.
修改需要执行 source /etc/profile 命令.


```bash

```

### 修改文件 /etc/bashrc (ubuntu 为 /etc/bash.bashrc)

bash 初始化.
对所有用户有效.
只对 bash 有效.
修改需要执行 source /etc/bashrc 命令.

```bash

```

### 修改文件 ~/.bash_profile (ubuntu 为 ~/.profile)

特定用户初始化配置.
只对当前用户有效.
对所有 shell 有效.
修改需要执行 source ~/.bash_profile 命令.

```bash

```

### 修改文件 ~/.bashrc

特定用户的 bash 初始化.
只对当前用户生效.
只对 bash 生效.
修改需要执行 source ~/.bashrc 命令.

```bash

```
