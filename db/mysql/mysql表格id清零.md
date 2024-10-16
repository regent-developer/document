# mysql表格id清零

## 利用TRUNCATE TABLE语句清空表格并重置id
```sql
-- TRUNCATE TABLE语句会删除表格中所有的数据，所以在使用前需要备份数据。
TRUNCATE TABLE user;

```

## 利用ALTER TABLE语句修改自增长id的初始值
```sql
-- 修改id的初始值可能会导致数据冲突
ALTER TABLE user AUTO_INCREMENT = 1;

```

## 利用DELETE语句删除表格中的数据并重置id
```sql
-- 删除数据可能会影响其他表格的数据关系
DELETE FROM user;

```