# Android获取当前屏幕的宽高
```java
// 获取屏幕的显示参数
DisplayMetrics dm = getResources().getDisplayMetrics();
// 获取屏幕的宽度
int width = dm.widthPixels;
// 获取屏幕的高度
int height = dm.heightPixels;
```