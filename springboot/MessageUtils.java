import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import java.util.Locale;
 
@Component
public class MessageUtils {
 
    private static MessageSource messageSource;
 
    private String basename = "classpath:static/messages";
 
    @Bean(name = "messageSource")
    //此行代码表明这是一个定义 bean 的注解，它指定了这个 bean 的名称为"messageSource"。
    public ReloadableResourceBundleMessageSource getMessageResource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
    //这行代码通过调用 ReloadableResourceBundleMessageSource 类的默认构造函数实例化一个对象，并将该对象赋给 source 变量。
        source.setBasename(basename);
    //这个方法设置基本名称为 basename。basename 是一个表示消息源文件的基本名称的字符串，包括路径和文件名，但不包括语言代码和国家/地区代码的部分。例如，如果 basename 是 "classpath:/messages/messages"，则此类将从 classpath 中加载 messages.properties 文件。
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        messageSource = source;
        return source;
    }
 
    /**
     * 获取单个国际化翻译值
     *
     * @param msgKey
     * @return
     */
    public static String get(String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msgKey;
        }
    }
}