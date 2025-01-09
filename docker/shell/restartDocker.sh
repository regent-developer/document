#!/bin/bash
# 使用重启策略重启容器
CONTAINER_NAME=$1
docker update --restart always $CONTAINER_NAME
echo "$CONTAINER_NAME 现在将在失败后自动重启。"
 