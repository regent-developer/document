## Spring Boot之多线程、异步：@Async

@Service
@Async
public class AsyncTaskService2 {
    public Future<String> asyncTask1() throws InterruptedException {
        Thread.sleep(10000);//模拟阻塞操作
        System.out.println(new Date() + ": asyncTask1 complete");
        return new AsyncResult<String>("asyncTask1 complete");
    }

    public Future<String> asyncTask2() throws InterruptedException {
        Thread.sleep(10000);//模拟阻塞操作
        System.out.println(new Date() + ": asyncTask1 complete");
        return new AsyncResult<String>("asyncTask1 complete");
    }

    public Future<String> asyncTask3() throws InterruptedException {
        Thread.sleep(10000);//模拟阻塞操作
        System.out.println(new Date() + ": asyncTask1 complete");
        return new AsyncResult<String>("asyncTask1 complete");
    }
}

@RestController
@RequestMapping("/async/complex")
public class AsyncTaskController {
    @Autowired
    private AsyncTaskService2 asyncTaskService2;

    public static String status = "async tasks are not triggered.";
    public static Future<String> task1;
    public static Future<String> task2;
    public static Future<String> task3;

    @GetMapping("/task")
    @ResponseBody
    public String execute() throws InterruptedException {
        long startTimeStamp = System.currentTimeMillis();
        task1 = asyncTaskService2.asyncTask1();
        task2 = asyncTaskService2.asyncTask2();
        task3 = asyncTaskService2.asyncTask3();
        long endTimeStamp = System.currentTimeMillis();
        status = "async tasks are doing.";
        String message = "async tasks are triggered successfully, duration: " + (endTimeStamp - startTimeStamp) + " ms";
        System.out.println(message);
        return message;
    }

    @GetMapping("/task/status")
    @ResponseBody
    public String getTasksStatus() {
        assert task1 != null;
        if (task1.isDone() && task2.isDone() && task3.isDone()) {
            status = "async tasks are done.";
        }
        return status;
    }

    @GetMapping("/task/status/{taskId}")
    @ResponseBody
    public Boolean getTaskStatus(@PathVariable(name = "taskId") int taskId) {
        boolean taskStatus = false;
        switch (taskId) {
            case 1:
                taskStatus = task1.isDone();
                break;
            case 2:
                taskStatus = task2.isDone();
                break;
            case 3:
                taskStatus = task3.isDone();
                break;
        }
        return taskStatus;
    }
}
