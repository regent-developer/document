# Springboot使用外部配置文件

```bash
// 运行使用的就是同目录下的application.yml，经过测试放config/application.yml，也会直接使用config/application.yml下的yml
java -jar xxx.jar



// 命令启动，加载尾部application.yml配置文件，会把内部的覆盖掉，成功使用外部文件
java -jar xxx.jar --spring.config.location=config/application.yml

```