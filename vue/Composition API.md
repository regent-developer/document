# Composition API

## setup

- setup 函数也是 Compsition API 的入口函数，我们的变量、方法都是在该函数里定义的。
- setup 中，this 指向的是 undefined。（在 Vue2 中，我们访问 data 或 props 中的变量，都是通过类似 this.xxx 这样的形式去获取的）
- setup 函数有两个参数，分别是 props 、context，前者存储着定义当前组件允许外界传递过来的参数名称以及对应的值；后者是一个上下文对象，能从中访问到 attr 、emit 、slots。
- setup 函数代替了 beforeCreate 和 created 两个生命周期函数，因此我们可以认为它的执行时间在 beforeCreate 和 created 之间。

```html
<template>
  <div id="app">
    <p>{{ number }}</p>
    <button @click="add">增加</button>
  </div>
</template>

<script>
  // 1. 从 vue 中引入 ref 函数
  import { ref } from 'vue'
  export default {
    name: 'App',
    setup() {
      // 2. 用 ref 函数包装一个响应式变量 number
      let number = ref(0)

      // 3. 设定一个方法
      function add() {
        // number是被ref函数包装过了的，其值保存在.value中
        number.value++
      }

      // 4. 将 number 和 add 返回出去，供template中使用
      return { number, add }
    },
  }
</script>
```

## 生命周期

```html
<template>
  <div id="app"></div>
</template>

<script>
  // 1. 从 vue 中引入 多个生命周期函数
  import {
    onBeforeMount,
    onMounted,
    onBeforeUpdate,
    onUpdated,
    onBeforeUnmount,
    unMounted,
  } from 'vue'
  export default {
    name: 'App',
    setup() {
      onBeforeMount(() => {
        // 在挂载前执行某些代码
      })

      onMounted(() => {
        // 在挂载后执行某些代码
      })

      onBeforeUpdate(() => {
        // 在更新前前执行某些代码
      })

      onUpdated(() => {
        // 在更新后执行某些代码
      })

      onBeforeUnmount(() => {
        // 在组件销毁前执行某些代码
      })

      unMounted(() => {
        // 在组件销毁后执行某些代码
      })

      return {}
    },
  }
</script>
```

## reactive

- reactive 方法是用来创建一个响应式的数据对象，该 API 也很好地解决了 Vue2 通过 defineProperty 实现数据响应式的缺陷。

```html
<template>
  <div id="app">
    <!-- 5. 访问响应式数据对象中的 count  -->
    <p>{{ state.count }}</p>
    <button @click="add">增加</button>
  </div>
</template>

<script>
  // 1. 从 vue 中导入 reactive
  import { reactive } from 'vue'
  export default {
    name: 'App',
    setup() {
      // 2. 创建响应式的数据对象
      const state = reactive({ count: 3 })

      // 3. 设定一个方法
      function add() {
        // number是被reactive函数包装过了的
        count++
      }

      // 4. 将响应式数据对象state和add return 出去，供template使用
      return { state, add }
    },
  }
</script>
```

## ref

- ref 是用来创建一个响应式的数据对象。
- ref 是通过 reactive 包装了一个对象 ，然后是将值传给该对象中的 value 属性。
- ref(obj)->reactive({value: obj})

```html
<script>
  import { ref, reactive } from 'vue'
  export default {
    name: 'App',
    setup() {
      const obj = { count: 3 }
      const state1 = ref(obj)
      const state2 = reactive(obj)

      console.log(state1)
      console.log(state2)
    },
  }
</script>
```

## reactive 和 ref 的区别

- ref 数据响应式监听。ref 函数传入一个值作为参数，一般传入基本数据类型，返回一个基于该值的响应式 Ref 对象，该对象中的值一旦被改变和访问，都会被跟踪到。
- reactive 是用来定义更加复杂的数据类型，但是定义后里面的变量取出来就不再是响应式 Ref 对象数据了。（需要用 toRefs 函数转化为响应式数据对象）
- 基本类型值（String 、Nmuber 、Boolean 等）或单值对象（类似像 {count: 3} 这样只有一个属性值的对象）使用 ref。
- 引用类型值（Object 、Array）使用 reactive。

## toRef

- toRef 是将某个对象中的某个值转化为响应式数据，其接收两个参数，第一个参数为 obj 对象；第二个参数为对象中的属性名。

```html
<template>
  <p>{{ state1 }}</p>
  <button @click="add1">增加</button>

  <p>{{ state2 }}</p>
  <button @click="add2">增加</button>
</template>

<script>
  import { ref, toRef } from 'vue'
  export default {
    setup() {
      const obj = { count: 3 }
      const state1 = ref(obj.count)
      const state2 = toRef(obj, 'count')

      function add1() {
        state1.value++
        console.log('原始值：', obj)
        console.log('响应式数据对象：', state1)
      }

      function add2() {
        state2.value++
        console.log('原始值：', obj)
        console.log('响应式数据对象：', state2)
      }

      return { state1, state2, add1, add2 }
    },
  }
</script>
```

## ref 和 toRef 的区别

- ref 是对传入数据的拷贝，toRef 是对传入数据的引用。
- ref 的值改变会更新视图，toRef 的值改变不会更新视图。

## toRefs

- 将传入的对象里所有的属性的值都转化为响应式数据对象，该函数支持一个参数，即 obj 对象。

```html
<script>
  // 1. 导入 toRefs
  import { toRefs } from 'vue'
  export default {
    setup() {
      const obj = {
        name: 'Vue3',
        age: 22,
        gender: 0,
      }
      // 2. 将 obj 对象中属性count的值转化为响应式数据
      const state = toRefs(obj)

      // 3. 打印查看一下
      console.log(state)
    },
  }
</script>
```

## shallowReactive

- 只为某个对象的私有（第一层）属性创建浅层的响应式代理，不会对“属性的属性”做深层次、递归地响应式代理，而只是保留原样。
- shallowReactive 是一个用于性能优化的 API（reactive 是深层的响应式数据对象）

```html
<script>
  import { reactive } from 'vue'
  export default {
    setup() {
      const obj = {
        a: 1,
        first: {
          b: 2,
          second: {
            c: 3,
          },
        },
      }

      const state = reactive(obj)

      console.log(state)
      console.log(state.first)
      console.log(state.first.second)
    },
  }
</script>
```

```html
<script>
  import { shallowReactive } from 'vue'
  export default {
    setup() {
      const obj = {
        a: 1,
        first: {
          b: 2,
          second: {
            c: 3,
          },
        },
      }

      const state = shallowReactive(obj)

      console.log(state)
      console.log(state.first)
      console.log(state.first.second)
    },
  }
</script>
```

```html
<template>
  <p>{{ state.a }}</p>
  <p>{{ state.first.b }}</p>
  <p>{{ state.first.second.c }}</p>
  <button @click="change1">改变1</button>
  <button @click="change2">改变2</button>
</template>
<script>
  import { shallowReactive } from 'vue'
  export default {
    setup() {
      const obj = {
        a: 1,
        first: {
          b: 2,
          second: {
            c: 3,
          },
        },
      }

      const state = shallowReactive(obj)

      function change1() {
        state.a = 7
      }

      function change2() {
        state.first.b = 8
        state.first.second.c = 9
        console.log(state)
      }

      return { state }
    },
  }
</script>
```

## shallowReactive 和 reactive 的区别

- shallowReactive 是浅层的响应式数据对象，reactive 是深层的响应式数据对象。
- Reactive 生成响应式数据对象时是每一层都使用 Proxy 包装，shallowReactive 生成响应式数据对象时是只会将第一层使用 Proxy 包装。
- shallowReactive 只会监听第一层的数据对象的值，若发生改变则更新视图，深层的则不会监听并更新视图，reactive 会监听全部层的数据对象的值，若发生改变则更新视图。

## shallowRef

- shallowReactive 是监听对象第一层的数据变化用于驱动视图更新，那么 shallowRef 则是监听 .value 的值的变化来更新视图的。

```html
<template>
  <p>{{ state.a }}</p>
  <p>{{ state.first.b }}</p>
  <p>{{ state.first.second.c }}</p>
  <button @click="change1">改变1</button>
  <button @click="change2">改变2</button>
</template>

<script>
  import { shallowRef } from 'vue'
  export default {
    setup() {
      const obj = {
        a: 1,
        first: {
          b: 2,
          second: {
            c: 3,
          },
        },
      }

      const state = shallowRef(obj)
      console.log(state)

      function change1() {
        // 直接将state.value重新赋值
        state.value = {
          a: 7,
          first: {
            b: 8,
            second: {
              c: 9,
            },
          },
        }
      }

      function change2() {
        state.value.first.b = 8
        state.value.first.second.c = 9
        console.log(state)
      }

      return { state, change1, change2 }
    },
  }
</script>
```

## triggerRef

- 只有当 value 发生改变时 shallowRef 声明的变量才会在视图上进行更新，而 triggerRef 的作用则是手动执行与 shallowRef 关联的任何数据对象，强制更新视图。

```html
<template>
  <p>{{ state.a }}</p>
  <p>{{ state.first.b }}</p>
  <p>{{ state.first.second.c }}</p>
  <button @click="change">改变</button>
</template>

<script>
  import { shallowRef, triggerRef } from 'vue'
  export default {
    setup() {
      const obj = {
        a: 1,
        first: {
          b: 2,
          second: {
            c: 3,
          },
        },
      }

      const state = shallowRef(obj)
      console.log(state)

      function change() {
        state.value.first.b = 8
        state.value.first.second.c = 9
        // 修改值后立即驱动视图更新
        triggerRef(state)
        console.log(state)
      }

      return { state, change }
    },
  }
</script>
```

## toRaw

- toRaw 方法是用于获取 ref 或 reactive 对象的原始数据的。
- 当我们想修改数据，但不想让视图更新时，可以选择直接修改原始数据上的值。可以使用 toRaw 来获取原始数据。
- 当 toRaw 方法接收的参数是 ref 对象时，需要加上 .value 才能获取到原始数据对象

```html
<template>
  <p>{{ state.name }}</p>
  <p>{{ state.age }}</p>
  <button @click="change">改变</button>
</template>

<script>
  import { reactive } from 'vue'
  export default {
    setup() {
      const obj = {
        name: 'Vue3',
        age: 22,
      }

      const state = reactive(obj)

      function change() {
        state.age = 90
        console.log(obj) // 打印原始数据obj
        console.log(state) // 打印 reactive对象
      }

      return { state, change }
    },
  }
</script>
```

```html
<script>
  import { reactive, toRaw } from 'vue'
  export default {
    setup() {
      const obj = {
        name: 'Vue3',
        age: 22,
      }

      const state = reactive(obj)
      const raw = toRaw(state)

      console.log(obj === raw) // true
    },
  }
</script>
```

## markRaw

- markRaw 方法可以将原始数据标记为非响应式的，即使用 ref 或 reactive 将其包装，仍无法实现数据响应式，其接收一个参数，即原始数据，并返回被标记后的数据。

```html
<template>
  <p>{{ state.name }}</p>
  <p>{{ state.age }}</p>
  <button @click="change">改变</button>
</template>

<script>
  import { reactive, markRaw } from 'vue'
  export default {
    setup() {
      const obj = {
        name: 'Vue3',
        age: 22,
      }
      // 通过markRaw标记原始数据obj, 使其数据更新不再被追踪
      const raw = markRaw(obj)
      // 试图用reactive包装raw, 使其变成响应式数据
      const state = reactive(raw)

      function change() {
        state.age = 90
        console.log(state)
      }

      return { state, change }
    },
  }
</script>
```

## provide && inject

- provide ：向子组件以及子孙组件传递数据。接收两个参数，第一个参数是 key，即数据的名称；第二个参数为 value，即数据的值。
- inject ：接收父组件或祖先组件传递过来的数据。接收一个参数 key，即父组件或祖先组件传递的数据名称。

```html
// A.vue
<script>
  import { provide } from 'vue'
  export default {
    setup() {
      const obj = {
        name: 'Vue3',
        age: 22,
      }

      // 向子组件以及子孙组件传递名为info的数据
      provide('info', obj)
    },
  }
</script>

// B.vue
<script>
  import { inject } from 'vue'
  export default {
    setup() {
      // 接收A.vue传递过来的数据
      inject('info') // {name: 'Vue3', age: 22}
    },
  }
</script>

// C.vue
<script>
  import { inject } from 'vue'
  export default {
    setup() {
      // 接收A.vue传递过来的数据
      inject('info') // {name: 'Vue3', age: 22}
    },
  }
</script>
```

## watch && watchEffect

- watch 和 watchEffect 都是用来监视某项数据变化从而执行指定的操作的。
- 区别
  - watchEffect 不需要手动传入依赖，watch 需手动传入依赖。
  - watchEffect 每次初始化时会执行一次回调函数来自动获取依赖， 当组件初始化时， watch 不会执行第二个参数中的回调函数，若我们想让其初始化时就先执行一遍，可以在第三个参数对象中设置 immediate: true。
    3,watchEffect 无法获取到原值，只能得到变化后的值，watch 可以获取原值和变化后的值。

```html
// 当watch监听 ref 类型时
<script>
  import { ref, watch } from 'vue'
  export default {
    setup() {
      const state = ref(0)

      watch(state, (newValue, oldValue) => {
        console.log(`原值为${oldValue}`)
        console.log(`新值为${newValue}`)
        /* 1秒后打印结果：
                            原值为0
                            新值为1
            */
      })

      // 1秒后将state值+1
      setTimeout(() => {
        state.value++
      }, 1000)
    },
  }
</script>
```

```html
// 当watch监听 reactive 类型时
<script>
  import { reactive, watch } from 'vue'
  export default {
    setup() {
      const state = reactive({ count: 0 })

      watch(
        () => state.count,
        (newValue, oldValue) => {
          console.log(`原值为${oldValue}`)
          console.log(`新值为${newValue}`)
          /* 1秒后打印结果：
                            原值为0
                            新值为1
            */
        }
      )

      // 1秒后将state.count的值+1
      setTimeout(() => {
        state.count++
      }, 1000)
    },
  }
</script>
```

```html
// 当watch当同时监听多个值时
<script>
  import { reactive, watch } from 'vue'
  export default {
    setup() {
      const state = reactive({ count: 0, name: 'zs' })

      watch(
        [() => state.count, () => state.name],
        ([newCount, newName], [oldvCount, oldvName]) => {
          console.log(oldvCount) // 旧的 count 值
          console.log(newCount) // 新的 count 值
          console.log(oldName) // 旧的 name 值
          console.log(newvName) // 新的 name 值
        }
      )

      setTimeout(() => {
        state.count++
        state.name = 'ls'
      }, 1000)
    },
  }
</script>
```

```html
<script>
  import { reactive, watchEffect } from 'vue'
  export default {
    setup() {
      const state = reactive({ count: 0, name: 'zs' })

      watchEffect(() => {
        console.log(state.count)
        console.log(state.name)
        /*  初始化时打印：
                            0
                            zs

                1秒后打印：
                            1
                            ls
            */
      })

      setTimeout(() => {
        state.count++
        state.name = 'ls'
      }, 1000)
    },
  }
</script>
```

## getCurrentInstance

- Vue2 的任何一个组件中想要获取当前组件的实例可以通过 this 来得到，而在 Vue3 中我们大量的代码都在 setup 函数中运行，并且在该函数中 this 指向的是 undefined。
- 开发环境下 getCurrentInstance 才能获取到当前组件的实例。在项目打包后，会报错的。

```html
<template>
  <p>{{ num }}</p>
</template>
<script>
  import { ref, getCurrentInstance } from 'vue'
  export default {
    setup() {
      const num = ref(3)
      const instance = getCurrentInstance()
      console.log(instance)

      return { num }
    },
  }
</script>
```

## useStore

- 使用 useStore 来与获取到 Vuex 实例。
- 在 Vue2 中使用 Vuex，我们都是通过 this.$store 来与获取到 Vuex 实例。

```html
// store 文件夹下的 index.js import Vuex from 'vuex' const store =
Vuex.createStore({ state: { name: '前端印象', age: 22 }, mutations: { …… }, ……
}) // example.vue
<script>
  // 从 vuex 中导入 useStore 方法
  import { useStore } from 'vuex'
  export default {
    setup() {
      // 获取 vuex 实例
      const store = useStore()

      console.log(store)
    },
  }
</script>
```

## 获取标签元素

- 在 Vue2 中，我们获取元素都是通过给元素一个 ref 属性，然后通过 this.$refs.xx 来访问的。
- Vue3 中，给目标元素设置 ref 属性，在 setup 中调用 ref 函数给某变量赋值，把创建的引用返回回去。
- 设置的元素引用变量只有在组件挂载后才能访问到，因此在挂载前对元素进行操作都是无效的。

```html
<template>
  <div>
    <div ref="el">div元素</div>
  </div>
</template>

<script>
  import { ref, onMounted } from 'vue'
  export default {
    setup() {
      // 创建一个DOM引用，名称必须与元素的ref属性名相同
      const el = ref(null)

      // 在挂载后才能通过 el 获取到目标元素
      onMounted(() => {
        el.value.innerHTML = '内容被修改'
      })

      // 把创建的引用 return 出去
      return { el }
    },
  }
</script>
```
