import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import java.lang.reflect.Field;

/**
 * 获取代理原始对象的工具
 *
 */
public class AopTargetUtil {

    private static final Log log = Log.get();

    /**
     * 获取被代理对象的原始对象
     *
     */
    public static Object getTarget(Object proxy) {

        // 判断是不是代理对象，如果不是直接返回
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }

        try {
            if (AopUtils.isJdkDynamicProxy(proxy)) {
                return getJdkDynamicProxyTargetObject(proxy);
            } else {
                return getCglibProxyTargetObject(proxy);
            }
        } catch (Exception e) {
            return null;
        }
    }

    // 获取Cglib代理目标对象
    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        // 获取代理对象的CGLIB$CALLBACK_0字段
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        // 设置字段可访问
        h.setAccessible(true);
        // 获取代理对象的dynamicAdvisedInterceptor字段
        Object dynamicAdvisedInterceptor = h.get(proxy);
        // 获取dynamicAdvisedInterceptor的advised字段
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        // 设置字段可访问
        advised.setAccessible(true);
        // 返回代理对象的targetSource的target字段
        return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
    }

    // 获取JDK动态代理的目标对象
    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        // 获取代理对象的父类中的h字段
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        // 设置h字段可访问
        h.setAccessible(true);
        // 获取代理对象中的AopProxy对象
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        // 获取AopProxy对象中的advised字段
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        // 设置advised字段可访问
        advised.setAccessible(true);
        // 获取AdvisedSupport对象中的targetSource字段
        return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
    }

}