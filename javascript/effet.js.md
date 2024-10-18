# effet.js

Effet.js 是一个轻量级的人脸样式框架，专注于为网页带来生动的面部动画和动态效果。通过简单易用的 API， 开发者不仅可以实现眨眼、张嘴、摇头等基础表情，还可以应用于多种场景，如人脸打卡、人脸登录、睡眠监测等。同时，Effet.js 对外提供 API，用户可根据需求灵活定制，实现更多的动作或功能，使得前端应用更加互动、智能且个性化。

## 官网

https://faceeffet.com

## vue实例

### 引入库

更改镜像
```shell
npm config set registry https://registry.npmmirror.com

```

安装插件
```shell
npm i face-effet -S

```

### main.js中注册全局
```js
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
// 引入核心样式
import 'face-effet/effet/effet.css'
// 引入核心主文件
import faceEffet from 'face-effet/effet/effet.js'
// 注册为全局对象
Vue.prototype.$faceEffet = faceEffet


Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

```

### 使用
```js
 <template>
  <div>
    <div ref="faceVueTest" id="faceId" ></div>
    <el-button @click="startFace">开启人脸</el-button>
    <el-button @click="restartFace">重启人脸</el-button>
    <el-button @click="closeFace">关闭人脸</el-button>
  </div>
  </template>
   
  <script>
  // $faceEffet 对象是在main.js 注册好的全局对象
  export default {
    name: "index",
    data(){
      return {
      }
    },
    beforeDestroy(){
      if (this.$faceEffet){
        this.$faceEffet.close();
      }
    },
    methods:{
      startFace(){
          // 人脸容器的初始化
        this.$faceEffet.init({
          el:this.$refs.faceVueTest, // 直接传入html元素 也可以直接传入字符 'faceId'
          type:'checkLogin', // 人脸登录模式
          callBack:this.callBack // 阶段回调函数，会打印每个执行步骤，一般是在这个方法调用后端接口
        })
      },
      callBack(data){
          // 验证过程的回调打印
        console.log(data)
      },
      restartFace(){
          // 重启人脸容器
        this.$faceEffet.restart()
      },
      closeFace(){
          // 关闭人脸容器
        this.$faceEffet.close();
      }
    }
  }
  </script> 
```


#### 人脸打卡模式

```js
this.$faceEffet.init({
   el:this.$refs.faceVueTest, // 直接传入html元素 也可以直接传入字符 'faceId'
   type:'clockIn', // 人脸打卡
   callBack:this.callBack // 阶段回调函数，会打印每个执行步骤，一般是在这个方法调用后端接口
 })

```

#### 人脸添加模式

```js
this.$faceEffet.init({
  el:this.$refs.faceVueTest, // 直接传入html元素 也可以直接传入字符 'faceId'
   type:'addFace', // 人脸打卡
   callBack:this.callBack // 阶段回调函数，会打印每个执行步骤，一般是在这个方法调用后端接口
 })

```

#### 睡眠检测模式

```js
this.$faceEffet.init({
   el:this.$refs.faceVueTest, // 直接传入html元素 也可以直接传入字符 'faceId'
   type:'checkSleep', // 人脸打卡
   callBack:this.callBack // 阶段回调函数，会打印每个执行步骤，一般是在这个方法调用后端接口
 })

```

## h5实例

```js
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Face-effet.js 人脸登录示例</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://unpkg.com/face-effet/effet/effet.css">
    <script src="https://unpkg.com/face-effet/effet/effet.js"></script>
    <style>
        #myface{
            margin-top: 350px;
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            effet.init({
                el: 'myface',
                callBack: (data) => {
                    console.log(data);
                }
            });
        });
    </script>
</head>
<body>
<!-- 用于渲染人脸识别的容器 -->
<div id="myface"></div>
<button onclick="effet.restart()">重新检测</button>
</body>
</html>


```