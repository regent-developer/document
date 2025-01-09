#!/bin/bash
# 列出所有暴露的端口
docker ps --format '{{.ID}}: {{.Ports}}'
 