# TongLINKQ

TongLinkQ 是面向分布式应用的消息中间件产品，主要功能是在应用程序之间进行实时、高效和可靠的传递消息，使得消息在不同的网络协议、不同的计算机系统和不同的应用软件之间进行网络传输。

TongLinkQ 应用程序可灵活地运行在多平台的多节点上。当应用程序之间要传送消息时，应用程序只需 将消息目的地、消息的属性和内容以及消息的控制信息通过 API 接口传递给 TongLinkQ ，TongLinkQ 根据应用提供 的信息对消息进行处理，并且利用 TongLinkQ  的节点组成的虚拟网，将消息传送到消息接收者所在节点上， 最后提交给消息的接收者。

## 特点

* 支持开发语言：C/C++/C#,JAVA,JMS
* 支持协议：支持 TCP/IP 协议,支持SSL 安全传输协议
* 支持多种网络底层环境:租用线 (Leased line)、拨号线、分组交换网（X..25）、DDN , 帧中继 (Frame Relay) 、卫星网络等(以太网，广域网，拨号上网，DDB专线卫星网)等
* 支持多运行平台：Windwos系列，Linux系列，AIX系列，Solaris系列（Linux 中支持国产 Linux 操作系统）



## 安装



## 启动



## 常用命令

* 启动：tlq
* 停止：tlq -cabort -y -w1
* 查看lic信息：tlqstat –lic
* 查看队列消息：tlqstat  -qcu  qcu名  -c
* 查看发送连接状态：tlqstat  -snd  qcu名  -1 -ct 1
* 查看指定的Qcu连接状态：tlqstat  -rcv QCU名 1 -ct 1
* 查看ipc资源：tlqipc
* 清理ipc资源：tlqipc -rm 2
  