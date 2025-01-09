#!/bin/bash
# 启动所有停止的容器
docker start $(docker ps -aq)
 