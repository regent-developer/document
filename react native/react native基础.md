# React Native基础

## 类组件

```js
import React, { Component } from 'react';
import { Text, View } from 'react-native';

class HelloWorldApp extends Component {
  render() {
    return (
      <View>
        <Text>Hello, world!</Text>
      </View>
    );
  }
}

export default HelloWorldApp;
```
## 函数式组件

```js
import React from 'react';
import { Text, View } from 'react-native';

const HelloWorldApp = () => {
  return (
    <View>
      <Text>Hello, world!</Text>
    </View>
  );
}

export default HelloWorldApp;
```

## 特定平台代码

### 使用Platform模块

* Platform.OS
```js
import { Platform } from 'react-native';

// ios平台
Platform.OS === 'ios'
// android平台
Platform.OS === 'android'
```

* Platform.select()

```js
import { Platform } from 'react-native';

Platform.select({
    ios: {
    // ios平台
    },
    android: {
    // android平台
    }
})

const Component = Platform.select({
  ios: () => require('ComponentIOS'),
  android: () => require('ComponentAndroid')
})();

<Component />;
```

* Platform.Version

```js
import { Platform } from 'react-native';

// android平台
if (Platform.Version === android版本) {
 
}

// ios平台
const majorVersionIOS = parseInt(Platform.Version, 10);
if (majorVersionIOS <= ios版本) {

}

```


### 使用特定平台扩展名

```
xxx.ios.js
xxx.android.js

import xxx from './xxx';
```

## Flex

* 在组件样式中使用flex可以使其在可利用的空间中动态地扩张或收缩。一般而言我们会使用flex:1来指定某个组件扩张以撑满所有剩余的空间。如果有多个并列的子组件使用了flex:1，则这些子组件会平分父容器中剩余的空间。如果这些并列的子组件的flex值不一样，则谁的值更大，谁占据剩余空间的比例就更大（即占据剩余空间的比等于并列组件间flex值的比）。
  
* 组件能够撑满剩余空间的前提是其父容器的尺寸不为零。如果父容器既没有固定的width和height，也没有设定flex，则父容器的尺寸为零。其子组件如果使用了flex，也是无法显示的。

* Flex Direction：在组件的style中指定flexDirection可以决定布局的主轴。子元素是应该沿着水平轴(row)方向排列，还是沿着竖直轴(column)方向排列呢？默认值是竖直轴(column)方向。
* Justify Content：在组件的style中指定justifyContent可以决定其子元素沿着主轴的排列方式。
* Align Items：在组件的 style 中指定alignItems可以决定其子元素沿着次轴（与主轴垂直的轴，比如若主轴方向为row，则次轴方向为column）的排列方式。
* Align Content：
* Align Self：
* Flex Wrap：
* Flex Basis：
* Grow：
* Shrink：


## 组件props的children属性
children属性是表示该组件的子节点，只要组件内部有子节点，props中就有该属性。

```js
import React, { useState } from "react";
import { View } from "react-native";

const Parent = () => {
  return (
    <Child>
      this is children node
    </Child>
  );
};

const Child = ({
    children
    }) => (
  <View>
      {children}
  </View>
);

export default Parent;
```
