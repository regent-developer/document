# SpringBoot防篡改防重放

基于nonce + timestamp 的方案：nonce的意思是仅一次有效的随机字符串，要求每次请求时该参数要保证不同。实际使用用户信息+时间戳+随机数等信息做个哈希之后，作为nonce参数。

Nonce是Number once的缩写，在密码学中Nonce是一个只被使用一次的任意或非重复的随机数值。

## 流程
* 在redis中查找是否有key为nonce:{nonce}的string。
* 如果没有，则创建这个key，把这个key失效的时间和验证timestamp失效的时间一致，比如是60s。
* 如果有，说明这个key在60s内已经被使用了，那么这个请求就可以判断为重放请求。


## 请求头对象
```java
@Data
@Builder
public class RequestHeader {
   private String sign ;
   private Long timestamp ;
   private String nonce;
}
```
## 从HttpServletRequest获取请求参数的工具类
```java
@Slf4j
@UtilityClass
public class HttpDataUtil {
    /**
     * post请求处理：获取 Body 参数，转换为SortedMap
     *
     * @param request
     */
    public  SortedMap<String, String> getBodyParams(final HttpServletRequest request) throws IOException {
        byte[] requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        String body = new String(requestBody);
        return JsonUtil.json2Object(body, SortedMap.class);
    }
​
​
    /**
     * get请求处理：将URL请求参数转换成SortedMap
     */
    public static SortedMap<String, String> getUrlParams(HttpServletRequest request) {
        String param = "";
        SortedMap<String, String> result = new TreeMap<>();
​
        if (StringUtils.isEmpty(request.getQueryString())) {
            return result;
        }
​
        try {
            param = URLDecoder.decode(request.getQueryString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
​
        String[] params = param.split("&");
        for (String s : params) {
            String[] array=s.split("=");
            result.put(array[0], array[1]);
        }
        return result;
    }
}
```
这里放入SortedMap中对其进行字典排序，同样，前端构建签名时同样需要对参数进行字典排序（使用相同的排序）。


## 签名验证工具类
```java
@Slf4j
@UtilityClass
public class SignUtil {
    /**
     * 验证签名
     * 验证算法：把timestamp + JsonUtil.object2Json(SortedMap)合成字符串，然后MD5
     */
    @SneakyThrows
    public  boolean verifySign(SortedMap<String, String> map, RequestHeader requestHeader) {
        String params = requestHeader.getNonce() + requestHeader.getTimestamp() + JsonUtil.object2Json(map);
        return verifySign(params, requestHeader);
    }
​
    /**
     * 验证签名
     */
    public boolean verifySign(String params, RequestHeader requestHeader) {
        log.debug("客户端签名: {}", requestHeader.getSign());
        if (StringUtils.isEmpty(params)) {
            return false;
        }
        log.info("客户端上传内容: {}", params);
        String paramsSign = DigestUtils.md5DigestAsHex(params.getBytes()).toUpperCase();
        log.info("客户端上传内容加密后的签名结果: {}", paramsSign);
        return requestHeader.getSign().equals(paramsSign);
    }
}
```

## HttpServletRequest包装类
```java
public class SignRequestWrapper extends HttpServletRequestWrapper {
    //用于将流保存下来
    private byte[] requestBody = null;
​
    public SignRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        requestBody = StreamUtils.copyToByteArray(request.getInputStream());
    }
​
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
​
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }
​
            @Override
            public boolean isReady() {
                return false;
            }
​
            @Override
            public void setReadListener(ReadListener readListener) {
​
            }
​
            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
​
    }
​
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
```
防篡改和防重放我们会通过SpringBoot Filter来实现，而编写的filter过滤器需要读取request数据流，但是request数据流只能读取一次，需要自己实现HttpServletRequestWrapper对数据流包装，目的是将request流保存下来。

## 创建过滤器实现安全校验
```java
@Configuration
public class SignFilterConfiguration {
    @Value("${sign.maxTime}")
    private String signMaxTime;
​
    //filter中的初始化参数
    private Map<String, String> initParametersMap =  new HashMap<>();
​
    @Bean
    public FilterRegistrationBean contextFilterRegistrationBean() {
        initParametersMap.put("signMaxTime",signMaxTime);
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(signFilter());
        registration.setInitParameters(initParametersMap);
        registration.addUrlPatterns("/sign/*");
        registration.setName("SignFilter");
        // 设置过滤器被调用的顺序
        registration.setOrder(1);
        return registration;
    }
​
    @Bean
    public Filter signFilter() {
        return new SignFilter();
    }
}
```

```java
@Slf4j
public class SignFilter implements Filter {
    @Resource
    private RedisUtil redisUtil;
​
    //从fitler配置中获取sign过期时间
    private Long signMaxTime;
​
    private static final String NONCE_KEY = "x-nonce-";
​
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
​
        log.info("过滤URL:{}", httpRequest.getRequestURI());
​
        HttpServletRequestWrapper requestWrapper = new SignRequestWrapper(httpRequest);
        //构建请求头
        RequestHeader requestHeader = RequestHeader.builder()
                .nonce(httpRequest.getHeader("x-Nonce"))
                .timestamp(Long.parseLong(httpRequest.getHeader("X-Time")))
                .sign(httpRequest.getHeader("X-Sign"))
                .build();
​
        //验证请求头是否存在
        if(StringUtils.isEmpty(requestHeader.getSign()) || ObjectUtils.isEmpty(requestHeader.getTimestamp()) || StringUtils.isEmpty(requestHeader.getNonce())){
            responseFail(httpResponse, ReturnCode.ILLEGAL_HEADER);
            return;
        }
​
        /*
         * 1.重放验证
         * 判断timestamp时间戳与当前时间是否操过60s（过期时间根据业务情况设置）,如果超过了就提示签名过期。
         */
        long now = System.currentTimeMillis() / 1000;
​
        if (now - requestHeader.getTimestamp() > signMaxTime) {
            responseFail(httpResponse,ReturnCode.REPLAY_ERROR);
            return;
        }
​
        //2. 判断nonce
        boolean nonceExists = redisUtil.hasKey(NONCE_KEY + requestHeader.getNonce());
        if(nonceExists){
            //请求重复
            responseFail(httpResponse,ReturnCode.REPLAY_ERROR);
            return;
        }else {
            redisUtil.set(NONCE_KEY+requestHeader.getNonce(), requestHeader.getNonce(), signMaxTime);
        }
​
​
        boolean accept;
        SortedMap<String, String> paramMap;
        switch (httpRequest.getMethod()){
            case "GET":
                paramMap = HttpDataUtil.getUrlParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, requestHeader);
                break;
            case "POST":
                paramMap = HttpDataUtil.getBodyParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, requestHeader);
                break;
            default:
                accept = true;
                break;
        }
        if (accept) {
            filterChain.doFilter(requestWrapper, servletResponse);
        } else {
            responseFail(httpResponse,ReturnCode.ARGUMENT_ERROR);
            return;
        }
​
    }
​
    private void responseFail(HttpServletResponse httpResponse, ReturnCode returnCode)  {
        ResultData<Object> resultData = ResultData.fail(returnCode.getCode(), returnCode.getMessage());
        WebUtils.writeJson(httpResponse,resultData);
    }
​
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String signTime = filterConfig.getInitParameter("signMaxTime");
        signMaxTime = Long.parseLong(signTime);
    }
}
```

## Redis工具类
```java
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
​
    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
​
​
    /**
     * 普通缓存放入并设置时间
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
​
    /**
     * 普通缓存放入
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
​
}
```



