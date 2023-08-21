# class

## 属性的类型

类的属性可以在顶层声明，也可以在构造方法内部声明。

对于顶层声明的属性，可以在声明时同时给出类型。（如果不给出类型，TypeScript 会认为类型都是`any`。）

如果声明时给出初值，可以不写类型，TypeScript 会自行推断属性的类型。

## readonly 修饰符

属性名前面加上 readonly 修饰符，就表示该属性是只读的。实例对象不能修改这个属性。

readonly 属性的初始值，可以写在顶层属性，也可以写在构造方法里面。

```typescript
// 在顶层属性中设置初始值
class A {
  readonly id:string;

  constructor() {
    this.id = 'bar'; // 正确
  }
}

// 在构造方法中设置初始值
class A {
  readonly id:string = 'foo';

  constructor() {
    this.id = 'bar'; // 正确
  }
}

// 如果两个地方都设置了只读属性的值，以构造方法为准。在其他方法修改只读属性都会报错。
```

## 方法的类型

类的方法就是普通函数，类型声明方式与函数一致。



## 存取器方法

存取器（accessor）是特殊的类方法，包括取值器（getter）和存值器（setter）两种方法。

它们用于读写某个属性，取值器用来读取属性，存值器用来写入属性。

```typescript
class A {
  _name = '';
  get name() {
    return this._name;
  }
  set name(value) {
    this._name = value;
  }
}
```

TypeScript 对存取器有以下规则:

* 如果某个属性只有`get`方法，没有`set`方法，那么该属性自动成为只读属性。
* `set`方法的参数类型，必须兼容`get`方法的返回值类型，否则报错。
* `get`方法与`set`方法的可访问性必须一致，要么都为公开方法，要么都为私有方法。

## 属性索引

类允许定义属性索引。

由于类的方法是一种特殊属性（属性值为函数的属性），所以属性索引的类型定义也涵盖了方法。如果一个对象同时定义了属性索引和方法，那么前者必须包含后者的类型。

```typescript
class A {
  [s:string]: boolean | (() => boolean);
  f() {
    return true;
  }
}
```

## 类的 interface 接口

### implements 关键字

interface 接口或 type 别名，可以用对象的形式，为 class 指定一组检查条件。然后，类使用 implements 关键字，表示当前类满足这些外部类型条件的限制。

```typescript
// interface接口
interface Country {
  name:string;
  capital:string;
}
// or type别名
type Country = {
  name:string;
  capital:string;
}

class MyCountry implements Country {
  name = '';
  capital = '';
}
```

`implements`关键字后面，不仅可以是接口，也可以是另一个类。这时，后面的类将被当作接口。（需要实现class中的属性和方法，否则就会报错）

### 实现多个接口

类可以实现多个接口（其实是接受多重限制），每个接口之间使用逗号分隔。

### 类与接口的合并

TypeScript 不允许两个同名的类，但是如果一个类和一个接口同名，那么接口会被合并进类。

```typescript
class A {
  x:number = 1;
}

interface A {
  y:number;
}

let a = new A();
a.y = 10;

a.x // 1
a.y // 10
```

## Class 类型

### 实例类型

TypeScript 的类本身就是一种类型，但是它代表该类的实例类型，而不是 class 的自身类型。

对于引用实例对象的变量来说，既可以声明类型为 Class，也可以声明类型为 Interface，因为两者都代表实例对象的类型。

```typescript
interface MotorVehicle {
}

class Car implements MotorVehicle {
}

// 写法一
const c1:Car = new Car();
// 写法二
const c2:MotorVehicle = new Car();

// 如果类Car有接口MotoVehicle没有的属性和方法，那么只有变量c1可以调用这些属性和方法。
```

### 类的自身类型

要获得一个类的自身类型，一个简便的方法就是使用 typeof 运算符。

类的自身类型就是一个构造函数，可以单独定义一个接口来表示。

```typescript
interface PointConstructor {
  new(x:number, y:number):Point;
}

function createPoint(
  PointClass: PointConstructor,
  x: number,
  y: number
):Point {
  return new PointClass(x, y);
}
```



### 结构类型原则

Class 也遵循“结构类型原则”。一个对象只要满足 Class 的实例结构，就跟该 Class 属于同一个类型。

如果两个类的实例结构相同，那么这两个类就是兼容的，可以用在对方的使用场合。

### 类的继承

类（这里又称“子类”）可以使用 extends 关键字继承另一个类（这里又称“基类”）的所有属性和方法。

子类也可以用于类型为基类的场合。

子类可以覆盖基类的同名方法。

子类的同名方法不能与基类的类型定义相冲突。

如果基类包括保护成员（`protected`修饰符），子类可以将该成员的可访问性设置为公开（`public`修饰符），也可以保持保护成员不变，但是不能改用私有成员（`private`修饰符）

