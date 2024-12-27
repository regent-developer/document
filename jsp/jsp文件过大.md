# jsp文件过大（超过65535 bytes）

## 问题描述

在开发过程中，发现jsp文件过大（超过65535 bytes），导致无法编译，报错如下：

```
org.apache.jasper.JasperException: /index.jsp (line: 1, column: 1) The page cannot be displayed because an internal server error occurred.
```

## 解决方案

1. 将jsp文件拆分成多个小的jsp文件，每个文件不超过65535 bytes。
2. 使用include指令将多个小的jsp文件引入到主jsp文件中。例如：
```jsp
<%@ include file="header.jsp" %>
<%@ include file="content.jsp" %>
<%@ include file="footer.jsp" %>
```
3. 使用jsp:include标签将多个小的jsp文件引入到主jsp文件中。例如：
```jsp
<jsp:include flush="true" page="header.jsp" />
<jsp:include flush="true" page="content.jsp" />
<jsp:include flush="true" page="footer.jsp" />
```

## 扩展知识
<%@ include file=" "%>标签是在jsp容器里将jsp翻译成servlet文件，并编译它时，是静态包含jsp的，也就是编译出来是一个类文件，而java类文件是不允许超过65K这么大的，所以会报错如标题所示。而<jsp:include flush="true" page=" "/>在翻译并编译后，产生的两个类文件，也就是说呗调用的jsp文件生成独立的类文件，而调用它的jsp生成的类文件中，只包含一个调用jsp的方法。

## 注意事项
1. 在使用include指令时，需要注意文件的路径是否正确。    
2. 在使用include指令时，需要注意文件的编码格式是否一致。
