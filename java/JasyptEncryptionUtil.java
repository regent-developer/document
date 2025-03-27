package com.example.demo.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

/**
 * 加密工具类，提供字符串的加密和解密功能
 */
public class JasyptEncryptionUtil {

    // 加密算法
    private static final String ALGORITHM = "PBEWithHMACSHA512AndAES_256";
    // 加密密码
    private static final String PASSWORD = "your-secret-password";

    // 加密器实例
    private static StandardPBEStringEncryptor encryptor;

    // 静态初始化块，配置加密器
    static {
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm(ALGORITHM);
        encryptor.setPassword(PASSWORD);
        encryptor.setIvGenerator(new RandomIvGenerator());
    }

    /**
     * 加密字符串
     * @param input 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String input) {
        return encryptor.encrypt(input);
    }

    /**
     * 解密字符串
     * @param encryptedInput 需要解密的字符串
     * @return 解密后的原始字符串
     */
    public static String decrypt(String encryptedInput) {
        return encryptor.decrypt(encryptedInput);
    }

    /**
     * 配置加密参数
     * @param algorithm 加密算法
     * @param password 加密密码
     */
    public static void configure(String algorithm, String password) {
        encryptor.setAlgorithm(algorithm);
        encryptor.setPassword(password);
        encryptor.setIvGenerator(new RandomIvGenerator());
    }
}
