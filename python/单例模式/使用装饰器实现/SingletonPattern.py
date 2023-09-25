def singleton(cls):
   instances = {}

   def get_instance(*args, **kwargs):
       if cls not in instances:
           instances[cls] = cls(*args, **kwargs)
       return instances[cls]

   return get_instance


@singleton
class SingletonClass:
   def __init__(self):
       self.value = 1

   def get_value(self):
       return self.value


# 使用单例模式
instance1 = SingletonClass()
instance2 = SingletonClass()

print(instance1 is instance2)  # 输出 True，说明instance1和instance2是同一个实例
print(instance1.get_value())  # 输出 1，说明可以通过instance1访问get_value方法