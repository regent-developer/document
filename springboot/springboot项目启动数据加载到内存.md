# springboot项目启动数据加载到内存

## 使用@PostConstruct注解（properties/yaml文件）

```java
package com.example.demo.config;
 
import com.example.demo.service.ICodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
@Component
public class InitData {
 
    public static Map<Integer, String> codeMap = new HashMap<Integer, String>();
 
    @Autowired
    private ICodeService codeService;
 
    @PostConstruct
    public void init() {
        // 查询数据库数据
        List<String> codeList = codeService.listAll();
        for (int i = 0; i < codeList.size(); i++) {
            codeMap.put(i, codeList.get(i));
        }
    }
 
    @PreDestroy
    public void destroy() {
    }
}
```

## 使用@Order注解和CommandLineRunner接口

```java
package com.example.demo.config;
 
import com.example.demo.service.ICodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
@Component
@Order(1) // 初始化加载优先级，数字越小优先级越高
public class InitData implements CommandLineRunner {
 
    public static Map<Integer, String> codeMap = new HashMap<Integer, String>();
 
    @Autowired
    private ICodeService codeService;
 
    @Override
    public void run(String... args) throws Exception {
        // 查询数据库数据
        List<String> codeList = codeService.listAll();
        for (int i = 0; i < codeList.size(); i++) {
            codeMap.put(i, codeList.get(i));
        }
    }
}

```

## 使用@Order注解和ApplicationRunner接口

```java
package com.example.demo.config;
 
import com.example.demo.service.ICodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
@Component
@Order(1) // 初始化加载优先级，数字越小优先级越高
public class InitData implements ApplicationRunner {
 
    public static Map<Integer, String> codeMap = new HashMap<Integer, String>();
 
    @Autowired
    private ICodeService codeService;
 
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 查询数据库数据
        List<String> codeList = codeService.listAll();
        for (int i = 0; i < codeList.size(); i++) {
            codeMap.put(i, codeList.get(i));
        }
    }
}
```