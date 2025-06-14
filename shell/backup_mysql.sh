#!/bin/bash
#
# backup_mysql.sh —— 自动备份 MySQL 主库
# 环境：AlmaLinux + Docker
# 说明：依赖 docker、gzip、mysqldump
#

# —— 一、基本变量定义 —— 
# 容器名称或 ID
CONTAINER_NAME="mysql-master"
# 备份存放目录
BACKUP_DIR="/home/dba/scripts/backups"
# 保留天数
RETENTION_DAYS=7
# 时间戳
DATE=$(date +"%F_%H%M")

# —— 二、命令检查 —— 
for cmd in docker mysqldump gzip;do
command-v$cmd>/dev/null 2>&1
if[$?-ne0];then
echo "错误：未检测到命令 $cmd，请安装后重试！"
exit1
fi
done

# —— 三、创建备份目录 —— 
mkdir -p "$BACKUP_DIR"
if[$?-ne0];then
echo "错误：创建目录 $BACKUP_DIR 失败！"
exit 1
fi

# —— 四、执行备份 —— 
BACKUP_FILE="$BACKUP_DIR/mysql_backup_${DATE}.sql.gz"
echo "[$(date +"%F %T")] 开始备份：$BACKUP_FILE"
dockerexec$CONTAINER_NAME\
    mysqldump -uroot -p'your_password' --all-databases \
|gzip>"$BACKUP_FILE"

if[$?-eq0];then
echo"[$(date +"%F %T")] 备份完成！"
else
echo"[$(date +"%F %T")] 备份失败！"
exit1
fi

# —— 五、清理过期备份 —— 
echo"[$(date +"%F %T")] 开始清理 $RETENTION_DAYS 天前的备份"
find"$BACKUP_DIR"-type f -name"mysql_backup_*.sql.gz"\
-mtime +$RETENTION_DAYS-print-delete

echo"[$(date +"%F %T")] 清理完成！"