# LiteFlow

LiteFlow是一个轻量且强大的国产规则引擎框架，可用于复杂的组件化业务的编排领域，独有的DSL规则驱动整个复杂业务，并可实现平滑刷新热部署，支持多种脚本语言规则的嵌入。帮助系统变得更加丝滑且灵活。



https://liteflow.cc/

![](https://github.com/regent-developer/document/blob/master/LiteFlow/arch.svg)

## 使用

### 非Spring环境

* 引入pom依赖

```xml
<dependency>
   <groupId>com.yomahub</groupId>
   <artifactId>liteflow-core</artifactId>
   <version>2.6.13</version>
</dependency>
```

* 定义业务Node

定义业务Node（继承NodeComponent，并使用注解@Component("xxx")），重写process方法。

* 编写xml

```xml
<!-- 利用el表达式定义规则表达式 -->
<!-- 串行编排 -->
<chain name="chain1">
    THEN(a, b, c, d);
</chain>

<!-- 并行编排 -->
<chain name="chain1">
    WHEN(a, b, c);
</chain>

<!-- 选择编排 -->
<chain name="chain1">
    SWITCH(a).to(b, c, d);
</chain>

<!-- 条件编排 -->
<chain name="chain1">
    IF(x, a);
</chain>

<chain name="chain1">
    IF(x, a, b);
</chain>

<chain name="chain1">
    IF(x, a).ELSE(b);
</chain>

<chain name="chain1">
    IF(x1, a).ELIF(x2, b).ELSE(c);
</chain>

<!-- 使用子流程 -->
<chain name="subChain">
   THEN(C, D);
</chain>

<chain name="mainChain">
    THEN(
     A, B,
     subChain,
     E
    );
</chain>

```



* 执行

构建LiteflowConfig，传入xml，构建FlowExecutor，调用execute2Resp（需传入chain名）



### Spring环境

* 引入pom依赖

```xml
<dependency>
   <groupId>com.yomahub</groupId>
   <artifactId>liteflow-spring-boot-starter</artifactId>
   <version>2.6.13</version>
</dependency>
```



* 定义业务Node

定义业务Node（继承NodeComponent，并使用注解@Component("xxx")或@LiteflowComponent("xxx")），重写process方法。

* 在application配置文件中配置xml文件路径（本地规则文件）

```
liteflow.rule-source=config/flow.el.xml
```

* SQL数据库配置

  ```xml
  <dependency>
      <groupId>com.yomahub</groupId>
      <artifactId>liteflow-rule-sql</artifactId>
      <version>2.11.4</version>
  </dependency>
  ```

  ```yaml
  liteflow:
    rule-source-ext-data-map:
      # LiteFlow从v2.10.6开始支持了使用项目中已存在的Datasource来进行数据库连接
      # jdbc的连接url
      url: jdbc:mysql://localhost:3306/xxx 
      # 驱动器类名
      # LiteFlow从v2.10.6开始支持了使用项目中已存在的Datasource来进行数据库连接
      driverClassName: com.mysql.cj.jdbc.Driver 
      # 数据库用户名
      # LiteFlow从v2.10.6开始支持了使用项目中已存在的Datasource来进行数据库连接
      username: root 
      # 数据库密码
      # LiteFlow从v2.10.6开始支持了使用项目中已存在的Datasource来进行数据库连接
      password: 123456 
      # 你的应用名称
      applicationName: demo
      #是否开启SQL日志
      sqlLogEnabled: true
      #是否开启SQL数据轮询自动刷新机制 默认不开启
      pollingEnabled: true
      # SQL数据轮询时间间隔(s) 默认为60s
      pollingIntervalSeconds: 60
      # 规则配置后首次轮询的起始时间(s) 默认为60s
      pollingStartSeconds: 60
      #以下是chain表的配置
      # 编排规则表的表名
      chainTableName: chain
      # 编排规则表中应用名称存储字段名
      chainApplicationNameField: application_name
      # 规则名称存储的字段名
      chainNameField: chain_name
      # EL表达式的字段(只存EL)
      elDataField: el_data
      此chain是否生效，此字段最好在mysql中定义成tinyInt类型
      chainEnableField: enable
      #以下是script表的配置，如果你没使用到脚本，下面可以不配置
      # 你的脚本存储表的表名
      scriptTableName: script
      # 脚本组件的Id的字段名
      scriptApplicationNameField: application_name
      # 脚本组件名称的字段名
      scriptIdField: script_id
      # 脚本组件名称的字段名
      scriptNameField: script_name
      # 脚本数据的字段名
      scriptDataField: script_data
      # 脚本类型的字段名
      scriptTypeField: script_type
      # 脚本语言类型（groovy | qlexpress | js | python | lua | aviator | java）
      scriptLanguageField: script_language
      # 此脚本是否生效，此字段最好在mysql中定义成tinyInt类型
      scriptEnableField: enable
  
  ```

* 表结构

表结构无要求，通过yaml文件中的配置和表结构一致即可

* 其他如非Spring环境所述

  

