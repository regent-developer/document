# SpringBoot整合Hasor

## 添加依赖
```xml
<dependencies>
    <!--Spring-web依赖 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!--连通 Spring 和 Hasor-->
    <dependency>
        <groupId>net.hasor</groupId>
        <artifactId>hasor-spring</artifactId>
        <version>4.1.7</version>
    </dependency>
    <!--DataWay 是Hasor生态中的一员-->
    <dependency>
        <groupId>net.hasor</groupId>
        <artifactId>hasor-dataway</artifactId>
        <version>4.1.7</version><!-- 4.1.4 包存在UI资源缺失问题 -->
    </dependency>
    <!--mysql-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    <!--JDBC-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
</dependencies>
```

* hasor-spring：负责Spring和Hasor框架之间的整合。
* hasor-dataway：工作在Hasor之上，利用hasor-spring我们就可以使用dataway。

## 创建数据库表

```sql
CREATE TABLE `interface_info` (
  `api_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `api_method` varchar(12) NOT NULL COMMENT 'HttpMethod：GET、PUT、POST',
  `api_path` varchar(512) NOT NULL COMMENT '拦截路径',
  `api_status` int(2) NOT NULL COMMENT '状态：0草稿，1发布，2有变更，3禁用',
  `api_comment` varchar(255) DEFAULT NULL COMMENT '注释',
  `api_type` varchar(24) NOT NULL COMMENT '脚本类型：SQL、DataQL',
  `api_script` mediumtext NOT NULL COMMENT '查询脚本：xxxxxxx',
  `api_schema` mediumtext COMMENT '接口的请求/响应数据结构',
  `api_sample` mediumtext COMMENT '请求/响应/请求头样本数据',
  `api_option` mediumtext COMMENT '扩展配置信息',
  `api_create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `api_gmt_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`api_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='Dataway 中的API';

CREATE TABLE `interface_release` (
  `pub_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Publish ID',
  `pub_api_id` int(11) NOT NULL COMMENT '所属API ID',
  `pub_method` varchar(12) NOT NULL COMMENT 'HttpMethod：GET、PUT、POST',
  `pub_path` varchar(512) NOT NULL COMMENT '拦截路径',
  `pub_status` int(2) NOT NULL COMMENT '状态：0有效，1无效（可能被下线）',
  `pub_type` varchar(24) NOT NULL COMMENT '脚本类型：SQL、DataQL',
  `pub_script` mediumtext NOT NULL COMMENT '查询脚本：xxxxxxx',
  `pub_script_ori` mediumtext NOT NULL COMMENT '原始查询脚本，仅当类型为SQL时不同',
  `pub_schema` mediumtext COMMENT '接口的请求/响应数据结构',
  `pub_sample` mediumtext COMMENT '请求/响应/请求头样本数据',
  `pub_option` mediumtext COMMENT '扩展配置信息',
  `pub_release_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间（下线不更新）',
  PRIMARY KEY (`pub_id`),
  KEY `idx_interface_release` (`pub_api_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='Dataway API 发布历史。';
```

SQL可以在dataway的依赖jar包中 “META-INF/hasor-framework/mysql” 目录下（以上sql是mysql版本）  

## 核心代码

* 新建一个 Hasor 的 模块，并且将其交给 Spring 管理。然后把数据源通过Spring注入进来。
```java
@DimModule  // Hasor 中的标签，表明是一个Hasor的model
@Component  // Spring 中的标签，表明是一个组件
public class HasorComponent implements SpringModule {

    private final DataSource dataSource;

    public HasorComponent(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Hasor 启动的时候会调用 loadModule 方法，
     * 在这里再把 DataSource 设置到 Hasor 中。
     * @param apiBinder
     * @throws Throwable
     */
    @Override
    public void loadModule(ApiBinder apiBinder) throws Throwable {
        apiBinder.installModule(new JdbcModule(Level.Full, this.dataSource));
    }

}
```
Hasor启动的时候会调用loadModule方法，需把DataSource设置到Hasor中。

* 在SprintBoot中启用 Hasor

```java
@EnableHasor
@EnableHasorWeb  // 将 hasor-web 配置到 Spring 环境中，Dataway 的 UI 是通过 hasor-web 提供服务。
@SpringBootApplication
public class HasorApplication {

    public static void main(String[] args) {
        SpringApplication.run(HasorApplication.class, args);
    }
}
```

## 配置文件
```yml
server:
  port: 9800

spring:
  profiles:
    active: dev
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver


# 是否启用 Dataway 功能（必选：默认false）
HASOR_DATAQL_DATAWAY: true

# 是否开启 Dataway 后台管理界面（必选：默认false）
HASOR_DATAQL_DATAWAY_ADMIN: true

# dataway  API工作路径（可选，默认：/api/）
HASOR_DATAQL_DATAWAY_API_URL: /interface/

# dataway-ui 的工作路径（可选，默认：/interface-ui/）
HASOR_DATAQL_DATAWAY_UI_URL: /config/

# SQL执行器方言设置（可选，建议设置）
HASOR_DATAQL_FX_PAGE_DIALECT: mysql
```

## 启动程序&访问接口管理页面进行接口配置
http://127.0.0.1:9800/config/，即可打开DatawayAPI配置页面。  

可以通过DataQL或者sql来进行设定。

### Dataway简介

Dataway 是基于 DataQL 服务聚合能力，为应用提供的一个接口配置工具。使得使用者无需开发任何代码就配置一个满足需求的接口。 整个接口配置、测试、冒烟、发布。一站式都通过 Dataway 提供的 UI 界面完成。UI 会以 Jar 包方式提供并集成到应用中并和应用共享同一个 http 端口，应用无需单独为 Dataway 开辟新的管理端口。  

这种内嵌集成方式模式的优点是，可以使得大部分老项目都可以在无侵入的情况下直接应用 Dataway。进而改进老项目的迭代效率，大大减少企业项目研发成本。  