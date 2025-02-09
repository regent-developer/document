# coord-convert

## 安装
```bash
pip install coord-convert
```

## 高德地图坐标转WGS84
```python
from coord_convert import transform
 
def amap_to_wgs84(amap_lng, amap_lat):
    """将高德地图坐标转为WGS84"""
    return transform.gcj2wgs(amap_lng, amap_lat)
 
# 示例：上海市中心（高德坐标）
amap_lng, amap_lat = 121.473701, 31.230416
wgs_lng, wgs_lat = amap_to_wgs84(amap_lng, amap_lat)
print(f"WGS84坐标：{wgs_lng:.6f}, {wgs_lat:.6f}")
```

## 百度地图坐标转WGS84
```python
from coord_convert import transform
 
def baidu_to_wgs84(bd_lng, bd_lat):
    """将百度坐标转为WGS84"""
    return transform.bd2wgs(bd_lng, bd_lat)
 
# 示例：北京天安门（百度坐标）
bd_lng, bd_lat = 116.403847, 39.915526
wgs_lng, wgs_lat = baidu_to_wgs84(bd_lng, bd_lat)
print(f"WGS84坐标：{wgs_lng:.6f}, {wgs_lat:.6f}")
```

## 混合转换（GCJ-02 → BD-09 → WGS84）
```python
from coord_convert import transform
 
def hybrid_conversion(gcj_lng, gcj_lat):
    """GCJ-02 → BD-09 → WGS84"""
    # 转换为百度坐标
    bd_lng, bd_lat = transform.gcj2bd(gcj_lng, gcj_lat)
    # 再转WGS84
    return transform.bd2wgs(bd_lng, bd_lat)
 
# 示例：深圳腾讯大厦（高德坐标）
gcj_lng, gcj_lat = 113.944531, 22.528922
wgs_lng, wgs_lat = hybrid_conversion(gcj_lng, gcj_lat)
print(f"最终WGS84坐标：{wgs_lng:.6f}, {wgs_lat:.6f}")
```
