# npm install不成功

现象1

报opensslerror相关错误

解决方案

```
$env:NODE_OPTIONS="--openssl-legacy-provider"
```


现象2

执行npm build 发生：ERROR Build failed with errors

解决方案

删除node_modules文件夹  
删除package-lock.json文件   
清空npm缓存 npm clean cache -f  
重新npm install
