# Oracle数据库

## 创建 oradata 目录，并且为该目录设置 chmod 777 权限

## 配置 docker-compose.yml 文件

```yaml
services:
  oracle:
    image: container-registry.oracle.com/database/enterprise:19.3.0.0
    container_name: oracledb
    ports:
      - 1521:1521
      - 5500:5500
    volumes:
      - ./oracle/oradata:/opt/oracle/oradata
    environment:
      TZ: Asia/Shanghai
      ORACLE_SID: orcl
      ORACLE_PWD: oracle123456
      ENABLE_ARCHIVELOG: true
    logging:
      driver: "json-file"
      options:
        max-size: "1g"
        max-file: "20"


```
## 参数说明
```
Parameters:
 --name:                 The name of the container (default: auto generated
 -p:                     The port mapping of the host port to the container port.
                         Two ports are exposed: 1521 (Oracle Listener), 5500 (OEM Express)
 -e ORACLE_SID:          The Oracle Database SID that should be used (default:ORCLCDB)
 -e ORACLE_PDB:          The Oracle Database PDB name that should be used (default: ORCLPDB1)
 -e ORACLE_PWD:          The Oracle Database SYS, SYSTEM and PDBADMIN password (default: auto generated)
 -e INIT_SGA_SIZE:       The total memory in MB that should be used for all SGA components (optional)
 -e INIT_PGA_SIZE:       The target aggregate PGA memory in MB that should be used for all server processes attached to the instance (optional)
 -e ORACLE_EDITION:      The Oracle Database Edition (enterprise/standard, default: enterprise)
 -e ORACLE_CHARACTERSET: The character set to use when creating the database (default: AL32UTF8)
 -e ENABLE_ARCHIVELOG:   To enable archive log mode when creating the database (default: false). Supported 19.3 onwards.
 -v /opt/oracle/oradata
                         The data volume to use for the database. Has to be writable by the Unix "oracle" (uid: 54321) user inside the container
                         If omitted the database will not be persisted over container recreation.
 -v /opt/oracle/scripts/startup | /docker-entrypoint-initdb.d/startup
                         Optional: A volume with custom scripts to be run after database startup.
                         For further details see the "Running scripts after setup and on
                         startup" section below.
 -v /opt/oracle/scripts/setup | /docker-entrypoint-initdb.d/setup
                         Optional: A volume with custom scripts to be run after database setup.
                         For further details see the "Running scripts after setup and on startup" section below.
```

## 连接到容器内SQLPlus

```shell
$ docker exec -it <oracle-db> sqlplus / as sysdba
$ docker exec -it <oracle-db> sqlplus sys/<your_password>@<your_SID> as sysdba
$ docker exec -it <oracle-db> sqlplus system/<your_password>@<your_SID>
$ docker exec -it <oracle-db> sqlplus pdbadmin/<your_password>@<your_PDBname>

```

## 更改SYS用户的默认密码
在容器的第一次启动时，如果没有提供，将为数据库生成一个随机密码。在创建数据库并且相应的容器处于健康状态后，用户必须强制更改密码。
```shell
$ docker exec <oracle-db> ./setPassword.sh <your_password>

```

## 浏览器登录 Oracle EM Express
https://localhost:5500/em/

## url
https://container-registry.oracle.com/ords/f?p=113:10:5244630326871:::RP,10::&cs=3PLO3q4jvLN3b8N-jegt0VQrYfbyywidc_ujij3tuTV7WkDPjSYCeT4z_8nhHhz_lhwXA4_KX2nCYu3fUm4wscQ

