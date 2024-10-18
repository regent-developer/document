# StopWatch

## 简介

StopWatch是Spring提供的一个用于性能监控的工具类，可以用来统计代码执行时间，方便我们进行性能调优。

## 使用

### 引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
```

### 使用示例

```java

import org.springframework.util.StopWatch;
public static void main(String[] args) throws InterruptedException {
    StopWatch stopWatch = new StopWatch();

    stopWatch.start("task1");
    System.out.println("当前任务名称："+stopWatch.currentTaskName());
    Thread.sleep(1000);
    stopWatch.stop();
    System.out.println("task1耗时毫秒："+stopWatch.getLastTaskTimeMillis());

    stopWatch.start("task2");
    System.out.println("当前任务名称："+stopWatch.currentTaskName());
    Thread.sleep(2000);
    stopWatch.stop();
    System.out.println("task2耗时毫秒："+stopWatch.getLastTaskTimeMillis());

    System.out.println("总任务数："+stopWatch.getTaskCount());
    System.out.println("总耗时毫秒："+stopWatch.getTotalTimeMillis());
    System.out.println("所有任务简要信息：\n"+stopWatch.shortSummary());
    System.out.println("所有任务详细信息：\n"+stopWatch.prettyPrint());
}

```
