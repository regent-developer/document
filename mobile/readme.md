# 移动端知识点总结

## 屏幕
通常我们所指的屏幕尺寸，实际上是指屏幕对角线的长度（一般用英寸来度量）

## 分辨率
屏幕的垂直和水平最多放像素的个数

## 长度单位
* 相对长度单位
em ex ch rem vw vh vmin vmax %

* 绝对长度单位
cm mm in px pt pc

## 像素密度
* DPI(Dots Per Inch)是印刷行业中用来表示打印机每英寸可以喷的墨汁点数
* PPI (Pixels Per Inch)表示每英寸所拥有的像素数目，数值越高，代表屏幕能以更高的密度显示图像

PPI的值越大说明单位尺寸里所能容纳的像素的数量越多，所能展现画面的品质也就越精细，反之就越粗糙。Retina即视网膜屏幕，苹果注册的命名方式，指具有较高的PPI（大于320）的屏幕


## 像素
* 物理像素(分辨率)是指设备屏幕实际拥有的像素点。比如iPhone6的屏幕在宽度方向有750个像素点，高度方向有1334个像素点，所以iPhone 6总共有750*1334个物理像素。
* 像素比dpr是物理像素与设备独立像素之间的比例。当像素比为1:1时，使用1个物理像素显示1个设备独立像素;当像素比为2:1时，使用4个物理像素(长2倍，宽2倍，乘起来就是4倍)显示1个设备独立像素
* CSS像素: CSS中的长度单位，在CSS中使用的px都是指CSS像素。CSS中的1px并不等于设备的1px。从iphone4开始，推出了所谓的Retina屏，分辨率提高了一倍，变成640*960. 但屏幕尺寸却没变化, 这就意味着同样大小的屏幕上，像素却多了一倍，这时，1个CSS像素是等于4个物理像素。


## rem px em的区别
* px:像素 相对单位
* rem:相对单位，相对于HTML元素的根元素大小 默认1rem =16px
* em:相对元素，相对于当前的元素的大小，如果当前的元素没有设置大小，向上级找参考点，找具有大小的元素1em=16px

