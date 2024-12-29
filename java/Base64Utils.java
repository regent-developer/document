package com.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * 编解码工具类，提供BASE64和URL的编解码功能
 */
public class Base64Utils {

    // 默认的URL编码字符集
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    // BASE64编码器实例
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();

    // BASE64解码器实例
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    /**
     * 对文本进行BASE64编码
     *
     * @param text 需要编码的文本
     * @return 编码后的字符串
     * @throws UnsupportedEncodingException 如果字符编码不被支持
     */
    public static String encode(String text) throws UnsupportedEncodingException {
        // 将文本按指定字符集转换为字节数组，再进行BASE64编码
        return BASE64_ENCODER.encodeToString(text.getBytes(DEFAULT_URL_ENCODING));
    }

    /**
     * 对BASE64编码的文本进行解码
     *
     * @param encodedText 需要解码的BASE64编码文本
     * @return 解码后的字符串
     * @throws UnsupportedEncodingException 如果字符编码不被支持
     */
    public static String decode(String encodedText) throws UnsupportedEncodingException {
        // 对BASE64编码的文本进行解码，再按指定字符集转换为字符串
        return new String(BASE64_DECODER.decode(encodedText), DEFAULT_URL_ENCODING);
    }

    /**
     * 对URL的部分文本进行编码
     *
     * @param part 需要编码的URL部分文本
     * @return 编码后的字符串
     * @throws UnsupportedEncodingException 如果字符编码不被支持
     */
    public static String urlEncode(String part) throws UnsupportedEncodingException {
        // 使用URLEncoder对文本进行URL编码
        return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
    }

    /**
     * 对URL的部分文本进行解码
     *
     * @param part 需要解码的URL部分文本
     * @return 解码后的字符串
     * @throws UnsupportedEncodingException 如果字符编码不被支持
     */
    public static String urlDecode(String part) throws UnsupportedEncodingException {
        // 使用URLDecoder对URL编码的文本进行解码
        return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
    }

}
