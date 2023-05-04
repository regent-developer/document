# kingbase常用命令



## 查看最大可用连接数

```sql
show max_connections;
```


## 查询license有效期

```sql
select GET_LICENSE_VALIDDAYS();
```



## 重新加载data目录

```bash
sys_ctl reload -D /home/kingbase/KingbaseES/V8/data/
```



## 重启kingbase服务

```bash
sys_ctl restart -D /home/kingbase/KingbaseES/V8/data/
```
