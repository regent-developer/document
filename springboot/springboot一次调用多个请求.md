# springboot一次调用多个请求

在Java Spring Boot中，我们可以使用CompletableFuture类和@Async注解来实现异步编程。CompletableFuture是Java提供的一个实现了CompletionStage接口的类，它提供了一系列方法来处理异步操作的结果。@Async注解可以用来标记一个方法是异步的，Spring Boot会自动创建一个线程来执行这个方法。



```java
@Service
public class TestService {

    @Async
    public CompletableFuture<Test1> getTest1(int id) {
        // 调用第一个请求，获取Test1信息
        // ...

        return CompletableFuture.completedFuture(test1);
    }

    @Async
    public CompletableFuture<Test2> getTest2(int id) {
        // 调用第二个请求，获取Test2信息
        // ...

        return CompletableFuture.completedFuture(test2);
    }
}

```



```java
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test/{id}")
    public Test getTest(@PathVariable("id") int id) throws ExecutionException, InterruptedException {
        // 调用异步请求
        CompletableFuture<User> test1Future = testService.getTest1(id);
        CompletableFuture<Order> test2Future = testService.getTest2(id);

        // 等待两个异步请求执行完成
        CompletableFuture.allOf(test1Future, test2Future).join();

        // 获取异步请求的结果
        Test1 test1 = test1Future.get();
        Test2 test2 = test2Future.get();

        // 处理结果
        // ...

        return test;
    }
}

```

