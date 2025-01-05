# SpringBoot中常用的Redis命令实现

## 设置键值对

```java
@Service
public class RedisService {
 
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
 
    // 设置键值对
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
    
    // 获取值
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
```

## 操作 Hash 类型
```java
@Service
public class RedisService {
 
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
 
    // 设置 Hash 值
    public void setHashValue(String hashKey, String field, String value) {
        redisTemplate.opsForHash().put(hashKey, field, value);
    }
    
    // 获取 Hash 值
    public String getHashValue(String hashKey, String field) {
        return (String) redisTemplate.opsForHash().get(hashKey, field);
    }
}
```

## 操作 List 类型
```java
@Service
public class RedisService {
 
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
 
    // 左侧插入 List
    public void leftPushToList(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }
 
    // 右侧插入 List
    public void rightPushToList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }
 
    // 获取 List
    public List<Object> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}
```

## 操作 Set 类型
```java
@Service
public class RedisService {
 
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
 
    // 添加元素到 Set
    public void addToSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }
 
    // 获取 Set
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }
}
```

## 操作 Sorted Set (ZSet)
```java
@Service
public class RedisService {
 
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
 
    // 向 Sorted Set 中添加元素
    public void addToZSet(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }
 
    // 获取 Sorted Set 中的元素
    public Set<Object> getZSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }
}
```

## 删除键
```java
public void deleteKey(String key) {
    redisTemplate.delete(key);
}
```

## 检查键是否存在
```java
public boolean hasKey(String key) {
    return redisTemplate.hasKey(key);
}
```

## 设置键的过期时间
```java
public void setKeyExpiration(String key, long timeout) {
    redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
}
```

## 获取键的过期时间
```java
public long getKeyExpiration(String key) {
    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
}
```