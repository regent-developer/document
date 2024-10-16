# curl连接MySQL

```shell
curl https://localhost:3306 \
--user username:password \
--data-binary @-<< EOF
USE database;
SHOW TABLES;
EOF
```