# postgresql常用管理命令

## 查看版本
psql --version

## 数据库列表
psql -Upostgres -c '\l'

## 列出所有表
psql -Upostgres -d ttrss
\dt

## 删除数据库
su - postgres
dropdb ttrss

## 备份数据库结构
su - postgres
pg_dump -Fc -s -f ttrss.sql ttrss

## 备份数据库数据
pg_dump -Fc -a -f ttrss.sql ttrss

## 备份数据库结构和数据
pg_dump -Fc -f ttrss.sql ttrss

## 备份数据库中指定表结构
pg_dump -Fc -s -t citycode -f citycode_schema.sql testdb

## 备份数据库中指定表数据
pg_dump -Fc -a -t ttrss_users -f ttrss_users_data.sql ttrss

## 备份数据库中指定表（结构和数据）
pg_dump -Fc -t ttrss_users -f ttrss_users_schemadata.sql ttrss

## 创建新数据库ttrss
su - postgres
createdb ttrss;

## 恢复数据结构（only schema）
pg_restore -s -d ttrss ttrssschema.sql

## 恢复数据库数据（only data）
pg_restore -a -d ttrss ttrssdata.sql

## 恢复数据库结构和数据（schema and data）
pg_restore -d ttrss ttrssschemadata.sql

## 指定表数据恢复
1.删除表
psql ttrss
DROP TABLE ttrss_user;

2.恢复表结构
pg_restore -s -t ttrss_user -d ttrss ttrss_user_schema.sql

3.恢复表数据
pg_restore -a -t ttrss_user -d ttrss ttrss_user_data.sql

4.恢复表（结构和数据）
pg_restore -t ttrss_user -d ttrss ttrss_user_schemadata.sql

















