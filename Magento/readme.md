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

## 重写

### url
* 第一部分：路由名称设定
具体定义位置是在routes.xml中定义的frontName的值。

* 第二部分：控制器文件所在目录名称设定，module/Controller下建立的目录名称
* 第三部分：控制器类名
如只写第一部分，则控制器为controller下的index目录和index.php文件  

### 布局文件
通过布局文件，通过访问的url地址，可找到该页面的模板，block，控制器。

布局文件的命名规则：路由_控制器目录名_控制器名.xml

### 重写控制器

前提：在app/code下新建重写的模块xxx，在该模块的etc目录下新建module.xml文件，模块根目录下新建registration.php，使用命令php bin/magento setup:upgrade进行模块的注册。

* 新建并修改路由文件：在app/code/xxx/xxx/etc/frontend/下新建routes.xml文件，并重写。
* 新建并修改依赖注入di.xml文件：在app/code/xxx/xxx/etc/下新建di.xml文件，并重写。
* 新建控制器->重写控制器
* 更新module：php bin/magento setup:upgrade
* 重新生成编译文件：php bin/magento setup:deploy:compile
* 设置目录权限：chmod -R 777 ./

### 重写模板

* 找到layout文件
* 新建自定义module下的layout文件，指定新的模板
* 添加新模板

### 重写block

* 找到layout文件
* 新建自定义module下的layout文件，指定新的block和模板文件
* 添加新block文件和模板文件
* 更新module：php bin/magento setup:upgrade
* 设置目录权限：chmod -R 777 ./


### 重写model

* 修改di.xml
* 新建model
* block中调用重写的model

### 重写其他类

在di.xml中有有两种方法：
* perference for ...  type ...：perference方式是完全覆盖原有的类。
* type name ... plugin name ... type ...：不算重写，允许在挂接的类的方法之前，周围或者之后执行代码。茶碱类不会替换目标类，它不是一个实例。只有before{method}，around{method}，after{method}方法，这些方法在目标类上的{method}的适当时间执行。由于插件不会替换目标类，因此可以同时在一个类上激活任意数量的插件。magento只是根据xml中的sortOrder参数一个接一个地执行它们。

### widget

widget可以从数据库获取数据并展示为html页面的代码，可以添加到cms页面中或，在其他页面中调用和展示。

#### 安装
Magento_Widget依赖以下模块：
* Magento_Catalog
* Magento_Cms
* Magento_Store

在禁用或卸载之前，依赖以下模块：
* Magento_CatalogWidget
* Magento_CurrencySymbol
* Magento_im

### 主题
一个网站的页面风格，可以一键切换模板，样式。主题目录通常存放模板文件，css，js。

主题目录：app/design