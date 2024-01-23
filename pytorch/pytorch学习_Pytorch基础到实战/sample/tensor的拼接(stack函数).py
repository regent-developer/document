import torch

data1= torch.randint(0, 10, [2, 3])
data2= torch.randint(0, 10, [2, 3])
print(data1)
print(data2)

# 按0维度叠加
new_data = torch.stack([data1, data2], dim=0)
print(new_data)
print(new_data.shape)

# 按1维度叠加
new_data = torch.stack([data1, data2], dim=1)
print(new_data)
print(new_data.shape)

# 按2维度叠加
new_data = torch.stack([data1, data2], dim=2)
print(new_data)
print(new_data.shape)