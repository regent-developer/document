# Eventbus实现原理

EventBus是消息传递的一种方式，基于一个消息中心，订阅和发布消息的模式，称为发布订阅者模式。

## 实现
```js
class Bus {
  constructor () {
    this.callbacks = {}
  }

  // 订阅
  $on(name,fn) {
    this.callbacks[name] = this.callbacks[name] || []
    this.callbacks[name].push(fn)
  }

  // 发布
  $emit(name,args) {
    if(this.callbacks[name]){
       //存在遍历所有callback
       this.callbacks[name].forEach(cb => cb(args))
    }
  }

  // 取消
//  $off(name) {
//    this.callbacks[name].pop()
//  }
}
```

## 使用
```js
const EventBus = new EventBusClass()

// 订阅
EventBus.on('fn1', function(msg) {
    alert(`订阅的消息是：${msg}`);
});

// 订阅事件
EventBus.emit('fn1', '你好，世界！');
```