# @Autowired 和 @Resource

## @Autowired
1. @Autowired 是 Spring 框架提供的注解。
2. 默认情况下，@Autowired 要求必须找到匹配的 bean，如果找不到会抛出异常。不过，可以通过设置 @Autowired 的 required 属性为 false 来使其成为非必须的依赖。
3. @Autowired 可以通过构造函数、字段或者 setter 方法进行注入。
4. @Autowired 可以与 @Qualifier 注解一起使用，以解决同名 bean 的歧义问题。
5. @Autowired 按照类型进行匹配，如果有多个同类型的 bean，默认情况下会导致不确定性，除非使用 @Qualifier 指定具体的 bean 名称。

## @Resource
1. @Resource 是 Java EE 5 提供的注解，由 Common Annotations 规范的一部分。
2. @Resource 默认按名称进行注入，如果找不到名称匹配的 bean，才会退回到按类型注入。
3. @Resource 可以通过构造函数、字段或者 setter 方法进行注入。
4. @Resource 的 name 属性允许你指定要注入的 bean 的名称，如果没有指定 name 属性，那么会根据字段名或 setter 方法的参数名进行匹配。
5. @Resource 可以指定 lookup 属性，用于指定要注入的 bean 的名称，即使字段名或参数名与 bean 名称不一致。
6. @Resource 不能与 @Qualifier 注解一起使用，因为它不是 Spring 框架的一部分。

## 区别

1. @Autowired 是 Spring 提供的注解，@Resource 是 J2EE 提供的注解。
2. @Autowired 默认按照类型注入，@Resource 默认按照名称注入。
3. @Autowired 可以和 @Qualifier 一起使用，@Qualifier 可以指定注入的 bean 名称，@Resource 可以通过 name 属性指定注入的 bean 名称。
4. @Autowired 仅在 Spring 容器中有效，而 @Resource 可以在任何支持 Java EE 注解的容器中工作。

