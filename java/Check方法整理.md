# Java check方法整理

## 非空Check
```java
import org.springframework.util;
// 判断Object类型是否为空
StringUtils.isEmpty(Object str);
```

```java
import org.apache.commons.lang3;
// 判断String类型是否为空
StringUtils.isEmpty(CharSequence cs);
```

```java
// 判断集合是否为空
CollectionUtils.isEmpty();

// 判断集合是否不为空
CollectionUtils.isNotEmpty();

// 并集
CollectionUtils.union(a, b);

// 交集
CollectionUtils.intersection(a, b);

// 交集的补集
CollectionUtils.disjunction(a, b);

// A与B的差
CollectionUtils.subtract(a, b);
```

## 是否为数字Check
```java
public static boolean isInteger(String str) {  
    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
    return pattern.matcher(str).matches();  
}
```

## 是否为字母Check
```java
public boolean isAlpha(String str) {
    Pattern pattern = Pattern.compile("[a-zA-Z]+");  
    return pattern.matcher(str).matches();  
}
```

## 是否为半角字符串Check
```java
```
## 是否为全角字符串Check
```java
```
## 是否为邮件Check
```java
```
## 