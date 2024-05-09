# JVM参数

|参数	|说明|
| ------------- |:-------------:    |
| -XX:+UseParNewGC	| 使用ParNew作为垃圾回收器
| -XX:HandlePromotionFailure	| 设置如果老年代内存<新生代所有对象大小时要不要Full GC，如果没设置就要Fuul GC，设置了就不要Full GC，而是进一步检查。
| -Xmx, -Xms, -Xmn, -XX:PermSize, -XX:MaxPermSize	| 内存相关参数
| -XX:SurvivorRatio	| 设置Eden区和S区的比例，如果-XX:SurvivorRatio=8，则Eden区与S区的比例是8:1:1
| -XX:ParallelGCThreads	| 设置ParNew垃圾回收线程数
| -XX:MaxTenuringThreshold	| 判断对象为老年代的年龄的参数
| -XX:PretenureSizeThreshold	| 判断对象为大对象进入老年代的参数
| -XX:CMSInitiatingOccupancyFraction	| 老年代内存达到多少比例触发CMS垃圾回收
| -XX:CMSFullGCsBeforeCompaction	| 执行多少次Full GC后执行一次内存碎片整理，默认是0
| -XX:+UseConcMarkSweepGC	| CMS: Concurrent Mark Sweep；并发标记清理垃圾回收器
| -XX:+UseG1GC	| 设置使用G1垃圾回收器
| -XX:G1HeapRegionSize	| 指定Region大小
| -XX:G1NewSizePercent	| 新生代占用堆内存比例(初始)
| -XX:G1MaxNewSizePercent	| 新生代最大堆内存占比(最大)
| -XX:MaxGCPauseMillis	| 设定G1执行GC的时候可以让系统停顿多长时间
| -XX:InitiatingHeapOccupancyPercent	| 默认值45%, G1的参数，如果老年代占用的Region超过这个值，此时就会尝试触发新生代+老年代一起混合回收阶段
| -XX:G1MixedGCCountTarget	| 在一次混合回收过程中，最后一个阶段执行几次，默认是8次
| -XX:HeapWastePercent	| 默认5%，混合回收时，如果空Region占堆内存的这个参数时停止回收
| -XX:MixedGCLiveThresholdPercent	| 默认85%，当一个Region存活对象占比85%以下时才回收。如果存活对象过多，复制起来反而更麻烦，不如不回收。
| -XX:+PrintGCDetails	| 打印详细的gc日志
| -XX:+PrintGCTimeStamps	| 这个参数可以打印出来每次GC发生的时间
| -Xloggc:gc.log	| 这个参数可以设置将gc日志写入一个磁盘文件
| -Xms	| Java堆内存大小；这是JVM启动时堆内存的大小
| -Xmx	| Java堆内存最大大小；随着程序的运行堆内存可能不够，这里设置了允许扩大的最大内存大小。
| -Xmn	| 年轻代内存大小;
| 堆内存	| 年轻代内存+老年代内存
| 老年代内存	| 堆内存 – 年轻代内存
| -XX:PermSize	| 永久代内存大小
| -XX:MaxPermSize	| 永久代最大大小
| -Xss	| 每个线程栈内存大小
