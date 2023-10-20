# 常用sql整理



## 添加主键

```sql
ALTER TABLE t_table ADD PRIMARY KEY(id);
```



## 删除约束

```sql
ALTER TABLE t_table DROP CONSTRAINT t_table_constraint_1;
```



## 修改表名

```sql
alter table t_table to t_table_1 
```



## 指定字段改为自增

```sql
ALTER TABLE t_table  ALTER column id set default nextval('t_table_id_SEQ');
```

