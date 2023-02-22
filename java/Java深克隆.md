# Java深克隆

## 继承Cloneable接口，重写clone方法实现深克隆

```java
@Data
@AllArgsConstructor
@ToString
public class User implements Cloneable{
    private String name;
    private int age;
    private House house;
 
    @Override
    protected User clone() throws CloneNotSupportedException {
        User user = (User) super.clone();
        user.setHouse(house.clone());
        return user;
    }
}
 
@Data
@AllArgsConstructor
@ToString
public class House implements Cloneable{
    private String location;
    private double price;
 
    @Override
    protected House clone() throws CloneNotSupportedException {
        return (House) super.clone();
    }
}

public static void main(String[] args) {
    House house = new House("大连", 58955);
    User user = new User("张三", 18, house);
    User cloneUser = user.clone();
}
```


## 序列化与反序列化的方式实现深克隆

```java
@Data
@AllArgsConstructor
@ToString
public class House implements Serializable {
    private String location;
    private double price;
}
 
@Data
@AllArgsConstructor
@ToString
public class User implements Serializable {
    private String name;
    private int age;
    private House house;
}
 
@SneakyThrows
public static void main(String[] args) {
    House house = new House("大连", 58955);
    User user = new User("张三", 18, house);
 
    //序列化
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    objectOutputStream.writeObject(user);
    //反序列化
    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    User cloneUser = (User)objectInputStream.readObject();
}
```


## 第三方工具类实现深克隆，克隆对象需继承Serializable接口
```java
@Data
@AllArgsConstructor
@ToString
public class House implements Serializable {
    private String location;
    private double price;
}
 
@Data
@AllArgsConstructor
@ToString
public class User implements Serializable {
    private String name;
    private int age;
    private House house;
}
```

* Apache Commons Lang的SerializationUtils.clone方法
```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>
```

```java
public static void main(String[] args) {
    House house = new House("大连", 58955);
    User user = new User("张三", 18, house);
    User cloneUser = SerializationUtils.clone(user);
}
```

* Gson工具类
```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.5</version>
</dependency>
```

```java
@SneakyThrows
public static void main(String[] args) {
    House house = new House("大连", 58955);
    User user = new User("张三", 18, house);    Gson gson = new Gson();
    //将对象序列化为json字符串
    String userStr = gson.toJson(user);
    //然后将字符串反序列化为对象
    User cloneUser = gson.fromJson(userStr, User.class);
}
```

* FastJson工具类
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.78</version>
</dependency>
```

```java
@SneakyThrows
public static void main(String[] args) {
    House house = new House("大连", 58955);
    User user = new User("张三", 18, house);
 
    //将对象序列化为json字符串
    String userStr = JSON.toJSONString(user);
    //然后将字符串反序列化为对象
    User cloneUser = JSON.parseObject(userStr, User.class);
}
```