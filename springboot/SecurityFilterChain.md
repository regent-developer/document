# SecurityFilterChain

SecurityFilterChain 是Spring Security 5.4及更高版本中引入的一个关键概念，它取代了之前的WebSecurityConfigurerAdapter作为配置安全性的主要方式。SecurityFilterChain 是Spring Security中用来配置安全过滤器链的主要方式，它提供了丰富的API来定制和管理安全设置。

* Spring Boot 2.x升级Spring Boot 3.x涉及到Security（Security5.x升级Security6.x）的升级，而Security5.x升级Security6.x的其中一个重要变化就是SecurityFilterChain的引入。

SecurityFilterChain中可用的一些主要配置选项及其用途。  

## authorizeHttpRequests
authorizeHttpRequests 是用于配置哪些请求需要认证以及哪些请求可以匿名访问的关键方法。它允许你根据请求的URL、HTTP方法等来决定是否需要认证。

```java
http
    .authorizeHttpRequests((authorize) -> authorize
        .requestMatchers("/public/**").permitAll() // 公共资源
        .requestMatchers(HttpMethod.POST, "/secure/post/**").hasRole("ADMIN") // POST请求需要管理员权限
        .anyRequest().authenticated() // 其他所有请求都需要认证
    );

```

## formLogin
formLogin 是用于配置表单登录的选项。它允许你指定登录页面的URL、登录成功后的默认URL、登录失败后的处理方式等。

```java
http
    .formLogin((form) -> form
        .loginPage("/login") // 自定义登录页面
        .loginProcessingUrl("/login") // 处理登录请求的URL
        .defaultSuccessUrl("/welcome", true) // 登录成功后的默认跳转URL
        .failureUrl("/login?error") // 登录失败后的URL
        .permitAll() // 允许任何人访问登录页面
    );

```

## httpBasic
httpBasic 用于配置基本的身份验证，通常用于RESTful服务或API。

```java
http
    .httpBasic(Customizer.withDefaults()); // 启用HTTP Basic认证
```

## logout
logout 用于配置登出逻辑。

```java
http
    .logout((logout) -> logout
        .logoutUrl("/logout") // 登出请求的URL
        .logoutSuccessUrl("/login?logout") // 登出成功后的URL
        .permitAll() // 允许任何人访问登出URL
    );

```

## sessionManagement
sessionManagement 用于配置会话管理，例如是否自动创建会话、会话超时时间等。

```java
http
    .sessionManagement((session) -> session
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 默认策略，需要时创建会话
        .maximumSessions(1) // 最大会话数
        .maxSessionsPreventsLogin(false) // 是否阻止登录
        .expiredUrl("/session-expired") // 会话过期后的URL
    );

```

## cors
cors 用于配置跨域资源共享（CORS），允许你控制哪些源可以访问你的应用程序。

```java
http
    .cors((cors) -> cors
        .configurationSource(request -> newCorsConfiguration()) // 自定义CORS配置
    );

```

## csrf
csrf 用于配置跨站请求伪造（CSRF）保护。

```java
http
    .csrf((csrf) -> csrf
        .disable() // 禁用CSRF保护
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // 自定义CSRF令牌存储
    );

```

## oauth2Login
oauth2Login 用于配置OAuth2登录。

```java
http
    .oauth2Login((oauth2) -> oauth2
        .loginPage("/login/oauth2") // OAuth2登录页面
        .userInfoEndpoint((userInfo) -> userInfo
            .userService(oAuth2UserService) // 自定义OAuth2用户服务
        )
    );

```

## oauth2ResourceServer
oauth2ResourceServer 用于配置OAuth2资源服务器，保护API端点。

```java
http
    .oauth2ResourceServer((oauth2) -> oauth2
        .jwt(Customizer.withDefaults()) // 配置JWT令牌验证
    );

```

## rememberMe
rememberMe 用于配置记住我功能，使用户在关闭浏览器后仍能保持登录状态。

```java
http
    .rememberMe((rememberMe) -> rememberMe
        .key("uniqueAndSecret") // 密钥
        .tokenValiditySeconds(1209600) // 记住我令牌的有效期（秒）
        .userDetailsService(userDetailsService) // 用户详细信息服务
    );

```

## exceptionHandling
exceptionHandling 用于配置异常处理。

```java
http
    .exceptionHandling((exceptions) -> exceptions
        .authenticationEntryPoint(new MyAuthenticationEntryPoint()) // 自定义未认证入口点
        .accessDeniedHandler(new MyAccessDeniedHandler()) // 自定义拒绝访问处理器
    );

```

## headers
headers 用于配置响应头。

```java
http
    .headers((headers) -> headers
        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // 配置X-Frame-Options
    );

```

## authenticationManager
authenticationManager 用于配置认证管理器。

```java
@Bean
public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .authenticationProvider(authenticationProvider())
        .and()
        .build();
}

```

## authenticationProvider
authenticationProvider 用于配置认证提供者。

```java
@Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
}

```

## addFilterBefore 和 addFilterAfter
addFilterBefore 和 addFilterAfter 用于在安全过滤器链中插入自定义过滤器。

```java
@Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
}

```
