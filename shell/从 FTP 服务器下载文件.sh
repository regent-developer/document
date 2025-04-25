#!/bin/bash  
if [ $# -ne 1 ]; then  
    echo "Usage: $0 filename"  
fi  
dir=$(dirname $1)   #提取目录
file=$(basename $1) #提取文件名
  
ftp -n -v << EOF   # -n 自动登录  
open 127.0.0.1  # ftp服务器  
user admin password  
binary   # 设置ftp传输模式为二进制，避免MD5值不同或.tar.gz压缩包格式错误  
# lcd：更改本地工作目录
cd $dir  
get "$file"  
EOF  