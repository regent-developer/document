# for-in和for-of的区别

## for-in
* 一般用于遍历对象的可枚举属性。以及对象从构造函数原型中继承的属性。对于每个不同的属性，语句都会被执行。

* 不建议使用for in 遍历数组，因为输出的顺序是不固定的。

* 如果迭代的对象的变量值是null或者undefined, for-in不执行循环体，建议在使用for-in循环之前，先检查该对象的值是不是null或者undefined



## for-of
* for-of语句在可迭代对象（包括 Array，Map，Set，String，TypedArray，arguments 对象等等）上创建一个迭代循环，调用自定义迭代钩子，并为每个不同属性的值执行语句

## 如何让object使用for-of遍历
对象是不可迭代的。
如果我们希望一个对象可以迭代，必须为对象添加一个名为Symbol.iterator的方法（一个专门使对象可迭代的内建Symbol）

### 作用
* 当使用for-of循环迭代对象时，就会调用Symbol.iterator方法，这个方法必须返回一个迭代器（一个有next()方法的对象）。
* 得到迭代器后，for-of会不断的调用迭代器的next()方法获得下一个元素。
* next()方法返回的内容必须符合格式：{done:Boolean,value:any}，当done:true时，循环结束，否则value就是下一个值。

#### 迭代器
迭代器是借鉴C++等语言的概念，迭代器的原理就像指针一样，它指向数据集合中的某个元素，你可以获取它指向的元素，也可以移动它以获取其它元素。迭代器类似于数组中下标的拓展，各种数据结构，如链表（List）、集合（Set）、映射（Map）都有与之对应的迭代器。

JS中的迭代器是专门为了遍历这一操作设计的。每次获取到的迭代器总是初始指向第一个元素，并且迭代器只有next()一种行为，直到获取到数据集的最后一个元素。我们无法灵活移动迭代器的位置，所以，迭代器的任务，是按某种次序遍历数据集中的元素。

```js
// 实现一个可迭代对象(形式1：构造内建函数)
let obj = {
    val1: 1,
    val2: 2
}

obj[Symbol.iterator] = function(){
    //返回一个迭代器
    return {
        current:this.val1,
        last:this.val2,
        next(){
            if(this.current < this.last){
                return {
                    done : false, 
                    value : this.current++
                    }
            }else{
                return {
                    done : true
                    }//迭代结束
            }
        }
    }
}

for(let item of obj){
    // 可迭代
    console.log(item)
}
```

```js
// 实现一个可迭代对象(形式2：本身作为迭代器)
let obj = {
    val1: 1,
    val2: 2
    [Symbol.iterator](){
        this.current = this.val1;
        return this;//返回对象本身
    },
    next(){//给对象添加一个next方法
        if(this.current < this.val2){
            return {
                done:false,
                value:this.current++
                }
        }else{
            return {
                done:true
                }
        }
    }
}
for(let item of obj){
    // 可迭代
    console.log(item)
}
```