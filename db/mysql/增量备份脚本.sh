#!/bin/bash
##增量备份脚本
DIRLOGS=/var/log/mysql
LOGS_HOME=/usr/local/mysql/data
TODAY=$(data +%F)
YESTERDAY=$(date -d '-1 day' +%F)
USER=root
PASSWORD=abc123
 
#先判断收集备份文件的目录是否存在，不存在则创建目录
[ -d $DIRLOGS ]||mkdir -p $DIRLOGS
 
##备份二进制日志的索引文件到指定目录并加入时间标记
\cp  $LOGS_HOME/mysql_bin.index $DIRLOGS/mysql_bin.index.$TODAY
 
#先判断昨天的二进制日志的索引文件是否存在，存在则作为过滤条件过滤出今天需要备份的二进制日志文件名称
if [ -f $DIRLOGS/mysql_bin.index.$YESTERDAY ];then
    BINGLOG=$(cat $DIRLOGS/mysql_bin.index.$TODAY|grep -v $(cat $DIRLOGS/mysql_bin.index.$YESTERDAY)|awk -F/ '{print $2}')
  else
    BINGLOG=$(cat $DIRLOGS/mysql_bin.index.$TODAY|awk -F/ '{print $2}')
fi
 
##刷新生成新的二进制日志文件，便于收集最新的二进制日志
mysqladmin -u"$USER" -p"$PASSWORD" flush-logs &>/dev/null
 
##使用for循环，对所有的新产生的binlog文件进行备份
for i in $BINGLOG
do
mv $LOGS_HOME/$i  $DIRLOGS/$i.$TODAY
done