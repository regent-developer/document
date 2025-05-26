# QT单例模式

## 静态局部变量实现（C++11及以上推荐）

```cpp
class Singleton
{
public:
    // 获取单例实例的静态方法
    static Singleton& getInstance()
    {
        static Singleton instance;  // 线程安全的静态局部变量(C++11起)
        return instance;
    }
 
    // 示例方法
    void doSomething(const QString &message)
    {
        qDebug() << "Singleton is doing something"<<message;
    }
 
private:
    // 私有构造函数防止外部实例化
    Singleton() {}
 
    // 防止复制和赋值
    Singleton(const Singleton&) = delete;
    Singleton& operator=(const Singleton&) = delete;
};
```

```cpp
// 使用方式
Singleton::getInstance().doSomething("Message 1");
```

## 指针实现（兼容旧版C++）

```cpp
class Singleton
{
public:
    static Singleton* getInstance()
    {
        if (!instance) {
            instance = new Singleton();
        }
        return instance;
    }
 
    void doSomething()
    {
        qDebug() << "Singleton is doing something";
    }
 
private:
    Singleton() {}
    static Singleton* instance;
};


// 不是线程安全的，如果需要线程安全，需要加锁
```

```cpp
// 使用方式
// 初始化静态成员变量
Singleton* Singleton::instance = nullptr;
```

## QT特有的单例实现（Q_GLOBAL_STATIC）

```cpp
#include <QGlobalStatic>
 
Q_GLOBAL_STATIC(Singleton, singletonInstance)
 
class Singleton
{
public:
    void doSomething()
    {
        qDebug() << "Singleton is doing something";
    }
 
private:
    Singleton() {}
    friend class QGlobalStatic<Singleton>;
};

```

```cpp
// 使用方式
singletonInstance()->doSomething();
```
