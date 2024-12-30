# jar导入到本地仓库

```bash
mvn install:install-file -DgroupId=maula -DartifactId=covout -Dversion=1 -Dfile="xxx.jar"  -Dpackaging=jar -DgeneratePom=true
```