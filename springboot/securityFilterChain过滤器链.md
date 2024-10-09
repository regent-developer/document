# securityFilterChain过滤器链

## 默认配置的过滤器及顺序
* org.springframework.security.web.session.DisableEncodeUrlFilter
* org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter
* org.springframework.security.web.context.SecurityContextPersistenceFilter
* org.springframework.security.web.header.HeaderWriterFilter
* org.springframework.security.web.csrf.CsrfFilter
* org.springframework.security.web.authentication.logout.LogoutFilter
* org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
* org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter
* org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter
* org.springframework.security.web.authentication.www.BasicAuthenticationFilter
* org.springframework.security.web.savedrequest.RequestCacheAwareFilter
* org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter
* org.springframework.security.web.authentication.AnonymousAuthenticationFilter
* org.springframework.security.web.session.SessionManagementFilter
* org.springframework.security.web.access.ExceptionTranslationFilter
* org.springframework.security.web.access.intercept.FilterSecurityInterceptor

## 登录相关的过滤器
* SecurityContextPersistenceFilter
* LogoutFilter
* UsernamePasswordAuthenticationFilter
* DefaultLoginPageGeneratingFilter
* DefaultLogoutPageGeneratingFilter
* AnonymousAuthenticationFilter
* ExceptionTranslationFilter
* FilterSecurityInterceptor



### SecurityContextPersistenceFilter

#### 实现功能
登陆成功之后的身份认证

#### 处理请求类型
所有请求

#### 是否会终止过滤器链
不会

#### 实现步骤
* 从认证仓库中（SecurityContextRepository）获取认证信息（SecurityContext，基于session实现），如果为空则创建未认证的认证信息
* 将认证信息设置到认证上下文中（SecurityContextHolder，线程绑定的）中供后续业务使用
* 调用后续过滤器链
* 从认证上下文中获取最新的认证信息
* 清除认证上下文中的认证信息
* 将步骤4中的认证信息添加到认证仓库中

### LogoutFilter
#### 实现功能
* 清除认证信息
* 重定向登录页面


#### 处理请求类型
登出提交请求（默认：POST、/logout请求）

#### 是否会终止过滤器链
登出请求时会终止

#### 实现步骤
* 匹配请求地址
* 清除认证信息（CompositeLogoutHandler中注册的LogoutHandler实现类）
* 调用登出成功处理器，默认SimpleUrlLogoutSuccessHandler实现重定向登录页面功能，推荐自定义配置


### UsernamePasswordAuthenticationFilter

#### 实现功能

* 使用提交的用户名密码生成认证信息
* 根据认证结果做不同处理

#### 处理请求类型
登录提交请求（默认：POST、/login请求）

#### 是否会终止过滤器链
* 认证失败时会终止过滤器链，重定向默认登录地址
* 认证成功时会终止过滤器链，重定向到目标URL地址

#### 实现步骤
* 匹配请求地址
* 默认配置：提交的用户名密码和内存中用户名密码匹配，并校验用户和密码的是否有效等信息
* 认证失败时重定向到登录页面
* 认证成功时securityContextRepository中保存SecurityContext
* 重定向到目标URL地址（未认证访问目标地址，会先重定向登录页面，登录成功后再重定向到目标URL地址）

### DefaultLoginPageGeneratingFilter

#### 实现功能

生成默认登录页面

#### 处理请求类型

* 登录页面请求（默认GET、/login请求）
* 登录失败
* 登出成功

#### 是否会终止过滤器链
登录页面请求、登录失败、登出成功时会终止过滤器链

#### 实现步骤

### DefaultLogoutPageGeneratingFilter

#### 实现功能
生成默认登录页面


#### 处理请求类型
登出页面请求（默认：GET、/logout请求）

#### 是否会终止过滤器链
登出页面请求时会终止过滤器链

#### 实现步骤


### AnonymousAuthenticationFilter

#### 实现功能
当前认证信息为空时生成匿名认证信息


#### 处理请求类型
所有请求

#### 是否会终止过滤器链
不会

#### 实现步骤


### ExceptionTranslationFilter

#### 实现功能
处理FilterSecurityInterceptor抛出的异常，根据异常做相应处理


#### 处理请求类型
所有请求

#### 是否会终止过滤器链
* 认证失败时会重定向登录页面
* 授权失败时会返回错误信息

#### 实现步骤


### FilterSecurityInterceptor

#### 实现功能
认证和授权


#### 处理请求类型
所有请求

#### 是否会终止过滤器链
认证或授权失败会抛出异常由ExceptionTranslationFilter处理该异常

#### 实现步骤
