# interface

interface 是对象的模板，可以看作是一种类型约定，使用了某个模板的对象，就拥有了指定的类型结构。



## 对象属性

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



## 对象的属性索引

```typescript
interface A {
  [prop: string]: number;
}
```



属性索引共有`string`、`number`和`symbol`三种类型。

一个接口中，最多只能定义一个字符串索引。

## 对象方法

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



## 函数



## 构造函数





