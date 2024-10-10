# Spring Boot读取resources目录下文件（打成jar可用），并放入Guava缓存

## 依赖
```xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>23.0</version>
</dependency>

```

## 读取放入缓存的代码

```java
@Service
@AllArgsConstructor
@Slf4j
public class TxtCheckService {

    private static final Cache<String, String> XXX_WORDS_CACHE = CacheBuilder.newBuilder()
            // 设置缓存容量数
            .maximumSize(1)
            .build();

    static {
        try {
            ClassLoader classLoader = DemoApplication.class.getClassLoader();
            Enumeration<URL> resources = classLoader.getResources("static/XXX/xxx.txt");
            List<String> allXXXList = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream(), "utf-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    // 一行行读取
                    allXXXList.add(line);
                }
                XXX_WORDS_CACHE.put(RedisKeyConstant.ALL_XXX_WORDS, JSON.toJSONString(allXXXList));
            }
        } catch (Exception e) {
            log.error("加载失败", e);
        }
    }

    public List<String> getXXXWordsCache() {
        return JSON.parseArray(XXX_WORDS_CACHE.getIfPresent(RedisKeyConstant.ALL_XXX_WORDS), String.class);
    }

}

```