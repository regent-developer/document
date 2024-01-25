import torch

data = torch.randint(0, 10, [4, 5])
print(data)

# 第三列大于五的行数据
print(data[data[:, 2] > 5])