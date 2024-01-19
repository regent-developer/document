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
      url: jdbc:mysql://localhost:3306/xxx # LiteFlow从v2.10.6开始支持了使用项目中已存在的Datasource来进行数据库连接
      driverClassName: com.mysql.cj.jdbc.Driver # LiteFlow从v2.10.6开始支持了使用项目中已存在的Datasource来进行数据库连接
      username: root # LiteFlow从v2.10.6开始支持了使用项目中已存在的Datasource来进行数据库连接
      password: 123456 # LiteFlow从v2.10.6开始支持了使用项目中已存在的Datasource来进行数据库连接
      applicationName: demo
      #是否开启SQL日志
      sqlLogEnabled: true
      #是否开启SQL数据轮询自动刷新机制 默认不开启
      pollingEnabled: true
      pollingIntervalSeconds: 60
      pollingStartSeconds: 60
      #以下是chain表的配置，这个一定得有
      chainTableName: chain
      chainApplicationNameField: application_name
      chainNameField: chain_name
      elDataField: el_data
      chainEnableField: enable
      #以下是script表的配置，如果你没使用到脚本，下面可以不配置
      scriptTableName: script
      scriptApplicationNameField: application_name
      scriptIdField: script_id
      scriptNameField: script_name
      scriptDataField: script_data
      scriptTypeField: script_type
      scriptLanguageField: script_language
      scriptEnableField: enable
  
  ```

* 表结构

表结构无要求，通过yaml文件中的配置和表结构一致即可

* 其他如非Spring环境所述

  

