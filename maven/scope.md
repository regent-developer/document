# scope

## 依赖范围

| 依赖范围 | 编译时 | 测试时 | 运行时 | 例子 |
| :--- | :--- | :--- | :--- | :--- |
| compile | Y | Y | Y | log4j|
| test | - | Y | - | junit |
| provided | Y | Y | - | servlet-api |
| runtime | - | Y | Y | JDBC-dirver |
| system | Y | Y | - | 非Maven仓库本地依赖 |
| import | - | - | - | |

### compile

默认范围，编译、测试、运行时都有效。

### test

只在测试时有效。

### provided

编译和测试时有效，运行时无效。例如：servlet-api，运行时容器已经提供。

### runtime

只在测试和运行时有效，编译时无效。例如：JDBC-dirver。

### system

只在编译和测试时有效，运行时无效。与provided类似，但必须通过systemPath指定本地路径。

### import

只在编译时有效，运行时无效。例如：Maven依赖的依赖。