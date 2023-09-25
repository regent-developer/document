class SingletonMeta(type):
 _instances = {}

 def __call__(cls, *args, **kwargs):
     if cls not in cls._instances:
         cls._instances[cls] = super().__call__(*args, **kwargs)
     return cls._instances[cls]


class SingletonClass(metaclass=SingletonMeta):
 def __init__(self):
     self.value = 1

 def get_value(self):
     return self.value


# 使用单例模式
instance1 = SingletonClass()
instance2 = SingletonClass()

print(instance1 is instance2)  # 输出 True，说明instance1和instance2是同一个实例
print(instance1.get_value())  # 输出 1，说明可以通过instance1访问get_value方法