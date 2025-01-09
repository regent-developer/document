#!/bin/bash
# 从 tar 备份恢复容器
BACKUP_FILE=$1
docker import $BACKUP_FILE restored_container:latest
echo "容器恢复为 'restored_container:latest'"
 