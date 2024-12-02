/**
	<!-- binlog监听 -->
    <dependency>
      <groupId>com.zendesk</groupId>
      <artifactId>mysql-binlog-connector-java</artifactId>
      <version>0.29.2</version>
    </dependency>

 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventHeader;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;




@Component
public class BinlogListener {
	private static final Logger logger = LoggerFactory.getLogger(BinlogListener.class);
	
	@Resource
	private Environment env;
	
	private BinaryLogClient client = null;
	
	public BinlogListener() {
		
	}
	
	@PostConstruct
	public void start() {
		String hostname = env.getProperty("app.hostname");
		String port = env.getProperty("app.port");
		String username = env.getProperty("app.username");
		String password = env.getProperty("app.password");
		
		int port_number = Integer.parseInt(port);
		client = new BinaryLogClient(hostname, port_number, username, password);
        client.registerEventListener(this::onEvent);
        
        // 启动 Binary Log Client，springboot启动自动监听
        new Thread(() -> {
            try {
                client.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
	}
	
	private void onEvent(Event event) {
		EventHeader header = event.getHeader();
        //操作状态信息获取
        EventType etype = header.getEventType();
        //操作数据信息获取
        EventData edata = event.getData();
        
		//监听到变更操作
		if(etype == com.github.shyiko.mysql.binlog.event.EventType.EXT_UPDATE_ROWS) {
			UpdateRowsEventData updateData = (UpdateRowsEventData) edata;
			//获取的变更数据
        	System.out.println(updateData);
		}
		//监听到写入操作
        if(etype == com.github.shyiko.mysql.binlog.event.EventType.EXT_WRITE_ROWS) {
        	WriteRowsEventData writeData = (WriteRowsEventData) edata;
        	//获取的写入数据
        	System.out.println(writeData);
        }
        //监听到删除操作
        if(etype == com.github.shyiko.mysql.binlog.event.EventType.EXT_DELETE_ROWS) {
        	DeleteRowsEventData deleteData = (DeleteRowsEventData) edata;
        	//获取的删除数据
        	System.out.println(deleteData);
        }
	}
}

