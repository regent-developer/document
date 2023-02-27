# Row size too large (> 8126)

* 查看数据库版本
```sql
select version();
```
* 查看是否启用严格模式
```sql
show variables like '%innodb_strict_mode%';
```

* 关闭严格模式
```sql
set GLOBAL innodb_strict_mode=OFF
```