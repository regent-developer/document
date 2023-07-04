# depcheck 

npm-check 用于检查项目中的依赖项，并提供有关过期，未使用和缺少依赖项的信息。npm-check 是基于 depcheck 实现的。它可以提示项目依赖项的状态，但它只会检查并更新项目的直接依赖项，并不会检查和更新嵌套的依赖项（即项目的依赖项的依赖项）  

## 安装
```shell
npm install -g npm-check
```

* node.js >= 10  

## 使用
```shell
depcheck <你的项目目录>
```
结果类型说明：  
* Unused 表示没有使用的依赖包
* Missing 表示使用到了但是没有在json文件中声明的依赖包