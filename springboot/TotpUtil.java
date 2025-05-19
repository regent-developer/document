import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.stereotype.Component;
 
/* 
<dependency>
    <groupId>com.warrenstrange</groupId>
    <artifactId>googleauth</artifactId>
    <version>1.5.0</version>
</dependency>
*/

@Component
public class TotpUtil {
 
    // 创建GoogleAuthenticator对象
    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();
 
    /**
     *
     * @param secret 生成二维码链接时使用的GoogleAuthenticatorKey对象的secret值
     * @param code 输入的验证码
     * @return boolean
     */
    public boolean verifyCode(String secret, String code) {
        try {
            // 将输入的验证码转换为整数
            int intCode = Integer.parseInt(code);
            // 验证输入的验证码是否正确
            return gAuth.authorize(secret, intCode);
        } catch (Exception e) {
            // 如果转换失败，返回false
            return false;
        }
    }
 
    // 获取GoogleAuthenticatorKey对象
    public GoogleAuthenticatorKey getCredentialsKey(){
        return gAuth.createCredentials();
    }
    /**
     *
     * @param systemName app上显示绑定的系统名称
     * @param userNameOrEmail 用户绑定的账户邮箱
     * @param key 生成二维码链接绑定的 key
     * @return 生成的二维码链接
     */
    public String generateOtpAuthUrl(String systemName, String userNameOrEmail, GoogleAuthenticatorKey key){
        // 生成二维码 URL
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(systemName, userNameOrEmail, key);
    }
}