# Springboot定时任务

## 开启定时任务
添加注解@EnableScheduling

## 固定周期定时任务

```java
@Component
public class SpringStaticCronTask {
    // 每天9时—18时之间每小时执行
    @Scheduled(cron = "0 0/60 9-18 * * ?")
    public void staticCornTask() {
        logger.debug("staticCronTask is running...");
    }
 
 ```

## 动态修改定时周期

实现SchedulingConfigurer 接口，重写 configureTasks方法

```java

@Component
public class SpringDynamicCronTask implements SchedulingConfigurer {

    private static String cron = "0/5 * * * * ?";
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(() -> {
            // 任务逻辑
            logger.error("dynamicCronTask is running...");
        }, triggerContext -> {
            // 任务触发，在这里可修改任务的执行周期,因为每次调度都会执行这里
            CronTrigger cronTrigger = new CronTrigger(cron);
            return cronTrigger.nextExecutionTime(triggerContext);
        });
    }
    public SpringDynamicCronTask() {
        //模拟业务修改周期,可以在具体业务中修改参数cron
        new Thread(() -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 每天9时—18时之间每小时执行
            cron = "0 0/60 9-18 * * ?";
        }).start();
    }
}

```