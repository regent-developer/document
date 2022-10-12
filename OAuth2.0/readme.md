# OAuth2.0

OAuth 2.0 is the industry-standard protocol for authorization. OAuth 2.0 focuses on client developer simplicity while providing specific authorization flows for web applications, desktop applications, mobile phones, and living room devices. This specification and its extensions are being developed within the IETF OAuth Working Group.  

https://oauth.net/2/  

## 流程

* 用户打开客户端以后，客户端要求用户给予授权。
* 用户同意给予客户端授权。
* 客户端使用上一步获得的授权，向认证服务器申请令牌。
* 认证服务器对客户端进行认证以后，确认无误，同意发放令牌。
* 客户端使用令牌，向资源服务器申请获取资源。
* 资源服务器确认令牌无误，同意向客户端开放资源。

## 授权模式
* 授权码模式（authorization code）
* 简化模式（implicit）
* 密码模式（resource owner password credentials）
* 客户端模式（client credentials）

### 授权码模式（authorization code）


### 简化模式（implicit）


### 密码模式（resource owner password credentials）


### 客户端模式（client credentials）

https://www.cnblogs.com/youcoding/p/13941387.html

https://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html


服务认证授权：OAuth2.0
https://blog.csdn.net/qq_38490457/article/details/113801009