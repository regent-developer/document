# 微信小程序

## 微信小程序封装的基于promise的request的请求方法

```js
// 同时发送异步代码的次数
let cnt = 0;

// 使用promise封装请求
export function request(params){
    cnt++  //发送一次请求就加一次

    // show loading
    wx.showLoading({
        title: 'Loading',
      })

    // url
    const baseUrl = ""

    // promise
    return new Promise((resolve,reject)=>{
        // request        
        wx.request({
            ...params,
            url:baseUrl + params.url,
            success:(res)=>{
                // success
                resolve(res)
            },
            fail:(err)=>{
                // failed
                reject(err)
            },
            complete:()=>{
                // completed
                cnt-- 
                if(cnt === 0){
                    // hide loading
                    wx.hideLoading()
                }
            }
        });
    });
}
```