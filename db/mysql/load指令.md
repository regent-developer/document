# load

## 1. 数据库连接服务器时, 加上参数 --local-infile
mysql --local-infile -u root -p

## 2. 设置全局参数local_file为1, 开启从本地加载文件导入数据的开关
set global local_infile = 1;

## 3. 执行load指令将准备好的数据, 加载到表结构中
load data local infile 'D:\data.csv' into table people fields terminated by ',' lines terminated by '\n';