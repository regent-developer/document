# sm2国密算法加解密

## 1. sm2简介

SM2算法是一种基于椭圆曲线的公钥密码算法，是中国的国家密码标准。SM2算法与RSA算法类似，都是非对称加密算法，但SM2算法的安全性更高，计算速度更快。

SM2算法主要包括以下几个部分：

- SM2公钥密码算法：包括SM2加密、SM2解密、SM2签名和SM2验签。
- SM2密钥交换算法：用于在两个通信方之间安全地交换密钥。
- SM2密钥生成算法：用于生成SM2算法所需的公钥和私钥。

## 2. pom依赖
```xml
<!-- hutool -->
<dependency>
  <groupId>cn.hutool</groupId>
  <artifactId>hutool-all</artifactId>
  <version>5.8.5</version>
</dependency>
<!-- 加解密依赖包 -->
<dependency>
  <groupId>org.bouncycastle</groupId>
  <artifactId>bcprov-jdk15to18</artifactId>
  <version>1.71</version>
</dependency>

```

### 3 工具类
```java
public class HutoolSmUtils {

    //generateKey方法生成的私钥和公钥
    public static final String PRIVATE_KEY = "00fecbb553865d805f13571d224ebc3f2e0454d297f4c7d871f9e35b1ad3faa62c";
    public static final String PUBLIC_KEY = "04f61b9932dd9de8ecdc837e83280bdd5711d6887692eec59325799f748a20c7036015fb8f08add1fa85b1edbce3bfd33131e5e1320a582eb06a05da64f497381c";

    /**
     * 生成公钥、私钥
     */
    public static void generateKey() {
        SM2 sm2 = SmUtil.sm2();
        // 私钥
        byte[] privateKey = BCUtil.encodeECPrivateKey(sm2.getPrivateKey());
        // 公钥
        byte[] publicKey = ((BCECPublicKey) sm2.getPublicKey()).getQ().getEncoded(false);
        System.out.println("公钥：" + HexUtil.encodeHexStr(publicKey));
        System.out.println("私钥：" + HexUtil.encodeHexStr(privateKey));

    }


    /**
     * sm2明文加密
     * PRIVATE_KEY:生成的私钥
     * PUBLIC_KEY：生成的公钥
     * @param data 加密前的明文
     * @return 加密后的密文
     */
    public static String encrypt(String data) {
        SM2 sm2 = SmUtil.sm2(ECKeyUtil.toSm2PrivateParams(PRIVATE_KEY), ECKeyUtil.toSm2PublicParams(PUBLIC_KEY));
        String encryptBcd = sm2.encryptBcd(data, KeyType.PublicKey);
        if (StrUtil.isNotBlank(encryptBcd)) {
            // 生成的加密密文会带04，因为前端sm-crypto默认的是1-C1C3C2模式，这里需去除04才能正常解密
            if (encryptBcd.startsWith("04")) {
                encryptBcd = encryptBcd.substring(2);
            }
            // 前端解密时只能解纯小写形式的16进制数据，这里需要将所有大写字母转化为小写
            encryptBcd = encryptBcd.toLowerCase();
        }
        return encryptBcd;
    }

    /**
     * sm2密文解密
     * PRIVATE_KEY:生成的私钥
     * PUBLIC_KEY：生成的公钥
     * @param encryptData 加密密文
     * @return 解密后的明文字符串
     */
    public static String decrypt(String encryptData) throws Exception {
        if (StrUtil.isBlank(encryptData)) {
            throw new RuntimeException("解密串为空，解密失败");
        }
        SM2 sm2 = SmUtil.sm2(ECKeyUtil.toSm2PrivateParams(PRIVATE_KEY), ECKeyUtil.toSm2PublicParams(PUBLIC_KEY));
        // BC库解密时密文开头必须带04，如果没带04则需补齐
        if (!encryptData.startsWith("04")) {
            encryptData = "04".concat(encryptData);
        }
        byte[] decryptFromBcd = sm2.decryptFromBcd(encryptData, KeyType.PrivateKey);
        if (decryptFromBcd != null && decryptFromBcd.length > 0) {
            return StrUtil.utf8Str(decryptFromBcd);
        } else {
            throw new Exception("密文解密失败");
        }
    }


    public static void main(String[] args) throws Exception {
        String data = "测试数据";
        String encryCode = encrypt(data);
        System.out.println(encryCode);
        System.out.println(decrypt(encryCode));

    }

}

```