# Springboot集成XXL-JOB

## 配置运行调度中心（xxl-job-admin）

源代码地址：https://github.com/xuxueli/xxl-job

### 创建XXXL-JOB依赖的数据库表
doc/db/tables_xxl_job.sql


### 配置调度中心

application.properties配置

```properties配置
### xxl-job, datasource
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


### xxl-job, email
spring.mail.host=smtp.qq.com
spring.mail.port=25
spring.mail.username=1739468244@qq.com
spring.mail.from=1739468244@qq.com
# 此处不是邮箱登录密码，而是开启SMTP服务后的授权码
spring.mail.password=xxxxx

```

修改logback.xml配置日志输出路径
```xml
<property name="log.path" value="/Users/xxx/xxl-job-2.3.1/logs/xxl-job-admin.log"/>
```

## 配置运行执行器项目

### 依赖
```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>2.6.3</version>
  <relativePath/>
</parent>

<properties>
  <java.version>1.8</java.version>
  <fastjson.version>1.2.73</fastjson.version>
  <hutool.version>5.5.1</hutool.version>
  <mysql.version>8.0.19</mysql.version>
  <org.mapstruct.version>1.4.2.Final</org.mapstruct.version>
  <org.projectlombok.version>1.18.20</org.projectlombok.version>
  <druid.version>1.1.18</druid.version>
  <springdoc.version>1.6.9</springdoc.version>
</properties>

<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
  </dependency>
  <dependency>
    <groupId>com.xuxueli</groupId>
    <artifactId>xxl-job-core</artifactId>
    <version>2.3.1</version>
  </dependency>

  <dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.1</version>
  </dependency>
  <dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus</artifactId>
    <version>3.5.1</version>
  </dependency>
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>${mysql.version}</version>
    <scope>runtime</scope>
  </dependency>
  <dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>${druid.version}</version>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.20</version>
  </dependency>
  <dependency>
    <groupId>com.alibaba.fastjson2</groupId>
    <artifactId>fastjson2</artifactId>
    <version>2.0.12</version>
  </dependency>
  <dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>${org.mapstruct.version}</version>
  </dependency>
  <dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>${org.mapstruct.version}</version>
  </dependency>
  <dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>${hutool.version}</version>
  </dependency>
  <dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>${springdoc.version}</version>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
  </plugins>
</build>

```

### application.yml 
```yml
server:
  port: 9090

# xxl-job
xxl:
  job:
    admin:
      addresses: http://127.0.0.1:8080/xxl-job-admin # 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
    executor:
      appname: hresh-job-executor # 执行器 AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册
      ip: # 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"；
      port: 6666 # ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；
      logpath: /Users/xxx/xxl-job-2.3.1/logs/xxl-job # 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；
      logretentiondays: 30 # 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；
    accessToken: default_token  # 执行器通讯TOKEN [选填]：非空时启用；

spring:
  application:
    name: xxl-job-practice
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/xxl_job?serverTimezone=Hongkong&characterEncoding=utf-8&useSSL=false
    username: root
    password: root

mybatis:
  mapper-locations: classpath:mapper/*Mapper.xml
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    lazy-loading-enabled: true

```

### xxl-job 配置类
```java
@Configuration
public class XxlJobConfig {

  @Value("${xxl.job.admin.addresses}")
  private String adminAddresses;
  @Value("${xxl.job.executor.appname}")
  private String appName;
  @Value("${xxl.job.executor.ip}")
  private String ip;
  @Value("${xxl.job.executor.port}")
  private int port;
  @Value("${xxl.job.accessToken}")
  private String accessToken;
  @Value("${xxl.job.executor.logpath}")
  private String logPath;
  @Value("${xxl.job.executor.logretentiondays}")
  private int logRetentionDays;

  @Bean
  public XxlJobSpringExecutor xxlJobExecutor() {
    // 创建 XxlJobSpringExecutor 执行器
    XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
    xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
    xxlJobSpringExecutor.setAppname(appName);
    xxlJobSpringExecutor.setIp(ip);
    xxlJobSpringExecutor.setPort(port);
    xxlJobSpringExecutor.setAccessToken(accessToken);
    xxlJobSpringExecutor.setLogPath(logPath);
    xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
    // 返回
    return xxlJobSpringExecutor;
  }
}

```

### xxl-job 工具类
```java
@Component
@RequiredArgsConstructor
public class XxlUtil {

  @Value("${xxl.job.admin.addresses}")
  private String xxlJobAdminAddress;

  private final RestTemplate restTemplate;

  // 请求Url
  private static final String ADD_INFO_URL = "/jobinfo/addJob";
  private static final String REMOVE_INFO_URL = "/jobinfo/removeJob";
  private static final String GET_GROUP_ID = "/jobgroup/loadByAppName";

  /**
   * 添加任务
   *
   * @param xxlJobInfo
   * @param appName
   * @return
   */
  public String addJob(XxlJobInfo xxlJobInfo, String appName) {
    Map<String, Object> params = new HashMap<>();
    params.put("appName", appName);
    String json = JSONUtil.toJsonStr(params);
    String result = doPost(xxlJobAdminAddress + GET_GROUP_ID, json);
    JSONObject jsonObject = JSON.parseObject(result);
    Map<String, Object> map = (Map<String, Object>) jsonObject.get("content");
    Integer groupId = (Integer) map.get("id");
    xxlJobInfo.setJobGroup(groupId);
    String xxlJobInfoJson = JSONUtil.toJsonStr(xxlJobInfo);
    return doPost(xxlJobAdminAddress + ADD_INFO_URL, xxlJobInfoJson);
  }

  // 删除job
  public String removeJob(long jobId) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    map.add("id", String.valueOf(jobId));
    return doPostWithFormData(xxlJobAdminAddress + REMOVE_INFO_URL, map);
  }

  /**
   * 远程调用
   *
   * @param url
   * @param json
   */
  private String doPost(String url, String json) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(json, headers);
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
    return responseEntity.getBody();
  }

  private String doPostWithFormData(String url, MultiValueMap<String, String> map) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
    return responseEntity.getBody();
  }
}

```

### xxl-job-admin 修改 JobGroupController，新增 loadByAppName 方法
```java
@RequestMapping("/loadByAppName")
@ResponseBody
@PermissionLimit(limit = false)
public ReturnT<XxlJobGroup> loadByAppName(@RequestBody Map<String, Object> map) {
  XxlJobGroup jobGroup = xxlJobGroupDao.loadByAppName(map);
  return jobGroup != null ? new ReturnT<XxlJobGroup>(jobGroup)
    : new ReturnT<XxlJobGroup>(ReturnT.FAIL_CODE, null);
}

```

### xxl-job-admin XxlJobGroupDao 文件以及对应的 xml 文件
```java
XxlJobGroup loadByAppName(Map<String, Object> map);
```

```xml
<select id="loadByAppName" parameterType="java.util.HashMap" resultMap="XxlJobGroup">
    SELECT
    <include refid="Base_Column_List"/>
    FROM xxl_job_group AS t
    WHERE t.app_name = #{appName}
</select>
```

### xxl-job-admin 修改 JobInfoController，增加 addJob 方法和 removeJob 方法
```java
@RequestMapping("/addJob")
@ResponseBody
@PermissionLimit(limit = false)
public ReturnT<String> addJob(@RequestBody XxlJobInfo jobInfo) {
    return xxlJobService.add(jobInfo);
}

@RequestMapping("/removeJob")
@ResponseBody
@PermissionLimit(limit = false)
public ReturnT<String> removeJob(String id) {
    return xxlJobService.remove(Integer.parseInt(id));
}

```

### XxlService 创建任务
```java
@Service
@Slf4j
@RequiredArgsConstructor
public class XxlService {

  private final XxlUtil xxlUtil;

  @Value("${xxl.job.executor.appname}")
  private String appName;

  public void addJob(XxlJobInfo xxlJobInfo) {
    xxlUtil.addJob(xxlJobInfo, appName);
    long triggerNextTime = xxlJobInfo.getTriggerNextTime();
    log.info("任务已添加，将在{}开始执行任务", DateUtils.formatDate(triggerNextTime));
  }

}
```

### 业务代码 UserService
```java
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserMapper userMapper;
  private final UserStruct userStruct;
  private final WeatherService weatherService;
  private final XxlService xxlService;

  /**
   * 假设有这样一个业务需求，每当有新用户注册，则1分钟后会给用户发送欢迎通知.
   *
   * @param userRequest 用户请求体
   */
  @Transactional
  public void register(UserRequest userRequest) {
    if (Objects.isNull(userRequest) || isBlank(userRequest.getUsername()) ||
        isBlank(userRequest.getPassword())) {
      BusinessException.fail("账号或密码为空！");
    }

    User user = userStruct.toUser(userRequest);
    userMapper.insert(user);

    LocalDateTime scheduleTime = LocalDateTime.now().plusMinutes(1L);

    XxlJobInfo xxlJobInfo = XxlJobInfo.builder().jobDesc("定时给用户发送通知").author("hresh")
        .scheduleType("CRON").scheduleConf(DateUtils.getCron(scheduleTime)).glueType("BEAN")
        .glueType("BEAN")
        .executorHandler("sayHelloHandler")
        .executorParam(user.getUsername())
        .misfireStrategy("DO_NOTHING")
        .executorRouteStrategy("FIRST")
        .triggerNextTime(DateUtils.toEpochMilli(scheduleTime))
        .executorBlockStrategy("SERIAL_EXECUTION").triggerStatus(1).build();

    xxlService.addJob(xxlJobInfo);
  }


  public void sayHelloToUser(String username) {
    if (StrUtil.isBlank(username)) {
      log.error("用户名为空");
    }
    User user = userMapper.selectByUserName(username);
    String message = "Welcome to Java,I am hresh.";
    log.info(user.getUsername() + " , hello, " + message);
  }


  public void pushWeatherNotification() {
    List<User> users = userMapper.queryAll();
    log.info("执行发送天气通知给用户的任务。。。");
    WeatherInfo weatherInfo = weatherService.getWeather(WeatherConstant.WU_HAN);
    for (User user : users) {
      log.info(user.getUsername() + "----" + weatherInfo.toString());
    }
  }
}

```

### 业务代码 UserController
```java
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public Result<Object> register(@RequestBody UserRequest userRequest) {
    userService.register(userRequest);
    return Result.ok();
  }

}

```

###  任务处理器
* 用于处理 UI 页面创建的任务
```java
@RequiredArgsConstructor
@Slf4j
public class DemoHandler extends IJobHandler {

  @XxlJob(value = "demoHandler")
  @Override
  public void execute() throws Exception {
    log.info("自动任务" + this.getClass().getSimpleName() + "执行");
  }
}

```

* 处理代码创建的任务
```java
@Component
@RequiredArgsConstructor
public class SayHelloHandler {

  private final UserService userService;

  @XxlJob(value = "sayHelloHandler")
  public void execute() {
    String param = XxlJobHelper.getJobParam();
    userService.sayHelloToUser(param);
  }
}

```