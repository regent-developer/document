# Linux服务器根据端口号反向查询进程号

## 1. 使用 netstat 命令：
netstat -tuln | grep :<端口号>
netstat -nlp|grep :<端口号>
netstat -apn|grep :<端口号>

## 2. 使用 lsof 命令（Linux/MacOS），可能需要安装lsof插件（yum install lsof）：
lsof -i :<端口号>
lsof -i tcp:<端口号>

## 3. 使用 ss 命令（Linux）：
ss -ltnp 'sport = :<端口号>'

## 4. 使用 tasklist（Windows）：
netstat -ano | findstr :<端口号>

## 使用该 PID 可以进一步查询进程的详细信息：
tasklist /FI "PID eq <PID>"