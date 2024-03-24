# SpringBoot Banner

## banner是如何工作的
通过SpringApplicationBannerPrinter类，Spring Boot 默认寻找 Banner 的顺序是:
* 在Classpath下找文件banner.gif，banner.jpg 和 banner.png，使用优先找到的文件
* 若没找到上面文件的话，继续Classpath下找banner.txt
* 若上面都没有找到的话, 用默认的 SpringBoot Banner，也就是上面输出的SpringBoot Logo

一般是把 banner.* 文件放在 src/main/resources/ 目录下。  

## 变量

|变量|	描述|
|:---|:---|
|${application.version}	|MANIFEST.MF 中定义的版本。如：1.0|
|${application.version}	|MANIFEST.MF 中定义的版本。如：1.0|
|${application.formatted-version}|	MANIFEST.MF 中定义的版本，并添加一个 v 前缀。如：v1.0|
|${spring-boot.version}	|Spring Boot 版本。如：1.5.7.RELEASE|
|${spring-boot.formatted-version}|	Spring Boot 版本，并添加一个 v 前缀。如：v1.5.7.RELEASE|
|${Ansi.NAME} (or ${AnsiColor.NAME}, ${AnsiBackground.NAME}, ${AnsiStyle.NAME})	|ANSI 颜色、字体|
|${application.title}	|MANIFEST.MF 中定义的应用名|

## 配置
application.properties

```
# banner 模式。有三种模式：console/log/off
# console 打印到控制台（通过 System.out）
# log - 打印到日志中
# off - 关闭打印
spring.main.banner-mode = off
# banner 文件编码
spring.banner.charset = UTF-8
# banner 文本文件路径
spring.banner.location = classpath:banner.txt
# banner 图像文件路径（可以选择 png,jpg,gif 文件）
spring.banner.image.location = classpath:banner.gif
used).
# 图像 banner 的宽度（字符数）
spring.banner.image.width = 76
# 图像 banner 的高度（字符数）
spring.banner.image.height =
# 图像 banner 的左边界（字符数）
spring.banner.image.margin = 2
# 是否将图像转为黑色控制台主题
spring.banner.image.invert = false
```

## 生成banner的网站
* http://www.network-science.de/ascii/
* http://patorjk.com/software/taag
* http://www.degraeve.com/img2txt.php

如果想完全个人定制 Banner，可以先实现 org.springframework.boot.Banner#printBanner 接口来自己定制 Banner。在将这个 Banner 通过 SpringApplication.setBanner() 方法注入 Spring Boot。

参考  
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-banner