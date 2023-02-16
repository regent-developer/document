# SpringBoot集成RabbitMQ

MQ全称为Message Queue，即消息队列。消息队列是在消息的传输过程中保存消息的容器。它是典型的：生产者、消费者模型。生产者不断向消息队列中生产消息，消费者不断的从队列中获取消息。因为消息的生产和消费都是异步的，而且只关心消息的发送和接收，没有业务逻辑的侵入，这样就实现了生产者和消费者的解耦  

RabbitMQ金融场景中经常使用 ，常作为交易数据作为数据传输管道， 具有较高的严谨性，数据丢失的可能性更小，具备更高的实时性，和Spring是统一厂商开发，后期支持比较好，目前最流行的，对容错性的处理比较完善  

RabbitMQ 支持发布订阅、轮询分发、公平分发、重发、消息拉取  

## SpringBoot整合RabbitMQ

### 依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

### 配置文件
```yml
spring:
  rabbitmq:
    username: guest
    password: guest
    virtual-host: /
    host: 通道运行的地址
    port: 5672
```
### 生产者配置类Config
```java
@Configuration
public class mtRabbitConfig {

	//正常通道的交换机
    @Bean
    public FanoutExchange mtExchange(){
        return new FanoutExchange("mt_fanout_exchange",true,false);
    }

	//死信通道的交换机
    @Bean
    public FanoutExchange mtDeadExchange(){
        return new FanoutExchange("mt_fanout_dead_exchange",true,false);
    }

	//正常通道，给消息设计过期时间，超过该时间未被消费，则进入指定的mt_fanout_dead_exchange
    @Bean
    public Queue mtQueue(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-message-ttl",5000);
        args.put("x-dead-letter-exchange","mt_fanout_dead_exchange");
        return new Queue("mt_queue",true,false,false,args);
    }

    @Bean
    public Queue mtDaedQueue(){
        return new Queue("mt_dead_queue",true,false,false);
    }

	//交换机与通道绑定
    @Bean
    public Binding mtBinding(){
        return BindingBuilder.bind(mtQueue()).to(mtExchange());
    }

    @Bean
    public Binding mtDeadBinding(){
        return BindingBuilder.bind(mtDaedQueue()).to(mtDeadExchange());
    }
}
```

### 生产者（模拟用户下单）
```java
@Autowired
    private RabbitTemplate rabbitTemplate;

    //模拟美团订单下单，若5秒不接单（消费）,则进入死信队列（加急派单）
    public void mtTakeOutOrder(String name, String food, String number) {
        UUID takeOutId = UUID.randomUUID();
        String orderTime = DateFormat.getDateTimeInstance().format(new Date());
        String exchangeName = "mt_fanout_exchange";
        String takeOutMes = "美团订单编号：" + takeOutId + " " + orderTime + " " + name + " " + food + " " + number;
        String routingKey = "";
        
		//将消息放入通道内
        Object result = rabbitTemplate.convertSendAndReceive(exchangeName, routingKey, takeOutMes);
        System.out.println("配送中心响应："+result);
    }

```

### 测试类
```java
@Test
    void mtTakeOutOrder() throws InterruptedException {
         takeOutOrder.mtTakeOutOrder("小张"+i, "麻辣烫", "10086");
 }
```

### 消费者（外卖接单中心）

#### 正常接单消费者
```java
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "mt_queue", autoDelete = "false"),
        exchange = @Exchange(value = "mt_fanout_exchange", type = ExchangeTypes.FANOUT)))
@Component
public class mtTakeOutDelivery {

    @RabbitHandler
    public String buyTrainTickets(String message) {
        System.out.println("正常美团外卖订单已接单：" + message);
        return "配送中心已接单";
    }
}
```
#### 加急接单消费者（死信队列内的消息）
```java
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "mt_dead_queue", autoDelete = "false"),
        exchange = @Exchange(value = "mt_fanout_dead_exchange", type = ExchangeTypes.FANOUT)))
@Component
public class mtDeadTakeOutDelivery {

    @RabbitHandler
    public String buyTrainTickets(String message) {
        System.out.println("加急饿了么外卖订单已接单：" + message);
        return "配送中心已接单";
    }
}

```
