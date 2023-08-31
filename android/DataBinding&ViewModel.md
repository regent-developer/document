# DataBinding&ViewModel

## ViewModel

ViewModel 是 Jetpack 中的一个组件，它旨在存储和管理与 UI 相关的数据。ViewModel 的设计思想是将 UI 层与业务逻辑分离，使得数据在配置更改（例如屏幕旋转）时仍然保持可用。ViewModel 独立于 UI 层的生命周期，并在配置更改时保留其状态，因此可以轻松地处理生命周期感知的数据。
ViewModel 通常与 LiveData结合使用，后者是一个可观察的数据容器。LiveData 使数据在 ViewModel 和 UI 之间进行双向绑定成为可能，从而使数据的更新可以自动反映在 UI 上。

## DataBinding

在传统的Android应用开发中，布局文件通常只负责应用界面的布局工作，如果需要实现页面交互就需要调用setContentView()将Activity、fragment和XML布局文件关联起来。然后通过控件的id找到控件，接着在页面中通过代码对控件进行逻辑处理。在这种传统的开发方式中，页面承担了大部分的工作量，大量的逻辑处理需要在Activity、Fragment中进行处理，因此页面显得臃肿不堪，维护起来也很困难，为了减轻页面的工作量，Google提出了DataBiiding（视图绑定）。

DataBinding的出现让布局文件承担了原本属于Activity、Fragment页面的部分逻辑，使得Activity页面和XML布局之间的耦合度进一步降低。DataBinding主要有以下特点： - 代码更加简介，可读性高，能够在XML文件中实现UI控制。 - 不再需要findViewById操作， - 能够与Model实体类直接绑定，易于维护和扩展。

事实上，DataBinding和MVVM架构是分不开的，DataBinding正是Google为了能够更好的实现MVVM架构而实现的。

### gradle配置



```gradle
android {
    buildFeatures {
            viewBinding true
            dataBinding true
        }
}
```



### layout xml配置

使用layout和data标签

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    
    <data>
        <variable
            name="data"
            type="com.demo.DemoViewModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".DemoFragment">
        <TextView
            android:id="@+id/text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:layout_marginTop="8dp"
            android:layout_marginEnd="8dp"
            android:textAlignment="center"
            android:textSize="20sp"
            android:text="@{String.valueOf(txt.getData)}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    
        <Button
            android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="60dp"
            android:text="更新viewmodel数据"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/text" />
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```



### 创建数据源ViewModel

```java
public class DemoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DemoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("initial test data");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void updateData() {
        mText.setValue("update data");
    }
}
```



### 在activity中设置数据绑定视图和数据源

```java
public class DemoFragment extends Fragment {

    private DemoViewModel demoViewModel;
    private FragmentDemoBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        demoViewModel = new ViewModelProvider(this).get(DemoViewModel.class);

        binding = FragmentDemoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 方案1：通过viewmodel的观察者模式来更新视图数据
        final TextView textView = binding.text;
        demoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // 方案2：通过databinding绑定视图与数据源来更新视图数据
        binding.setData(notificationsViewModel);
        binding.setLifecycleOwner(this);
        
        Button btn = binding.button;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demoViewModel.updateData();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
```

