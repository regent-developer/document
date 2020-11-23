# replace全部替换的方法

```js
String.prototype.replaceAllEx = function(f,e){
    var reg=new RegExp(f,"g"); //创建正则RegExp对象   
    return this.replace(reg,e); 
}

str.replaceAllEx('src','dest');
```