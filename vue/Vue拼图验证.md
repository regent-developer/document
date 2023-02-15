# Vue拼图验证

## 安装
```js
npm install vue-puzzle-verification
```

## main.js里引入
```js
import PuzzleVerification from 'vue-puzzle-verification'
Vue.use(PuzzleVerification);
```

## 页面引入使用
```html
<template>
    <div>
        <div @click="show = true">显示</div>
        <PuzzleVerification
            v-model="show"
            :puzzleImgList="puzzleImgList"
            blockType="puzzle"
            :onSuccess="handleSuccess"
            :onError="handleError"
          />
    </div>
</template>
 
<script>
import PuzzleVerification from "vue-puzzle-verification";
export default {
    components: { PuzzleVerification },
    data() {
        return {
          show: false,
          puzzleImgList: [
              require("../../assets/images/img-timeline-02.jpg"),
              require("../../assets/images/img-timeline-03.jpg"),
              require("../../assets/images/img-timeline-04.jpg"),
              require("../../assets/images/img-timeline-05.jpg"),
              require("../../assets/images/img-timeline-06.jpg"),
              require("../../assets/images/img-timeline-07.jpg"),
              require("../../assets/images/img-timeline-08.jpg"),
          ],
        };
    },
    methods: {
        handleSuccess() {
          this.show=false
          console.log("验证成功");
        },
        handleError() {
          console.log("验证失败");
        },
    }
}
</script>

```

