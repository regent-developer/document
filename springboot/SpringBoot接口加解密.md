# SpringBoot接口加解密

在 controller 层进行拦截操作，使用 ControllerAdvice 对于 RequestBodyAdviceAdapter 和 ResponseBodyAdvice 进行拦截和处理。

1，依赖的类
```java
package com.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataTypeEnum {

    NORMAL(1, "普通数据"),

    ENCRYPT(2, "加密数据"),
    ;
    private final Integer value;

    private final String desc;
}
```

```java
package com.demo.domain;

import lombok.Data;

/**
 * 加密数据的表单
 */
@Data
public class ApiEncryptForm {

    private String encryptData;
}
```

```java
package com.demo.domain;

import lombok.Data;
import com.demo.common.DataTypeEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 请求返回对象
 */
@Data
@Schema
public class ResponseDto<T> {

    public static final int OK_CODE = 0;

    public static final String OK_MSG = "操作成功";

    private Integer code;

    private String level;

    private String msg;

    private Boolean ok;

    private T data;

    private Integer dataType;

    public ResponseDto(Integer code, String level, boolean ok, String msg, T data) {
        this.code = code;
        this.level = level;
        this.ok = ok;
        this.msg = msg;
        this.data = data;
        this.dataType = DataTypeEnum.NORMAL.getValue();
    }

    public ResponseDto(Integer code, String level, boolean ok, String msg) {
        this.code = code;
        this.level = level;
        this.ok = ok;
        this.msg = msg;
        this.dataType = DataTypeEnum.NORMAL.getValue();
    }

    public static <T> ResponseDto<T> ok() {
        return new ResponseDto<>(OK_CODE, null, true, OK_MSG, null);
    }

    publicstatic <T> ResponseDto<T> ok(T data) {
        return new ResponseDto<>(OK_CODE, null, true, OK_MSG, data);
    }

    publicstatic <T> ResponseDto<T> okMsg(String msg) {
        return new ResponseDto<>(OK_CODE, null, true, msg, null);
    }
}
```

2，创建加解密 Advice
```java
package com.demo.advice;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.demo.annotation.ApiDecrypt;
import com.demo.domain.ApiEncryptDto;
import com.demo.service.ApiEncryptService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Resource;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 解密
 */
@Slf4j
@ControllerAdvice
public class DecryptRequestAdvice extends RequestBodyAdviceAdapter {

    private static final String ENCODING = "UTF-8";

    @Resource
    private ApiEncryptService apiEncryptService;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(ApiDecrypt.class) || methodParameter.hasParameterAnnotation(ApiDecrypt.class) || methodParameter.getContainingClass().isAnnotationPresent(ApiDecrypt.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType)  {
        try {
            String bodyStr = IOUtils.toString(inputMessage.getBody(), ENCODING);
            ApiEncryptDto apiEncryptDto = JSONObject.parseObject(bodyStr, ApiEncryptDto.class);
            if (Objects.isNull(apiEncryptDto.getEncryptData())) {
                return inputMessage;
            }
            String decrypt = apiEncryptService.decrypt(apiEncryptDto.getEncryptData());
            return new DecryptHttpInputMessage(inputMessage.getHeaders(), IOUtils.toInputStream(decrypt, ENCODING));
        } catch (Exception e) {
            log.error("", e);
            return inputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    static class DecryptHttpInputMessage implements HttpInputMessage {
        private final HttpHeaders headers;

        private final InputStream body;

        public DecryptHttpInputMessage(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

}
```

```java
package com.demo.advice;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import com.demo.ResponseDto;
import com.demo.common.DataTypeEnum;
import com.demo.annotation.ApiEncrypt;
import com.demo.service.ApiEncryptService;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * 加密
 */
@Slf4j
@ControllerAdvice
public class EncryptResponseAdvice implements ResponseBodyAdvice<ResponseDto> {

    @Resource
    private ApiEncryptService apiEncryptService;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(ApiEncrypt.class) || returnType.getContainingClass().isAnnotationPresent(ApiEncrypt.class);
    }

    @Override
    public ResponseDto beforeBodyWrite(ResponseDto body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body.getData() == null) {
            return body;
        }

        String encrypt = null;
        try {
            encrypt = apiEncryptService.encrypt(objectMapper.writeValueAsString(body.getData()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        body.setData(encrypt);
        body.setDataType(DataTypeEnum.ENCRYPT.getValue());
        return body;
    }
}
```

3，加解密注解
```java
package com.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解密注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ApiDecrypt {
}
```

```java
package com.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加密注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ApiEncrypt {
}
```

4，加解密 Service以及aes或sm的实现类
```java
package com.demo.service;

/**
 * 加解密 Service
 */
public interface ApiEncryptService {

    /**
     * 解密
     */
    String decrypt(String data);

    /**
     * 加密
     */
    String encrypt(String data);

}
```

AES 加解密：

• AES 加密算法支持三种密钥长度：128位、192 位和 256 位，这里选择 128 位
• AES 要求秘钥为 128bit，转化字节为 16 个字节
• js前端使用 UCS-2 或者 UTF-16 编码，字母、数字、特殊符号等占用 1 个字节

```java
package com.demo.service;

import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SM4;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Base64;

/**
 * AES 加密和解密
 */
@Slf4j
public class ApiEncryptServiceAesImpl implements ApiEncryptService {

    private static final String CHARSET = "UTF-8";

    private static final String AES_KEY = "t123456__t123456";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public String encrypt(String data) {
        try {
            //  AES 加密 并转为 base64
            AES aes = new AES(hexToBytes(stringToHex(AES_KEY)));
            return aes.encryptBase64(data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    @Override
    public String decrypt(String data) {
        try {
            // 第一步： Base64 解码
            byte[] base64Decode = Base64.getDecoder().decode(data);
            // 第二步： AES 解密
            AES aes = new AES(hexToBytes(stringToHex(AES_KEY)));
            byte[] decryptedBytes = aes.decrypt(base64Decode);
            return new String(decryptedBytes, CHARSET);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * 16 进制串转字节数组
     *
     * @param hex 16进制字符串
     * @return byte数组
     */
    public static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] result;
        if (length % 2 == 1) {
            length++;
            result = new byte[(length / 2)];
            hex = "0" + hex;
        } else {
            result = new byte[(length / 2)];
        }
        int j = 0;
        for (int i=0; i < length; i += 2) {
            result[j] = hexToByte(hex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    public static String stringToHex(String input) {
        char[] chars = input.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char c : chars) {
            hex.append(Integer.toHexString((int) c));
        }
        return hex.toString();
    }

    /**
     * 16 进制字符转字节
     *
     * @param hex 16进制字符 0x00到0xFF
     * @return byte
     */
    private static byte hexToByte(String hex) {
        return (byte) Integer.parseInt(hex, 16);
    }
}
```
SM4 加解密:

• 国密 SM4 要求秘钥为 128bit，转化字节为 16 个字节
• js 前端使用 UCS-2 或者 UTF-16 编码，字母、数字、特殊符号等占用 1 个字节
• java 中每个字母数字也是占用 1 个字节

```java
package com.demo.service;

import cn.hutool.crypto.symmetric.SM4;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.Base64;

/**
 * SM4 加解密
 */
@Slf4j
@Service
public class ApiEncryptServiceSmImpl implements ApiEncryptService {

    private static final String CHARSET = "UTF-8";

    private static final String SM4_KEY = "t123456__t123456";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public String encrypt(String data) {
        try {
            // 第一步： SM4 加密
            SM4 sm4 = new SM4(hexToBytes(stringToHex(SM4_KEY)));
            String encryptHex = sm4.encryptHex(data);
            // 第二步： Base64 编码
            return new String(Base64.getEncoder().encode(encryptHex.getBytes(CHARSET)), CHARSET);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    @Override
    public String decrypt(String data) {
        try {
            // 第一步： Base64 解码
            byte[] base64Decode = Base64.getDecoder().decode(data);
            // 第二步： SM4 解密
            SM4 sm4 = new SM4(hexToBytes(stringToHex(SM4_KEY)));
            return sm4.decryptStr(new String(base64Decode));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    // 将字符串转换为十六进制字符串
    public static String stringToHex(String input) {
        // 将字符串转换为字符数组
        char[] chars = input.toCharArray();
        // 创建一个StringBuilder对象，用于存储十六进制字符串
        StringBuilder hex = new StringBuilder();
        // 遍历字符数组
        for (char c : chars) {
            // 将字符转换为十六进制字符串，并添加到StringBuilder对象中
            hex.append(Integer.toHexString((int) c));
        }
        // 返回十六进制字符串
        return hex.toString();
    }

    /**
     * 16 进制串转字节数组
     *
     * @param hex 16进制字符串
     * @return byte数组
     */
    public static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] result;
        if (length % 2 == 1) {
            length++;
            result = new byte[(length / 2)];
            hex = "0" + hex;
        } else {
            result = new byte[(length / 2)];
        }
        intj=0;
        for (int i=0; i < length; i += 2) {
            result[j] = hexToByte(hex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * 16 进制字符转字节
     *
     * @param hex 16进制字符 0x00到0xFF
     * @return byte
     */
    private static byte hexToByte(String hex) {
        return (byte) Integer.parseInt(hex, 16);
    }
}
```

5，前端的加解密封装

encrypt.js
```js
import CryptoJS from 'crypto-js';
import CryptoSM from 'sm-crypto';

function object2string(data) {
 if (typeof data === 'object') {
    return JSON.stringify(data);
  }

let str = JSON.stringify(data);
if (str.startsWith("'") || str.startsWith('"')) {
    str = str.substring(1);
  }
if (str.endsWith("'") || str.endsWith('"')) {
    str = str.substring(0, str.length - 1);
  }
return str;
}

/**
 * 字符串转为数字
 */
function stringToHex(str) {
let hex = '';
for(let i = 0; i < str.length; i++) {
    hex += str.charCodeAt(i).toString(16).padStart(2, '0');
  }
return hex;
}

/*
 * AES 加密、解密
 */

// 秘钥 Key
const AES_KEY = 't123456__t123456';
const AES = {
encryptData: function (data) {
    // AES 加密 并转为 base64
    let utf8Data = CryptoJS.enc.Utf8.parse(object2string(data));
    const key = CryptoJS.enc.Utf8.parse(AES_KEY);
    const encrypted = CryptoJS.AES.encrypt(utf8Data, key, {
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7,
    });
    return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);
  },

decryptData: function (data) {
    //  第一步：Base64 解码
    let words = CryptoJS.enc.Base64.parse(data);

    // 第二步：AES 解密
    const key = CryptoJS.enc.Utf8.parse(AES_KEY);
    return CryptoJS.AES.decrypt({ ciphertext: words }, key, {
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7,
    }).toString(CryptoJS.enc.Utf8);
  },
};

/*
 * 国密SM4算法 加密、解密
 */

// 秘钥 Key
const SM4_KEY = 't123456__t123456';
const SM4 = {
encryptData: function (data) {
    // 第一步：SM4 加密
    let encryptData = CryptoSM.sm4.encrypt(object2string(data), stringToHex(SM4_KEY));
    // 第二步： Base64 编码
    return CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(encryptData));
  },

decryptData: function (data) {
    // 第一步：Base64 解码
    let words = CryptoJS.enc.Base64.parse(data);
    let decode64Str = CryptoJS.enc.Utf8.stringify(words);

    // 第二步：SM4 解密
    return CryptoSM.sm4.decrypt(decode64Str, stringToHex(SM4_KEY));
  },
};


/*
 * 对外暴露： 加密、解密
 */

// 默认使用 SM4 算法
const EncryptObject = SM4;
// const EncryptObject = AES;

/**
 * 加密
 */
exportconst encryptData = function (data) {
 return !data ? null : EncryptObject.encryptData(data);
};

/**
 * 解密
 */
export const decryptData = function (data) {
 return !data ? null : EncryptObject.decryptData(data);
};
```

axios.js
```js
import axios from 'axios';
import { decryptData, encryptData } from './encrypt';

// token的消息头
const TOKEN_HEADER = 'token';
// 加密数据类型
const DATA_TYPE_ENCRYPT = 2;

// 创建axios对象
const tAxios = axios.create({
// baseURL: import.meta.env.VITE_API_URL,
baseURL: window.VITE_API_URL, // 后端地址
});

// 请求拦截器
tAxios.interceptors.request.use(
(config) => {
    // 在发送请求之前消息头加入 token，根据业务自己修改
    const token = localStorage.getItem(TOKEN_HEADER) || '';
    if (token) {
      config.headers[TOKEN_HEADER] = token;
    } else {
      delete config.headers[TOKEN_HEADER];
    }
    return config;
  },
(error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器 
tAxios.interceptors.response.use(
(response) => {
    // 根据content-type ，判断是否为 json 数据
    let contentType = response.headers['content-type'] ? response.headers['content-type'] : response.headers['Content-Type'];
    if (contentType.indexOf('application/json') === -1) {
      return Promise.resolve(response);
    }

    // 如果是json数据
    if (response.data && response.data instanceof Blob) {
      return Promise.reject(response.data);
    }

    // 如果是加密数据
    if (response.data.dataType === DATA_TYPE_ENCRYPT) {
      response.data.encryptData = response.data.data;
      let decryptStr = decryptData(response.data.data);
      if (decryptStr) {
        response.data.data = JSON.parse(decryptStr);
      }
    }

    return Promise.resolve(response.data);
  },
(error) => {
    // 对响应错误处理
    return Promise.reject(error);
  }
);

// 对外提供请求方法

/**
 * get请求
 */
export const getRequest = (url, params) => {
return request({ url, method: 'get', params });
};

/**
 * post请求
 */
export const postRequest = (url, data) => {
return request({
    data,
    url,
    method: 'post',
  });
};

// 加密

/**
 * post 请求加密参数
 */
export const postEncryptRequest = (url, data) => {
return request({
    data: { encryptData: encryptData(data) },
    url,
    method: 'post',
  });
};
```

6，使用

TestController.java
```java
package com.demo.controller;

import com.demo.domain.ResponseDto;
import com.demo.annotation.ApiDecrypt;
import com.demo.annotation.ApiEncrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据加解密测试
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 测试请求加密
     */
    @ApiDecrypt
    @PostMapping("/testRequestEncrypt")
    public ResponseDto<Test> testRequestEncrypt(@RequestBody Test test) {
        return ResponseDto.ok(test);
    }

    /**
     * 测试返回加密
     */
    @ApiEncrypt
    @PostMapping("/testResponseEncrypt")
    public ResponseDto<Test> testResponseEncrypt(@RequestBody Test test) {
        return ResponseDto.ok(test);
    }

    /**
     * 测试请求参数加密和解密、返回数据加密和解密
     */
    @ApiDecrypt
    @ApiEncrypt
    @PostMapping("/testDecryptAndEncrypt")
    public ResponseDto<JweForm> testDecryptAndEncrypt(@RequestBody Test test) {
        return ResponseDto.ok(test);
    }

    @Data
    public static class Test {

        // 姓名
        private String name;

        // 年龄
        private Integer age;
    }
}
```

Encrypt.vue
```js
<script setup>
import { reactive, ref } from 'vue';
import { encryptApi } from './encryptApi';
import { encryptData } from './encrypt';

//  请求参数加密 
const requestEncryptForm = reactive({
    age: 25, 
    name: '张三',
  });

// 参数字符串
const requestEncryptFormStr = ref('');
// 参数字符串 加密
const requestEncryptFormEncryptStr = ref('');
// 返回结果
const requestEncryptResponse = ref('');

async function testRequestEncrypt() {
    // 参数加密
    requestEncryptFormStr.value = JSON.stringify(requestEncryptForm);
    requestEncryptFormEncryptStr.value = encryptData(requestEncryptForm);

    // 发送请求
    const result = await encryptApi.testRequestEncrypt(requestEncryptForm);
    requestEncryptResponse.value = JSON.stringify(result.data);
  }

// 返回结果解密 
const responseEncryptForm = reactive({
    age: 25,
    name: '张三',
});

const responseEncryptFormStr = ref('');
const responseEncryptStr = ref('');
const responseStr = ref('');

async function testResponseEncrypt() {
    responseEncryptFormStr.value = JSON.stringify(responseEncryptForm);
    const result = await encryptApi.testResponseEncrypt(responseEncryptForm);
    responseEncryptStr.value = result.encryptData;
    responseStr.value = JSON.stringify(result.data);
  }

//  请求加密、返回解密 
const form = reactive({
    age: 25,
    name: '张三',
});

const formStr = ref('');
const formEncryptStr = ref('');
const responseEncrypt = ref('');
const responseDecryptStr = ref('');

async function testBoth() {
    formStr.value = JSON.stringify(form);
    formEncryptStr.value = encryptData(form);
    const result = await encryptApi.testDecryptAndEncrypt(form);
    responseEncrypt.value = result.encryptData;
    responseDecryptStr.value = JSON.stringify(result.data);
  }
</script>
<template>
  <el-card title="请求加密">
    <el-form>
      <el-row>
        <el-form-item label="姓名">
          <el-input v-model:value="requestEncryptForm.name" placeholder="姓名" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model:value="requestEncryptForm.age" placeholder="年龄" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="testRequestEncrypt">请求加密</el-button>
        </el-form-item>
      </el-row>
      <el-row>
        <div v-if="requestEncryptFormStr">请求参数：{{ requestEncryptFormStr }}</div>
      </el-row>
      <el-row>
        <div v-if="requestEncryptFormEncryptStr">请求参数加密：{{ requestEncryptFormEncryptStr }}</div>
      </el-row>
      <el-row>
        <div v-if="requestEncryptResponse">返回结果（不加密）：{{ requestEncryptResponse }}</div>
      </el-row>
    </el-form>
</el-card>
<br />
<el-card title="返回加密">
    <el-form>
      <el-row>
        <el-form-item label="姓名">
          <el-input v-model:value="responseEncryptForm.name" placeholder="姓名" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model:value="responseEncryptForm.age" placeholder="年龄" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="testResponseEncrypt">返回加密</el-button>
        </el-form-item>
      </el-row>
      <el-row>
        <div v-if="responseEncryptFormStr">请求参数： {{ responseEncryptFormStr }}</div>
      </el-row>
      <el-row>
        <div v-if="responseEncryptStr">返回结果：{{ responseEncryptStr }}</div>
      </el-row>
      <el-row>
        <div v-if="responseStr">返回结果 解密：{{ responseStr }}</div>
      </el-row>
    </el-form>
</el-card>
<br />
<el-card title="请求和返回都加密">
    <el-form>
      <el-row>
        <el-form-item label="姓名">
          <el-input v-model:value="form.name" placeholder="姓名" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model:value="form.age" placeholder="年龄" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="testBoth">请求和返回都加密</el-button>
        </el-form-item>
      </el-row>
      <el-row>
        <div v-if="formStr">请求参数： {{ formStr }}</div>
      </el-row>
      <el-row>
        <div v-if="formEncryptStr">请求参数加密： {{ formEncryptStr }}</div>
      </el-row>
      <el-row>
        <div v-if="responseEncrypt">返回结果：{{ responseEncrypt }}</div>
      </el-row>
      <el-row>
        <div v-if="responseDecryptStr">返回结果 解密：{{ responseDecryptStr }}</div>
      </el-row>
    </el-form>
</el-card>
</template>
```
encryptApi.js
```js
import { postRequest, postEncryptRequest } from './axios';

exportconst encryptApi = {

/**
   * 请求加密 
   */
testRequestEncrypt: (param) => {
    return postEncryptRequest('/test/testRequestEncrypt', param);
  },

/**
   * 返回加密 
   */
testResponseEncrypt: (param) => {
    return postRequest('/test/testResponseEncrypt', param);
  },

/**
   * 请求参数加密和解密、返回数据加密和解密
   */
testDecryptAndEncrypt: (param) => {
    return postEncryptRequest('/test/testDecryptAndEncrypt', param);
  },
}
```