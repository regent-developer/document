import torch

data = torch.randint(0, 10, [4, 5,3])
print(data)

# 第一个维度的前两个，第二个维度的前两个
print(data[:2,:2])