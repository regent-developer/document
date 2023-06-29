# Alova

Alova是一个基于Promise的轻量级JavaScript库，用于在Web应用程序中进行HTTP请求。  

相比于Axios，Alova有以下优点：
* 体积更小：Alova只有3KB大小，相比之下Axios有19KB大小。因此，使用Alova可以减少页面加载时间和网络带宽。
* 简单易用：Alova具有简单的API，易于学习和使用。它的API设计类似于fetch API，因此对于熟悉fetch API的开发人员来说，Alova很容易上手。
* 可定制性更强：Alova支持自定义拦截器、请求和响应转换器，以及全局和局部配置。这使得开发人员可以根据应用程序的需求灵活地配置和使用Alova。

劣势：  
* 功能相对较少：相比于Axios，Alova的功能相对较少。它缺少Axios中的一些高级功能，如取消请求、并发请求、CSRF保护等。因此，在一些复杂的应用程序中，Alova可能无法完全替代Axios。
* 社区支持相对较少：相比于Axios，Alova的社区支持相对较少。因此，在使用Alova时，可能需要自行解决某些问题。

Alova是一个体积小、简单易用、可定制性强的JavaScript库。它适用于一些简单的Web应用程序，特别是那些需要快速加载和响应的应用程序。然而，对于一些复杂的应用程序，特别是那些需要高级功能的应用程序，Axios可能更加适合。因此，选择Alova还是Axios，需要根据具体应用程序的需求和开发人员的技术水平来进行选择。  


## 安装&使用

### 安装
```sh
npm i alova
```

### 使用

```js
// 引入alova
import alova from 'alova'

// 创建一个alova实例
const request = alova.create({
  // 可以配置一些全局选项，如baseURL、timeout等
  baseURL: 'https://example.com/api',
  timeout: 5000
})

// 使用alova发送请求
request.get('/users', {
  // 可以配置一些局部选项，如params、headers等
  params: {
    page: 1,
    limit: 10
  },
  // 可以配置一些策略选项，如cache、retry等
  cache: true,
  retry: 3
}).then(res => {
  // 处理响应数据
  console.log(res.data)
}).catch(err => {
  // 处理错误信息
  console.error(err.message)
})
```

#### 内置策略
* cache：请求级缓存。提供内存模式、持久化模式等多种服务端数据缓存模式，提升用户体验，同时降低服务端压力。
* aretry：重试。在请求失败时自动重试一定次数，增加请求成功率。
* cancel：取消。在请求发送前或响应返回后取消请求，避免无效或过时的请求。
* mergea：合并。在一定时间内合并相同或相似的请求，减少重复或冗余的请求。
* delay：延迟。在一定时间后发送请求，避免过早或过频繁的请求。

```js

// 启用缓存策略，并设置缓存模式为持久化模式
request.get('/users', {
  cache: true,
  cacheMode: 'persistent'
})

// 启用重试策略，并设置重试次数为5次
request.post('/login', {
  retry: true,
  retryTimes: 5
})

// 启用取消策略，并设置取消条件为响应返回后
request.put('/profile', {
  cancel: true,
  cancelWhen: 'after'
})

// 启用合并策略，并设置合并时间为1000毫秒
request.get('/posts', {
  merge: true,
  mergeTime: 1000
})

// 启用延迟策略，并设置延迟时间为2000毫秒
request.delete('/comments', {
  delay: true,
  delayTime: 2000
})

```


#### 自定义策略
```js

// 创建一个alova实例
const request = alova.create()

// 添加一个请求拦截器，在每个请求中添加一个token参数
request.interceptors.request.use(config => {
  config.params = config.params || {}
  config.params.token = 'some-token'
  return config
})

// 添加一个响应拦截器，在每个响应中检查状态码是否正常
request.interceptors.response.use(res => {
  if (res.status !== 200) {
    throw new Error('Request failed')
  }
  return res
})

```