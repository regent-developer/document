# ldap

LDAP（轻量级目录访问协议，Lightweight Directory Access Protocol)是实现提供被称为目录服务的信息服务。目录服务是一种特殊的数据库系统，其专门针对读取，浏览和搜索操作进行了特定的优化。目录一般用来包含描述性的，基于属性的信息并支持精细复杂的过滤能力。目录一般不支持通用数据库针对大量更新操作操作需要的复杂的事务管理或回卷策略。而目录服务的更新则一般都非常简单。这种目录可以存储包括个人信息、web链结、jpeg图像等各种信息。为了访问存储在目录中的信息，就需要使用运行在TCP/IP 之上的访问协议—LDAP。  


| 关键字        | 英文全称           | 含义  |
| ------------- |:-------------:    | -----:|
|dc	            |Domain Component	|域名的部分,其格式是将完整的域名分成几部分,如域名为example.com变成dc=example,dc=com|
|uid	        |User Id	        |用户ID,如“tom”|
|ou	            |Organization Unit	|组织单位,类似于Linux文件系统中的子目录,它是一个容器对象,组织单位可以包含其他各种对象(包括其他组织单元),如“market”|
|cn	            |Common Name	    |公共名称,如“Thomas Johansson”|
|sn	            |Surname	        |姓,如“Johansson”|
|dn	            |Distinguished Name	|惟一辨别名,类似于Linux文件系统中的绝对路径,每个对象都有一个惟一的名称, 如“uid= tom,ou=market,dc=example,dc=com”,在一个目录树中DN总是惟一的|
|rdn	        |Relative dn	    |相对辨别名,类似于文件系统中的相对路径,它是与目录树结构无关的部分,如“uid=tom”或“cn= Thomas Johansson”|
|c	            |Country            |	国家,如“CN”或“US”等.|
|o	            |Organization       |	组织名,如“Example,Inc.”|


例子：  
               +-------------------------------------------+
               |             o=example.com                 |
               +-------------------+-----------------------+
               |                   |                       |
   +-----------+              +----+----+                  +---------+
   |ou=managers|              |ou=people|                  |ou=group |  
   +----+---+--+              +--+---+--+                  +--+---+--+
        |   |                    |   |                        |   |
+--------+  +-----+     +--------+   +--------+      +-------->   +-------+
|cn=admin|  |cn=le|     |ou=test1|   |ou=test2|      |cn=admin|   |cn=ldap|
+--------+  +-----+     +--------+   +--------+      +--------+   +-------+
