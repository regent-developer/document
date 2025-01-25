# Android中获取当前程序的版本号

```java
try {
    int version = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
} catch (NameNotFoundException e) {
    e.printStackTrace();
}
```