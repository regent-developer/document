import psutil
import time
import datetime

print("当前日期   时间            CPU使用率  内存使用率  磁盘使用率")
while True:
    cpu_usage = psutil.cpu_percent(interval=1)
    memory_usage = psutil.virtual_memory().percent
    disk_usage = psutil.disk_usage('/').percent
    current_time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    print(current_time,"    ",str(cpu_usage)+"%    ",str(memory_usage)+"%    ",str(disk_usage)+"%")
    time.sleep(0)

