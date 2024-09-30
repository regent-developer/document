# JwtUtil

## pom依赖

```xml
<!-- 创建、解析 和 验证JSON Web Tokens (JWT)-->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>

```

## 工具类

```java
package com.xxx.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "1ost"; // 替换为你的密钥
    private static final long EXPIRATION_TIME = 3600000; // 1小时

    /**
     * 生成 JWT
     *
     * @param subject 主题，可以是用户的唯一标识
     * @return 生成的 JWT
     */
    public static String generateToken(String subject) {
        Map<String, Object> claims = new HashMap<>();
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY);
        return builder.compact();
    }

    /**
     * 验证 JWT
     *
     * @param token 要验证的 JWT
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从 JWT 中提取主题
     *
     * @param token 要提取的 JWT
     * @return 主题
     */
    public static String getSubjectFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}

```