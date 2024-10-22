# Dingtalk机器人发送群内消息

```java
public class DingTalkNotifierTest {
 
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
 
		String atMobile = "xxxxx";
		String text = "xxxxx";
 
		HttpEntity<Map<String, Object>> request = createMessage("markdown", "服务告警", text, atMobile);
 
		restTemplate.postForEntity(
				"https://oapi.dingtalk.com/robot/send?access_token=xxxxxxxxxxx",
				request, Void.class);
		
	}
 
	private static HttpEntity<Map<String, Object>> createMessage(String msgtype, String title, String text,
			String atMobiles) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("text", text + getAtMobilesString(atMobiles));
		params.put("title", title);
 
		Map<String, Object> at = new HashMap<String, Object>(2);
		at.put("atMobiles", getAtMobilesList(atMobiles));
		at.put("isAtAll", false);
 
		Map<String, Object> messageJson = new HashMap<String, Object>(3);
		messageJson.put("msgtype", msgtype);
		messageJson.put(msgtype, params);
		messageJson.put("at", at);
 
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new HttpEntity<>(messageJson, headers);
	}
 
	private static String getAtMobilesString(String s) {
		StringBuilder atMobiles = new StringBuilder();
		String[] mobiles = s.split(",");
		for (String mobile : mobiles) {
			atMobiles.append("@").append(mobile);
		}
		return atMobiles.toString();
	}
 
	private static List<String> getAtMobilesList(String s) {
		String[] mobiles = s.split(",");
		List<String> atMobilesList = new ArrayList<String>();
		for (String mobile : mobiles) {
			atMobilesList.add(mobile);
		}
		return atMobilesList;
	}
 
}
```

* 参考连接：https://ding-doc.dingtalk.com/doc#/serverapi2/qf2nxq