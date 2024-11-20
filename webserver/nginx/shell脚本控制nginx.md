# shell脚本控制nginx

```shell
#!/bin/bash
# 使用脚本首先要免密码登陆
# 定义LNMP 服务器的 IP 地址
server1="server1-ip"

# 启动 Nginx 服务
start_nginx() {
    ssh user@$server1 "sudo systemctl start nginx"
}

# 停止 Nginx 服务
stop_nginx() {
    ssh user@$server1 "sudo systemctl stop nginx"
}

# 根据脚本参数执行相应的操作
case "$1" in
    start)
        start_nginx
        ;;
    stop)
        stop_nginx
        ;;
    *)
        echo "Usage: $0 {start|stop}"
        exit 1
        ;;
esac

exit 0

```