# nginx各种配置

## 静态文件服务器
```
server {
    listen 80;
    server_name your_domain.com;

    location /static {
        root /path/to/static/files;
    }
}

```

## 反向代理服务器
```
server {
    listen 80;
    server_name your_domain.com;

    location / {
        proxy_pass http://backend_server;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
    # 配置多个代理
    location /blog1 {
        proxy_pass http://blog1_backend_address;
    }

    location /blog2 {
        proxy_pass http://blog2_backend_address;
    }

    location /blog3 {
        proxy_pass http://blog3_backend_address;
    }
}

```

## 负载均衡
```
# 轮询（Round Robin）
upstream backend {
    server backend1.example.com;
    server backend2.example.com;
    server backend3.example.com;
}

location / {
    proxy_pass http://backend;
}

# IP哈希（IP Hash）
upstream backend {
    ip_hash;
    server backend1.example.com;
    server backend2.example.com;
    server backend3.example.com;
}

location / {
    proxy_pass http://backend;
}

# 加权轮询（Weighted Round Robin）
upstream backend {
    server backend1.example.com weight=3;
    server backend2.example.com weight=2;
    server backend3.example.com weight=1;
}

location / {
    proxy_pass http://backend;
}

# 最小连接数（Least Connections）
upstream backend {
    least_conn;
    server backend1.example.com;
    server backend2.example.com;
    server backend3.example.com;
}

location / {
    proxy_pass http://backend;
}

# URL哈希（Hash）
upstream backend {
    hash $request_uri;
    server backend1.example.com;
    server backend2.example.com;
    server backend3.example.com;
}

location / {
    proxy_pass http://backend;
}

# 随机（Random）
upstream backend {
    random;
    server backend1.example.com;
    server backend2.example.com;
    server backend3.example.com;
}

location / {
    proxy_pass http://backend;
}

# 最快响应时间（Least Time）
upstream backend {
    least_time first_byte;
    server backend1.example.com;
    server backend2.example.com;
    server backend3.example.com;
}

location / {
    proxy_pass http://backend;
}

```

## 缓存
```
# 基本缓存配置
proxy_cache_path /path/to/cache levels=1:2 keys_zone=my_cache:10m;

server {
    location / {
        proxy_cache my_cache;
        proxy_cache_valid 200 1h;  # 缓存200响应1小时
        proxy_cache_valid 404 1m;  # 缓存404响应1分钟
        proxy_pass http://backend;
    }
}

# 忽略一些请求参数的缓存
proxy_cache_key "$host$request_uri $cookie_user $request_method";

# 动态刷新缓存
location / {
    proxy_cache my_cache;
    proxy_cache_valid 200 1h;
    proxy_cache_background_update on;
    proxy_cache_lock on;
    proxy_pass http://backend;
}

# 缓存大小限制
proxy_cache_path /path/to/cache levels=1:2 keys_zone=my_cache:10m max_size=1g inactive=7d;

server {
    location / {
        proxy_cache my_cache;
        proxy_cache_valid 200 1h;
        proxy_pass http://backend;
    }
}

```

## SSL/TLS 加密
```
server {
    listen 443 ssl;
    server_name your_domain.com;

    ssl_certificate /path/to/certificate.crt;
    ssl_certificate_key /path/to/private.key;

    location / {
        proxy_pass http://backend_server;
    }
}

```

## URL 重写
```
server {
    listen 80;
    server_name your_domain.com;

    location /old-url {
        rewrite /old-url/(.*) /new-url/$1 permanent;
    }

    location / {
        proxy_pass http://backend_server;
    }
}

```

##  HTTP/2
```
server {
    listen 443 ssl http2;
    server_name your_domain.com;

    ssl_certificate /path/to/certificate.crt;
    ssl_certificate_key /path/to/private.key;
    
	location / {
	    proxy_pass http://backend_server;
	}
}

```

## WebSocket
```
server {
    listen 80;
    server_name your_domain.com;

    location /websocket {
        proxy_pass http://backend_server;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "Upgrade";
    }
}

```

## 反向代理缓存
```
http {
    proxy_cache_path /path/to/cache levels=1:2 keys_zone=my_cache:10m;

    server {
        listen 80;
        server_name your_domain.com;

        location / {
            proxy_cache my_cache;
            proxy_pass http://backend_server;
        }
    }
}

```

## 安全限制
```
server {
    listen 80;
    server_name your_domain.com;
	# 限制ip地址
    location / {
        allow 192.168.1.0/24;
        deny all;
        proxy_pass http://backend_server;
    }
}

# allow 192.168.1.0/24: 这个指令允许来自192.168.1.0/24网络（也就是IP地址从192.168.1.1到192.168.1.254的任何地址）的请求。

```

## gzip 压缩
```
http {
    gzip on;
    gzip_types text/plain text/css application/javascript;

    server {
        listen 80;
        server_name your_domain.com;

        location / {
            proxy_pass http://backend_server;
        }
    }
}

# 启用Gzip压缩
gzip on;
gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;

# 压缩级别
gzip_comp_level 5;

# 最小文件大小
gzip_min_length 1000;

# 禁用压缩的User-Agent
   gzip_disable "MSIE [1-6]\.";

# 条件性压缩
gzip_proxied any;

# 启用对Precompressed文件的支持
gzip_static on;

```

## 请求限速
```
http {
    limit_req_zone $binary_remote_addr zone=my_zone:10m rate=1r/s;

    server {
        listen 80;
        server_name your_domain.com;

        location / {
            limit_req zone=my_zone burst=5;
            proxy_pass http://backend_server;
        }
    }
}

# 基于连接数的限速
limit_conn_zone $binary_remote_addr zone=addr:10m;
limit_conn addr 10;

# 基于请求频率的限速
limit_req_zone $binary_remote_addr zone=one:10m rate=1r/s;
limit_req zone=one burst=5;

# 基于缓存大小的限速
limit_rate 100k;

# 基于连接速率的限速
limit_rate_after 1m;
limit_rate 500k;


# 基于缓冲区大小的限速
limit_req_zone $binary_remote_addr zone=buf:10m rate=10r/s;
limit_req zone=buf burst=20 nodelay;

```

## 日志记录
```
http {
    access_log /var/log/nginx/access.log;
    error_log /var/log/nginx/error.log;

    server {
        listen 80;
        server_name your_domain.com;

        location / {
            proxy_pass http://backend_server;
        }
    }
}

```

## SSL 证书续订
```
server {
    listen 443 ssl;
    server_name your_domain.com;

    ssl_certificate /path/to/certificate.crt;
    ssl_certificate_key /path/to/private.key;

    ssl_certificate_renewal 30 days;

	location / {
	    proxy_pass http://backend_server;
	}
}

```