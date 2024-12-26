# Springboot对接微信公众号实现通知功能

## pom依赖
```xml
<dependency>
    <groupId>com.github.binarywang</groupId>
    <artifactId>weixin-java-mp</artifactId>
    <version>3.3.0</version>
</dependency>
```

## 实现代码
```java
import com.navi.user.constant.SystemConstant;
import com.navi.user.entity.PsQuestion;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SendApproveMsg {


    public static void sendApproveMsg( PsQuestion entity) {
        //1-配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(SystemConstant.APP_ID);
        wxStorage.setSecret(SystemConstant.APP_SECRET);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        String color = "#000000";//黑色
        //color = "#001EFF";蓝色
        //color = "#FF0000";红色
        //color = "#FFFF00";黄色

        //2-推送消息
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        //消息模板id
        templateMessage.setTemplateId(SystemConstant.MSG_TEMPLATE_ID);
        //点击模版消息要访问的网址，注释掉之后就不会有点击跳转
       String alarmPath = "https://url.html?openid="+entity.getOpenid()
               +"&&id="+entity.getId();
        templateMessage.setUrl(alarmPath);
        List<WxMpTemplateData> wxMpTemplateData = new ArrayList<>();
        // {{first.DATA}}
        wxMpTemplateData.add(new WxMpTemplateData("first", "您的问题已经反馈，请及时登录查看，感谢您的支持！领航磐石产品部", color));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = sdf.format(new Date());
        //下面的keyword1对应模板上的位置
        wxMpTemplateData.add(new WxMpTemplateData("keyword1", startTime, color));
        wxMpTemplateData.add(new WxMpTemplateData("keyword2", entity.getQuestion(), color));
        wxMpTemplateData.add(new WxMpTemplateData("keyword3", entity.getAnswer(), color));
       // wxMpTemplateData.add(new WxMpTemplateData("remark", "领航磐石产品部", color));
        templateMessage.setData(wxMpTemplateData);
        try {
            //要推送的用户openid
            templateMessage.setToUser(entity.getOpenid());
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            //System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }

    }
}


public class SystemConstant {


    /**
     * 用户appid
     */
    public static String  APP_ID = "";

    /**
     *密码
     */
    public static String APP_SECRET = "";

    /**
     * 模板的id
     */
    public static String MSG_TEMPLATE_ID = "WUcHsDGMuFuZygOBmbCRtvghaPw67BfMWkzpNPo1pm0";
}

```