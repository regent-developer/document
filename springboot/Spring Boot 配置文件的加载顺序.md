# Spring Boot 配置文件的加载顺序

## 1. 基础配置文件

- application.properties/application.yml
1. Spring Boot 中默认的配置文件，通常用于定义应用的业务逻辑配置、数据库连接、日志设置等。
2. application.yml 会在 bootstrap.yml 之后加载。
- bootstrap.properties/bootstrap.yml
1. 主要用于 Spring Cloud 应用中，配置应用启动时需要的参数，通常包含应用的环境配置、服务发现、配置中心等。  
2. bootstrap.yml 会在 Spring Boot 应用的 上下文初始化之前加载。它用于初始化 Spring 环境和配置，特别是在 Spring Cloud 中，它会读取配置中心的相关配置。

## 2. 加载顺序和覆盖规则

1. bootstrap.yml（或 bootstrap.properties）：首先加载，适用于 Spring Cloud 环境。
2. application.yml（或 application.properties）：紧接着加载，适用于 Spring Boot 默认配置。
3. 后加载的配置会覆盖先加载的配置，所以如果 bootstrap.yml 和 application.yml 中有相同的配置项（例如 jdbc 配置），则 application.yml 中的配置会覆盖 bootstrap.yml 中的配置。