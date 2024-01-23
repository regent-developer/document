import torch

data = torch.randint(0, 10, [4, 5])
print(data)

# 简单行索引
print(data[0])

# 简单列索引
print(data[:, 0])