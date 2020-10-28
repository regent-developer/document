# PM2命令

pm2是一个进程管理工具,可以用它来管理你的node进程，并查看node进程的状态，当然也支持性能监控，进程守护，负载均衡等功能。  

## 安装
```shell
npm install -g pm2
```

## 常用命令

### 启动
```shell
pm2 start index.js
```

### 启动，重命名并监视文件变化
```shell
pm2 start index.js --name="api" --watch
```

### 模式启动4个app.js的应用实例 # 4个应用程序会自动进行负载均衡

```shell
pm2 start app.js -i 4
```

### 停止
```shell
pm2 stop all
```

### 关闭并删除
```shell
pm2 delete all
```

### 列表显示
```shell
pm2 list
```

### CPU以及内存使用量
```shell
pm2 monit
```


### 显示应用程序信息
```shell
pm2 show [app-name]
```


### 显示log
```shell
pm2 logs
```


### 停止id为0的应用程序
```shell
pm2 stop 0
```


### 重启cluster mode下的所有应用
```shell
pm2 reload all
```

### 重启所有应用
```shell
pm2 restart all
```

### 删除指定应用
```shell
pm2 delete 0
```

### 把名字叫api的应用扩展到10个实例
```shell
pm2 scale api 10
```

### 创建开机自启动命令
```shell
pm2 startup
```

