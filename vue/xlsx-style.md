# 导入xlsx-style后cpexcel.js文件编译错误的解决方法

## 问题

import XLSX from "xlsx-style"报错：This relative module was not found: ./cptable in ./node_modules/xlsx-style@0.8.13@xlsx-style/dist/cpexcel.js\  


## 解决方法

1，修改源码

在\node_modules\xlsx-style\dist\cpexcel.js 807行 的 var cpt = require(’./cpt’ + ‘able’); 改成 var cpt = cptable;  


2，追加vue.config.js

2.1 vue2x版本  

在webpack.base.conf.js中追加下面的配置。
```js
module.exports = {
	externals: {
    	'./cptable': 'var cptable'
 	}
}
```

2.2 vue3x版本  
vue-cli3 脚手架搭建完成后，项目目录中没有 vue.config.js 文件，需要手动创建。
vue.config.js 是一个可选的配置文件，如果项目的 (和 package.json 同级的) 根目录中存在这个文件，那么它会被 @vue/cli-service 自动加载。  

在vue.congif.js中追加下面的配置。
```js
module.exports = {
	configureWebpack: {
    	externals: {
      		'./cptable': 'var cptable'
    	}
  	}
}
```

