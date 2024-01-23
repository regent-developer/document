import torch

data1 = torch.randint(0, 10, [3, 5, 4])
data2 = torch.randint(0, 10, [3, 5, 4])

# 按0维度拼接
new_data = torch.cat([data1, data2], dim=0)
print(new_data.shape)

# 按1维度拼接
new_data = torch.cat([data1, data2], dim=1)
print(new_data.shape)

# 按2维度拼接
new_data = torch.cat([data1, data2], dim=2)
print(new_data.shape)  