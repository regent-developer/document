package xxx.util;
 
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * Cookie工具类
 * @author 
 **/
public class CookieUtil
{
    /**
     * 写入 Cookie 对象
     * 过期时间（单位为秒）：-1：负数永不过期；0：则立马过期
     */
    public static void writeCookie(String name, String value, int maxAge, HttpServletResponse response)
    {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);      // 设置过期时间（单位为秒）
        cookie.setDomain("127.0.0.1"); // 设置域名，实现跨域功能
        cookie.setPath("/");
        response.addCookie(cookie);
    }
 
    /**
     * 读取 Cookie 的值
     */
    public static String readCookie(String name, HttpServletRequest request)
    {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0)
        {
            for (Cookie cookie : cookies)
            {
                if (name.equals(cookie.getName()))
                {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }
 
    /**
     * 删除 Cookie 对象
     */
    public static void deleteCookie(String name, HttpServletRequest request, HttpServletResponse response)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0)
        {
            for (Cookie cookie : cookies)
            {
                if (name.equals(cookie.getName()))
                {
                    cookie.setMaxAge(0); // 设置有效时间为0秒的时候：创建完cookie后，则立马过期（删除）;
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
    }
}