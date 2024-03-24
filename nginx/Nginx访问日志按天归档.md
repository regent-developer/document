# Nginx访问日志按天归档

```shell
#!/bin/bash  

# 定义日志存放目录  
LOG_DIR=/usr/local/nginx/logs  
  
# 获取昨天的日期  
YESTERDAY_TIME=$(date -d "yesterday" +%F)  
  
# 定义按月归档的日志目录格式  
LOG_MONTH_DIR=$LOG_DIR/$(date +"%Y-%m")  
  
# 定义需要切割的日志文件列表  
LOG_FILE_LIST="default.access.log"  
  
# 循环处理每一个日志文件  
for LOG_FILE in $LOG_FILE_LIST; do  
    # 如果按月归档的日志目录不存在，则创建它  
    [ ! -d $LOG_MONTH_DIR ] && mkdir -p $LOG_MONTH_DIR  
      
    # 将日志文件移动到按月归档的目录中，并在文件名后添加昨天的日期  
    mv $LOG_DIR/$LOG_FILE $LOG_MONTH_DIR/${LOG_FILE}_${YESTERDAY_TIME}  
done  
  
# 发送USR1信号给Nginx主进程，使其重新打开日志文件  
kill -USR1 $(cat /var/run/nginx.pid)
```

## 使用方法

* 将上述脚本保存为一个文件，例如log_rotate.sh
* 赋予脚本执行权限：chmod +x log_rotate.sh
* 通过cron定时任务每天执行该脚本，例如：0 0 * * * /path/to/log_rotate.sh（每天凌晨0点执行）