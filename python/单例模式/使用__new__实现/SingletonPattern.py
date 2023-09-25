class SingletonClass:
  _instance = None

  def __new__(cls, *args, **kwargs):
      if cls._instance is None:
          cls._instance = super().__new__(cls, *args, **kwargs)
      return cls._instance

  def __init__(self):
      self.value = 1

  def get_value(self):
      return self.value


# 使用单例模式
instance1 = SingletonClass()
instance2 = SingletonClass()

print(instance1 is instance2)  # 输出 True，说明instance1和instance2是同一个实例
print(instance1.get_value())  # 输出1，说明可以通过instance1访问get_value方法