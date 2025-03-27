# sql数据库表名动态写法

```xml
<select id="getXXX" resultType="java.lang.Integer">
    select SUM(1) from <![CDATA[ ${TableName} ]]>
</select>

```
