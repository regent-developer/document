# Magento

Magento是一款新的专业开源电子商务平台，采用php进行开发，使用Zend Framework框架。设计得非常灵活，具有模块化架构体系和丰富的功能。易于与第三方应用系统无缝集成。在设计上，包含相当全面，以模块化架构体系，让应用组合变得相当灵活，功能也相当丰富。

https://github.com/magento/magento2

Magento中文社区 https://bbs.mallol.cn/

## 配置变量位置
* app/etc/env.php
* app/etc/config.php
* table/core_config_data
* module里定义的config.xml

magento读取配置的顺序是按照上面顺序依次读取，如果一个配置重复定义，则按照上面的优先级读取。

## MVC-Controller

magento的控制器通常不会直接向模板传递数据，但提供了向block传递数据的能力。  
模板中数据来源与block文件。  
控制器只有一个方法execute()，控制器的入口方法。


## MVC-Model

Model分为以下三个层：
* Model层
* ResourceModel层
* Collection层

Model层包含ResourceMOdel层，ResourceModel包含Collection层，每个层提供了不同的功能。
