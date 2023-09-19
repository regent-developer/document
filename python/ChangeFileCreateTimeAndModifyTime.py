import win32file
from datetime import datetime

new_ctime = datetime.strptime("2023-03-06 16:00:00", "%Y-%m-%d %H:%M:%S")
new_mtime = datetime.strptime("2023-03-06 16:00:00", "%Y-%m-%d %H:%M:%S")
new_atime = datetime.strptime("2023-03-06 16:00:00", "%Y-%m-%d %H:%M:%S")

file_path = "xxx.pdf"

handle = win32file.CreateFile(
    file_path,
    win32file.GENERIC_READ | win32file.GENERIC_WRITE,
    0,
    None,
    win32file.OPEN_EXISTING,
    0,
    0,
)
win32file.SetFileTime(handle, new_ctime, new_atime, new_mtime)  # 注意这里的顺序，访问时间在前，修改时间在后
win32file.CloseHandle(handle)