# docker postgresql

## postgresql
```shell
docker pull postgres
```

## 创建并启动posgresql容器
```shell
docker run -it --name postgres --restart always -e POSTGRES_PASSWORD='123456' -e ALLOW_IP_RANGE=0.0.0.0/0 -v /usr/local/docker/postgres/data:/var/lib/postgresql /usr/local/docker/conf/pg_hba.conf:/var/lib/postgresql/data/pg_hba.conf /usr/local/docker/conf/postgresql.conf:/var/lib/postgresql/data/postgresql.conf -p 5432:5432 -d postgres

```

## 设置远程访问许可
### 修改pg_hba.conf

```
# TYPE DATABASE  USER    CIDR-ADDRESS     METHOD
 # "local" is for Unix domain socket connections only
 local all    all               trust
 # IPv4 local connections:
 host  all    all    127.0.0.1/32     trust
 *host  all    all    0.0.0.1/0    md5*
 # IPv6 local connections:
 host  all    all    ::1/128       trust
```

### 修改postgresql.conf
```
listen_addresses = '*'
```