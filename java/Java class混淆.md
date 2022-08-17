# Java class混淆（工具：ClassFinal）

ClassFinal是一款java class文件安全加密工具，支持直接加密jar包或war包，无需修改任何项目代码，兼容spring-framework；可避免源码泄漏或字节码被反编译。

Gitee: https://gitee.com/roseboy/classfinal

## 下载地址

https://gitee.com/roseboy/classfinal/blob/master/README.md#%E4%B8%8B%E8%BD%BD

## 混淆使用方法 

### 混淆命令方式
```shell
java -jar classfinal-fatjar.jar -file yourproject.jar -libjars a.jar,b.jar -packages com.yourpackage,com.yourpackage2 -exclude com.yourpackage.Main -pwd 123456 -Y
```

|参数|说明|
| ------ | ------ |
| -file | 加密的jar/war完整路径 |
| -packages | 加密的包名(可为空,多个用","分割) |
| -libjars | jar/war包lib下要加密jar文件名(可为空,多个用","分割) |
| -cfgfiles | 需要加密的配置文件，一般是classes目录下的yml或properties文件(可为空,多个用","分割) |
| -exclude | 排除的类名(可为空,多个用","分割) |
| -classpath | 外部依赖的jar目录，例如/tomcat/lib(可为空,多个用","分割) |
| -pwd | 加密密码，如果是#号，则使用无密码模式加密 |
| -code | 机器码，在绑定的机器生成，加密后只可在此机器上运行 |
| -Y | 无需确认，不加此参数会提示确认以上信息 |


#### 例子

```shell
java -jar classfinal-fatjar-1.2.1.jar -file xxx.jar -libjars a.jar,b.jar -packages com.xxx  -pwd 123456 -Y
```
### 命令提示方式

```shell
java -jar classfinal-fatjar-1.2.1.jar
```

* 说明：使用jd反编译加密后的jar，将不能查看代码实现（仅能看到类和方法名），但是使用父子pom的项目测试不能混淆。
  
## 执行加密后的jar

```shell
java -javaagent:yourproject-encrypted.jar -jar yourproject-encrypted.jar
```