# spring-banner-plugin
spring-banner-plugin是用于生成Spring Boot应用启动时显示的Banner信息的Maven插件，主要用于在应用启动时打印项目名称、版本等信息。

<plugin>
    <groupId>ch.acanda.maven</groupId>
    <artifactId>spring-banner-plugin</artifactId>
    <version>1.6.0</version>
    <executions>
    <execution>
    <id>generate-spring-banner</id>
    <phase>generate-resources</phase>
    <goals>
    <goal>generate</goal>
    </goals>
    </execution>
    </executions>
    <configuration>
    <text>${project.name}</text>
    <outputDirectory>${project.build.outputDirectory}</outputDirectory>
    <filename>banner.txt</filename>
</configuration>
``` ‌:ml-citation{ref="1" data="citationList"}

