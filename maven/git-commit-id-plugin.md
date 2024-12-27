# git-commit-id-plugin

## 1. 简介

git-commit-id-plugin 是一个 Maven 插件，用于在构建过程中生成 git 信息，并将其嵌入到构建产物中。这些信息包括 git 提交的哈希值、分支名称、构建时间等。

* 明确部署的版本  
  git-commit-id:revision：将构建时的信息保存到指定文件中或maven的属性中，默认绑定生命周期的阶段（phase）：initialize
* 校验属性是否符合预期值  
  git-commit-id:validateRevision：校验属性是否符合预期值，默认绑定阶段：verify
  
## 2. 使用方法

### 2.1 获取git仓库以及提交相关信息，在运行时可以动态获取

```xml
<plugin>
    <groupId>com.demo</groupId>
    <artifactId>git-commit-id-plugin</artifactId>
    <version>2.2.5</version>
    <executions>
        <execution>
            <id>get-the-git-infos</id>
            <!-- 默认绑定阶段initialize -->
            <phase>initialize</phase>
            <goals>
            	<!-- 目标：revision -->
                <goal>revision</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
    	<!-- 检查的仓库根目录，${project.basedir}：项目根目录，即包含pom.xml文件的目录 -->
        <dotGitDirectory>${project.basedir}/.git</dotGitDirectory>
        <!-- false：扫描路径时不打印更多信息，默认值false，可以不配置 -->
        <verbose>false</verbose>
        <!-- 定义插件中所有时间格式，默认值：yyyy-MM-dd’T’HH:mm:ssZ -->
        <dateFormat>yyyy-MM-dd HH:mm:ss</dateFormat>
        <!-- git属性文件中各属性前缀，默认值git，可以不配置 -->
        <prefix>git</prefix>
        <!-- 生成git属性文件，默认false：不生成 -->
        <generateGitPropertiesFile>true</generateGitPropertiesFile>
        <!-- 生成git属性文件路径及文件名，默认${project.build.outputDirectory}/git.properties -->
        <generateGitPropertiesFilename>${project.build.outputDirectory}/git.properties</generateGitPropertiesFilename>
        <!-- 生成git属性文件格式，默认值properties -->
        <format>json</format>
        <!-- 配置git-describe命令 -->
        <gitDescribe>
            <skip>false</skip>
            <always>false</always>
            <dirty>-dirty</dirty>
        </gitDescribe>
    </configuration>
</plugin>
```

读取打包后的git.properties文件内容，可以自定义一个方法实现即可
```java
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/version")
public class VersionController {

	@GetMapping("/info")
    public Map<String, Object> getVersionInfo() {
        return readGitProperties();
    }

    private Map<String, Object> readGitProperties() {
        InputStream inputStream = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            inputStream = classLoader.getResourceAsStream("git.properties");
			// 读取文件内容，自定义一个方法实现即可
            String versionJson = FileUtils.getStringFromStream(inputStream);
            JSONObject jsonObject = JSON.parseObject(versionJson);
            Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
            if (CollectionUtils.isNotEmpty(entrySet)) {
                return entrySet.stream()
                		.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (o, n) -> n));
            }
        } catch (Exception e) {
            log.error("get git version info fail", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.error("close inputstream fail", e);
            }
        }
        return new HashMap<>();
    } 
}
```

### 2.2 配置打包名称（添加git版本信息）
```xml
<plugin>
    <groupId>com.demo</groupId>
    <artifactId>myproject</artifactId>
    <!-- 拼接最后一次git提交的版本号，默认7位 -->
    <version>1.0.0-${git.commit.id.abbrev}</version>

    <build>
        <plugins>
            <!-- git-commit-id插件，配置同示例2.1 -->
            <plugin>
                <groupId>pl.project13.maven</groupId>
                <artifactId>git-commit-id-plugin</artifactId>
                <version>2.2.5</version>
                <executions>
                    <execution>
                        <id>get-the-git-infos</id>
                        <phase>initialize</phase>
                        <goals>
                            <goal>revision</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <dotGitDirectory>${project.basedir}/.git</dotGitDirectory>
                    <prefix>git</prefix>
                    <verbose>false</verbose>
                    <dateFormat>yyyy-MM-dd HH:mm:ss</dateFormat>
                    <generateGitPropertiesFile>true</generateGitPropertiesFile>
                    <generateGitPropertiesFilename>${project.build.outputDirectory}/git.properties</generateGitPropertiesFilename>
                    <format>json</format>
                    <!-- git.commit.id.abbrev属性值的长度，取值范围在[2, 40]，默认值7 -->
                    <abbrevLength>7</abbrevLength>
                    <gitDescribe>
                        <skip>false</skip>
                        <always>false</always>
                        <dirty>-dirty</dirty>
                    </gitDescribe>
                </configuration>
            </plugin>
        </plugins>
    </build>
</plugin>
```

### 2.3 校验Git属性

```xml
<plugin>
    <groupId>pl.project13.maven</groupId>
    <artifactId>git-commit-id-plugin</artifactId>
    <version>2.2.5</version>
    <executions>
        <execution>
            <id>get-the-git-infos</id>
            <phase>initialize</phase>
            <goals>
                <goal>revision</goal>
            </goals>
        </execution>
        <!-- 绑定validateRevision目标到package阶段 -->
        <execution>
            <id>validate-the-git-infos</id>
            <phase>package</phase>
            <goals>
                <goal>validateRevision</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <dotGitDirectory>${project.basedir}/.git</dotGitDirectory>
        <verbose>false</verbose>
        <dateFormat>yyyy-MM-dd HH:mm:ss</dateFormat>
        <prefix>git</prefix>
        <generateGitPropertiesFile>true</generateGitPropertiesFile>
        <generateGitPropertiesFilename>${project.build.outputDirectory}/git.properties</generateGitPropertiesFilename>
        <format>json</format>
        <gitDescribe>
            <skip>false</skip>
            <always>false</always>
            <dirty>-dirty</dirty>
        </gitDescribe>
		<!-- 定义需要校验的属性 -->	
        <validationProperties>
            <validationProperty>
            	<!-- 校验失败时提示使用 -->
                <name>validating git dirty</name>
                <!-- 需要校验的属性 -->
                <value>${git.dirty}</value>
                <!-- 期望的属性值：false -->
                <shouldMatchTo>false</shouldMatchTo>
            </validationProperty>
        </validationProperties>
        <!-- 配置校验的属性值与期望值不一致是否构建失败，默认值true：失败，false：继续构建 -->
        <validationShouldFailIfNoMatch>true</validationShouldFailIfNoMatch>
    </configuration>
</plugin>
```