# AntPathMatcher

## 1. 简介

AntPathMatcher 是 Spring 提供的一个路径匹配工具类，它支持 Ant 风格的通配符，可以用于匹配 URL 路径。

## 2. Ant 风格通配符

|符号|作用|示例|
|---|---|---|
| ?  | 匹配一个字符。<br>不能匹配目录：这个字符不能是代表路径分隔符的/  |  /dir/app?<br>匹配：/dir/app1、/dir/app2 <br> 不匹配：/dir/app、/dir/app12、index/  |
| * |  匹配0到多个字符。 |  /dir/app*  <br> 匹配：/dir/app、/dir/app1、/dir/app12、/dir/appa/ <br> 不匹配：/dir/app/a  |
| **  | 匹配多级目录。  |  /dir/**/app* <br> 匹配：/dir/xxx/app* /dir/xxx/yyy/app* <br> /dir/app/** <br> 匹配：dir/app/aa/bcd/e  |
| {spring:[a-z]+}  |  将正则表达式[a-z]+匹配到的值，赋值给名为 spring 的路径变量。<br>  必须是完全匹配才行，在SpringMVC中只有完全匹配才会进入controller层的方法|  @RequestMapping("/index/{username:[a-b]+}") <br> 结果 <br> index/ab           true  输出 ab <br> index/abbaaa    true  输出 abbaaa <br> index/a             false 404错误 <br> index/ac            false 404错误|

测试用例：https://github.com/spring-projects/spring-framework/blob/main/spring-core/src/test/java/org/springframework/util/AntPathMatcherTests.java