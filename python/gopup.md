# gopup

GoPUP项目进行了公共数据采集，主要有指数数据、宏观经济数据、新经济数据、微博KOL数据、信息数据、生活数据、疫情数据等。  

http://doc.gopup.cn/#/README


## 安装方法
```shell
pip install gopup
```

## 升级方法
```shell
pip install gopup --upgrade
```

## 示例
```python
import gopup as gp
df = gp.weibo_index(word='疫情',time_type='3month')
for item in df.iterrows():
    print(item[1][0])
```