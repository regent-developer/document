# python实现ssh+scp文件上传下载


```python
import  paramiko
from scp import SCPClient

def ssh():
    ssh_client = paramiko.SSHClient()
    ssh_client.set_missing_host_key_policy(paramiko.AutoAddPolicy)
    ssh_client.connect(hostname='172.168.1.11', port=22, username='root', password="密码")
    # 上传文件, 包含文件名
    scpclient = SCPClient(ssh_client.get_transport(), socket_timeout=15.0)
    path = ['C:/xxx.txt','/home/root/']
    scpclient.put(path[0], path[1])
    scpclient.get(path[1],path[0])
    
    
    
if __name__ == '__main__':
    ssh()
```

