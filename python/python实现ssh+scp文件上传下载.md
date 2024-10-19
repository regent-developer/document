# python实现ssh+scp文件上传下载


```python
import paramiko


class SSH(object):
    def __init__(self, host='127.0.0.1', port=22, username='root', password='密码'):
        self.host = host  # 主机ip
        self.port = port  # ssh端口
        self.username = username  # 用户名
        self.password = password  # 密码
        self.transport = None  # 连接通道

    # 建立连接
    def connect(self):
        try:
            transport = paramiko.Transport(self.host, self.port)
            transport.connect(username=self.username, password=self.password)
            self.transport = transport
            print('连接成功')
        except Exception as e:
            print('连接失败')
        #执行命令
       stdin, stdout, stderr = ssh_client.exec_command('ls -all')
        #读取执行命令后输出的内容
        out = stdout.readlines()
        for m in out:
            print(m)

    '''下载文件
    :param remote_path:远端地址
    :param local_path:本地地址
    '''

    def download_file(self, remote_path, local_path):
        try:
            sftp = paramiko.SFTPClient.from_transport(self.transport)
            sftp.get(remote_path, local_path)
            return True
        except Exception as e:
            print(e)
            return False


    '''上传文件'''

    def upload_file(self, remote_path, local_path):
        try:
            sftp = paramiko.SFTPClient.from_transport(self.transport)
            sftp.put(local_path, remote_path)
            return True
        except Exception as e:
            return False


if __name__ == '__main__':
    ssh_client = SSH()
    ssh_client.connect()
    print("我连接成功啦，开始get")
    w = ssh_client.download_file(remote_path='/home/root/zhenhua.txt ',local_path='C:/xxx.txt')
    print(w)

```

