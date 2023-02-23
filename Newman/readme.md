# Newman

newman是postman的命令行collections(集合)的运行程序，可以直接从命令行运行和测试postman collections(集合)，而且有可拓展性，可以与CI/CD(持续集成/持续交付)结合，比如Jenkins。

## 安装
```shell
npm install -g newman
```

## 安装报告插件
```shell
npm install -g newman-reporter-html
```

## 导出 postman Collections
打开postman，选择要导出的集合 右键==>Export，选择Collection v2.1 并导出

## newman快速运行
newman run 导出的文件名.postman_collections.json

