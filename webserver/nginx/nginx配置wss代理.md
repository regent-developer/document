# nginx配置wss代理


server {    listen 443 ssl;    server_name test.example.com;
    ssl_certificate /certs/cert.pem;    ssl_certificate_key /certs/cert.key;
    location /api {        proxy_pass http://127.0.0.1:8082;        proxy_set_header Host $host;        proxy_set_header X-Real-IP $remote_addr;    }
    location /ws { # 升级协议头 websocketproxy_http_version 1.1;proxy_set_header Upgrade $http_upgrade;   proxy_set_header Connection upgrade;#转发到ws服务器proxy_pass http://127.0.0.1:8082;      #------对请求头等的一些设置，可根据情况进行配置------proxy_set_header Host $host;proxy_set_header X-Real_IP $remote_addr;proxy_set_header X-Forwarded-For $remote_addr:$remote_port;#关闭重定向proxy_redirect off;client_max_body_size 50m;proxy_connect_timeout 600;proxy_read_timeout 600;proxy_send_timeout 600;    }}