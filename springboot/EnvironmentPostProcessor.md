# EnvironmentPostProcessor
EnvironmentPostProcessor 是 Spring Boot 提供的一个扩展接口（注意：并非注解），允许开发者在 Spring 应用环境（Environment）初始化完成后、应用上下文（ApplicationContext）创建之前，动态修改或增强环境配置。其核心功能包括：

动态加载配置：从数据库、远程服务或自定义文件中加载配置并注入环境。
覆盖默认配置：调整或覆盖 application.properties/application.yml 中的属性。
多环境适配：根据运行环境（如开发、生产）动态切换配置源。
高优先级属性控制：通过 addFirst 或 addLast 方法控制属性源的加载顺序。

https://mp.weixin.qq.com/s/0dLhnUer7X5t1oO90-goRA