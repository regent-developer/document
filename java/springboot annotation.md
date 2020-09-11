# SpringBoot注解

## @SpringBootApplication
@SpringBootApplication是 @Configuration、@EnableAutoConfiguration、@ComponentScan 注解的集合。  

@EnableAutoConfiguration：启用 SpringBoot 的自动配置机制
@ComponentScan： 扫描被@Component (@Service,@Controller)注解的 bean，注解默认会扫描该类所在的包下所有的类。
@Configuration：允许在 Spring 上下文中注册额外的 bean 或导入其他配置类。  

## @Autowired
自动导入对象到类中，被注入进的类同样要被 Spring 容器管理。  

## @Component
通用的注解，可标注任意类为 Spring 组件。如果一个 Bean 不知道属于哪个层，可以使用@Component 注解标注。  

## @Repository
对应持久层即 Dao 层，主要用于数据库相关操作。  

## @Service
对应服务层，主要涉及一些复杂的逻辑，需要用到 Dao 层。  

## @Controller
对应 Spring MVC 控制层，主要用户接受用户请求并调用 Service 层返回数据给前端页面。  

## @RestController
@RestController注解是@Controller和@ResponseBody的合集,表示这是个控制器 bean,并且是将函数的返回值直接填入HTTP响应体中,是REST风格的控制器。  

## @Scope
声明 Spring Bean 的作用域。　　
#### 四种常见的 Spring Bean 的作用域
singleton : 唯一 bean 实例，Spring 中的 bean 默认都是单例的。
prototype : 每次请求都会创建一个新的 bean 实例。
request : 每一次 HTTP 请求都会产生一个新的 bean，该 bean 仅在当前 HTTP request 内有效。
session : 每一次 HTTP 请求都会产生一个新的 bean，该 bean 仅在当前 HTTP session 内有效。  

## @Configuration
一般用来声明配置类，可以使用 @Component注解替代，不过使用@Configuration注解声明配置类更加语义化。  

## @GetMapping
@GetMapping("xxx") = @RequestMapping(value="/xxx",method=RequestMethod.GET)  

## @PostMapping
@PostMapping("xxx") = @RequestMapping(value="/xxx",method=RequestMethod.POST)  

## @PutMapping
@PutMapping("xxx") = @RequestMapping(value="/xxx",method=RequestMethod.PUT)  

## @DeleteMapping
@DeleteMapping("xxx") = @RequestMapping(value="/xxx",method=RequestMethod.DELETE)  

## @PatchMapping
@PatchMapping("xxx") = @RequestMapping(value="/xxx",method=RequestMethod.PATCH)

## @PathVariable
@PathVariable用于获取路径参数。  

## @RequestParam
@RequestParam用于获取查询参数。

## @RequestBody
用于读取 Request 请求（可能是 POST,PUT,DELETE,GET 请求）的 body 部分并且Content-Type 为 application/json 格式的数据，接收到数据之后会自动将数据绑定到 Java 对象上去。系统会使用HttpMessageConverter或者自定义的HttpMessageConverter将请求的 body 中的 json 字符串转换为 java 对象。  

## @value
使用 @Value("${property}") 读取配置信息。 
```yml
xxx: property
```

```java
@Value("${xxx}")
String xxx;
```

## @ConfigurationProperties
@ConfigurationProperties读取配置信息并与 bean 绑定。 

## @PropertySource
读取指定 properties 文件。  

## @Validated
Spring 去校验方法参数。  


## @ControllerAdvice
注解定义全局异常处理类。

## @ExceptionHandler
注解声明异常处理方法。

## @Entity
声明一个类对应一个数据库实体。（JPA）

## @Table
设置表名（JPA）

##　@Id
声明一个字段为主键。（JPA）

##　@GeneratedValue
指定主键生成策略。（JPA）

##　@Column
声明字段。（JPA）

## @Transient
声明不需要与数据库映射的字段，在保存的时候不需要保存进数据库 。（JPA）

## @Lob
声明某个字段为大字段。（JPA）

## @Enumerated
使用枚举类型的字段。（JPA）

## @Modifying
示 JPA 该操作是修改操作。（JPA）

## @OneToOne
声明一对一关系。（JPA）

## @OneToMany 
声明一对多关系。（JPA）

## @ManyToOne
声明多对一关系。（JPA）

## @MangToMang
声明多对多关系。（JPA）

## @Transactional
开启事务。（JPA）

## @JsonIgnoreProperties
@JsonIgnoreProperties 作用在类上用于过滤掉特定字段不返回或者不解析。

## @JsonIgnore
@JsonIgnore一般用于类的属性上。

## @JsonFormat
格式化 json 数据。

## @JsonUnwrapped
扁平对象。

## @ActiveProfiles
@ActiveProfiles一般作用于测试类上， 用于声明生效的 Spring 配置文件。

## @Test
声明一个方法为测试方法。

## @Transactional
被声明的测试方法的数据会回滚，避免污染测试数据。

## @WithMockUser
用来模拟一个真实用户，并且可以赋予权限。



