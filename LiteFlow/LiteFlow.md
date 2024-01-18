# LiteFlow

LiteFlow是一个轻量且强大的国产规则引擎框架，可用于复杂的组件化业务的编排领域，独有的DSL规则驱动整个复杂业务，并可实现平滑刷新热部署，支持多种脚本语言规则的嵌入。帮助系统变得更加丝滑且灵活。



https://liteflow.cc/

![](C:\Users\user\Downloads\arch.svg)

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

* 在application配置文件中配置xml文件路径
* 其他如非Spring环境所述

