import torch

# 在指定区间按照步长生成元素 [start, end, step)
data = torch.arange(0, 10, 2)
print(data)

# 在指定区间按照元素个数生成
data = torch.linspace(0, 11, 10)
print(data)

# 创建随机张量
data = torch.randn(2, 3)  # 创建2行3列张量
print(data)

# 随机数种子设置
print('随机数种子:', torch.random.initial_seed())
torch.random.manual_seed(100) # 设置随机种子
print('随机数种子:', torch.random.initial_seed())