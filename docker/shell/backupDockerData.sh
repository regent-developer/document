#!/bin/bash
# 备份容器的数据
CONTAINER_ID=$1
BACKUP_FILE="${CONTAINER_ID}_backup_$(date +%F).tar"
docker export $CONTAINER_ID > $BACKUP_FILE
echo "备份保存到 $BACKUP_FILE"
 