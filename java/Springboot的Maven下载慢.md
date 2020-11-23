# Maven下载慢的解决方法

maven的settings.xml中追加下面mirrors

```xml
<mirror>
    <id>alimaven</id>
    <name>aliyun maven</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    <mirrorOf>central</mirrorOf>
</mirror>
 
<mirror>
    <id>uk</id>
    <mirrorOf>central</mirrorOf>
    <name>Human Readable Name for this Mirror.</name>
    <url>http://uk.maven.org/maven2/</url>
</mirror>
 
<mirror>
    <id>CN</id>
    <name>OSChina Central</name>
    <url>http://maven.oschina.net/content/groups/public/</url>
    <mirrorOf>central</mirrorOf>
</mirror>
 
<mirror>
    <id>nexus</id>
    <name>internal nexus repository</name>
    <url>http://repo.maven.apache.org/maven2</url>
    <mirrorOf>central</mirrorOf>
</mirror>

```