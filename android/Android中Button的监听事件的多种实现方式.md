# Android中Button的监听事件的多种实现方式

1. 采用内部类的方式
```java
public class MainActivity extends Activity {
    private Button btn = null;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btn = (Button) this.findViewById(R.id.btn);
        this.btn.setOnClickListener(new MyOnclickBtn());
    }
 
    // 方式一 采用内部类
    private class MyOnclickBtn implements OnClickListener {
        @Override
        public void onClick(View v) {
 
        }
    }
 
}
```

2. 采用匿名内部类的方式
```java
public class MainActivity extends Activity {
    private Button btn = null;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btn = (Button) this.findViewById(R.id.btn);
        this.btn.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
 
            }
        });
    }
 
}
```


3. 使用本类对象的方式
```java
public class MainActivity extends Activity implements OnClickListener {
    private Button btn = null;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btn = (Button) this.findViewById(R.id.btn);
        this.btn.setOnClickListener(this);
    }
 
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
 
    }
 
}
```

4. 采用配置.xml 文件的方式来反射实现监听事件

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical"
    tools:context=".MainActivity" >
 
 
    <Button
        android:id="@+id/btn"
        android:onClick="myBtnOnclick"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dip"
        android:text="确定" />
 
</LinearLayout>

```

```java

public class MainActivity extends Activity {
    private Button btn = null;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btn = (Button) this.findViewById(R.id.btn);
    }
 
    public void myBtnOnclick(View view) {
	//to do something
    }
 

//关于第四方法中一定要保证.xml 文件中配置的方法名与代码中的方法名一致。
```