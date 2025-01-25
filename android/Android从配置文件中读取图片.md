# Android从配置文件中读取图片

```xml
<?xml version="1.0" encoding="UTF-8"?>
 <resources>
    <array name="img_grid">
        <item>@drawable/light_grid</item>
        <item>@drawable/light_grid</item>
        <item>@drawable/light_grid</item>
        <item>@drawable/watch_grid</item>  
        <item>@drawable/socket_grid</item>
        <item>@drawable/air_grid</item>
        <item>@drawable/curtain_grid</item>
        <item>@drawable/load_speaker_grid</item>
        <item>@drawable/gas_grid</item>
        <item>@drawable/fog_grid</item>
        <item>@drawable/fire_grid</item>
        <item>@drawable/default_grid</item>
        <item>@drawable/default_grid</item>
</array>
</resources> 
```

```java
TypedArray  typeImg = context.getResources().obtainTypedArray(R.array.img_grid);
int[] img = new int[typeImg.length()];
for (int index = 0; index < typeImg.length(); index++) {
    img[index] = typeImg.getResourceId(index, R.drawable.default_grid);
}
```