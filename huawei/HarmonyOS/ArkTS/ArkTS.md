# ArkTS

ArkTS是鸿蒙生态的应用开发语言。它在保持TypeScript（简称TS）基本语法风格的基础上，对TS的动态类型特性施加更严格的约束，引入静态类型。同时，提供了声明式UI、状态管理等相应的能力，让开发者可以以更简洁、更自然的方式开发高性能应用。

## 官方文档
https://developer.huawei.com/consumer/cn/arkts/

## 基本语法

### 概述
* 装饰器： 用于装饰类、结构、方法以及变量，并赋予其特殊的含义。@Entry、@Component和@State等都是装饰器，@Component表示自定义组件，@Entry表示该自定义组件为入口组件，@State表示组件中的状态变量，状态变量变化会触发UI刷新。
* UI描述：以声明式的方式来描述UI的结构。
* 自定义组件：可复用的UI单元，可组合其他组件。
* 系统组件：ArkUI框架中默认内置的基础和容器组件，可直接被开发者调用。
* 属性方法：组件可以通过链式调用配置多项属性。
* 事件方法：组件可以通过链式调用设置多个事件的响应逻辑。
* 系统组件、属性方法、事件方法具体使用可参考基于ArkTS的声明式开发范式。

### 声明式UI描述

#### 创建组件
根据组件构造方法的不同，创建组件包含有参数和无参数两种方式。

* 无参数
```js
Component()
```

* 有参数
```js
Component('xxx')
```

#### 配置属性
属性方法以“.”链式调用的方式配置系统组件的样式和其他属性，建议每个属性方法单独写一行。

```js
Component('xxx').property('xxx')
```

#### 配置事件
事件方法以“.”链式调用的方式配置系统组件支持的事件，建议每个事件方法单独写一行。

```js
// 使用箭头函数配置组件的事件方法。
Component('xxx').onClick(() => {
    xxxxx;
  })

// 使用匿名函数表达式配置组件的事件方法，要求使用bind，以确保函数体中的this指向当前组件。
Component('xxx').onClick(function(){
    xxxxx;
  }.bind(this))

// 使用组件的成员函数配置组件的事件方法。
myClickHandler(): void {
  xxxxx;
}

Component('xxx').onClick(this.myClickHandler.bind(this))

// 使用声明的箭头函数，可以直接调用，不需要bind this。
fn = () => {
  xxxx;
}
...
Component('xxx').onClick(this.fn)

```

#### 配置子组件
如果组件支持子组件配置，则需在尾随闭包"{...}"中为组件添加子组件的UI描述。Column、Row、Stack、Grid、List等组件都是容器组件。


## 状态管理

## 渲染控制