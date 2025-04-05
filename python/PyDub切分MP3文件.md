# PyDub切分MP3文件

## 安装PyDub

```shell
pip install -U pydub
```

## 代码

```python
from pydub import AudioSegment
# 从pydub库中导入AudioSegment类
 podcast = AudioSegment.from_mp3("xxx.mp3")
# 从xxx.mp3文件中读取音频文件
 # PyDub handles time in milliseconds
# PyDub以毫秒为单位处理时间
 ten_minutes = 15 * 60 * 1000
# 定义一个变量ten_minutes，表示15分钟，单位为毫秒
 total_length = len(podcast)
# 定义一个变量total_length，表示音频的总长度
 start = 0
# 定义一个变量start，表示切割音频的起始位置
 index = 0
# 定义一个变量index，表示切割音频的索引
 while start < total_length:
# 当start小于音频的总长度时，执行循环
   end = start + ten_minutes
   # 定义一个变量end，表示切割音频的结束位置
   if end < total_length:
       chunk = podcast[start:end]
   else:
       chunk = podcast[start:]
   # 如果end小于音频的总长度，则切割音频为[start:end]的片段
   # 否则，切割音频为[start:]的片段
   with open(f"xxx_clip_{index}.mp3", "wb") as f:
       chunk.export(f, format="mp3")
   # 将切割的音频片段保存为xxx_clip_index.mp3文件
   start = end
   # 将start更新为end
   index += 1
```

