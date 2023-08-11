# 查看表结构相关的sql

```sql
SELECT a.attname AS field,
       b.description AS COMMENT,
       t.typname AS type,
       CASE WHEN a.atttypmod<=0 THEN NULL ELSE (a.atttypmod-4) END AS lengthvar,
       a.attnotnull AS notnull
  FROM sys_class c 
     INNER JOIN sys_namespace n on c.relnamespace = n.oid,
     sys_attribute a LEFT JOIN sys_description b ON a.attrelid=b.objoid AND a.attnum = b.objsubid,
     sys_type t    
 WHERE a.attnum > 0
       and a.attrelid = c.oid
       and a.atttypid = t.oid
       AND nspname = '模式名'     -- schema
       AND c.relname = '表名' -- table_name
 ORDER BY a.attnum;
```

