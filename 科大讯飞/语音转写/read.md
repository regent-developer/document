# 科大讯飞-语音转写

## 注册账户

在下面url中科大讯飞账户

url：https://www.xfyun.cn/

## 申请免费语音转写试用

## 利用Java SDK实现语音转写

### Java SDK下载

url：https://console.xfyun.cn/services/lfasr

### 项目集成
* lfasr-sdk-3.0.0.jar 包放入目标工程
* 将示例中pom里的依赖拷贝到对应目标工程的pom中

### 调用样例
```java
//1、创建客户端实例
LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);

//2、上传音频文件
Message task = lfasrClient.upload(AUDIO_FILE_PATH);
String taskId = task.getData();
System.out.println("转写任务 taskId：" + taskId);

//3、查看转写进度
int status = 0;
while (status != 9) {
    Message message = lfasrClient.getProgress(taskId);
    JSONObject object = JSON.parseObject(message.getData());
    status = object.getInteger("status");
    System.out.println(message.getData());
    TimeUnit.SECONDS.sleep(2);
}
//4、获取结果
Message result = lfasrClient.getResult(taskId);

JSONArray arr = JSON.parseArray(result.getData());
StringBuffer sBuffer = new StringBuffer();
arr.stream().forEach(jsonobejct->arrayIdToString((JSONObject) jsonobejct, sBuffer));

System.out.println("转写结果: \n" + sBuffer.toString());

private static StringBuffer arrayIdToString(JSONObject jsonobejct, StringBuffer sBuffer) {
    return sBuffer.append(jsonobejct.getString("onebest"));
}

```

APP_ID：创建测试工程时科大讯飞提供的APPID
SECRET_KEY：创建测试工程时科大讯飞提供的SECRETKEY