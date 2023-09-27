import psutil
# 安装psutil的命令
# pip install psutil
for proc in psutil.process_iter(['pid', 'name']):
    if proc.name() == 'python.exe':
        print(proc.info, proc.pid, proc.name(), proc.cmdline())
