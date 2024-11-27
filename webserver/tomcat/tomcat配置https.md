# 配置https

* 配置文件：conf/server.xml

```
<Connector protocol="org.apache.coyote.http11.Http11NioProtocol"   	
        port="8443" minSpareThreads="5" maxSpareThreads="75"   	
        enableLookups="true" disableUploadTimeout="true"     	
        acceptCount="100"  maxThreads="200"   	
        scheme="https"  SSLEnabled="true" secure="true"	
        clientAuth="false" sslProtocol="TLS"   	
        keystoreFile="c:/tomcat.keystore"     	
        keystorePass="123456"/> 	
```