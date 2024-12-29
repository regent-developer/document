package com.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 */
@Slf4j
public class ThreadPoolUtils {

    /**
     * 私有构造方法，防止实例化该工具类。
     */
    private ThreadPoolUtils() {
    }

    /**
     * 优雅地关闭线程池
     *
     * @param pool 需要关闭的线程池
     * @param shutdownTimeout 调用shutdown后等待线程池终止的时间
     * @param shutdownNowTimeout 调用shutdownNow后等待线程池终止的时间
     * @param timeUnit 时间单位
     */
    public static void shutdownPool(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout, TimeUnit timeUnit) {
        // 调用shutdown以开始优雅地关闭线程池
        pool.shutdown();
        try {
            // 等待所有任务完成或超时
            if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
                // 调用shutdownNow强制关闭线程池
                pool.shutdownNow();
                // 再次等待所有任务完成或超时
                if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
                    log.error("ThreadPoolUtils.shutdownPool.error");
                }
            }
        } catch (InterruptedException ie) {
            // 捕获中断异常并记录错误信息
            log.error("ThreadPoolUtils.shutdownPool.interrupted.error:{}", ie.getMessage(), ie);
            // 再次调用shutdownNow强制关闭线程池
            pool.shutdownNow();
            // 设置当前线程中断状态
            Thread.currentThread().interrupt();
        }
    }
}
