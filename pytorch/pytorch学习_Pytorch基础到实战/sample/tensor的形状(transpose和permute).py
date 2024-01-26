import torch
import numpy as np

data = torch.tensor(np.random.randint(0, 10, [3, 4, 5]))
print(data.size())
print(data)

# 使用transpose交换维度1和维度2
new_data = torch.transpose(data, 1, 2)
print(new_data.size())
print(new_data)

# 多次使用transpose将形状修改为(4,5,3)
new_data = torch.transpose(data, 0, 1)
new_data = torch.transpose(new_data, 1, 2)
print(new_data.size())

# 使用permute将形状修改为(4,5,3)
new_data = torch.permute(data, [1, 2, 0])
print(new_data.size())
print(new_data)