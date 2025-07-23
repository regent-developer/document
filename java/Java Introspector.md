# Java Introspector
Java Introspector 是 Java 反射机制的一个重要组成部分，它提供了一种标准化的方式来分析和操作 Java Bean 的属性、方法和事件。通过内省机制(内省 是指：Java 通过反射机制在运行时分析类的属性、方法和事件等信息，并提供统一的访问方式。)，我们可以在运行时动态地获取和操作对象的属性，而无需在编译时知道具体的类结构。

## Java Bean 
* 规范公共的无参构造函数：public Person()。
* 私有化的字段（成员变量）：private String name;。
* 公共的 Getter/Setter 方法：提供用于读取和写入属性的公共方法，如 public String getName() 和 public void setName(String name)。对于布尔类型的属性，getter 方法通常是 isXxx()。
* 可序列化（可选）：实现 java.io.Serializable 接口。

