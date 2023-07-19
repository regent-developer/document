# 查看表结构相关的sql



##  获取表

```sql
select table_name from user_tables; //当前用户的表      
select table_name from all_tables; //所有用户的表  
select table_name from dba_tables; //包括系统表
select table_name from dba_tables where owner='用户名';

```



## 获取表字段

```sql
select * from user_tab_columns where Table_Name='用户表';
select * from all_tab_columns where Table_Name='用户表';
select * from dba_tab_columns where Table_Name='用户表';

```



```sql
--获取表相关的说明
select 
	utc.column_name 字段名,
	utc.data_type 字段类型,
	utc.data_length 数据长度,
	utc.data_precision 数据精度,
	utc.nullable 是否为空 ,
	ucc.comments 备注
from user_tab_columns utc,user_col_comments ucc
where utc.column_name = ucc.column_name and utc.Table_Name = ucc.Table_Name 
and utc.Table_Name = '表名' and owner='用户名'
```





## 获取表注释



```sql
select * from user_tab_comments
```





## 查看表对应的数据量

```sql
SELECT t.table_name, t.num_rows FROM user_tables t WHERE NUM_ROWS IS NOT NULL AND TABLE_NAME NOT LIKE '%HIS%' ORDER BY NUM_ROWS DESC;
```





## 查看存储过程脚本内容

```sql
-- 查看存储过程脚本内容
SELECT text FROM user_source WHERE NAME = '存储过程名' ORDER BY line;
```

