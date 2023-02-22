# GIS数据格式坐标转换

地理信息系统 (GIS) 是一个创建、管理、分析和绘制所有类型数据的系统。GIS 将数据连接到地图，将位置数据（事物所在位置）与所有类型的描述性信息（事物在该位置的情况）集成到一起。

## 坐标系
坐标系是一种用于表示地理要素、影像和观察值位置的参照系统，为定义真实世界的位置提供了框架。

使用 [经度,纬度]来描述点的横纵坐标，经度(longitude)范围是[-180,180],纬度(latitude)范围是[-90,90]，那么大于90或者小于90的一定不会是纬度，另外我们中国所覆盖的范围大约是经度73.66 ~ 135.05,纬度3.86 ~ 53.55，因此在国内项目上可以通过坐标范围来判定经纬度顺序：经度缩写为lng，纬度缩写为lat。

### 地球坐标 (WGS84)
* 国际标准，从 GPS 设备中取出的数据的坐标系。
* 国际地图提供商使用的坐标系(谷歌地图国外、osm、mapbox)。

### 国测局坐标系(GCJ-02、火星坐标系)
* 中国标准，从国行移动设备中定位获取的坐标数据使用这个坐标系。
* 国家规定：国内出版的各种地图系统(包括电子形式)，必须至少采用GCJ-02对地理位置进行首次加密(高德地图、腾讯地图等) 。
* WGS84基础上的加密。

### 百度坐标(BD-09)
* 百度标准，百度 SDK，百度地图，百度GeoCoding 使用。
* GCJ-02基础上的二次加密。

### 国家大地2000坐标系(CGCS2000)
* 国家天地图使用的坐标系。
* 基本跟WGS84相近(厘米级)。

WGS84 通过国测局一次加密偏移后为 GCJ02(国测局2002)坐标系，BD09在此基础上进行了二次加密，而国家大地2000(CGCS2000)则是我们国家目前在推的标准规范平时精度要求不高，我们可以约等同于 WGS84。

## 坐标系之间转换

### 百度坐标系(BD-09) 与火星坐标系(GCJ-02)的转换
```python
"""
  *百度坐标系(BD-09) 与火星坐标系(GCJ-02)的转换
  *即百度转谷歌(国内)、高德、腾讯
  * @ parambd_lon
  * @ parambd_lat
  * @ returns{*[]}
"""
def bd09togcj02(bd_lon, bd_lat):
    x = bd_lon - 0.0065
    y = bd_lat - 0.006
    z = math.sqrt(x * x + y * y) - 0.00002 * math.sin(y * x_PI)
    theta = math.atan2(y, x) - 0.000003 * math.cos(x * x_PI)
    gg_lng = z * math.cos(theta)
    gg_lat = z * math.sin(theta)
    return [gg_lng, gg_lat]

# print(bd09togcj02(120.199672, 30.331184))
```

### 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
```python
"""
  * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
  * 即谷歌、高德 转 百度
  * @param lng
  * @param lat
  * @returns {*[]}
"""

def gcj02tobd09(lng, lat):
    z = math.sqrt(lng * lng + lat * lat) + 0.00002 * math.sin(lat * x_PI)
    theta = math.atan2(lat, lng) + 0.000003 * math.cos(lng * x_PI)
    bd_lng = z * math.cos(theta) + 0.0065
    bd_lat = z * math.sin(theta) + 0.006
    return [bd_lng, bd_lat]

# print(gcj02tobd09(120.19312059585862, 30.325466905933578))

```

### wgs84坐标转换

* 坐标偏移和经纬度单独转换
```python
"""
  * 判断是否在国内，不在国内则不做偏移
  * @param lng
  * @param lat
  * @returns {boolean}
"""


def out_of_china(lng, lat):
    # 纬度3.86~53.55,经度73.66~135.05
    if 73.66 < lng < 135.05 and 3.86 < lat < 53.55:
        return False


"""
  * 经纬度偏移转换
"""


def transform_lat(lng, lat):
    ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * math.sqrt(math.fabs(lng))
    ret += (20.0 * math.sin(6.0 * lng * PI) + 20.0 * math.sin(2.0 * lng * PI)) * 2.0 / 3.0
    ret += (20.0 * math.sin(lat * PI) + 40.0 * math.sin(lat / 3.0 * PI)) * 2.0 / 3.0
    ret += (160.0 * math.sin(lat / 12.0 * PI) + 320 * math.sin(lat * PI / 30.0)) * 2.0 / 3.0
    return ret


def transform_lng(lng, lat):
    ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * math.sqrt(math.fabs(lng))
    ret += (20.0 * math.sin(6.0 * lng * PI) + 20.0 * math.sin(2.0 * lng * PI)) * 2.0 / 3.0
    ret += (20.0 * math.sin(lng * PI) + 40.0 * math.sin(lng / 3.0 * PI)) * 2.0 / 3.0
    ret += (150.0 * math.sin(lng / 12.0 * PI) + 300.0 * math.sin(lng / 30.0 * PI)) * 2.0 / 3.0
    return ret



```

* GCJ02 转换为 WGS84
```python
"""
  * GCJ02 转换为 WGS84
  * @param lng
  * @param lat
  * @returns {*[]}
"""


def gcj02_to_wgs84(lng, lat):
    # 判断是否为国外坐标
    if out_of_china(lng, lat):
        return [lng, lat]
    else:
        dlat = transform_lat(lng - 105.0, lat - 35.0)
        dlng = transform_lng(lng - 105.0, lat - 35.0)
        radlat = lat / 180.0 * PI
        magic = math.sin(radlat)
        magic = 1 - ee * magic * magic
        sqrtmagic = math.sqrt(magic)
        dlat = (dlat * 180.0) / ((aa * (1 - ee)) / (magic * sqrtmagic) * PI)
        dlng = (dlng * 180.0) / (aa / sqrtmagic * math.cos(radlat) * PI)
        mglat = lat + dlat
        mglng = lng + dlng
        return [lng * 2 - mglng, lat * 2 - mglat]

```

* WGS84转GCj02
```python
"""
  * WGS84转GCj02
  * @param lng
  *  @param lat
  *  @returns {*[]}
"""


def wgs84_to_gcj02(lng, lat):
    if out_of_china(lng, lat):
        return [lng, lat]
    else:
        dlat = transform_lat(lng - 105.0, lat - 35.0)
        dlng = transform_lng(lng - 105.0, lat - 35.0)
        radlat = lat / 180.0 * PI
        magic = math.sin(radlat)
        magic = 1 - ee * magic * magic
        sqrtmagic = math.sqrt(magic)
        dlat = (dlat * 180.0) / ((aa * (1 - ee)) / (magic * sqrtmagic) * PI)
        dlng = (dlng * 180.0) / (aa / sqrtmagic * math.cos(radlat) * PI)
        mglat = lat + dlat
        mglng = lng + dlng
        return [mglng, mglat]

```

* WGS84 百度坐标系 (BD-09) 的转换
```python
"""
  *WGS84 百度坐标系 (BD-09) 的转换
  *@param lng
  *@param lat
  *@returns {*[]}
"""


def wgs84_to_bd09(lng, lat):
    point = wgs84_to_gcj02(lng, lat)
    bdpoint = gcj02_to_bd09(point[0], point[1])
    return [bdpoint[0], bdpoint[1], point[0], point[1]]

```

* 百度坐标系 (BD-09) WGS84 的转换
```python
"""
  * 百度坐标系 (BD-09) WGS84 的转换
  * @param lng
  * @param lat
  * @returns {*[]}
"""

def bd09_to_wgs84(lng, lat):
    point = bd09_to_gcj02(lng, lat)
    wgs84point = gcj02_to_wgs84(point[0], point[1])
    return [wgs84point[0], wgs84point[1]]


```

