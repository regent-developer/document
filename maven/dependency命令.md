# dependency命令
执行mvn dependency:analyze命令后，我们会得到一个详细的依赖分析报告，报告中主要包含两个重要部分：Used undeclared dependencies found和Unused declared dependencies found。

## Used undeclared dependencies found 
这一部分指的是项目中实际使用了某个依赖包，但并未在pom.xml文件中显式声明。这些依赖包可能是通过其他依赖间接引入的。

## Unused declared dependencies found 
这一部分则是指你在项目的pom.xml文件中声明了某个依赖包，但在实际的项目代码中并未使用到它。这些依赖包可能是不必要的，可以考虑从pom.xml中移除。
