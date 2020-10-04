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
