# toast&modal

```js
/** 提示消息框 */
function toast({
  title = "数据加载中",
  icon = "none",
  duration = 2000,
  mask = true,
}) {
  wx.showToast({
    title,
    icon,
    duration,
    mask,
  })
}

/** 模拟对话框 */
function modal(opts = {}) {
  const defaultOpts = {
    title: "提示",
    content: "您确定执行该操作吗？",
    confirmColor: "#f3514f",
  }
  return new Promise((resolve) => {
    const options = Object.assign({}, defaultOpts, opts)
    wx.showModal({
      ...options,
      complete({ confirm, cancel }) {
        confirm && resolve(true)
        cancel && resolve(false)
      },
    })
  })
}

// 挂载到 wx 全局对象上
wx.toast = toast
wx.modal = modal

export { toast, modal }
```