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
// ArkTS语法不推荐使用成员函数配合bind this去配置组件的事件方法。
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
容器组件均支持子组件配置，可以实现相对复杂的多级嵌套。

### 自定义组件

#### 基本结构&创建自定义组件

在ArkUI中，UI显示的内容均为组件，由框架直接提供的称为系统组件，由开发者定义的称为自定义组件。

1. struct
   自定义组件基于struct实现，struct + 自定义组件名 + {...}的组合构成自定义组件，不能有继承关系。
2. @Component
   @Component装饰器仅能装饰struct关键字声明的数据结构。struct被@Component装饰后具备组件化的能力，需要实现build方法描述UI，一个struct只能被一个@Component装饰。
   * @Component可以接受一个可选的bool类型参数freezeWhenInactive  | 名称               | 类型 | 必填 | 说明                                                                |
     | ------------------ | ---- | ---- | ------------------------------------------------------------------- |
     | freezeWhenInactive | bool | 否   | 当组件处于非激活状态时，是否冻结组件的UI布局和渲染。默认值为false。 |
3. build()函数
   build()函数用于定义自定义组件的声明式UI描述，自定义组件必须定义build()函数。
   * @Entry装饰的自定义组件，其build()函数下的根节点唯一且必要，且必须为容器组件，其中ForEach禁止作为根节点。
   * @Component装饰的自定义组件，其build()函数下的根节点唯一且必要，可以为非容器组件，其中ForEach禁止作为根节点。
   * 不允许声明本地变量。
   * 不允许在UI描述里直接使用console.info，但允许在方法或者函数里使用
   * 不允许创建本地的作用域
   * 不允许调用没有用@Builder装饰的方法，允许系统组件的参数是TS方法的返回值。
   * 不允许使用switch语法，如果需要使用条件判断，请使用if。
   * 不允许使用表达式，请使用if组件
   * 不允许直接改变状态变量
4. @Entry
   @Entry装饰的自定义组件将作为UI页面的入口。在单个UI页面中，最多可以使用@Entry装饰一个自定义组件。
   * @Entry可以接受一个可选的LocalStorage的参数。| 名称             | 类型         | 必填 | 说明                                                                          |
     | ---------------- | ------------ | ---- | ----------------------------------------------------------------------------- |
     | routeName        | string       | 否   | 表示作为命名路由页面的名字。                                                  |
     | storage          | LocalStorage | 否   | 页面级的UI状态存储。                                                          |
     | useSharedStorage | boolean      | 否   | 是否使用LocalStorage.getShared()接口返回的LocalStorage实例对象，默认值false。 |
5. @Reusable
   @Reusable装饰的自定义组件具备可复用能力。
6. 成员函数/变量
   * 自定义组件的成员函数为私有的，且不建议声明成静态函数。
   * 自定义组件的成员变量为私有的，且不建议声明成静态变量。
   * 自定义组件的成员变量本地初始化有些是可选的，有些是必选的。具体是否需要本地初始化，是否需要从父组件通过参数传递初始化子组件的成员变量。
7. example

```ts
  @Component
  struct ChildComponent {
    build() {
      Button(`Hello World`)
    }
  }

  @Entry
  @Component
  struct MyComponent {
    build() {
      Row() {
        ChildComponent()
          .width(200)
          .height(300)
          .backgroundColor(Color.Red)
      }
    }
  }
```

#### 页面和自定义组件生命周期

页面生命周期，即被@Entry装饰的组件生命周期，提供以下生命周期接口：

1. onPageShow：页面每次显示时触发一次，包括路由过程、应用进入前台等场景。
2. onPageHide：页面每次隐藏时触发一次，包括路由过程、应用进入后台等场景。
3. onBackPress：当用户点击返回按钮时触发。

组件生命周期，即一般用@Component装饰的自定义组件的生命周期，提供以下生命周期接口：

1. aboutToAppear：组件即将出现时回调该接口，具体时机为在创建自定义组件的新实例后，在执行其build()函数之前执行。
2. onDidBuild：组件build()函数执行完成之后回调该接口，开发者可以在这个阶段进行埋点数据上报等不影响实际UI的功能。不建议在onDidBuild函数中更改状态变量、使用animateTo等功能，这可能会导致不稳定的UI表现。
3. aboutToDisappear：aboutToDisappear函数在自定义组件析构销毁之前执行。不允许在aboutToDisappear函数中改变状态变量，特别是@Link变量的修改可能会导致应用程序行为不稳定。

```ts
// Index.ets
import { router } from '@kit.ArkUI';

@Entry
@Component
struct MyComponent {
  @State showChild: boolean = true;
  @State btnColor:string = "#FF007DFF";

  // 只有被@Entry装饰的组件才可以调用页面的生命周期
  onPageShow() {
    console.info('Index onPageShow');
  }
  // 只有被@Entry装饰的组件才可以调用页面的生命周期
  onPageHide() {
    console.info('Index onPageHide');
  }

  // 只有被@Entry装饰的组件才可以调用页面的生命周期
  onBackPress() {
    console.info('Index onBackPress');
    this.btnColor ="#FFEE0606";
    return true // 返回true表示页面自己处理返回逻辑，不进行页面路由；返回false表示使用默认的路由返回逻辑，不设置返回值按照false处理
  }

  // 组件生命周期
  aboutToAppear() {
    console.info('MyComponent aboutToAppear');
  }

  // 组件生命周期
  onDidBuild() {
    console.info('MyComponent onDidBuild');
  }

  // 组件生命周期
  aboutToDisappear() {
    console.info('MyComponent aboutToDisappear');
  }

  build() {
    Column() {
      // this.showChild为true，创建Child子组件，执行Child aboutToAppear
      if (this.showChild) {
        Child()
      }
      // this.showChild为false，删除Child子组件，执行Child aboutToDisappear
      Button('delete Child')
        .margin(20)
        .backgroundColor(this.btnColor)
        .onClick(() => {
        this.showChild = false;
      })
      // push到page页面，执行onPageHide
      Button('push to next page')
        .onClick(() => {
          router.pushUrl({ url: 'pages/page' });
        })
    }
  }
}

@Component
struct Child {
  @State title: string = 'Hello World';
  // 组件生命周期
  aboutToDisappear() {
    console.info('[lifeCycle] Child aboutToDisappear');
  }

  // 组件生命周期
  onDidBuild() {
    console.info('[lifeCycle] Child onDidBuild');
  }

  // 组件生命周期
  aboutToAppear() {
    console.info('[lifeCycle] Child aboutToAppear');
  }

  build() {
    Text(this.title)
      .fontSize(50)
      .margin(20)
      .onClick(() => {
        this.title = 'Hello ArkUI';
      })
  }
}
```
* 使用无感监听页面路由的能力，能够实现在自定义组件中监听页面的生命周期。

#### 自定义组件的自定义布局
如果需要通过测算的方式布局自定义组件内子组件的位置，建议使用以下接口：
1. onMeasureSize：组件每次布局时触发，计算子组件的尺寸，其执行时间先于onPlaceChildren。
2. onPlaceChildren：组件每次布局时触发，设置子组件的起始位置。

#### 自定义组件成员属性访问限定符使用限制
ArkTS会对自定义组件的成员变量使用的访问限定符private/public/protected进行校验，当不按规范使用访问限定符private/public/protected时，会产生对应的日志信息。

1. 对于@State/@Prop/@Provide/@BuilderParam/常规成员变量(不涉及更新的普通变量)，当使用private修饰时，在自定义组件构造时，不允许进行赋值传参，否则会有编译告警日志提示。
2. 对于@StorageLink/@StorageProp/@LocalStorageLink/@LocalStorageProp/@Consume变量，当使用public修饰时，会有编译告警日志提示。
3. 对于@Link/@ObjectLink变量，当使用private修饰时，会有编译告警日志提示。
4. 由于struct没有继承能力，上述所有的这些变量使用protected修饰时，会有编译告警日志提示。
5. 当@Require和private同时修饰自定义组件struct的@State/@Prop/@Provide/@BuilderParam/常规成员变量(不涉及更新的普通变量)时，会有编译告警日志提示。

#### @Builder装饰器：自定义构建函数
@Builder装饰器用于定义自定义构建函数，自定义构建函数允许在自定义组件内、build方法和其他自定义构建函数中调用。

1. 私有自定义构建函数
* 定义
  ```ts
  @Builder MyBuilderFunction() {}
  ```
* 使用
  ```ts
  this.MyBuilderFunction()
  ```
  1. 允许在自定义组件内定义一个或多个@Builder方法，该方法被认为是该组件的私有、特殊类型的成员函数。
  2. 私有自定义构建函数允许在自定义组件内、build方法和其他自定义构建函数中调用。
  3. 在自定义函数体中，this指代当前所属组件，组件的状态变量可以在自定义构建函数内访问。建议通过this访问自定义组件的状态变量而不是参数传递。
   
2. 全局自定义构建函数
 * 定义
  ```ts
  @Builder function MyGlobalBuilderFunction() { ... }
  ```
 * 使用
  ```ts
  MyGlobalBuilderFunction()
  ```
##### 参数传递规则
1. 按引用传递参数
   * 按引用传递参数时，传递的参数可为状态变量，且状态变量的改变会引起@Builder方法内的UI刷新。
   * 按引用传递参数时，如果在@Builder方法内调用自定义组件，ArkUI提供$$作为按引用传递参数的范式。
2. 按引用传递
   * 调用@Builder装饰的函数默认按值传递。当传递的参数为状态变量时，状态变量的改变不会引起@Builder方法内的UI刷新。所以当使用状态变量的时候，推荐使用按引用传递。
   * 使用按值传递的方式，在@ComponentV2装饰器修饰的自定义组件里配合使用@ObservedV2和@Trace装饰器可以实现刷新UI功能。

##### 限制条件
1. @Builder装饰的函数内部，不允许修改参数值，否则框架会抛出运行时错误。开发者可以在调用@Builder的自定义组件里改变其参数。
   * @Builder通过按引用传递的方式传入参数，才会触发动态渲染UI，并且参数只能是一个。
   * @Builder如果传入的参数是两个或两个以上，不会触发动态渲染UI。
   * @Builder传入的参数中同时包含按值传递和按引用传递两种方式，不会触发动态渲染UI。
   * @Builder的参数必须按照对象字面量的形式，把所需要的属性一一传入，才会触发动态渲染UI。

##### 使用场景
1. 自定义组件内使用自定义构建函数
2. 使用全局自定义构建函数
3. 修改装饰器修饰的变量触发UI刷新
4. 使用全局和局部的@Builder传入customBuilder类型
5. 多层@Builder方法嵌套使用
6. @Builder函数联合V2装饰器使用
  

#### @LocalBuilder装饰器： 维持组件父子关系
为了解决组件的父子关系和状态管理的父子关系保持一致的问题，引入@LocalBuilder装饰器。@LocalBuilder拥有和局部@Builder相同的功能，且比局部@Builder能够更好的确定组件的父子关系和状态管理的父子关系。

1. 定义的语法
   ```ts
   @LocalBuilder MyBuilderFunction() { ... }
   ```
2. 使用方法
   ```ts
   this.MyBuilderFunction()
   ```

##### 限制条件
* @LocalBuilder只能在所属组件内声明，不允许全局声明。
* @LocalBuilder不能被内置装饰器和自定义装饰器使用。
* 自定义组件内的静态方法不能和@LocalBuilder一起使用。

##### @LocalBuilder和局部@Builder使用区别
@Builder方法引用传参时，为了改变this指向，使用bind(this)后，会导致组件的父子关系和状态管理的父子关系不一致，但是@LocalBuilder是否使用bind(this)，都不会改变组件的父子关系。

##### 参数传递规则
1. 按引用传递参数
   * 按引用传递参数时，传递的参数可为状态变量，且状态变量的改变会引起@LocalBuilder方法内的UI刷新。
   * 若子组件调用父组件的@LocalBuilder函数，传入的参数发生变化，不会引起@LocalBuilder方法内的UI刷新。
   * 按引用传递参数时，如果在@LocalBuilder方法内调用自定义组件，ArkUI提供$$作为按引用传递参数的范式。
   * 子组件引用父组件的@LocalBuilder函数，传入的参数为状态变量，状态变量的改变不会引发@LocalBuilder方法内的UI刷新，原因是@Localbuilder装饰的函数绑定在父组件上，状态变量刷新机制是刷新本组件以及其子组件，对父组件无影响，故无法引发刷新。若使用@Builder修饰则可引发刷新，原因是@Builder改变了函数的this指向，此时函数被绑定到子组件上，故能引发UI刷新。
2. 按值传递参数
   * 调用@LocalBuilder装饰的函数默认按值传递。当传递的参数为状态变量时，状态变量的改变不会引起@LocalBuilder方法内的UI刷新。所以当使用状态变量的时候，推荐使用按引用传递。
   * 

##### 使用场景
1. @LocalBuilder在@ComponentV2修饰的自定义组件中使用
   * 使用局部的@LocalBuilder在@ComponentV2修饰的自定义组件中调用，修改变量触发UI刷新

#### @BuilderParam装饰器：引用@Builder函数
@BuilderParam用来装饰指向@Builder方法的变量（@BuilderParam是用来承接@Builder函数的）。开发者可以在初始化自定义组件时，使用不同的方式（如：参数修改、尾随闭包、借用箭头函数等）对@BuilderParam装饰的自定义构建函数进行传参赋值，在自定义组件内部通过调用@BuilderParam为组件增加特定的功能。该装饰器用于声明任意UI描述的一个元素，类似slot占位符。

1. 初始化@BuilderParam装饰的方法
@BuilderParam装饰的方法只能被自定义构建函数（@Builder装饰的方法）初始化。
   * 使用所属自定义组件的自定义构建函数或者全局的自定义构建函数，在本地初始化@BuilderParam。
   ```ts
    @Builder function overBuilder() {}

    @Component
    struct Child {
      @Builder doNothingBuilder() {};

      // 使用自定义组件的自定义构建函数初始化@BuilderParam
      @BuilderParam customBuilderParam: () => void = this.doNothingBuilder;
      // 使用全局自定义构建函数初始化@BuilderParam
      @BuilderParam customOverBuilderParam: () => void = overBuilder;
      build(){}
    }
   ```
   * 用父组件自定义构建函数初始化子组件@BuilderParam装饰的方法。
   ```ts
    @Component
    struct Child {
      @Builder customBuilder() {};
      // 使用父组件@Builder装饰的方法初始化子组件@BuilderParam（默认使用本组件的@Builder装饰的方法）
      @BuilderParam customBuilderParam: () => void = this.customBuilder;

      build() {
        Column() {
          this.customBuilderParam()
        }
      }
    }

    @Entry
    @Component
    struct Parent {
      @Builder componentBuilder() {
        Text(`Parent builder `)
      }

      build() {
        Column() {
          Child({ customBuilderParam: this.componentBuilder })
        }
      }
    }
   ```
##### 限制条件
* @BuilderParam装饰的变量接收来自父组件使用@Builder装饰的函数，且@Builder函数是参数传递类型，仅支持局部@Builder函数作为参数传递。
* 在自定义组件尾随闭包的场景下，子组件有且仅有一个@BuilderParam用来接收此尾随闭包，且此@BuilderParam不能有参数。

##### 使用场景
1. 参数初始化组件:@BuilderParam装饰的方法可以是有参数和无参数的两种形式，需与指向的@Builder方法类型匹配。
2. 尾随闭包初始化组件:在自定义组件中使用@BuilderParam装饰的属性时也可通过尾随闭包进行初始化。在初始化自定义组件时，组件后紧跟一个大括号“{}”形成尾随闭包场景。
3. 使用全局和局部@Builder初始化@BuilderParam。
4. 在@ComponentV2修饰的自定义组件中使用@BuilderParam
  

#### wrapBuilder：封装全局@Builder
使用wrapBuilder来封装全局@Builder。

##### 接口说明
```ts
declare function wrapBuilder< Args extends Object[]>(builder: (...args: Args) => void): WrappedBuilder;

// 使用方法
let builderVar: WrappedBuilder<[string, number]> = wrapBuilder(MyBuilder)
let builderArr: WrappedBuilder<[string, number]>[] = [wrapBuilder(MyBuilder)] //可以放入数组
```

##### 限制条件
1. wrapBuilder方法只能传入全局@Builder方法。
2. wrapBuilder方法返回的WrappedBuilder对象的builder属性方法只能在struct内部使用。

##### 使用场景
1. @Builder方法赋值给变量
   * 把@Builder装饰器装饰的方法MyBuilder作为wrapBuilder的参数，再将wrapBuilder赋值给变量globalBuilder，用来解决@Builder方法赋值给变量后无法被使用的问题。
2. @Builder方法赋值给变量在UI语法中使用
   * 自定义组件Index使用ForEach来进行不同@Builder函数的渲染，可以使用builderArr声明的wrapBuilder数组进行不同@Builder函数效果体现。

##### 引用传递
通过按引用传递的方式传入参数，会触发UI的刷新。


## 状态管理

## 渲染控制
