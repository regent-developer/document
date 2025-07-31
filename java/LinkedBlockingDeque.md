# LinkedBlockingDeque
LinkedBlockingDeque 是 Java 集合框架中的一种线程安全的双端队列（Deque），它继承自 AbstractQueue 和 BlockingDeque 接口，具备以下特点：

线程安全：通过内部锁机制确保多线程环境下的数据一致性。

双端操作：可以高效地从队列两端进行插入和删除操作。

阻塞式操作：在队列为空时，取元素操作会阻塞，直到有元素可用。

高性能：基于链表结构，适合高并发场景下的数据传输。

在 Spring Boot 中，LinkedBlockingDeque 可以用来模拟消息队列的行为，作为生产者和消费者之间的中间存储结构，实现异步任务处理。