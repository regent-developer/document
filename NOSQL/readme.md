# 常见的非关系型数据库NOSQL分类
|NOSQL类型|主要数据库产品|类型特色|
|-------|-------|-------|
|K-V键值对存储类型|Redis、Memcached|使用key可以快速的查询到value，Memcached可以支持String类型的值value，Redis支持的值的数据类型很多如：String\set\hash\sortset\list等等|
|文档存储类型|MongoDB、CouchDB|使用JSON或类JSON的BSON数据结构，存储的内容为文档型，能够实现部分关系型数据库的功能|
|列存储类型|HBase、Cassandra|按照列进行数据存储，该类型便于存储结构化和半结构化的数据，可以方便做数据压缩和针对某一列或者某几列的数据查询|
|图存储类型|Neo4J、FlockDB|以图形关系存储数据，能够很好的弥补关系型数据库在图形存储时的不足|
|对象存储类型|Db4o、Versant|该存储类型的数据库通过类似面向对象的方式操作数据库，通过对象的方式存取数据|
|XML存储类型|Berkeley DB XML、BaseX|该类型数据库可以高效的存储XML数据，并且支持XML的内部查询语法，例如；XQuery、XPath|
