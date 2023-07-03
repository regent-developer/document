# Server-Sent Events

Server-Sent Events（简称SSE）是服务端发送事件，即服务端Push的一种机制。  

## SSE工作原理
HTTP协议是要客户端先请求服务器，服务器才能响应给客户端，无法做到服务器主动推送信息。但是，服务器可以向客户端声明，接下来要发送的是流信息（event-streaming），发送的不是一次性的数据包，而是一个数据流，会连续不断地发送过来。这时，客户端不会关闭连接，会一直等着服务器发过来的新的数据流。  
SSE 就是利用这种机制，使用流信息向客户端推送信息。

* 客户端请求建立事件流类型的连接，即Request Headers Accept = text/event-stream。
* 服务端响应请求，并将Response Headers Content-Type设置为text/event-stream，证明数据将以这种类型传送。
* 服务端如果有数据就会发送给客户端。

## SSE特点（以及与websocket的区别）
|  WebSocket   | SSE  |
|  ----  | ----  |
| 全双工，可以同时发送和接收消息  | 单工，只能服务端单向发送消息 |
| 独立的协议  | 基于HTTP协议 |
| 协议相对复杂  | 协议相对简单，易于理解和使用 |
| 默认不支持断线重连  | 默认支持断线重连 |
| 默认支持传送二进制数据  | 一般只用来传送文本，二进制数据需要编码后传送 |
| 不支持自定义发送的数据类型  | 支持自定义发送的数据类型 |
| 支持CORS  | 不支持CORS，协议和端口都必须相同 |

## SSE的推送数据格式
* event: 事件类型，服务端可以自定义，默认是message事件
* Id: 每一条事件流的ID，在失败重传事件流的时候有重要作用
* retry： 浏览器连接断开之后重连的间隔时间，单位：毫秒，在自动重新连接的过程中，之前收到的最后一个事件流ID会被发送到服务端。
* data: 发送的数据

真正的数据用data字段表示，一般放到最后，使用"\n\n"结尾。  
如果数据很长，可以分成多行，最后一行用\n\n结尾，前面行都用\n结尾。  

## SSE的使用

### 客户端

大部分浏览器都通过EventSource API 支持了SSE。  

* 判断你的浏览器是否支持SSE
```js
if(typeof(EventSource)!=="undefined")
{
    alert('支持SSE')
}
else
{
    alert('不支持SSE')
}

```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

</body>
<script>
var username = 'xujian';
// 创建EventSource对象，同时建立连接
<!--服务端使用SseEmitter时使用-->
var source = new EventSource('http://localhost:8080/sseEmitter/connect/' + username);
<!--服务端不使用SseEmitter时使用-->
<!--var source = new EventSource('http://localhost:8080/sseEmitter/data');-->

document.write(username + '正在连接...<br>');

// 监听连接建立完成事件
source.addEventListener('open', function (e) {
    document.write(username + '连接成功～<br>');
}, false);

// 监听连接错误事件
source.addEventListener('error', function (e) {
    document.write(username + '连接错误！<br>');
});

// 监听自定义消息推送事件，事件名称为“psh”，这个名字由服务端设置
source.addEventListener('psh', function (e) {
    document.write('收到消息：' + e.data + '<br>');
});
</script>
</html>

```

### 服务端

Springboot内置了SseEmitter封装了对SSE的支持。  

```java
package com.example.springbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sseEmitter")
public class SseEmitterController {
    private static final Map<String, SseEmitter> emitterMap = new HashMap<>();

    /**
     * 服务端不使用SseEmitter时使用
     *
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/data")
    public void getData(HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream;charset=UTF-8");
        response.getWriter().write("retry: 5000\n");
        response.getWriter().write("data: hahahaha\n\n");
        response.getWriter().flush();
        System.in.read();
    }

    /**
     * 服务端使用SseEmitter时使用
     *
     * @param username
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/connect/{username}", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter connect(@PathVariable String username) throws IOException {
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onCompletion(() -> {
            System.out.println(username + "连接结束！");
            emitterMap.remove(username);
        });
        sseEmitter.onError((t) -> {
            System.out.println(username + "连接出错！错误信息：" + t.getMessage());
            emitterMap.remove(username);
        });
        sseEmitter.onTimeout(() -> {
            System.out.println(username + "连接超时！");
            emitterMap.remove(username);
        });
        emitterMap.put(username, sseEmitter);

        sseEmitter.send("连接建立成功");
        return sseEmitter;
    }

    /**
     * 服务端使用SseEmitter时使用
     *
     * @param username
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/send/{username}")
    public String send(@PathVariable String username) throws IOException {
        SseEmitter sseEmitter = emitterMap.get(username);
        if (sseEmitter == null) {
            return "没查询到该用户的连接！";
        }
        sseEmitter.send(SseEmitter.event().name("psh").data("Hello～"));
        return "发送成功～";
    }

    /**
     * 服务端使用SseEmitter时使用
     *
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/sendAll")
    public String sendAll() throws IOException {
        for (SseEmitter sseEmitter : emitterMap.values()) {
            sseEmitter.send(SseEmitter.event().name("psh").data("Hello～"));
        }
        return "发送完成～";
    }
}
```

#### SseEmitter类简介

SpringBoot 利用 SseEmitter 来支持SSE，并对SSE规范做了一些封装。  
* send()：发送数据，如果传入的是一个非SseEventBuilder对象，那么传递参数会被封装到 data 中。
* complete()：表示执行完毕，会断开连接。
* onTimeout()：连接超时时回调触发。
* onCompletion()：结束之后的回调触发。
* onError()：报错时的回调触发。

```java
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * SseServer业务封装类来操作SEE
 */
@Slf4j
public class SseServerUtil {
   

    /**
     * 当前连接总数
     */
    private static AtomicInteger currentConnectTotal = new AtomicInteger(
0
);

    /**
     * messageId的 SseEmitter对象映射集
     */
    private static Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    /**
     * 创建sse连接
     *
     * @param messageId - 消息id（唯一）
     * @return
     */
    public static SseEmitter createConnect(String messageId) {
   
        /**
         * 设置连接超时时间。0表示不过期，默认是30秒，超过时间未完成会抛出异常
         */
        SseEmitter sseEmitter = new SseEmitter(
0L
);
        /*
        // 超时时间设置为3s，设置前端的重试时间为1s。重连时，注意总数的统计
        SseEmitter sseEmitter = new SseEmitter(3_000L);
        try {
            sseEmitter.send(
                    SseEmitter.event()
                    .reconnectTime(1000L)
                    //.data("前端重连成功") // 重连成功的提示信息
            );
        } catch (IOException e) {
            log.error("前端重连异常 ==> messageId={}, 异常信息：", messageId, e.getMessage());
            e.printStackTrace();
        }*/


        // 注册回调
        sseEmitter.onCompletion(completionCallBack(messageId));
        sseEmitter.onTimeout(timeOutCallBack(messageId));
        sseEmitter.onError(errorCallBack(messageId));
        sseEmitterMap.put(messageId, sseEmitter);

        //记录一下连接总数。数量+1
        int count = currentConnectTotal.incrementAndGet();
        log.info("创建sse连接成功 ==> 当前连接总数={}， messageId={}", count, messageId);
        return sseEmitter;
    }

    /**
     * 给指定 messageId发消息
     *
     * @param messageId - 消息id（唯一）
     * @param message   - 消息文本
     */
    public static void sendMessage(String messageId, String message) {
   
        if (sseEmitterMap.containsKey(messageId)) {
   
            try {
   
                sseEmitterMap.get(messageId).send(message);
            } catch (IOException e) {
   
                log.error("发送消息异常 ==> messageId={}, 异常信息：", messageId, e.getMessage());
                e.printStackTrace();
            }
        } else {
   
            throw new RuntimeException("连接不存在或者超时， messageId=" + messageId);
        }
    }

    /**
     * 给所有 messageId广播发送消息
     *
     * @param message
     */
    public static void batchAllSendMessage(String message) {
   
        sseEmitterMap.forEach((messageId, sseEmitter) -> {
   
            try {
   
                sseEmitter.send(message, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
   
                log.error("广播发送消息异常 ==> messageId={}, 异常信息：", messageId, e.getMessage());
                removeMessageId(messageId);
            }
        });
    }

    /**
     * 给指定 messageId集合群发消息
     *
     * @param messageIds
     * @param message
     */
    public static void batchSendMessage(List<String> messageIds, String message) {
   
        if (CollectionUtils.isEmpty(messageIds)) {
   
            return;
        }
        // 去重
        messageIds = messageIds.stream().distinct().collect(Collectors.toList());
        messageIds.forEach(userId -> sendMessage(userId, message));
    }


    /**
     * 给指定组群发消息（即组播，我们让 messageId满足我们的组命名确定即可）
     *
     * @param groupId
     * @param message
     */
    public static void groupSendMessage(String groupId, String message) {
   
        if (MapUtils.isEmpty(sseEmitterMap)) {
   
            return;
        }
        sseEmitterMap.forEach((messageId, sseEmitter) -> {
   
            try {
   
                // 这里 groupId作为前缀
                if (messageId.startsWith(groupId)) {
   
                    sseEmitter.send(message, MediaType.APPLICATION_JSON);
                }
            } catch (IOException e) {
   
                log.error("组播发送消息异常 ==> groupId={}, 异常信息：", groupId, e.getMessage());
                removeMessageId(messageId);
            }
        });
    }

    /**
     * 移除 MessageId
     *
     * @param messageId
     */
    public static void removeMessageId(String messageId) {
   
        sseEmitterMap.remove(messageId);
        //数量-1
        currentConnectTotal.getAndDecrement();
        log.info("remove messageId={}", messageId);
    }

    /**
     * 获取所有的 MessageId集合
     *
     * @return
     */
    public static List<String> getMessageIds() {
   
        return new ArrayList<>(sseEmitterMap.keySet());
    }

    /**
     * 获取当前连接总数
     *
     * @return
     */
    public static int getConnectTotal() {
   
        return currentConnectTotal.intValue();
    }

    /**
     * 断开SSE连接时的回调
     *
     * @param messageId
     * @return
     */
    private static Runnable completionCallBack(String messageId) {
   
        return () -> {
   
            log.info("结束连接 ==> messageId={}", messageId);
            removeMessageId(messageId);
        };
    }

    /**
     * 连接超时时回调触发
     *
     * @param messageId
     * @return
     */
    private static Runnable timeOutCallBack(String messageId) {
   
        return () -> {
   
            log.info("连接超时 ==> messageId={}", messageId);
            removeMessageId(messageId);
        };
    }

    /**
     * 连接报错时回调触发。
     *
     * @param messageId
     * @return
     */
    private static Consumer<Throwable> errorCallBack(String messageId) {
   
        return throwable -> {
   
            log.error("连接异常 ==> messageId={}", messageId);
            removeMessageId(messageId);
        };
    }
}
```