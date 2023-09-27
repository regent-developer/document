from ctypes import *
import os
import sys
import ftplib
import time
import tempfile
import win32api
import win32print

class FtpUtil:
    ftp = ftplib.FTP()
    def __init__(self, host, port=21):
        self.ftp.connect(host, port)
        self.ftp.encoding = 'gbk'

    # 登录
    def Login(self, user, passwd):
        self.ftp.login(user, passwd)
        print(self.ftp.welcome)

    # 下载单个文件
    def DownLoadFile(self, LocalFile, RemoteFile):  
        file_handler = open(LocalFile, 'wb')
        #print(file_handler)
        # self.ftp.retrbinary("RETR %s" % (RemoteFile), file_handler.write) # 接收服务器上文件并写入本地文件
        self.ftp.retrbinary('RETR ' + RemoteFile, file_handler.write)
        file_handler.close()
        return True

    # 下载整个目录下的文件
    def DownLoadFileTree(self, LocalDir, RemoteDir):  
        print("远程文件夹remoteDir:", RemoteDir)
        if not os.path.exists(LocalDir):
            os.makedirs(LocalDir)
        self.ftp.cwd(RemoteDir)
        RemoteNames = self.ftp.nlst()
        print("远程文件目录：", RemoteNames)
        for file in RemoteNames:
            Local = os.path.join(LocalDir, file)
            print("正在下载", self.ftp.nlst(file))
            if file.find(".") == -1:
                if not os.path.exists(Local):
                    os.makedirs(Local)
                self.DownLoadFileTree(Local, file)
            else:
                self.DownLoadFile(Local, file)
        self.ftp.cwd("..")
        return

    # 关闭
    def close(self):
        self.ftp.quit()


if __name__ == "__main__":
    ftp = FtpUtil('IP')
    ftp.Login('账户', '密码')
    data = str(time.strftime("%m%d"))
    local_path = 'D:/文件夹/'+data
    romte_path = '/文件夹/'+data
    ftp.DownLoadFileTree(local_path,romte_path)  # 从目标目录下载到本地目录d盘
    ftp.close()
    print("下载完成")
