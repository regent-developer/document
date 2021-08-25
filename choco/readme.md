# choco

Windows 包管理工具，相当于brew（The package manager for Windows）

## 官网
https://chocolatey.org/


## 环境
Windows 7+ / Windows Server 2003+


## 安装

### cmd 安装（管理员身份运行）
```cmd
@"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"
```

### 检查安装是否成功

```cmd
choco -v
```

### 更新

```cmd
choco upgrade chocolatey
```

## 命令

* choco list -li 查看本地安装的软件
* choco search xxx 查找安装包
* choco install xxx 下载
* choco uninstall xxx 卸载
* choco upgrade xxx 更新（update）
