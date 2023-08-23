# android.content.ActivityNotFoundException Unable to find explicit activity问题解决方案



## 原因

在AndroidManifest.xml中没有进行配置activity，只有配置后才有效，否则将无法执行启动。



## 自动配置

鼠标单击activity使光标在上面闪烁，然后使用快捷键 Alt+Enter选择Add activity to manifest即可实现将当前activity自动配置到清单文件AndroidManifest.xml中