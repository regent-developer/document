# mysql/达梦/人大金仓中查询schema下所有表

## mysql
```sql
select
	table_name tbName,
	table_schema
from
	information_schema.columns
join (
	select
		database() tbName) b on
	table_schema = (
	select
		database())
group by
	table_name
```

## 达梦数据库

```sql
# 查看某schema下所有表（不需要DBA权限）
select TABLE_NAME as tbName from all_tables where OWNER ='SYSDBA' 

# 查看某schema下所有表（需要DBA权限） 
select owner,SEGMENT_NAME as tbName from dba_segments  where segment_type='TABLE' and OWNER ='SYSDBA'

# 查看当前用户下所有表
select table_name as tbName from user_tables where  table_name like 'SYS_%' group by TABLE_NAME
```

## 人大金仓数据库

```sql
SELECT table_name from information_schema.TABLES WHERE  table_schema='schema名'
```