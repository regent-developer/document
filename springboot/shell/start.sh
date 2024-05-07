#!/bin/bash

# 设置jar包名以及路径
APP_NAME="you-application.jar"
JAR_PATH="/path/to/your/application/$APP_NAME"

# 检查jar是否存在
if [! -f "$JAR_PATH"]; then
    echo "Error: $APP_NAME not found"
    exit 1
fi

# 启动应用
nohup java -jar $JAR_PATH > /dev/null 2 >&1 &
echo "$APP_NAME started successfully"