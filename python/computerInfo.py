import psutil
 
def get_computer_info():
    cpu_info = psutil.cpu_freq()
    memory_info = psutil.virtual_memory()
    disk_info = psutil.disk_usage('/')
    print(f"CPU Frequency: {cpu_info.current} MHz")
    print(f"Memory Usage: {memory_info.used / (1024 * 1024 * 1024)} GB")
    print(f"Disk Usage: {disk_info.used / (1024 * 1024 * 1024)} GB")
 
get_computer_info()