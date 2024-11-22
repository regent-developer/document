import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vip.xiaonuo.core.exception.ServiceException;
import vip.xiaonuo.core.exception.enums.ServerExceptionEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpServlet工具类(获取当前request和response)
 *
 */
public class HttpServletUtil {

    /**
     * 获取当前请求的request对象
     *
     */
    public static HttpServletRequest getRequest() {
        // 获取当前请求的RequestAttributes
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 如果RequestAttributes为空，则抛出异常
        if (requestAttributes == null) {
            throw new ServiceException(ServerExceptionEnum.REQUEST_EMPTY);
        } else {
            // 否则返回Request
            return requestAttributes.getRequest();
        }
    }

    /**
     * 获取当前请求的response对象
     *
     */
    public static HttpServletResponse getResponse() {
        // 获取当前请求的ServletRequestAttributes对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 如果当前请求的ServletRequestAttributes对象为空，则抛出异常
        if (requestAttributes == null) {
            throw new ServiceException(ServerExceptionEnum.REQUEST_EMPTY);
        } else {
            // 否则返回当前请求的响应对象
            return requestAttributes.getResponse();
        }
    }
}