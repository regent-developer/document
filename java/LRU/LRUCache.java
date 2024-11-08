
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.get(2); // 访问2，将其移到链表尾部
        cache.put(4, "four"); // 容量超出，移除最老的元素1
        System.out.println(cache.keySet()); // 输出：[3, 2, 4]
    }
}


/*
1. 创建一个LinkedHashMap实例，并设置一个初始容量和加载因子。
2. 通过put和get方法操作缓存，LinkedHashMap会自动维护元素的访问顺序。
3. 重写removeEldestEntry方法，当缓存容量超出限制时，自动移除最老的元素。
 *  
 * */