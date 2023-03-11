# Magento

Magento是一款新的专业开源电子商务平台，采用php进行开发，使用Zend Framework框架。设计得非常灵活，具有模块化架构体系和丰富的功能。易于与第三方应用系统无缝集成。在设计上，包含相当全面，以模块化架构体系，让应用组合变得相当灵活，功能也相当丰富。

https://github.com/magento/magento2

Magento中文社区 https://bbs.mallol.cn/


## 模块目录结构
在app/code目录下建立

* controller：控制器目录
* model：模型层目录
* block：block层目录
* view：视图层，模板，layout，js，css等目录
* etc：模块配置目录
* i18n：翻译语言包目录
* frontendend：前台目录
* adminhtml：后台目录
* console：控制台命令行目录
* observer：观察者（事件）目录
* UI：ui组件目录
* helper：帮助类目录
* api：提供api的目录

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

## MVC-Block
用于向视图层展示数据，大部分业务逻辑会卸载block层中。

## MVC-模板
模板以.phtml后缀结尾，magento没有采用单独的模板引擎，二十采用php和html代码混写的方式。因此，在模板中可以调用block方法获取数据，也可以直接获取数据库连接对象，直接写数据库查询语句，直接调用php函数，直接写php逻辑。  

模板数据来源与哪一个block文件，是由layout布局机制指定。


## magento命令行
```shell
php bin/magento XXXXX  
```

### 常用命令
* php bin/magento setup:upgrade：用于更新module，当修改了一个模块的di.xml文件，配置文件，或者新建一个module时会用到该命令。

* php bin/magento cache:clean：用于更新缓存，当更改了后台配置，修改了模块，刷新页面发现没有变化时，使用该命令。

* php bin/magento setup:di:compile：用于重新编译class文件，当系统报错找不到某些class文件时，或者某个module修改了di.xml文件时，需要手动重新编译。

* php bin/magento indexer:reindex：重建索引，当发现在某些场景下，数据显示不正确，或者数据没有同步，该命令可手动重建索引。

