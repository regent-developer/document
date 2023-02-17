# Springboot集成Mongodb

## 依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

## application.yml配置

* 例子1
```yml
spring:
  data:
    mongodb:
      uri: mongodb://127.0.0.1:27017/mongodb_test
```
* 例子2
```yml
spring:
  data:
    mongodb:
      host: 127.0.0.1 #指定MongoDB服务地址
      port: 27017 #指定端口，默认就为27017
      database: mongodb #指定使用的数据库(集合)
      authentication-database: admin # 登录认证的逻辑库名
      username: admin #用户名
      password: abc123456 #密码
```
## 实体类
```java
@Data
@Document(collection = "article") //指定要对应的文档名（表名）
@Accessors(chain = true) //链式访问
public class Article {
    /**
     * 文章主键
     */
    @Id
    private String id;
    /**
     * 文章名
     */
    private String name;
    /**
     * 文章内容
     */
    private String content;
}
```

* @Document：文档是 MongoDB 中最基本的数据单元，由键值对组成，类似于 JSON 格式，可以存储不同字段，字段的值可以包括其他文档，数组和文档数组
* @Id（主键）：用来将成员变量的值映射为文档的_id的值
* @Indexed（索引）： 索引是一种特殊的数据结构，存储在一个易于遍历读取的数据集合中，能够对数据库文档中的数据进行排序。索引能极大提高文档查询效率，如果没有设置索引，MongoDB 会遍历集合中的整个文档，选取符合查询条件的文档记录。这种查询效率是非常低的
* @Field（字段）：文档中的字段，类似于 MySql 中的列
* @Aggregation（聚合）：聚合主要用于数据处理


## MongoRepository实现增删改查
```java
public interface ArticleRepository extends MongoRepository<Article,String> {

    /**
     * 根据id查询文章
     * @param id
     * @return
     */
    List<Article> findByid(String id);
}

```

## Service层
```java
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    public int create(Article article) {
        Article save = articleRepository.save(article);
        return 1;
    }

    /**
     * 删除文章
     *
     * @param ids
     */
    public int delete(List<String> ids) {
        List<Article> deleteList = new ArrayList<>();

        for(String id:ids){
            Article article = new Article();
            article.setId(id);
            deleteList.add(article);
        }
        articleRepository.deleteAll(deleteList);

        return ids.size();
    }

    /**
     * 查询文章
     * @param id
     * @return
     */
    public List<Article> get(String id) {
        return articleRepository.findByid(id);
    }

}

```

## Controller层
```java
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/create")
    public String create(@RequestBody Article article) {
        int result = articleService.create(article);
        if (result > 0) {
            return "文章创建成功";
        } else {
            return "文章创建失败";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("ids") List<String> ids) {
        int count = articleService.delete(ids);
        if (count > 0) {
            return "删除了" + count + "篇文章";
        } else {
            return "删除文章失败";
        }
    }

    @PostMapping("/get")
    public List<Article> get(String id) {
        List<Article> list = articleService.get(id);
        return list;
    }

}

```

## MongoTemplate实现增删改查
### 实体类
```java
@Data
@Document(collection = "book")
public class Book {
    @Id
    private String id;

    private String name;

    private String type;

    private String description;

}
```

### Service层
```java
@Service
@RequiredArgsConstructor
public class BookService {

    private final MongoTemplate mongoTemplate;

    /**
     * 添加文章
     *
     * @param book
     * @return
     */
    public int create(Book book) {
        Book save = mongoTemplate.save(book);
        return 1;
    }

    /**
     * 删除文章
     *
     * @param id
     */
    public int delete(String id) {
        List<Book> deleteList = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query,Book.class);
        return 1;
    }

    /**
     * 查询文章
     * @param id
     * @return
     */
    public Book get(String id) {
        Book byId = mongoTemplate.findById(id, Book.class);
        return byId;
    }

}

```

### Controller层
```java
@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public String create(@RequestBody Book book) {
        int result = bookService.create(book);
        if (result > 0) {
            return "图书创建成功";
        } else {
            return "图书创建失败";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") String id) {
        int count = bookService.delete(id);
        if (count > 0) {
            return "删除了" + count + "篇图书";
        } else {
            return "删除图书失败";
        }
    }

    @PostMapping("/get")
    public Book get(String id) {
        Book book = bookService.get(id);
        return book;
    }

}

```