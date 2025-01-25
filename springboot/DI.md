# DI

依赖注入是⼀个过程，是指IoC容器在创建Bean时,去提供运⾏时所依赖的资源，⽽资源指的就是对象. 在上⾯程序案例中，我们使⽤了 @Autowired 这个注解，完成了依赖注⼊的操作. 简单来说,就是把对象取出来放到某个类的属性中。

## 注入方式

三种方式：
1. 属性注入(FieldInjection)
2. 构造⽅法注入(ConstructorInjection)
3. Setter 注入(SetterInjection)

### 属性注入(FieldInjection)
属性注⼊是使⽤ @Autowired 实现的，将Service类注⼊到Controller类中。

### 构造⽅法注入(ConstructorInjection)
构造方法注入是在类的构造方法中实现注入。

### Setter注入(SetterInjection)
Setter注入和属性的Setter方法实现类似，只不过在设置set方法的时候需要加上@Autowired注解。

## 优缺点
|方式|优点	|缺点|
|---|---|---|
|属性注入	|简洁，使用方便	|1、只能⽤于IoC容器，如果是⾮IoC容器不可⽤，并且只有在使⽤的时候才会出现NPE（空指 针异常）2、不能注⼊⼀个Final修饰的属性|
|构造函数注入(Spring4.X推荐)	|1、可以注⼊final修饰的属性2、注⼊的对象不会被修改3、 依赖对象在使⽤前⼀定会被完全初始化，因为依赖是在类的构造⽅法中执⾏的，⽽构造⽅法 是在类加载阶段就会执⾏的⽅法.4、 通⽤性好,构造⽅法是JDK⽀持的,所以更换任何框架,他都是适⽤的|注⼊多个对象时,代码会⽐较繁琐|
|Setter注入(Spring3.X推荐)	|方便在类实例之后,重新对该对象进⾏配置或者注⼊	|1、不能注⼊⼀个Final修饰的属性 2、注⼊对象可能会被改变,因为setter⽅法可能会被多次调⽤,就有被修改的⻛险|

## @Autowired注解的问题

当同⼀类型存在多个bean时,使⽤@Autowired会存在问题

### 解决方案
1. 使用@Primary注解：当存在多个相同类型的Bean注入时，加上@Primary注解，来确定默认的实现。
2. 使用@Qualifier注解：指定当前要注⼊的bean对象。在@Qualifier的value属性中，指定注入的bean 的名称。（@Qualifier注解不能单独使用，必须配合@Autowired使用）
3. 使用@Resource注解：是按照bean的名称进行注入。通过name属性指定要注⼊的bean的名称。

### @Autowird与@Resource的区别
1、@Autowired是spring框架提供的注解，而@Resource是JDK提供的注解。
2、@Autowired默认是按照类型注入，而@Resource是按照名称注入.相比于@Autowired来说@Resource支持更多的参数设置，例如name设置，根据名称获取Bean。
