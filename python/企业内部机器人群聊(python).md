# 企业内部机器人群聊

## 企业内部机器人群聊实现@人接入指南

https://open.dingtalk.com/document/orgapp/robot-message-type-staff-information-in-an-enterprise

## python实现
```python
# -*- coding:utf-8 -*-
import sys
import traceback
from urllib import parse

import requests
import time
import hashlib
import base64
import hmac
import json
from fake_useragent import UserAgent
# 钉钉Webhook地址
webhook = "https://oapi.dingtalk.com/robot/send"
access_token = "xxx"   # webhook 自动生成
secret = "xxx"  # 钉钉上可以生成
mobile_list = ["xxx"]   # @指定人的列表

# 加签函数
def get_sign(secret):
    timestamp = str(int(time.time() * 1000))  # 当前时间戳（毫秒）
    secret_byte = bytes(timestamp +'\n'+secret, encoding='utf-8')  # 创建签名数据
    hmac_key = bytes(secret, encoding='utf-8')  # 使用secret创建HMAC密钥
    sign = hmac.new(hmac_key, secret_byte, digestmod=hashlib.sha256).digest()  # 生成签名
    sign_base64 = parse.quote_plus(base64.b64encode(sign))
    return sign_base64, timestamp  # 返回签名和时间戳

# 发送消息函数
def send_message(content):
    sign, timestamp = get_sign(secret)  # 获取签名和时间戳
    url = f"{webhook}?access_token={access_token}&timestamp={timestamp}&sign={sign}"  # 拼接成最终请求地址
    stauts = content["stauts"]
    stauts = "成功" if stauts else "失败"
    msg_except = content["exception"]
    msg_time = str(time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(int(time.time()))))
    # markdown 类型展示
    message = {
     "msgtype": "markdown",
     "markdown": {
         "title":"程序执行情况",
         "text": f"**执行情况: {stauts}**\n\n"
                 f"**服务器类型: {sys.platform}**\n\n"
                 f"**异常信息: {msg_except}**\n\n"
                 f"**时间: {msg_time}**\n\n @137*******"
     },
      "at": {
          "atMobiles": mobile_list,
          "isAtAll": False
      }
 }
 	# 文本类型展示
    # message = {
    #     "msgtype": "text",  # 消息类型
    #     "at": {
    #       "atMobiles": mobile_list,
    #       "atUserIds": [],
    #       "isAtAll": False
    #     },
    #     "text": {
    #     "content": f"{content}+"+"@137*******"
    #     },
    # }
    headers = {'Content-Type': 'application/json', "User-Agent":UserAgent().Chrome}  # 设置请求头
    response = requests.post(url, data=json.dumps(message), headers=headers)  # 发送请求

    # 打印响应结果
    if response.status_code == 200:
        return response.json()
    else:
        return response.text

```