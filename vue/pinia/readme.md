# pinia

Pinia 是 Vue 的存储库，它允许跨组件/页面共享状态(pinia比作为Vue3的Vuex)  

## 安装

cmd  
```shell
npm install pinia
```

main.ts  
```ts
import { createPinia } from 'pinia'

app.use(createPinia())
```

## 使用

* pinia定义文件
```ts
import { defineStore } from 'pinia'

export const userStore = defineStore('userStore', {
	state: () => ({
	  // 内容
	}),
	actions: {
	  // 内容
	}
})
```

* State

```ts
import { defineStore } from 'pinia'

// useStore 可以是State
// 第一个参数是应用程序中store的唯一id
// state是store的核心部分
export const useStore = defineStore('userStore', {
  // other options...
})
```

demo  
```ts
import { defineStore } from 'pinia'

// 唯一 ID: testStore
export const testStore = defineStore('testStore', {
    state: () => ({
        // 用户信息
		user: {
			id: '1',
			username: '张三',
			password: '123456'
		}
    })
})
```

```html
<template>
    <div>
        Store: 账号{{user.username}},密码：{{user.password}}
    </div>
</template>

<script setup lang="ts">
  // 获取 testStore
  import { testStore } from '@/store/test';
  import { ref,reactive } from 'vue';
  const user = reactive({
	id: '',
	username: '',
	password: ''
  })
  // 获取创建的 testStore()
  const store = testStore() as any
  // 赋值
  user.id = store.id as string
  user.username = store.user.username as string
  user.password = store.user.password as string

  // 更改store中的值
  store.$patch({
    user: {
		id: '2',
		username: '李四',
		password: '456789'
	}
  })
</script>

```

* Actions
Actions相当于组件中的 methods，使用defineStore()中的actions属性定义 

```ts
import { defineStore } from 'pinia'

export const testStore = defineStore('testStore', {
    state: () => ({
        // 用户信息
		user: {
			username: '',
			password: ''
		}
    }),
	actions: {
	    // 设置 user
	    setUser(val: any) {
			this.user = val
		}
	}
})

```

```html
<template>
    <div>
        <el-input v-model="loginForm.username"></el-input>
        <el-input v-model="loginForm.password"></el-input>
        <el-button  @click="onLogin()">提交</el-button>
        <div>
            <h3>store存储：账号：{{user.username}} ，密码： {{user.password}}</h3>
        </div>
    </div>
</template>

<script setup lang="ts">
  import { testStore } from '@/store/test';
  import { ref, reactive } from 'vue';

  // from
  const loginForm = reactive({
	username: '',
	password: ''
  })

  // user
  const user = reactive({
	username: '',
	password: ''
  })

  const store = testStore() as any

  // 点击事件
  const onLogin = () => {
    // store 设置 user
    store.setUser(loginForm)

    // 取出设置的user 并且展示
    user.username = store.user.username as string
    user.password = store.user.password as string
  }
</script>
```
