# 基于jxls导出excel（多个sheet）

## pom依赖(版本使用最新)
```maven
<dependency>
    <groupId>org.jxls</groupId>
    <artifactId>jxls</artifactId>
    <!--version>2.4.6</version-->
</dependency>
<dependency>
    <groupId>org.jxls</groupId>
    <artifactId>jxls-poi</artifactId>
    <!--version>1.0.15</version-->
</dependency>
<dependency>
    <groupId>org.jxls</groupId>
    <artifactId>jxls-jexcel</artifactId>
    <!--version>1.0.7</version-->
</dependency>
<dependency>
    <groupId>net.sf.jxls</groupId>
    <artifactId>jxls-core</artifactId>
    <!--version>1.0.5</version-->
</dependency>
```

## excel模板

依据实际情况设定excel模板

## 导出伪代码

```java
public void excelExport() throws Exception {
    // sheet1使用的数据
    Map map1 = new HashMap();
    map1.put("data", data);

    // sheet2使用的数据
    Map map2 = new HashMap();
    map2.put("data", data);

    // 获取模板文件
    InputStream is = this.getClass().getClassLoader().getResourceAsStream("templateFile.xls");

    // 实例化 XLSTransformer 对象
    XLSTransformer xlsTransformer = new XLSTransformer();

    //模板文件对应的sheet名称列表
    List templateSheetNameList = new ArrayList<String>();
    templateSheetNameList.add("Template1");
    templateSheetNameList.add("Template2");

    //生成文件对应的sheet名称列表
    List sheetNameList = new ArrayList<String>();
    sheetNameList.add("表单1");
    sheetNameList.add("表单2");

    //模板文件不同sheet对应的不同数据，注意保持顺序一致
    List beanParamsList = new ArrayList<Map<String,Object>>();
    beanParamsList.add(map1);
    beanParamsList.add(map2);

    // 获取Workbook ，传入模板和数据
    Workbook workbook =xlsTransformer.transformXLS(is,templateSheetNameList,sheetNameList,beanParamsList);

    // 写出文件
    OutputStream os = new BufferedOutputStream(new FileOutputStream("exportFile.xls"));

    // 输出
    workbook.write(os);

    // 关闭和刷新管道，不然可能会出现表格数据不齐，打不开之类的问题
    is.close();
    os.flush();
    os.close();
}
```