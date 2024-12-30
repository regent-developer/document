# 多模块maven下pom模块版本的统一升级

```bash
# 更新项目版本号
mvn versions:set -DnewVersion=x.x.x
# 更新子模块版本号
mvn -N versions:update-child-modules
# 提交版本号更新
mvn versions:commit
```