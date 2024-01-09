# wsl安装

* 启用适用于 Linux 的 Windows 子系统：打开powershell并输入下面命令

  ```shell
  dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
  ```

  

* 检查WSL2的要求：win+R打开运行，然后输入winver检查windows版本，需大于1903

* 启用虚拟化:以管理员打开powershell输入下列命令

  ```shell
  dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
  ```

  

* 下载[X64的WSL2 Linux内核升级包](https://wslstorestorage.blob.core.windows.net/wslblob/wsl_update_x64.msi)并安装

* 设置WSL默认版本

  ```shell
  wsl --set-default-version 2
  ```

  

* Microsoft Store中搜索wsl，下载ubuntu

  

  **如果Microsoft Store无法下载的话，可以在[ubuntu2204](https://wslstorestorage.blob.core.windows.net/wslblob/UbuntuUbuntu2204-221101.AppxBundle)下载，并双击运行**



