# Maven命令导出依赖jar文件

## 1. 下载依赖

```shell
mvn dependency:copy-dependencies -DoutputDirectory=lib
```

## 2. 下载依赖并排除指定依赖

```shell
mvn dependency:copy-dependencies -DoutputDirectory=lib -DincludeScope=runtime -DexcludeGroupIds=org.springframework.boot,org.springframework.cloud
```

## 3. 下载指定依赖

```shell
mvn dependency:copy -DoutputDirectory=lib -Dartifact=groupId:artifactId:version

最新版本：LATEST
```