#!/bin/bash  
################################################################  
#有一些脚本加入到了cron之中，存在脚本尚未运行完毕又有新任务需要执行的情况，  
#导致系统负载升高，因此可通过编写脚本，筛选出影响负载的进程一次性全部杀死。  
################################################################  
ps aux|grep 指定进程名|grep -v grep|awk '{print $2}'|xargs kill -9  