# SpringBoot集成Flink-CDC

## CDC简介   

CDC（Change Data Capture，变动数据捕获）是一种实时数据捕获技术，用于捕获数据库中的数据变化，并将其同步到其他系统或数据仓库中。CDC技术可以用于数据同步、数据备份、数据挖掘、数据分析和数据集成等多种场景。CDC技术可以实时捕获数据库中的数据变化，并将其同步到其他系统或数据仓库中，从而实现数据的实时同步和实时分析。

## Flink-CDC简介
Flink-CDC是Apache Flink的一个扩展项目，用于实时捕获数据库中的数据变化，并将其同步到Flink流处理系统中。Flink-CDC可以实时捕获数据库中的数据变化，并将其同步到Flink流处理系统中，从而实现数据的实时同步和实时分析。Flink-CDC支持多种数据库，包括MySQL、PostgreSQL、Oracle等，并且可以与Flink的其他组件（如Flink SQL、Flink Table API等）无缝集成。

## SpringBoot集成Flink-CDC

### 引入依赖

```xml
<properties>
    <encoding>UTF-8</encoding>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <java.version>1.8</java.version>
    <scala.version>2.12</scala.version>
    <flink.version>1.13.6</flink.version>
</properties>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.83</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.apache.flink</groupId>
        <artifactId>flink-clients_2.12</artifactId>
        <version>${flink.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.flink</groupId>
        <artifactId>flink-java</artifactId>
        <version>${flink.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.flink</groupId>
        <artifactId>flink-streaming-java_2.12</artifactId>
        <version>${flink.version}</version>
    </dependency>
    <!--mysql -cdc-->
    <dependency>
        <groupId>com.ververica</groupId>
        <artifactId>flink-connector-mysql-cdc</artifactId>
        <version>2.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.18</version>
    </dependency>
</dependencies>

```

### 接入springboot项目
实现ApplicationRunner将flink-cdc作为一个项目启动时需要运行的分支子任务。

#### 创建监听类 实现 ApplicationRunner
```java
import com.ververica.cdc.connectors.mysql.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumSourceFunction;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.runtime.state.storage.FileSystemCheckpointStorage;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class MysqlEventListener implements ApplicationRunner {

    private final DataChangeSink dataChangeSink;

    public MysqlEventListener(DataChangeSink dataChangeSink) {
        this.dataChangeSink = dataChangeSink;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DebeziumSourceFunction<DataChangeInfo> dataChangeInfoMySqlSource = buildDataChangeSource();
        DataStream<DataChangeInfo> streamSource = env
                .addSource(dataChangeInfoMySqlSource, "mysql-source")
                .setParallelism(1);
        streamSource.addSink(dataChangeSink);
        env.execute("mysql-stream-cdc");

    }

    private DebeziumSourceFunction<DataChangeInfo> buildDataChangeSource() {
        return MySqlSource.<DataChangeInfo>builder()
                .hostname("10.50.40.145")
                .port(3306)
                .databaseList("paas_common_db")
                .tableList("paas_common_db.base_business_driver_score_*")
                .username("root")
                .password("cdwk-3g-145")

                /**initial初始化快照,即全量导入后增量导入(检测更新数据写入)
                 * latest:只进行增量导入(不读取历史变化)
                 * timestamp:指定时间戳进行数据导入(大于等于指定时间错读取数据)
                 */
                .startupOptions(StartupOptions.latest())
                .deserializer(new MysqlDeserialization())
                .serverTimeZone("GMT+8")
                .build();
    }
}

```

#### 自定义数据读取解析器
```java
import com.alibaba.fastjson.JSON;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import io.debezium.data.Envelope;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import com.alibaba.fastjson.JSONObject;


import java.util.List;
import java.util.Optional;


public class MysqlDeserialization implements DebeziumDeserializationSchema<DataChangeInfo> {

    public static final String TS_MS = "ts_ms";
    public static final String BIN_FILE = "file";
    public static final String POS = "pos";
    public static final String CREATE = "CREATE";
    public static final String BEFORE = "before";
    public static final String AFTER = "after";
    public static final String SOURCE = "source";
    public static final String UPDATE = "UPDATE";


    @Override
    public void deserialize(SourceRecord sourceRecord, Collector<DataChangeInfo> collector) {
        String topic = sourceRecord.topic();
        String[] fields = topic.split("\\.");
        String database = fields[1];
        String tableName = fields[2];
        Struct struct = (Struct) sourceRecord.value();
        final Struct source = struct.getStruct(SOURCE);
        DataChangeInfo dataChangeInfo = new DataChangeInfo();
        dataChangeInfo.setBeforeData( getJsonObject(struct, BEFORE).toJSONString());
        dataChangeInfo.setAfterData(getJsonObject(struct, AFTER).toJSONString());
        //5.获取操作类型  CREATE UPDATE DELETE
        Envelope.Operation operation = Envelope.operationFor(sourceRecord);
        String type = operation.toString().toUpperCase();
        int eventType = type.equals(CREATE) ? 1 : UPDATE.equals(type) ? 2 : 3;
        dataChangeInfo.setEventType(eventType);
        dataChangeInfo.setFileName(Optional.ofNullable(source.get(BIN_FILE)).map(Object::toString).orElse(""));
        dataChangeInfo.setFilePos(Optional.ofNullable(source.get(POS)).map(x->Integer.parseInt(x.toString())).orElse(0));
        dataChangeInfo.setDatabase(database);
        dataChangeInfo.setTableName(tableName);
        dataChangeInfo.setChangeTime(Optional.ofNullable(struct.get(TS_MS)).map(x -> Long.parseLong(x.toString())).orElseGet(System::currentTimeMillis));
        //7.输出数据
        collector.collect(dataChangeInfo);
    }


    private JSONObject getJsonObject(Struct value, String fieldElement) {
        Struct element = value.getStruct(fieldElement);
        JSONObject jsonObject = new JSONObject();
        if (element != null) {
            Schema afterSchema = element.schema();
            List<Field> fieldList = afterSchema.fields();
            for (Field field : fieldList) {
                Object afterValue = element.get(field);
                jsonObject.put(field.name(), afterValue);
            }
        }
        return jsonObject;
    }



    @Override
    public TypeInformation<DataChangeInfo> getProducedType() {
        return TypeInformation.of(DataChangeInfo.class);
    }
}

```

#### 变更对象
```java
import lombok.Data;

@Data
public class DataChangeInfo {
    /**
     * 变更前数据
     */
    private String beforeData;
    /**
     * 变更后数据
     */
    private String afterData;
    /**
     * 变更类型 1新增 2修改 3删除
     */
    private Integer eventType;
    /**
     * binlog文件名
     */
    private String fileName;
    /**
     * binlog当前读取点位
     */
    private Integer filePos;
    /**
     * 数据库名
     */
    private String database;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 变更时间
     */
    private Long changeTime;

}

```

#### 自定义sink 交由spring管理
```java
import lombok.extern.log4j.Log4j2;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DataChangeSink implements SinkFunction<DataChangeInfo> {

    @Override
    public void invoke(DataChangeInfo value, Context context) {
        log.info("收到变更原始数据:{}", value);
        // todo 数据处理;因为此sink也是交由了spring管理，您想进行任何操作都非常简单
    }
}

```
