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

## xlsx-style实例代码

```js
// 设置表格中cell默认的字体，居中，颜色等
 defaultCellStyle: {
   font: { name: "宋体", sz: 11, color: { auto: 1 } },
     border: {
       color: { auto: 1 }
     },
     alignment: {
        /// 自动换行
       wrapText: 1,
         // 居中
       horizontal: "center",
       vertical: "center",
       indent: 0
  }
 }
```

```js
// 从json转化为sheet，xslx中没有aoaToSheet的方法，该方法摘自官方test
sheet_from_array_of_arrays(data) {
  const ws = {};
  const range = {s: {c:10000000, r:10000000}, e: {c:0, r:0 }};
    for(let R = 0; R !== data.length; ++R) {
    for(let C = 0; C !== data[R].length; ++C) {
      if(range.s.r > R) range.s.r = R;
      if(range.s.c > C) range.s.c = C;
      if(range.e.r < R) range.e.r = R;
      if(range.e.c < C) range.e.c = C;
      /// 这里生成cell的时候，使用上面定义的默认样式
      const cell = {v: data[R][C], s: this.defaultCellStyle};
      if(cell.v == null) continue;
      const cell_ref = XLSX.utils.encode_cell({c:C,r:R});

      /* TEST: proper cell types and value handling */
      if(typeof cell.v === 'number') cell.t = 'n';
      else if(typeof cell.v === 'boolean') cell.t = 'b';
      else if(cell.v instanceof Date) {
      cell.t = 'n'; cell.z = XLSX.SSF._table[14];
      cell.v = this.dateNum(cell.v);
      }
      else cell.t = 's';
      ws[cell_ref] = cell;
    }
  }

  /* TEST: proper range */
  if(range.s.c < 10000000) ws['!ref'] = XLSX.utils.encode_range(range);
  return ws;
}
```


