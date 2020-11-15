# GeoHash

Genhash 是一种地理编码，由 Gustavo Niemeyer 发明的。它是一种分级的数据结构，把空间划分为网格。Genhash 属于空间填充曲线中的 Z 阶曲线（Z-order curve）的实际应用。

Geohash 能够提供任意精度的分段级别。一般分级从 1-12 级。

字符串长度		cell 宽度		cell 高度
1	≤	5,000km	×	5,000km
2	≤	1,250km	×	625km
3	≤	156km	×	156km
4	≤	39.1km	×	19.5km
5	≤	4.89km	×	4.89km
6	≤	1.22km	×	0.61km
7	≤	153m	×	153m
8	≤	38.2m	×	19.1m
9	≤	4.77m	×	4.77m
10	≤	1.19m	×	0.596m
11	≤	149mm	×	149mm
12	≤	37.2mm	×	18.6mm

Geohash 有一个和 Z 阶曲线相关的性质，那就是一个点附近的地方(但不绝对) hash 字符串总是有公共前缀，并且公共前缀的长度越长，这两个点距离越近。

Geohash 就常常被用来作为唯一标识符。用在数据库里面可用 Geohash 来表示一个点。Geohash 这个公共前缀的特性就可以用来快速的进行邻近点的搜索。越接近的点通常和目标点的 Geohash 字符串公共前缀越长.

Geohash 也有几种编码形式，常见的有2种，base 32 和 base 36。

组合经度和纬度的规则：偶数位放经度，奇数位放纬度




```go
package geohash

import (
    "bytes"
)

const (
    BASE32                = "0123456789bcdefghjkmnpqrstuvwxyz"
    MAX_LATITUDE  float64 = 90
    MIN_LATITUDE  float64 = -90
    MAX_LONGITUDE float64 = 180
    MIN_LONGITUDE float64 = -180
)

var (
    bits   = []int{16, 8, 4, 2, 1}
    base32 = []byte(BASE32)
)

type Box struct {
    MinLat, MaxLat float64 // 纬度
    MinLng, MaxLng float64 // 经度
}

func (this *Box) Width() float64 {
    return this.MaxLng - this.MinLng
}

func (this *Box) Height() float64 {
    return this.MaxLat - this.MinLat
}

// 输入值：纬度，经度，精度(geohash的长度)
// 返回geohash, 以及该点所在的区域
func Encode(latitude, longitude float64, precision int) (string, *Box) {
    var geohash bytes.Buffer
    var minLat, maxLat float64 = MIN_LATITUDE, MAX_LATITUDE
    var minLng, maxLng float64 = MIN_LONGITUDE, MAX_LONGITUDE
    var mid float64 = 0

    bit, ch, length, isEven := 0, 0, 0, true
    for length < precision {
        if isEven {
            if mid = (minLng + maxLng) / 2; mid < longitude {
                ch |= bits[bit]
                minLng = mid
            } else {
                maxLng = mid
            }
        } else {
            if mid = (minLat + maxLat) / 2; mid < latitude {
                ch |= bits[bit]
                minLat = mid
            } else {
                maxLat = mid
            }
        }

        isEven = !isEven
        if bit < 4 {
            bit++
        } else {
            geohash.WriteByte(base32[ch])
            length, bit, ch = length+1, 0, 0
        }
    }

    b := &Box{
        MinLat: minLat,
        MaxLat: maxLat,
        MinLng: minLng,
        MaxLng: maxLng,
    }

    return geohash.String(), b
}
```