#!/bin/bash
# 删除所有停止的容器
docker rm $(docker ps -aq -f "status=exited")
 