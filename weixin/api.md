# 微信小程序API

## wx.getSetting
说明：获取用户的当前设置。返回值中只会出现小程序已经向用户请求过的权限。
返回值：请求过的权限可以通过res.authSetting拿到，用户是否授权通过res.authSetting[scope.userInfo]可以判断{scope.userInfo:true}授权为true  

```js
export function getSetting(){
    return new Promise((resolve,reject)=>{
        wx.getSetting({
            success:(result)=>{
                resolve(result)
            },
            fail:(err)=>{
                reject(err)
            }
        });
    });
}
```

## wx.chooseAddress
说明：调起用户编辑收货地址原生界面，并在编辑完成后返回用户选择的地址。
返回值：用户选择的地址

```js
export function chooseAddress(){
    return new Promise((resolve,reject)=>{
        wx.chooseAddress({
            success:(result)=>{
                resolve(result)
            },
            fail:(err)=>{
                reject(err)
            }
        });
    });
}
```

## wx.openSetting
说明：调起客户端小程序设置界面
返回值：用户设置的操作结果

```js
export function openSetting(){
    return new Promise((resolve,reject)=>{
        wx.openSetting({
            success:(result)=>{
                resolve(result)
            },
            fail:(err)=>{
                reject(err)
            }
        });
    });
}
```

## wx.showModal
说明：
返回值：

```js
export function showModal({content}) {
    return new Promise((resolve,reject)=>{
        wx.showModal({
            title:'提示',
            content:content,
            success:(res)=>{
                resolve(res);
            },
            fail:(err)=>{
                reject(err);
            }
        });
    });
}
```

## wx.showToast
说明：
返回值：

```js
export function showToast({title,icon="none"}) {
    return new Promise((resolve,reject)=>{
        wx.showToast({
            title:title,
            icon:icon,
            mask:true,
            duration: 1500,
            success:(res)=>{
                resolve(res);
            },
            fail:(err)=>{
                reject(err);
            }
        });
    });
}
```

## wx.login
说明：调用接口获取登录凭证（code）。通过凭证进而换取用户登录态信息，包括用户的唯一标识（openid）及本次登录的会话密钥（session_key）等。用户数据的加解密通讯需要依赖会话密钥完成。
返回值：成功或失败

```js
export function login() {
    return new Promise((resolve,reject)=>{
        wx.login({
            timeout:10000,
            success: (result) => {
                resolve(result)
            },
            fail: (err) => {
                reject(err)
            },
        });
    });
}
```

## wx.scanCode
调起客户端扫码界面进行扫码

```js
// 允许从相机和相册扫码
wx.scanCode({
  success (res) {
    console.log(res)
  }
})

// 只允许从相机扫码
wx.scanCode({
  onlyFromCamera: true,
  success (res) {
    console.log(res)
  }
})
```