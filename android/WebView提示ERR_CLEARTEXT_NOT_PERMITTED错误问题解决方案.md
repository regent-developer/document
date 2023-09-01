# WebView提示ERR_CLEARTEXT_NOT_PERMITTED错误问题解决方案



## AndroidManifest.xml中的配置

```xml
<uses-permission android:name="android.permission.INTERNET" />

<application
	······
	android:usesCleartextTraffic="true" >
	······
</application>
```



## 代码中设置javascript可用

```java
WebSettings settings = webView.getSettings();
settings.setJavaScriptEnabled(true);
```

