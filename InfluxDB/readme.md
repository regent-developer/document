# InfluxDB

InfluxDB 是一个由 InfluxData 开发的开源时序型数据。它由 Go 写成，着力于高性能地查询与存储时序型数据。InfluxDB 被广泛应用于存储系统的监控数据，IoT 行业的实时数据等场景。

## 数据格式

虚拟的 key 和其对应的 value(field value)构成

- database：数据库名。
- retention policy：储存策略，用于设置数据保留的时间，每个数据库刚开始会自动创建一个默认的存储策略 autogen，数据保留时间为永久，之后用户可以自己设置。InfluxDB 会定期清除过期的数据。
- measurement：测量指标名。
- tag sets:tags 在 InfluxDB 中会按照字典序排序，不管是 tagk 还是 tagv，只要不一致就分别属于两个 key。
- tag：标签，在 InfluxDB 中，tag 是一个非常重要的部分，表名+tag 一起作为数据库的索引，是“key-value”的形式。
- timestamp：每一条数据都需要指定一个时间戳，在 TSM 存储引擎中会特殊对待，以为了优化后续的查询操作。
- field name：数据。

## 与传统数据库名词的对比

| InfluxDB    | 传统数据库 |
| ----------- | ---------- |
| database    | 数据库     |
| measurement | 表         |
| points      | 记录       |

## 名词

- Point：由时间戳（time）、数据（field）、标签（tags）组成。

| Point 属性 | 传统数据库                                       |
| ---------- | ------------------------------------------------ |
| time       | 每个数据记录时间，是数据库中的主索引(会自动生成) |
| field      | 各种记录值（没有索引的属性）                     |
| tags       | 各种有索引的属性                                 |

- Series：Series 相当于是 InfluxDB 中一些数据的集合，在同一个 database 中，retention policy、measurement、tag sets 完全相同的数据同属于一个 series，同一个 series 的数据在物理上会按照时间顺序排列存储在一起。

- Shard：每一个存储策略下会存在许多 shard，每一个 shard 存储一个指定时间段内的数据，并且不重复，每一个 shard 都对应一个底层的 tsm 存储引擎，有独立的 cache、wal、tsm file。

- TSM 存储引擎组件

1. Cache： LSM Tree 中的 memtabl，插入数据时，实际上是同时往 cache 与 wal 中写入数据，可以认为 cache 是 wal 文件中的数据在内存中的缓存。当 InfluxDB 启动时，会遍历所有的 wal 文件，重新构造 cache，这样即使系统出现故障，也不会导致数据的丢失。
   默认上限为 25MB，每当 cache 中的数据达到阀值后，会将当前的 cache 进行一次快照，之后清空当前 cache 中的内容，再创建一个新的 wal 文件用于写入，剩下的 wal 文件最后会被删除，快照中的数据会经过排序写入一个新的 tsm 文件中。
2. WAL：持久化数据，当系统崩溃后可以通过 wal 文件恢复还没有写入到 tsm 文件中的数据。
3. TSM File：单个 tsm file 大小最大为 2GB，用于存放数据。
4. Compactor：在后台持续运行，每隔 1 秒会检查一次是否有需要压缩合并的数据。

## 目录结构

| 目录 | 说明                              |
| ---- | --------------------------------- |
| meta | 存储数据库的一些元数据（meta.db） |
| wal  | 存储预写日志文件（\*.wal）        |
| data | 存储实际存储的数据文件（\*.tsm ） |

## 基本操作

- 客户端命令行方式

| 操作                                      | 说明                 |
| ----------------------------------------- | -------------------- |
| show databases                            | 显示数据库           |
| create database xxx                       | 新建数据库           |
| drop database xxx                         | 删除数据库           |
| use xxx                                   | 使用某个数据库       |
| show measurements                         | 显示所有表           |
| insert xxxTable,in dex=indexxxx value=xxx | 新建表               |
| drop measurement xxxTable                 | 删除表               |
| insert xxxTable,index=indexxxx value=xxx  | 增加数据             |
| select \* from xxxTable                   | 查询数据             |
| -                                         | 无修改和删除数据操作 |
| show series from mem                      | series 操作          |

- HTTP API 接口

1. 建立数据库
   curl -POST http://localhost:8086/query --data-urlencode "q=CREATE DATABASE xxx"

2. 删除数据库
   curl -POST http://localhost:8086/query --data-urlencode "q=DROP DATABASE xxx"
3. 添加数据
   curl -i -XPOST http://localhost:8086/write?db=xxx --data-binary "xxxTable,tag1=xxxtag,tag2=xxxtag value=0.64 1434055562000000000"
4. 查询
   curl -GET http://localhost:8086/query?pretty=true --data-urlencode "db=xxx"
   --data-urlencode "q=SELECT value FROM xxxTable WHERE tag1='xxx'"

- 各语言 API 库

## 常用函数

| 操作                    | 说明                                                              | 语法                                                                                                          |
| ----------------------- | ----------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- |
| count                   | 返回一个（field）字段中的非空值的数量                             | SELECT COUNT(<field_key>) FROM <measurement_name> [WHERE <stuff>] [GROUP BY <stuff>]                          |
| distinct                | 返回一个字段（field）的唯一值                                     | SELECT DISTINCT(<field_key>) FROM <measurement_name> [WHERE <stuff>] [GROUP BY <stuff>]                       |
| mean                    | 返回一个字段（field）中的值的算术平均值（平均值）                 | SELECT MEAN(<field_key>) FROM <measurement_name> [WHERE <stuff>] [GROUP BY <stuff>]                           |
| median                  | 从单个字段（field）中的排序值返回中间值（中位数）                 | SELECT MEAN(<field_key>) FROM <measurement_name> [WHERE <stuff>] [GROUP BY <stuff>]                           |
| spread                  | 返回字段的最小值和最大值之间的差值                                | SELECT SPREAD(<field_key>) FROM <measurement_name> [WHERE <stuff>] [GROUP BY <stuff>]                         |
| sum                     | 返回一个字段中的所有值的和                                        | SELECT SUM(<field_key>) FROM <measurement_name> [WHERE <stuff>] [GROUP BY <stuff>]                            |
| top                     | 返回一个字段中最大的 N 个值                                       |                                                                                                               |
| bottom                  | 返回一个字段中最小的 N 个值                                       |                                                                                                               |
| first                   | 返回一个字段中最老的取值                                          | SELECT FIRST(<field_key>)[,<tag_key(s)>] FROM <measurement_name> [WHERE <stuff>] [GROUP BY <stuff>]           |
| last                    | 返回一个字段中最新的取值                                          | SELECT LAST(<field_key>)[,<tag_key(s)>] FROM <measurement_name> [WHERE <stuff>] [GROUP BY <stuff>]            |
| max                     | 返回一个字段中的最小值                                            | SELECT MIN(<field_key>)[,<tag_key(s)>] FROM <measurement_name> [WHERE <stuff>] [GROUP BY <stuff>]             |
| percentile              | 返回排序值排位为 N 的百分值                                       | SELECT PERCENTILE(<field_key>, <N>)[,<tag_key(s)>] FROM <measurement_name> [WHERE <stuff>] [GROUP BY <stuff>] |
| derivative              | 返回一个字段在一个 series 中的变化率                              | SELECT DERIVATIVE(<field_key>, [<unit>]) FROM <measurement_name> [WHERE <stuff>]                              |
| difference              | 返回一个字段中连续的时间值之间的差异                              | SELECT DIFFERENCE(<field_key>) FROM <measurement_name> [WHERE <stuff>]                                        |
| elapsed                 | 返回一个字段在连续的时间间隔间的差异，间隔单位可选，默认为 1 纳秒 | SELECT ELAPSED(<field_key>, <unit>) FROM <measurement_name> [WHERE <stuff>]                                   |
| moving_average          | 返回一个连续字段值的移动平均值                                    | SELECT MOVING_AVERAGE(<field_key>,<window>) FROM <measurement_name> [WHERE <stuff>]                           |
| non_negative_derivative | 返回在一个 series 中的一个字段中值的变化的非负速率                | SELECT NON_NEGATIVE_DERIVATIVE(<field_key>, [<unit>]) FROM <measurement_name> [WHERE <stuff>]                 |
| stddev                  | 返回一个字段中的值的标准偏差                                      | SELECT STDDEV(<field_key>) FROM <measurement_name> [WHERE <stuff>] [GROUP BY <stuff>]                         |

## 环境搭建

### windows

- 下载链接(https://portal.influxdata.com/downloads)

- 配置

| 目录 | 配置项目（influxdb.conf） |
| ---- | ------------------------- |
| meta | [meta]/dir                |
| data | [data]/dir                |
| wal  | [data]/wal-dir            |

- 启动 influxdb 服务器
  双击 influxd.exe

* 启动 influxdb 命令行客户端
  双击 influx.exe

### linux

## 性能测试

- 测试环境
  i3 4G Win10 64 位

- 测试数据
  1000W 条数据，每条数据 280 个字段

* 测试结果
  select count(value1) from tabl
  用时：4 秒左右（目测）
