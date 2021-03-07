# kafka(docker)

## 镜像拉取

```shell
docker pull wurstmeister/zookeeper
docker pull wurstmeister/kafka
```

## 定义 docker-compose.yml

```xml
version: '3'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
  kafka:
    image: wurstmeister/kafka
    depends_on: [ zookeeper ]
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: localhost
      KAFKA_CREATE_TOPICS: "test:1:1"
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /home/kafka/docker.sock:/var/run/docker.sock
```

在 docker-compose.yml 文件目录进行服务打包

```shell
docker-compose build
```

## kafka 配置

## 启动服务

```bash
docker-compose up -d
```

## 启动测试

### 进入 kafka 容器

```bash
docker exec -it home_kafka_1 bash
```

### 创建 topic

```shell
$KAFKA_HOME/bin/kafka-topics.sh --create --topic topic --partitions 4 --zookeeper home_zookeeper_1:2181 --replication-factor 1
```

### 查看 topic

```bash
$KAFKA_HOME/bin/kafka-topics.sh --zookeeper home_zookeeper_1:2181 --describe --topic test
```

### 发布信息

```bash
$KAFKA_HOME/bin/kafka-console-producer.sh --topic=test --bootstrap-server  localhost:9092
```

### 接收消息

```shell
bash-4.4# $KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic test
```
