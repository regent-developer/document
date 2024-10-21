# storage

```js
/** 同步存储数据 */
export const setStorage = (key, data) => {
  try {
    wx.setStorageSync(key, data)
  } catch (e) {
    console.error(`存储指定 ${key} 数据时发生了异常`, e)
  }
}

/** 同步获取数据 */
export const getStorage = (key) => {
  try {
    const value = wx.getStorageSync(key)
    if (value) {
      return value
    }
  } catch (e) {
    console.error(`获取指定 ${key} 数据时发生了异常`, e)
  }
}

/** 同步删除指定数据 */
export const removeStorage = (key) => {
  try {
    wx.removeStorageSync(key)
  } catch (e) {
    console.error(`移除指定 ${key} 数据时发生了异常`, e)
  }
}

/** 同步清空全部数据 */
export const clearStorage = () => {
  try {
    wx.clearStorageSync()
  } catch (e) {
    console.error(`清空数据发生了异常`, e)
  }
}

/** 异步存储数据 */
export const asyncSetStorage = (key, data) => {
  return new Promise((resolve) => {
    wx.setStorage({
      key,
      data,
      complete(res) {
        resolve(res)
      },
    })
  })
}

/** 异步获取指定数据 */
export const asyncGetStorage = (key) => {
  return new Promise((resolve) => {
    wx.getStorage({
      key,
      complete(res) {
        resolve(res)
      },
    })
  })
}

/** 异步删除指定数据 */
export const asyncRemoveStorage = (key) => {
  return new Promise((resolve) => {
    wx.removeStorage({
      key,
      complete(res) {
        resolve(res)
      },
    })
  })
}

/** 异步清空全部数据 */
export const asyncClearStorage = () => {
  return new Promise((resolve) => {
    wx.clearStorage({
      key,
      complete(res) {
        resolve(res)
      },
    })
  })
}
```
