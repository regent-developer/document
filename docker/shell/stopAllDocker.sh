#!/bin/bash
# 停止所有运行中的容器
docker stop $(docker ps -q)
 