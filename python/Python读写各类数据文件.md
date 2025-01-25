# Python读写各类数据文件

## Python 读写 txt 文件
```python
# 写入 TXT 文件
with open('example.txt', 'w', encoding='utf-8') as file:
    file.write("你好，Python 文件读写示例！\n第二行内容。")
 
# 读取 TXT 文件
with open('example.txt', 'r', encoding='utf-8') as file:
    content = file.read()
    print(content)

```

## Python 读写 CSV 文件
```python
import csv
# 写入 CSV 文件
with open('example.csv', 'w', newline='', encoding='utf-8') as file:
    writer = csv.writer(file)
    writer.writerow(["姓名", "年龄", "城市"])
    writer.writerow(["张三", 25, "北京"])
    writer.writerow(["李四", 30, "上海"])
 
# 读取 CSV 文件
with open('example.csv', 'r', encoding='utf-8') as file:
    reader = csv.reader(file)
    for row in reader:
        print(row)
```

## Python 读写 Excel 文件
```python
import pandas as pd
# 写入 Excel 文件
data = {'姓名': ['张三', '李四'], '年龄': [25, 30], '城市': ['北京', '上海']}
df = pd.DataFrame(data)
df.to_excel('example.xlsx', index=False)
 
# 读取 Excel 文件
df_read = pd.read_excel('example.xlsx')
print(df_read)
```

## Python 读写 JSON 文件
```python
import json
# 写入 JSON 文件
data = {'name': '张三', 'age': 25, 'city': '北京'}
with open('example.json', 'w', encoding='utf-8') as file:
    json.dump(data, file, ensure_ascii=False, indent=4)
 
# 读取 JSON 文件
with open('example.json', 'r', encoding='utf-8') as file:
    data_read = json.load(file)
    print(data_read)
```


## Python 读写 SQLite 数据库
```python
import sqlite3
# 创建 SQLite 数据库并写入数据
conn = sqlite3.connect('example.db')
cursor = conn.cursor()
cursor.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT, age INTEGER)")
cursor.execute("INSERT INTO users (name, age) VALUES (?, ?)", ('张三', 25))
conn.commit()
 
# 读取 SQLite 数据库数据
cursor.execute("SELECT * FROM users")
rows = cursor.fetchall()
for row in rows:
    print(row)
conn.close()
```

## Python 读写 XML 文件
```python
import xml.etree.ElementTree as ET
# 写入 XML 文件
root = ET.Element("root")
user = ET.SubElement(root, "user")
ET.SubElement(user, "name").text = "张三"
ET.SubElement(user, "age").text = "25"
tree = ET.ElementTree(root)
tree.write("example.xml", encoding='utf-8', xml_declaration=True)
 
# 读取 XML 文件
tree = ET.parse('example.xml')
root = tree.getroot()
for elem in root.iter():
    print(elem.tag, elem.text)
```

## Python 读写 Parquet 文件
```python
import pandas as pd
# 写入 Parquet 文件
data = {'姓名': ['张三', '李四'], '年龄': [25, 30], '城市': ['北京', '上海']}
df = pd.DataFrame(data)
df.to_parquet('example.parquet', index=False)
 
# 读取 Parquet 文件
df_read = pd.read_parquet('example.parquet')
print(df_read)
```

## Python 读写 YAML 文件
```python
import yaml
# 写入 YAML 文件
data = {'姓名': '张三', '年龄': 25, '城市': '北京'}
with open('example.yaml', 'w', encoding='utf-8') as file:
    yaml.dump(data, file, allow_unicode=True)
 
# 读取 YAML 文件
with open('example.yaml', 'r', encoding='utf-8') as file:
    data_read = yaml.safe_load(file)
    print(data_read)
```

## Python 读写 INI 文件
```python
import configparser
# 写入 INI 文件
config = configparser.ConfigParser()
config['DEFAULT'] = {'Server': 'localhost', 'Port': '8080'}
with open('example.ini', 'w', encoding='utf-8') as configfile:
    config.write(configfile)
 
# 读取 INI 文件
config.read('example.ini', encoding='utf-8')
print(dict(config['DEFAULT']))
```

##  Python 读写 PDF 文件
```python
from fpdf import FPDF
from PyPDF2 import PdfReader
# 写入 PDF 文件
pdf = FPDF()
pdf.add_page()
pdf.set_font('Arial', size=12)
pdf.cell(200, 10, txt="Python PDF 文件", ln=True, align='C')
pdf.output("example.pdf")
 
# 读取 PDF 文件
reader = PdfReader("example.pdf")
for page in reader.pages:
    print(page.extract_text())
```

## Python 读写 ZIP 文件
```python
import zipfile
# 写入 ZIP 文件
with zipfile.ZipFile('example.zip', 'w') as zipf:
    zipf.write('example.txt')
 
# 解压 ZIP 文件
with zipfile.ZipFile('example.zip', 'r') as zipf:
    zipf.extractall('output')
```

## Python 读写 Log 文件
```python
import logging
# 写入日志
logging.basicConfig(filename='example.log', level=logging.INFO, format='%(asctime)s - %(message)s')
logging.info("这是一个日志信息")
logging.warning("这是一个警告信息")
logging.error("这是一个错误信息")
 
# 读取日志
with open('example.log', 'r', encoding='utf-8') as file:
    logs = file.read()
    print(logs)
```