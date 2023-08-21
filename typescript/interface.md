# interface

interface 是对象的模板，可以看作是一种类型约定，使用了某个模板的对象，就拥有了指定的类型结构。



* 对象属性

```typescript
interface A {
  x: number;
  y: number;
}

// 可选
interface A {
  x?: string;
}
// 只读
interface A {
  readonly x: string;
}
```



* 对象的属性索引

```typescript
interface A {
  [prop: string]: number;
}
```



属性索引共有`string`、`number`和`symbol`三种类型。

一个接口中，最多只能定义一个字符串索引。

* 对象方法

```typescript

interface A {
  f(x: boolean): string;
}


interface B {
  f: (x: boolean) => string;
}


interface C {
  f: { (x: boolean): string };
}
```



* 函数

interface 也可以用来声明独立的函数。

```typescript
interface Add {
  (x:number, y:number): number;
}

const myAdd:Add = (x,y) => x + y;
```



* 构造函数

interface 内部可以使用`new`关键字，表示构造函数。

TypeScript 里面，构造函数特指具有`constructor`属性的类。

```typescript
interface ErrorConstructor {
  new (message?: string): Error;
}
```



## interface 的继承

* ### interface 继承 interface

interface 可以使用`extends`关键字，继承其他 interface。

`extends`关键字会从继承的接口里面拷贝属性类型，这样就不必书写重复的属性。

interface 允许多重继承。（如果子接口与父接口存在同名属性，那么子接口的属性会覆盖父接口的属性。注意，子接口与父接口的同名属性必须是类型兼容的，不能有冲突，否则会报错。）

```typescript
interface Style {
  color: string;
}

interface Shape {
  name: string;
}

interface Circle extends Style, Shape {
  radius: number;
}
```



* ### interface 继承 type

interface 可以继承`type`命令定义的对象类型。

```typescript
type Country = {
  name: string;
  capital: string;
}

interface CountryWithPop extends Country {
  population: number;
}
```

如果`type`命令定义的类型不是对象，interface 就无法继承。

* ### interface 继承 class

interface 还可以继承 class，即继承该类的所有成员。

```typescript
class A {
  x:string = '';

  y():boolean {
    return true;
  }
}

interface B extends A {
  z: number
}
```

## 接口合并

多个同名接口会合并成一个接口。

```typescript
interface Box {
  height: number;
  width: number;
}

interface Box {
  length: number;
}
```

同名接口合并时，同一个属性如果有多个类型声明，彼此不能有类型冲突。

同名接口合并时，如果同名方法有不同的类型声明，那么会发生函数重载。而且，后面的定义比前面的定义具有更高的优先级。（同名方法之中，如果有一个参数是字面量类型，字面量类型有更高的优先级。）

## interface 与 type 的异同

### 同

很多对象类型即可以用 interface 表示，也可以用 type 表示。而且，两者往往可以换用，几乎所有的 interface 命令都可以改写为 type 命令。

* 对象类型起名

### 异

* `type`能够表示非对象类型，而`interface`只能表示对象类型（包括数组、函数等）。
* `interface`可以继承其他类型，`type`不支持继承。

继承的主要作用是添加属性，`type`定义的对象类型如果想要添加属性，只能使用`&`运算符，重新定义一个类型。

```typescript
type Animal = {
  name: string
}

type Bear = Animal & {
  honey: boolean
}
```

`interface`添加属性，采用的是继承的写法。

继承时，type 和 interface 是可以换用的。

```typescript
// interface 可以继承 type。
type Foo = { x: number; };

interface Bar extends Foo {
  y: number;
}

// type 也可以继承 interface。
interface Foo {
  x: number;
}

type Bar = Foo & { y: number; };
```



* 同名`interface`会自动合并，同名`type`则会报错。也就是说，TypeScript 不允许使用`type`多次定义同一个类型。
* `interface`不能包含属性映射（mapping），`type`可以。
* `this`关键字只能用于`interface`。
* type 可以扩展原始数据类型，interface 不行。

```typescript
type MyStr = string & {
  type: 'new'
};
```



* `interface`无法表达某些复杂类型（比如交叉类型和联合类型），但是`type`可以。

```typescript
type A = { /* ... */ };
type B = { /* ... */ };

type AorB = A | B;
type AorBwithName = AorB & {
  name: string
};
```

