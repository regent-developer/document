# Fork/Join线程池框架

从 JDK 1.7 开始，引入了一种新的 Fork/Join 线程池框架，它可以把一个大任务拆成多个小任务并行执行，最后汇总执行结果。

Fork/Join采用的是分而治之的基本思想，分而治之就是将一个复杂的任务，按照规定的阈值划分成多个简单的小任务，然后将这些小任务的执行结果再进行汇总返回，得到最终的执行结果。

## 用法



* 测试类

```java
public class ForkJoinTest {

    public static void main(String[] args) throws Exception {
        // 创建2000个数组成的数组
        long[] array = new long[2000];
        
        // 1，记录for循环汇总计算的值
        long sourceSum = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
            sourceSum += array[i];
        }
        System.out.println("for循环汇总计算的值: " + sourceSum);

        // 2，fork/join汇总计算的值
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> taskFuture = forkJoinPool.submit(new SumTask(array, 0, array.length));
        System.out.println("fork/join汇总计算的值: " + taskFuture.get());
    }
}
```



* 实现可递归的多线程任务类

```java
public class SumTask extends RecursiveTask<Long> {

    /**
     * 最小任务数组最大容量
     */
    private static final int THRESHOLD = 500;

    private long[] array;
    private int start;
    private int end;

    public SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        // 检查任务是否足够小，如果任务足够小,直接计算
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += this.array[i];
            }
            return sum;
        }
        
        // 任务太大一分为二
        int middle = (end + start) / 2;
        
        // 拆分执行
        SumTask leftTask = new SumTask(this.array, start, middle);
        leftTask.fork();
        SumTask rightTask = new SumTask(this.array, middle, end);
        rightTask.fork();
        System.out.println("进行任务拆分，leftTask数组区间：" + start + "," + middle + "；rightTask数组区间：" + middle + "," + end);
        
        // 汇总结果
        return leftTask.join() +  rightTask.join();
    }
}
```



## 原理说明

Fork/Join框架的使用包含两个核心类ForkJoinPool和ForkJoinTask

* ForkJoinPool：是一个负责执行任务的线程池，内部使用了一个无限队列来保存需要执行的任务，而执行任务的线程数量则是通过构造函数传入，如果没有传入指定的线程数量，默认取当前计算机可用的 CPU 核心量。
* ForkJoinTask：是一个负责任务的拆分和合并计算结果的抽象类，通过它可以完成将大任务分解成多个小任务计算，最后将各个任务执行结果进行汇总处理。