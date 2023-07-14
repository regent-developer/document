# Battery Status API



## Battery Status API的使用

```js
// 请求电池信息
navigator.getBattery().then(function (battery) {
  // 获取设备电量剩余百分比
  var level = battery.level //最大值为1,对应电量100%
  console.log('Level: ' + level * 100 + '%')

  // 获取设备充电状态
  var charging = battery.charging
  console.log('充电状态: ' + charging)

  // 获取设备完全充电需要的时间
  var chargingTime = battery.chargingTime
  console.log('完全充电需要的时间: ' + chargingTime)

  // 获取设备完全放电需要的时间
  var dischargingTime = battery.dischargingTime
  console.log('完全放电需要的时间: ' + dischargingTime)
}) 
```





### 监听电池状态变化

```js
navigator.getBattery().then(function (battery) {
  // 添加事件，当设备电量改变时触发
  battery.addEventListener('levelchange', function () {
    console.log('电量改变: ' + battery.level)
  })

  // 添加事件，当设备充电状态改变时触发
  battery.addEventListener('chargingchange', function () {
    console.log('充电状态改变: ' + battery.charging)
  })

  // 添加事件，当设备完全充电需要时间改变时触发
  battery.addEventListener('chargingtimechange', function () {
    console.log('完全充电需要时间: ' + battery.chargingTime)
  })

  // 添加事件，当设备完全放电需要时间改变时触发
  battery.addEventListener('dischargingtimechange', function () {
    console.log('完全放电需要时间: ' + battery.dischargingTime)
  })
})

```





## 兼容性

https://developer.mozilla.org/en-US/docs/Web/API/BatteryManager#browser_compatibility



## 参考网站

https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API

