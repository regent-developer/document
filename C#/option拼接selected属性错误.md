# [Specification mandates value for attribute selected]错误

## 错误样式
Specification mandates value for attribute selected  

## 解决方法
apsx中拼接option字符串的selected属性错误。
  
例如："<option selected>test</option>" -> "<option select="selected">test</option>"