# posgreSQL备份脚本


```bash
#!/bin/bash
 
#设置环境变量
 
export PGHOST=localhost  # PostgreSQL服务器地址
 
export PGPORT=5432  #postgreSQL端口号
 
export PGUSER=postgres     # 数据库用户名
 
export PGPASSWORD=xxxxx  # 数据库密码
 
# 备份目标目录
BACKUP_DIR="/usr/local/share/PostgreSQLData"
 
DATE=$(date +%Y%m%d)
 
mkdir -p ${BACKUP_DIR}
 
pg_dumpall -c --encoding=UTF8  -U ${PGUSER}  > "${BACKUP_DIR}/all_databases_${DATE}.sql"
```