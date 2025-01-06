# hdc

hdc（HarmonyOS Device Connector）是为开发人员提供的用于调试的命令行工具，通过该工具可以在windows/linux/mac系统上与设备进行交互。


## 命令列表

|命令 |	说明|
|---|---|
|list targets	|查询已连接的所有目标设备。|
|wait	|等待设备正常连接。|
|tmode usb	|该命令已经废弃，不会实际操作设备连接通道，需要在设备设置界面通过USB调试开关进行设置。|
|tmode port	|打开设备网络连接通道。|
|tmode port close	|关闭设备网络连接通道。|
|tconn	|指定连接设备：通过“IP地址：端口号”来指定连接的设备。|
|shell	|在设备侧执行单次命令。|
|install	|安装指定的应用文件。|
|uninstall	|卸载指定的应用包。|
|file send	|从本地发送文件至远端设备。|
|file recv	|从远端设备发送文件至本地。|
|fport ls	|列出全部转发端口转发任务。|
|fport	|设置正向端口转发任务：监听“主机端口”，接收请求并进行转发， 转发到“设备端口”。|
|rport	|设置反向端口转发任务：监听“设备端口”，接收请求并进行转发，转发到“主机端口”。|
|fport rm	|删除指定的端口转发任务。|
|start	|启动hdc服务进程。|
|kill	|终止hdc服务进程。|
|hilog	|打印设备端的日志信息。|
|jpid	|显示设备上所有开启了JDWP调试协议的应用的PID。|
|track-jpid	|实时显示设备上开启了JDWP调试协议的应用的PID和应用名。|
|target boot	|重启目标设备。|
|keygen	|生成一个新的秘钥对。|
|version	|打印hdc版本信息，也可使用hdc -v打印版本信息。|
|checkserver	|获取客户进程与服务进程版本信息。|