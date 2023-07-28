# JMeter

## 聚合报告

* Label：请求的名称

* Samples：发送请求的数量

* Average：平均响应时间

* Median：中位数响应时间，也就是50%用户的响应时间不超过该值

* 90% Line：90%用户的响应时间不超过该值

* 95% Line：95%用户的响应时间不超过该值

* 99% Line：99%用户的响应时间不超过该值

* Min：最小响应时间

* Max：最大响应时间

* Error%：请求错误率，错误请求数/总请求数

* Throughput：吞吐量，默认情况下表示每秒完成的请求数(Requests per second)，通常认为它是TPS

* Received KB/Sec：每秒从服务器端接收到的数据量

* Sent KB/Sec：每秒向服务器发送的数据量

备注：
* 所有与响应时间相关的字段，单位都是毫秒
* Average默认是单个请求的平均响应时长，当使用了事务控制器，也可以以事务为单位显示平均响应时长

## 各版本下载链接

http://archive.apache.org/dist/jmeter/binaries/

