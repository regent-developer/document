#!/bin/bash
# 删除未使用镜像
docker rmi $(docker images -q -f "dangling=true")
 