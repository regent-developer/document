# python操作mysql

## 安装

```shell
pip install pymysql
```

## 基本操作

### 插入操作
```python
import pymysql

# 1. 创建连接（Connection）
conn = pymysql.connect(host='127.0.0.1', port=3306,
                       user='root', password='123456',
                       database='db2', charset='utf8mb4')
try:
    # 2. 获取游标对象（Cursor）
    with conn.cursor() as cursor:
        # 3. 通过游标对象向数据库服务器发出SQL语句
        cursor.execute('select `no`, `name`, `location` from `tb_dept`')
        # 4. 通过游标对象抓取数据
        row = cursor.fetchone()
        while row:
            print(row)
            row = cursor.fetchone()
except pymysql.MySQLError as err:
    print(type(err), err)
finally:
    # 5. 关闭连接释放资源
    conn.close()
```

### 批量插入
```python
import pymysql

# 提示用户输入多条部门信息
data_list = []
while True:
    no = input('请输入部门编号（输入q结束）: ')
    if no.lower() == 'q':
        break
    name = input('请输入部门名称: ')
    location = input('请输入部门所在地: ')
    # 将输入的数据存储为元组并添加到列表中
    data_list.append((int(no), name, location))

# 1. 创建连接（Connection）
conn = pymysql.connect(host='127.0.0.1', port=3306,
                       user='root', password='123456',
                       database='db2', charset='utf8mb4')

try:
    # 2. 获取游标对象（Cursor）
    with conn.cursor() as cursor:
        # 3. 通过游标对象向数据库服务器发出批量插入的SQL语句
        affected_rows = cursor.executemany(
            'insert into `tb_dept` values (%s, %s, %s)',
            data_list
        )
        if affected_rows == len(data_list):
            print('批量新增部门成功，共插入 {} 条数据！'.format(affected_rows))
            # 4. 提交事务（transaction）
        conn.commit()

except pymysql.MySQLError as err:
    # 4. 回滚事务
    conn.rollback()
    print(type(err), err)
finally:
    # 5. 关闭连接释放资源
    conn.close()

```

### 删除操作
```python
import pymysql

no = int(input('部门编号: '))

# 1. 创建连接（Connection）
conn = pymysql.connect(host='127.0.0.1', port=3306,
                       user='root', password='123456',
                       database='db2', charset='utf8mb4',
                       autocommit=True)
try:
    # 2. 获取游标对象（Cursor）
    with conn.cursor() as cursor:
        # 3. 通过游标对象向数据库服务器发出SQL语句
        affected_rows = cursor.execute(
            'delete from `tb_dept` where `no`=%s',
            (no, )
        )
        if affected_rows == 1:
            print('删除部门成功!!!')
finally:
    # 5. 关闭连接释放资源
    conn.close()

```

### 更新操作
```python
import pymysql

no = int(input('部门编号: '))
name = input('部门名称: ')
location = input('部门所在地: ')

# 1. 创建连接（Connection）
conn = pymysql.connect(host='127.0.0.1', port=3306,
                       user='root', password='123456',
                       database='db2', charset='utf8mb4')
try:
    # 2. 获取游标对象（Cursor）
    with conn.cursor() as cursor:
        # 3. 通过游标对象向数据库服务器发出SQL语句
        affected_rows = cursor.execute(
            'update `tb_dept` set `name`=%s, `location`=%s where `no`=%s',
            (name, location, no)
        )
        if affected_rows == 1:
            print('更新部门信息成功!!!')
    # 4. 提交事务
    conn.commit()
except pymysql.MySQLError as err:
    # 4. 回滚事务
    conn.rollback()
    print(type(err), err)
finally:
    # 5. 关闭连接释放资源
    conn.close()

```

### 查询操作
```python
import pymysql

# 1. 创建连接（Connection）
conn = pymysql.connect(host='127.0.0.1', port=3306,
                       user='root', password='123456',
                       database='db2', charset='utf8mb4')
try:
    # 2. 获取游标对象（Cursor）
    with conn.cursor() as cursor:
        # 3. 通过游标对象向数据库服务器发出SQL语句
        cursor.execute('select `no`, `name`, `location` from `tb_dept`')
        # 4. 通过游标对象抓取数据
        row = cursor.fetchone()
        while row:
            print(row)
            row = cursor.fetchone()
except pymysql.MySQLError as err:
    print(type(err), err)
finally:
    # 5. 关闭连接释放资源
    conn.close()

```


### 分页查询
```python
import pymysql

page = int(input('页码: '))
size = int(input('大小: '))

# 1. 创建连接（Connection）
con = pymysql.connect(host='127.0.0.1', port=3306,
                      user='root', password='123456',
                      database='db2', charset='utf8')
try:
    # 2. 获取游标对象（Cursor）
    with con.cursor(pymysql.cursors.DictCursor) as cursor:
        # 3. 通过游标对象向数据库服务器发出SQL语句
        cursor.execute(
            'select `no`, `name`, `location` from `tb_dept` order by `no` desc limit %s,%s',
            ((page - 1) * size, size)
        )
        # 4. 通过游标对象抓取数据
        for emp_dict in cursor.fetchall():
            print(emp_dict)
finally:
    # 5. 关闭连接释放资源
    con.close()
```