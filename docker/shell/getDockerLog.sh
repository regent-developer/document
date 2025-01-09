#!/bin/bash
# 显示所有容器的日志
docker ps -q | xargs -I {} docker logs {}
 