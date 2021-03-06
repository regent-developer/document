# kafka

Kafka 是最初由 Linkedin 公司开发，是一个分布式、分区的、多副本的、多订阅者，基于 zookeeper 协调的分布式日志系统（也可以当做 MQ 系统），常见可以用于 web/nginx 日志、访问日志，消息服务等等，Linkedin 于 2010 年贡献给了 Apache 基金会并成为顶级开源项目。

主要应用场景是：日志收集系统和消息系统。

Kafka 主要设计目标如下：

- 以时间复杂度为 O(1)的方式提供消息持久化能力，即使对 TB 级以上数据也能保证常数时间的访问性能。
- 高吞吐率。即使在非常廉价的商用机器上也能做到单机支持每秒 100K 条消息的传输。
- 支持 Kafka Server 间的消息分区，及分布式消费，同时保证每个 partition 内的消息顺序传输。
- 同时支持离线数据处理和实时数据处理。
- Scale out:支持在线水平扩展。

## 消息系统介绍

### 点对点消息传递模式

在点对点消息系统中，消息持久化到一个队列中。此时，将有一个或多个消费者消费队列中的数据。但是一条消息只能被消费一次。当一个消费者消费了队列中的某条数据之后，该条数据则从消息队列中删除。该模式即使有多个消费者同时消费数据，也能保证数据处理的顺序。

### 发布-订阅消息传递模式

在发布-订阅消息系统中，消息被持久化到一个 topic 中。与点对点消息系统不同的是，消费者可以订阅一个或多个 topic，消费者可以消费该 topic 中所有的数据，同一条数据可以被多个消费者消费，数据被消费后不会立马删除。在发布-订阅消息系统中，消息的生产者称为发布者，消费者称为订阅者。

## Kafka 的优点

- 解耦
- 冗余（副本）
- 扩展性
- 灵活性&峰值处理能力
- 可恢复性
- 顺序保证
- 缓冲
- 异步通信

## Kafka 术语

- broker
- Topic
- Partition
- Producer
- Consumer
- Consumer Group
- Leader
- Follower

## 下载地址

http://kafka.apache.org/downloads

## 部署

### windows

### linux
