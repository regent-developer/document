package com.demo;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: RedisUtil工具类
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate redisTemplate;

    private static final String CACHE_KEY_SEPARATOR = ".";

    /**
     * 构建缓存key
     * @param strObjs 多个字符串拼接成缓存key
     * @return 拼接后的缓存key
     */
    public String buildKey(String... strObjs) {
        return Stream.of(strObjs).collect(Collectors.joining(CACHE_KEY_SEPARATOR));
    }

    /**
     * 是否存在key
     * @param key Redis中的key
     * @return true如果key存在，否则false
     */
    public boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除key
     * @param key Redis中的key
     * @return true如果删除成功，否则false
     */
    public boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 设置key-value对
     * @param key Redis中的key
     * @param value 要设置的值
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置key-value对，如果key不存在，则设置成功，并指定过期时间
     * @param key Redis中的key
     * @param value 要设置的值
     * @param time 过期时间
     * @param timeUnit 时间单位
     * @return true如果设置成功，否则false
     */
    public boolean setNx(String key, String value, Long time, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
    }

    /**
     * 获取指定key的值
     * @param key Redis中的key
     * @return key对应的值
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 向有序集合中添加元素
     * @param key Redis中的key
     * @param value 元素的值
     * @param score 元素的分数
     * @return true如果添加成功，否则false
     */
    public Boolean zAdd(String key, String value, Long score) {
        return redisTemplate.opsForZSet().add(key, value, Double.valueOf(String.valueOf(score)));
    }

    /**
     * 获取有序集合的元素数量
     * @param key Redis中的key
     * @return 元素数量
     */
    public Long countZset(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取有序集合指定范围内的元素
     * @param key Redis中的key
     * @param start 起始位置
     * @param end 结束位置
     * @return 指定范围内的元素集合
     */
    public Set<String> rangeZset(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 删除有序集合中的指定元素
     * @param key Redis中的key
     * @param value 要删除的元素
     * @return 被删除的元素数量
     */
    public Long removeZset(String key, Object value) {
        return redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 删除有序集合中的多个元素
     * @param key Redis中的key
     * @param value 要删除的元素集合
     */
    public void removeZsetList(String key, Set<String> value) {
        value.forEach(val -> redisTemplate.opsForZSet().remove(key, val));
    }

    /**
     * 获取有序集合中指定元素的分数
     * @param key Redis中的key
     * @param value 元素的值
     * @return 元素的分数
     */
    public Double score(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 获取有序集合中指定分数范围内的元素
     * @param key Redis中的key
     * @param start 起始分数
     * @param end 结束分数
     * @return 指定分数范围内的元素集合
     */
    public Set<String> rangeByScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScore(key, Double.valueOf(String.valueOf(start)), Double.valueOf(String.valueOf(end)));
    }

    /**
     * 增加有序集合中指定元素的分数
     * @param key Redis中的key
     * @param obj 元素的值
     * @param score 增加的分数
     * @return 增加后的分数
     */
    public Object addScore(String key, Object obj, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, obj, score);
    }

    /**
     * 获取有序集合中指定元素的排名
     * @param key Redis中的key
     * @param obj 元素的值
     * @return 元素的排名
     */
    public Object rank(String key, Object obj) {
        return redisTemplate.opsForZSet().rank(key, obj);
    }

    /**
     * 从 Redis 有序集合（Sorted Set）中按分数范围获取成员及其分数
     * @param key 排行榜的key
     * @param start 起始位置（包含）
     * @param end 结束位置（包含）
     * @return Set<ZSetOperations.TypedTuple<String>> : 每个 TypedTuple 对象包含以下内容：value: 集合中的成员，score: 成员的分数。
     */
    public Set<ZSetOperations.TypedTuple<String>> rankWithScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 向Redis中的hash结构存储数据
     * @param key 一个hash结构的key
     * @param hashKey hash中的小key
     * @param hashVal hash中的小value
     */
    public void putHash(String key, String hashKey, Object hashVal) {
        redisTemplate.opsForHash().put(key, hashKey, hashVal);
    }

    /**
     * Redis中的String类型，获取value时将其转换为int类型
     * @param key Redis中的key
     * @return key对应的整数值
     */
    public Integer getInt(String key) {
        return (Integer) redisTemplate.opsForValue().get(key);
    }

    /**
     * Redis中的String类型，将value增加一
     * @param key Redis中的key
     * @param count 增加的数量
     */
    public void increment(String key, Integer count) {
        redisTemplate.opsForValue().increment(key, count);
    }

    /**
     * Redis中的hash类型，根据key来将每一个hashKey和hashValue转换为Map类型
     * @param key Redis中的hash结构的key
     * @return Map<Object, Object> 包含hash结构中的所有键值对
     */
    public Map<Object, Object> getHashAndDelete(String key) {
        Map<Object, Object> map = new HashMap<>();
        // 扫描hash，指定每一个Entry的类型，这里返回的就是Map的游标，可以进行遍历
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
        // 遍历每一条数据，放到map中
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> next = cursor.next();
            Object hashKey = next.getKey();
            Object hashValue = next.getValue();
            map.put(hashKey, hashValue);
            // 每遍历一条就删除
            redisTemplate.opsForHash().delete(key, hashKey);
        }
        return map;
    }
}
