# 剔除无用Jar引用

```bash
mvn dependency:analyze
```

* Used undeclared dependencies：未声明但已使用的依赖。
* Declared unused dependencies：声明但未使用的依赖。