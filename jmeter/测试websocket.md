# jmeter测试websocket接口

## 安装WebSocket插件

* 选项 > Plugins Manager 输入WebSocket，勾选对应插件，点击Apply Changes and Restart JMeter

该插件包含websocket-api.jar、websocket-common.jar、websocket-client.jar、jetty-http.jar、jetty-io.jar、jetty-util.jar等JAR包。

## WebSocket相关的取样器

* websocket close 用于正常关闭websocket连接；
* websocket open connection 用于显式设置websocket连接；
* websocket ping-pong 用于发送ping和接收 pong请求；
* websocket sampler 用于执行基本的请求-响应请求；
* websocket single read sampler 用于接收一个文本或二进制的websocket请求；
* websocket single write sampler 用于发送一个文本或二进制的websocket请求；


