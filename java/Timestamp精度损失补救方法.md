# Timestamp精度损失补救方法

```java
long ticks = System.currentTimeMillis() *  1000000L + System.nanoTime() % 1000000L;
Timestamp timestamp = new Timestamp(ticks / 1000000L);
timestamp.setNanos((int)(ticks % 1000000000L));
```