# apache配置https

### 修改\conf\httpd.conf

```
# 定义SRVROOT变量，其值为"D:/Program Files/Apache24"
Define SRVROOT "D:/Program Files/Apache24"

# 加载ssl模块
LoadModule ssl_module modules/mod_ssl.so
# 加载socache_shmcb模块
load_module socache_shmcb_module modules/mod_socache_shmcb.so

# 引入 conf/extra/httpd-ssl.conf 文件
Include conf/extra/httpd-ssl.conf
```

### 修改\conf\extra\httpd-ssl.conf

```
SSLCerificateFile "conf/ssl.crt/server.crt"
SSLCertificateKeyFile "conf/ssl.key/server.key"
SSLCAertificateChainFile "conf/ssl.crt/ca.crt"

```

### 修改\conf\extra\httpd-vhosts.conf

```
<VirtualHost *:443>	
    ServerName localhost	
    SSLEngine on	
    SSLProxyEngine on	
    SSLCertificateFile "${SRVROOT}/conf/key/server.crt"	
    SSLCertificateKeyFile "${SRVROOT}/conf/key/server.key"	
	
    ProxyRequests Off	
    ProxyPreserveHost On	
	
    ProxyPass /xxx/ http://localhost:8080/xxx/	
    ProxyPassReverse /xxx/ http://localhost:8080/xxx/	
</VirtualHost>	
```