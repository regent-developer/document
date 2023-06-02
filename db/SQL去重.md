# SQL去重



## row_number() over (parttion by 分组列 order by 排序列)

去重原理：现根据重复列进行分组，分组后再进行排序，不同的组序号为1，相同的组序号为2，排除为2的就达到了去重效果。  

```sql
select *from
(
--查询出重复行
select *, row_number() over (partition by column1, column2 order by column1 desc)num from Table1
)A
where A.num=1
```

