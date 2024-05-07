#!/bin/bash

# 设置应用名称
APP_NAME="you-application"

# 查找用用的进程id
PID=$(ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}')

# 检查进程是否存在
if [ -z "$PID"]; then
    echo "$APP_NAME is not running"
else 
    # 杀死进程
    kill -9 $PID
    echo "$APP_NAME stopped successfully"
fi