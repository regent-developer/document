import torch

# 创建指定形状全0张量
data = torch.zeros(2, 3)
print(data)

# 根据张量形状创建全0张量
data = torch.zeros_like(data)
print(data)

# 创建指定形状全0张量
data = torch.ones(2, 3)
print(data)

# 根据张量形状创建全1张量
data = torch.ones_like(data)
print(data)

# 创建指定形状指定值的张量
data = torch.full([2, 3], 10)
print(data)

# 根据张量形状创建指定值的张量
data = torch.full_like(data, 20)
print(data)
