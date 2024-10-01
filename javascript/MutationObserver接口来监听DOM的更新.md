# JS使用MutationObserver接口来监听DOM的更新

在JavaScript中，可以使用MutationObserver接口来监听DOM的更新。以下是一个使用MutationObserver的示例代码，它监听一个DOM节点的变化，并在变化发生时输出信息

```js
// 选择目标节点
const targetNode = document.getElementById('some-id');
 
// 创建一个观察者对象
const observer = new MutationObserver(function(mutationsList, observer) {
    // 使用mutationsList变化记录进行操作
    for(let mutation of mutationsList) {
        if (mutation.type === 'childList' || mutation.type === 'attributes') {
            console.log('DOM has been updated!');
        }
    }
});
 
// 观察者的配置（观察目标节点的子节点变化和属性变化）
const config = { attributes: true, childList: true, subtree: true };
 
// 传入目标节点和观察选项并开始观察
observer.observe(targetNode, config);
 
// 以后，你可以停止观察
observer.disconnect();
```