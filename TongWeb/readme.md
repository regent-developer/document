# 东方通Web中间件

# 安装
```shell
chomd 755 -R Install_TW7.0.3.0_Enterprise_Linux.bin

./Install_TW7.0.3.0_Enterprise_Linux.bin
```

# 启动
```shell
date -s "2020-09-01 11:11:11"

./startserver.sh
```
# 部署SpringBoot的war时遇到的问题

## jCacheCacheManager异常
直接删除TongWeb/lib下的hazelcast-3.10.5.jar或tongdatagrid-1.2.jar（但不能使用session复制）

## Unable to process Jar entry [module-info.class] from Jar...
在tongweb.properties的tongweb.util.scan.StandardJarScanFilter.jarsToSkip追加出现的jar包文件

## JPA冲突
TongWeb自带JPA，所以如果项目用的是hibernate的JPA 如:Spring Data JPA，那么就会冲突  
在external.vimoptions文件中修改&增加下面设置
* -DWebModuleOnly=true
* -DenableJPA=false

