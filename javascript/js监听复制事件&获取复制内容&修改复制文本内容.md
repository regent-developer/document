# js监听复制事件&获取复制内容&修改复制文本内容


```javascript
    // 使用Dom获取需要操作禁用时间的元素
    let element: any = document.getElementById('xxx');
 
    // 为该元素添加 copy 事件监听器
    element.addEventListener('copy', function (event: any) {
      // 在这里编写你想要执行的操作
      console.log('复制事件被触发');
 
      var selection: any = window.getSelection();
      var selectedText = selection.toString();
      console.log(selectedText, '打印查看复制的文本内容')
      // 将selectedText调用接口传给后端
 
      // 可以通过 event.clipboardData 对象来设置剪贴板的数据
      event.clipboardData.setData('text/plain', '自定义的复制文本(替换复制的文本内容)');
 
      // 阻止默认的复制行为
      event.preventDefault();
    });
```
