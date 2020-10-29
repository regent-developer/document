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

## 是否只为数字Check
```java
public static boolean isNumber(String str) {  
    Pattern pattern = Pattern.compile("^[0-9]+$");  
    return pattern.matcher(str).matches();  
}
```

## 是否只为字母Check
```java
public boolean isAlphabet(String str) {
    Pattern pattern = Pattern.compile("^[a-zA-Z]+$");  
    return pattern.matcher(str).matches();  
}
```

## 是否只为数字和字母Check
```java
public boolean isAlphabetNumber(String str) {
    Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");  
    return pattern.matcher(str).matches();  
}
```



## 位数Check
```java
// 是否为空
GenericValidator.isBlankOrNull(str);

// 位数相等
public boolean isMatchLength(String str, int length) {
    return str.codePointCount(0, str.length()) == length;
}

// 位数以内
public boolean isLessOrEqualLength(String str, int length) {
    return GenericValidator.maxLength(str, length);
}

// 位数范围
public boolean isMatchRange(String str, int minLength, int maxLength) {
    return value.codePointCount(0, str.length()) >= minLength && str.codePointCount(0, str.length()) <= maxLength;
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