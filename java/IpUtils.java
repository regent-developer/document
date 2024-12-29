package com.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * IP工具类
 */
@Slf4j
public class IpUtils {

    private static final String DEFAULT_IP = "127.0.0.1";
    private static final String UN_KNOWN = "unknown";
    private static final int IP_MAX_LENGTH = 15;
    private static final String SPLIT = ",";

    /**
     * 获取本地IP地址
     *
     * @return 本地IP地址字符串
     */
    private static String getLocalIp() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String localIp = localHost.getHostAddress();
            log.info("IpUtils.getLocalIp:{}", localIp);
            return localIp;
        } catch (Exception e) {
            log.error("IpUtils.getLocalIp.error:{}", e.getMessage(), e);
            return DEFAULT_IP;
        }
    }

    /**
     * 从HttpServletRequest中获取客户端IP地址
     *
     * @param request HttpServletRequest对象
     * @return 客户端IP地址字符串
     */
    public static String getIp(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("IpUtils.getIp.error:{}", e.getMessage(), e);
        }
        if (!StringUtils.isEmpty(ip) && ip.length() > IP_MAX_LENGTH) {
            if (ip.indexOf(SPLIT) > 0) {
                ip = ip.substring(0, ip.indexOf(SPLIT));
            }
        }
        return ip;
    }
}
