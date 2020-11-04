# onblur和onclick冲突

原因：js的单线程限制了只允许一个事件触发，onblur的优先性高于onclick

## 给onblur加settimeout，给onclick加cleartimeout
```js
var timer;
inp.onblur = function() {
        timer = setTimeout(function() {
        	//onblur事件要执行的代码
   		    some code；
        }, 120); 
    };

btn.onclick = function() {
    clearTimeout(timer);
    //onclick事件要执行的代码
    some code;
};
```



## onclick->onmousedown
```js
inp.onblur = function() {
    //onblur事件要执行的代码
   	some code；
};

btn.onmousedown = function() {
    //onclick事件要执行的代码
    some code;
};
```