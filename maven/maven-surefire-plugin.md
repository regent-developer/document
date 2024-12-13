# maven-surefire-plugin

## 简介

maven-surefire-plugin是Maven的一个插件，用于执行单元测试。它支持多种测试框架，如JUnit、TestNG等。

## 主要功能：
* 执行单元测试：这是它的主要功能，可以自动发现并执行项目中的测试类。
* 报告生成：提供详细的测试结果报告，包括测试用例的数量、成功/失败/忽略的数量等。
* 集成测试框架：虽然默认支持 JUnit，但它也可以通过配置来支持其他的测试框架，如 TestNG。
* 定制测试执行：允许用户通过配置来定制测试的执行方式，例如指定特定的测试类或方法进行执行。
* 测试排除：可以排除某些测试类或方法，使其不在构建过程中执行。
* 跳过测试：可以通过配置来跳过测试的执行，这在某些情况下可能是有用的，例如在构建过程中不需要测试时。

## 配置参数：
* testFailureIgnore：设置为true时，测试失败不会导致构建失败。
* skip：设置为true时，跳过测试的执行。
* includes：指定要执行的测试类或方法的模式。
* excludes：指定要排除的测试类或方法的模式。

## 使用方法

在 Maven 的 `pom.xml` 文件中，添加以下配置：

```xml
<build>
  <plugins>
    <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <skip>true</skip><!-- 跳过测试代码的编译 mvn clean install -Dmaven.test.skip=true-->
        </configuration>
    </plugin>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>2.22.2</version>
      <configuration>
        <testFailureIgnore>true</testFailureIgnore> <!-- 设置为true以忽略测试失败 mvn clean install -Dmaven.test.failure.ignore=true-->
        <skip>true</skip> <!-- 设置为true以跳过测试 mvn clean install -DskipTests-->
        <includes>
          <include>**/Test.java</include><!-- 任何子目录下所有命名以Test开头的Java类-->
          <include>**/*.Test.java</include><!-- 任何子目录下所有命名以Test结尾的Java类-->
          <include>**/*TestCase.java</include><!-- 任何子目录下所有命名以TestCase结尾的Java类-->
        </includes> 
        <excludes>
            <exclude>**/*IntegrationTest.java</exclude> <!-- 排除所有名称以 IntegrationTest 结尾的类 -->
        </excludes>
      </configuration>
    </plugin>
  </plugins>
</build>
```


