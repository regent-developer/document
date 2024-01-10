# TongWeb部署服务时请求为中文路径错误的解决方法

## 现象

请求中含有中文，导致请求发生404错误。



## 解决方法

将TongWeb中的tongweb.xml中所有的GBK替换为UTF-8后，重启TongWeb服务。

