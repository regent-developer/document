# mapstruct和lombok配合使用

## 引入依赖

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.20</version> 
</dependency>
<dependency>
	<groupId>org.mapstruct</groupId>
	<artifactId>mapstruct</artifactId>
	<version>1.5.5.Final</version>
</dependency>
<dependency>
	<groupId>org.mapstruct</groupId>
	<artifactId>mapstruct-processor</artifactId>
	<version>1.5.5.Final</version>
	<scope>provided</scope>
</dependency>
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok-mapstruct-binding</artifactId>
	<version>0.2.0</version>
</dependency>
```

* 如果不加lombok-mapstruct-binding依赖将不能生成自动映射的代码。
* lombok-mapstruct-binding 是一个用于配合 Lombok 和 MapStruct 使用的库，它帮助 MapStruct 正确处理由 Lombok 生成的构造方法、getter、setter 等方法，确保这两者能够顺利协作。
* mapStruct和lombok都是在编译期生成代码，如果mapStruct先进行编译，就会导致拿不到lombok自动生成的get、set方法，确保maven依赖lombok在mapStruct之前进行编译。