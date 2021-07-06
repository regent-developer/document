# 读取照片GPS


## 相关包

* metadata-extractor-2.8.1.jar
* xmpcore-5.1.2.jar

## 读取Exif原始信息

```java
public static HashMap<String, Object> readPicInfo(String file_path) {
	HashMap<String, Object> map = new HashMap<String,Object>();
	Tag tag = null;
    File jpegFile = new File(file_path);
    Metadata metadata;
    try {
        metadata = JpegMetadataReader.readMetadata(jpegFile);
        Iterator<Directory> it = metadata.getDirectories().iterator();
        while (it.hasNext()) {
            Directory exif = it.next();
            Iterator<Tag> tags = exif.getTags().iterator();
            while (tags.hasNext()) {
                tag = (Tag) tags.next();
                System.out.println(tag.getTagName()+"--"+tag.getDescription());
            }
        }
    } catch (JpegProcessingException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
	return map;
}

public static void main(String[] args) {
    //传入照片的绝对路径
	readPicInfo("text.jpg");
}
```

## GPS格式转换

```java
/***
 * 经纬度坐标格式转换
 * @param Gps
 */
public double latitude_and_longitude_convert_to_decimal_system(String Gps) {
	String a = Gps.split("°")[0].replace(" ", "");
	String b = Gps.split("°")[1].split("'")[0].replace(" ", "");
	String c = Gps.split("°")[1].split("'")[1].replace(" ", "").replace("\"", "");
	double gps_dou = Double.parseDouble(a)+Double.parseDouble(b)/60 + Double.parseDouble(c)/60/60;
	return gps_dou;
}
```

## 调用地图API将GPS坐标转换为地理位置