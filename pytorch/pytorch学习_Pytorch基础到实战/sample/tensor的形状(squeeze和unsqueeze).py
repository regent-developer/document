import torch
import numpy as np

data = torch.tensor(np.random.randint(0, 10, [1, 3, 1, 5]))
print(data.shape)
print(data)

# 去掉值为1的维度
new_data = data.squeeze()
print(new_data.shape)

# 去掉指定位置为1的维度
new_data = data.squeeze(2)
print(new_data.shape)

# 在2维度增加一个维度
new_data = data.unsqueeze(-1)
print(new_data.shape)
print(new_data)