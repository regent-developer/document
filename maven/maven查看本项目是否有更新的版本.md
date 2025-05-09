# maven查看本项目是否有更新的版本

## 1. 查看本地仓库是否有更新的版本

```shell    # 查看本地仓库是否有更新的版本
mvn versions:display-dependency-updates
```

## 2. 查看远程仓库是否有更新的版本（指定仓库）

```shell
# 查看远程仓库是否有更新的版本
mvn versions:display-dependency-updates -DremoteRepositories=http://maven.aliyun.com/nexus/content/groups/public
```
## 3. 查看远程仓库是否有更新的版本（指定仓库和指定依赖）

```shell
# 查看远程仓库是否有更新的版本（指定仓库）
mvn versions:display-dependency-updates -DremoteRepositories=http://maven.aliyun.com/nexus/content/groups/public -DgroupId=com.alibaba -DartifactId=dubbo
```
## 4. 查看远程仓库是否有更新的版本（指定仓库和指定版本）

```shell
# 查看远程仓库是否有更新的版本（指定仓库和版本）
mvn versions:display-dependency-updates -DremoteRepositories=http://maven.aliyun.com/nexus/content/groups/public -DgroupId=com.alibaba -DartifactId=dubbo -Dversion=2.6.2
```
## 5. 自动更新到最新版本

```shell
# 自动更新到最新版本
mvn versions:use-latest-releases
```
