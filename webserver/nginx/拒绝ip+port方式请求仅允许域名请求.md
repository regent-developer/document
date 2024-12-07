# 拒绝ip+port方式请求仅允许域名请求

```
server {
    
    listen 80 default_server;
    
    server_name _; # 使用下划线(_)表示匹配任何未定义的域名
    
    return 444; # 使用444状态码关闭连接
 
}
```