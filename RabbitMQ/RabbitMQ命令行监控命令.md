# RabbitMQ命令行监控命令

## 查看RabbitMQ服务器状态

```shell
rabbitmqctl status
```

## 查看RabbitMQ服务器的连接数

```shell
rabbitmqctl list_connections name user state channels
```

## 查看RabbitMQ服务器的队列信息

```shell
 rabbitmqctl list_queues name messages consumers memory   
```

## 查看RabbitMQ服务器的交换器信息

```shell
rabbitmqctl list_exchanges name    
```

## 查看RabbitMQ服务器的用户和权限

```shell
rabbitmqctl list_users

rabbitmqctl list_permissions    
```

## 查看RabbitMQ服务器的节点信息

```shell
rabbitmqctl cluster_status    
```