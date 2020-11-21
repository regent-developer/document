## 防抖和节流

### 防抖

如果短时间内大量触发同一事件，只会执行一次函数。

```js

function debounce(func, wait){
    let timeout;
    return function(){
        if(timeout){
            clearTimeout(timeout)
        }
        timeout = setTimeout(() => {
            func.apply(this, arguments)
        }, wait)
    }
}

```

### 节流
让函数执行一次后，在某个时间段内暂时失效，过了这段时间后再重新激活
```js
function throttle(func, wait){
    let timeout;
        return function(){
            if(!timeout){
                timeout = setTimeout(() => {
                    timeout = null;
                    func.apply(this, arguments)
                }, wait)
            }
    }
}
```

## 数组值变换
```js
let arr = [1,3,5,7,9];
arr.forEach(function(item,index,arr){
    arr[index] = 1;
})
```
