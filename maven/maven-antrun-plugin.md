# maven-antrun-plugin

## pom.xml
```xml
<plugin>
  <artifactId>maven-antrun-plugin</artifactId>
  <version>3.1.0</version>
  <executions>
    <execution>
      <phase>test</phase> <!--  指定在哪个阶段执行 -->
      <goals>
        <goal>run</goal> <!--  指定要执行的goal -->
      </goals>
      <configuration>
        <target>

          <!--
            Place any Ant task here. You can add anything
            you can add between <target> and </target> in a
            build.xml.
          -->

        </target>
      </configuration>
    </execution>
  </executions>
</plugin>

```

* 拷贝文件
  ```xml
  <copy todir="${project.build.directory}" overwrite="true">
   <fileset file="${basedir}/xxx/xxx.xml"/>
  </copy>

  ```
* 拷贝文件夹
  ```xml
  <copy todir="${project.build.directory}/xxx" overwrite="true">
   <fileset dir="${basedir}/xxx"/>
  </copy>
  ```

* 执行命令
  ```xml
   <ftp action="send" server="192.168.1.2" remotedir="/home/" userid="root" password="123456" depends="yes" verbose="yes">
    <fileset dir="${project.build.directory}">
        <include name="*.jar" />
    </fileset>
   </ftp>

  ```