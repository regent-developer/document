# @Preview

```ts
@Preview({
  title: 'Component1',  //预览组件的名称
  deviceType: 'phone',  //指定当前组件预览渲染的设备类型，默认为Phone
  width: 1080,  //预览设备的宽度，单位：px
  height: 2340,  //预览设备的长度，单位：px
  colorMode: 'light',  //显示的亮暗模式，当前支持取值为light
  dpi: 480,  //预览设备的屏幕DPI值
  locale: 'zh_CN',  //预览设备的语言，如zh_CN、en_US等
  orientation: 'portrait',  //预览设备的横竖屏状态，取值为portrait或landscape
  roundScreen: false  //设备的屏幕形状是否为圆形
})
```