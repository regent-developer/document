# apache设置反向代理（http）


## 修改\conf\httpd.conf文件
```
# 设置SRVROOT为当前apache的安装目录
Define SRVROOT "D:/xxx/xxx"

# 打开下面模块的注释
LoadModule proxy_module modules/mod_proxy.so
LoadModule proxy_http_module modules/mod_proxy_http.so

# 打开下面模块的注释
Include conf/extra/httpd-vhosts.conf
```

## 修改\conf\extra\httpd-vhosts.conf文件

```xml
<VirtualHost *:80>	
    ProxyRequests Off	
    ProxyPreserveHost On	
    ProxyPass /xxx/ http://localhost:8080/xxx/	
    ProxyPassReverse /xxx/ http://localhost:8080/xxx/	
</VirtualHost>	
```

