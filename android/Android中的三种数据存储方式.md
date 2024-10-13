# Android中的三种数据存储方式

## 文件存储
* 文件存储是指直接将数据以文件的形式保存到设备的内部或外部存储中。
* 内部存储通常是私有的，其他应用无法访问你的应用创建的文件，除非你明确地共享它们。
* 外部存储可以是公共的（比如SD卡），用户和其他应用都可以访问这些文件。
* 使用openFileOutput()和openFileInput()等API来读写文件。
* 适用于存储大量文本数据或者二进制数据，如图片、视频等。

### 内部存储

1. MODE_PRIVATE: 该文件只能被但当前程序读写  
2. MODE_APPEND:该文件的内容可以追加  
3. MODE_WORLD_READABLE:该文件的内容可以被其他程序读  
4. MODE_WORLD_WRITEABLE:该文件的内容可以被其他程序写  

```java
//写入流

FileOutputStream fos = openFileOutput(String name ,  int  mode)

//读取流

FileInputStream fis = openFileInput(String name);
```

### 外部存储
```java
    //外部写入
    public void outRead(){
 
        //获取SD卡状态
        String SDStatus = Environment.getExternalStorageState();
 
        //判断卡片是否可用
        if (!SDStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        //获取SD卡路径
 
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
 
 
        File file = new File(externalStorageDirectory,"data.txt");
 
        //创建写入流
        FileOutputStream fos = null;
 
        try {
            //写入流
            fos = new FileOutputStream(file);
            //写入内容
            fos.write("写入数据".getBytes());
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
```

### 内部读取
openFileInput()获取FileInputStream读取流fis===>读取流fis创建字节缓冲区===>fis.read()==>读取之后转化为字符串写入

```java
   //内部读取
    public void inRead(){
        String contextInfo = "";
        //读取流
        FileInputStream fis = null;
 
        try {
 
            //读取文件流
            fis = openFileInput("data.txt");
            byte[] bytes = new byte[fis.available()];
            //文件读取到bytes数组中
            fis.read(bytes);
 
            //将读取到的字节数组转换为字符串
            contextInfo = new String(bytes);
 
 
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
```

### 外部读取
```java
 
    //外部读取
    public void outReadTwo(){
        //SD状态  === 》 判断sd卡是否存在  ==》存在==>读取外部文件
        String SDStatus = Environment.getExternalStorageState();
        if (!SDStatus.equals(Environment.MEDIA_MOUNTED)){
            return;
        }
        //获取SD卡路径
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
 
 
 
        File file = new File(externalStorageDirectory,"data.txt");
        //读取流
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            String contextInfo = new String(bytes);
 
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
```


## SharePreferences存储
* SharedPreferences 是一种轻量级的数据存储方式，主要用于保存应用程序的配置信息，例如用户的设置偏好。
* 数据是以键值对(key-value)形式存储，并且是以XML文件格式存放在设备上。
* 使用getSharedPreferences()获取SharedPreferences对象，然后通过edit()方法编辑数据，最后调用apply()或commit()保存更改。
* 适合于存储少量的原始类型数据，如布尔值、整数、浮点数、字符串等。
* 不适合存储复杂的数据结构或大量的数据。

### 存储位置
* SharedPreferences 文件默认存储在应用私有目录下的 /data/data/<package_name>/shared_prefs/ 目录中。
* 用户无法直接访问这个目录，除非设备已 root 并且用户具有足够的权限。

### 支持的数据类型
* boolean
* float
* int
* long
* String
* Set<String>

### 示例

```java
// 1. 获取 SharedPreferences 对象
// 获取名为 "MyPreferences" 的 SharedPreferences 对象
SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

// 2. 编辑数据
// 获取 Editor 对象
SharedPreferences.Editor editor = sharedPreferences.edit();
 
// 添加键值对
editor.putString("key_string", "Hello, World!");
editor.putInt("key_int", 42);
editor.putBoolean("key_boolean", true);
 
// 提交更改
editor.apply();  // 异步操作，不阻塞主线程
// 或者使用 editor.commit(); // 同步操作，会阻塞主线程直到写入完成

// 3.读取数据
// 读取数据
String value = sharedPreferences.getString("key_string", "Default Value");
int intValue = sharedPreferences.getInt("key_int", 0);
boolean boolValue = sharedPreferences.getBoolean("key_boolean", false);

```
### 注意事项
* 线程安全：SharedPreferences 不是线程安全的，如果在多线程环境中使用，需要自行处理同步问题。
* 性能：频繁地调用 apply() 或 commit() 可能会导致性能问题，尽量减少不必要的写入操作。
* 安全性：虽然 SharedPreferences 是私有的，但并不适合存储敏感信息，如密码等，因为这些信息可能被反编译工具读取。


## SQLite数据库存储

* SQLite 是一个关系型数据库管理系统，它被嵌入到Android系统中，提供了强大的数据存储能力。
* 它支持SQL语言，允许开发者执行复杂的查询操作。
* 使用SQLiteOpenHelper类可以帮助管理数据库的创建和版本更新。
S* QLiteDatabase类用于进行数据库的操作，包括增删改查。
* 适合存储结构化的数据，以及需要进行复杂查询的数据集。
* 可以处理大量数据，但相比其他两种方式，它的使用稍微复杂一些。

### 示例
```java
// 1. 创建SQLite数据库
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "example.db";
    private static final int DATABASE_VERSION = 1;
 
    // 表名
    public static final String TABLE_NAME = "users";
 
    // 列名
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
 
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_AGE + " INTEGER" + ")";
 
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);  // 创建表
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 删除旧表并重新创建（简单处理）
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

// 2.新增操作
public long insertUser(String name, int age) {
    SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME, name);
    values.put(COLUMN_AGE, age);
 
    return db.insert(TABLE_NAME, null, values);
}

// 3.删除数据
public int deleteUserById(long id) {
    SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
    return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
}

// 4.删除数据
public int deleteUserById(long id) {
    SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
    return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
}

// 5.修改数据
public int updateUser(long id, String newName, int newAge) {
    SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME, newName);
    values.put(COLUMN_AGE, newAge);
 
    return db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
}

// 6.查询数据
public Cursor getAllUsers() {
    SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
    return db.query(TABLE_NAME, null, null, null, null, null, null);
}
 
public Cursor getUserById(long id) {
    SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
    return db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
}

// 7.事务
public boolean performTransaction() {
    SQLiteDatabase db = new DatabaseHelper().getWritableDatabase();
    try {
        db.beginTransaction();  // 开始事务
 
        // 执行一系列数据库操作
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "John");
        values.put(COLUMN_AGE, 30);
        db.insert(TABLE_NAME, null, values);
 
        values.clear();
        values.put(COLUMN_NAME, "Jane");
        values.put(COLUMN_AGE, 25);
        db.insert(TABLE_NAME, null, values);
 
        db.setTransactionSuccessful();  // 标记事务为成功
        return true;
    } finally {
        db.endTransaction();  // 结束事务
    }
}

```
