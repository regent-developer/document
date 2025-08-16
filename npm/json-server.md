#  json-server

一个用 JSON 文件直接生成完整 REST API 的工具，并且支持直接对这个 JSON 数据进行增删查改（持久化到文件）。

## 安装
npm install -g json-server

## 准备数据
{
  "users":[
    {"id":1,"name":"Tom"},
    {"id":2,"name":"Jerry"}
]
}

## 启动服务
json-server --watch db.json --port 3000

## 访问接口
GET    http://localhost:3000/users
POST   http://localhost:3000/users
PUT    http://localhost:3000/users/1
DELETE http://localhost:3000/users/1






