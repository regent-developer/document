# nginx代理MySQL连接并限制可访问IP

## 前提

在configure时添加`--with-stream`来进行构建

## 配置

```
stream  {
    allow 192.168.0.1;
    deny all;
    server {
        listen 3306;
        proxy_pass 192.168.0.2:3306;
    }
}
```

