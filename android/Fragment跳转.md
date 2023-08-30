# Fragment的跳转



## 从同一个Activiy的一个Fragment跳转到另外一个Fragment 

* 跳转

  ```java
  getParentFragmentManager()
        .beginTransaction()
        .replace(R.id.fragment_activity_main, new DestFragment(), null)
        .addToBackStack(null)
        .commit();
  ```

  

* 返回

  ```java
  getActivity().getSupportFragmentManager().popBackStack();
  ```

  

## 从一个Activity的Fragment跳转到另外一个Activity  

```java
Intent intent = new Intent(getActivity(), DestActivity.class);
startActivity(intent);
```



## 从一个Activity跳转到另外一个Activity的Fragment上

* 原activity

  

```java
Intent intent = new Intent(SrcActivity.this, DestActivity.class);
intent.putExtra("id",1);
startActivity(intent);
```

* 目标activity

  ```java
  int id = getIntent().getIntExtra("id", 0);
  if (id == 1) {      
       getSupportFragmentManager()
         .beginTransaction()
         .replace(R.id.fragment_container,new DestFragment())
         .addToBackStack(null)
         .commit(); 
  }
  ```

  

## 从一个Activity的Fragment跳转到另外一个Activity的Fragment上

* 原activity

```java
Intent intent = new Intent(getActivity(), DestActivity.class);
intent.putExtra("id",1);
startActivity(intent);
```

* 目标activity

  ```java
  int id = getIntent().getIntExtra("id", 0);
  if (id == 1) {      
       getSupportFragmentManager()
         .beginTransaction()
         .replace(R.id.fragment_container,new DestFragment())
         .addToBackStack(null)
         .commit(); 
  }
  ```

  