# ElasticSearch6.8.10

## 系统环境
```bash
uname -a

cat /proc/version

```

## 配置服务

* docker-compose.yml
```yml
  my-elasticsearch:
    image: elasticsearch:6.8.10
    container_name: my-elasticsearch
    environment:
      ES_JAVA_OPTS: -Djava.net.preferIPv4Stack=true -Xms1g -Xmx1g
      transport.host: 0.0.0.0
      discovery.zen.minimum_master_nodes: 1
      discovery.zen.ping.unicast.hosts: elasticsearch
      TZ: Asia/Shanghai
    volumes:
      - ./elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml
      - ./elasticsearch/data:/usr/share/elasticsearch/data
      - ./elasticsearch/logs:/usr/share/elasticsearch/logs
      - ./elasticsearch/plugins:/usr/share/elasticsearch/plugins
    ports:
      - "9200:9200"
      - "9300:9300"

```

* elasticsearch.yml
```yml
# 设置Elasticsearch节点的发现类型为单节点
discovery.type: "single-node"
# 尝试锁定内存，以防止被交换到磁盘
bootstrap.memory_lock: true
# Elasticsearch在所有可用网络接口上侦听，可以从任何地址访问
network.host: 0.0.0.0
# Elasticsearch HTTP API的监听端口
http.port: 9200
# Elasticsearch节点之间通信的TCP端口
transport.tcp.port: 9300
# Elasticsearch日志文件的输出路径
path.logs: /usr/share/elasticsearch/logs
# 启用跨域资源共享（CORS）支持
http.cors.enabled: true
# 允许来自任何源的跨域请求
http.cors.allow-origin: "*"
# 启用Elasticsearch安全性功能
xpack.security.enabled: true
# 允许包含 Authorization 头的跨域请求
http.cors.allow-headers: Authorization
# 启用节点之间传输层安全性（TLS/SSL）
xpack.security.transport.ssl.enabled: true
# 启用监控收集
xpack.monitoring.collection.enabled: true

```

## 部署服务

### 创建好对应的目录，并给予执行权限

```bash
mkdir -p /opt/docker/elasticsearch/config
mkdir -p /opt/docker/elasticsearch/data
mkdir -p /opt/docker/elasticsearch/logs
mkdir -p /opt/docker/elasticsearch/plugins

chown -R root:root elasticsearch
chmod 777 -R elasticsearch

```

### 启动服务
```bash
docker-compose -f docker-compose.yml up -d my-elasticsearch
```

## 修改密码
安全起见，所有的基础服务都开启认证并配置密码，进入容器，通过以下命令设置密码。
```bash
# 进入容器
docker exec -it my-elasticsearch /bin/bash
# 设置密码
elasticsearch-setup-passwords interactive

```

交互式设置密码
```bash
# 进入my-elasticsearch容器
docker exec -it my-elasticsearch /bin/bash

# 设置elasticsearch密码
elasticsearch-setup-passwords interactive
```

## 验证服务

查看节点健康状态：http://127.0.0.1:9200/_cat/health?v
查看节点索引：http://127.0.0.1:9200/_cat/indices?v
查看索引内容：http://127.0.0.1:9200/索引名称/_search?pretty

## 离线操作

```bash
# 保存elasticsearch:6.8.10镜像到elasticsearch.tar文件
docker save elasticsearch:6.8.10 -o elasticsearch.tar

# 从elasticsearch.tar文件加载镜像
docker load -i elasticsearch.tar 
```