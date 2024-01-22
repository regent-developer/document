import torch
import numpy as np

# 使用 from_numpy 函数
data_numpy = np.array([2, 3, 4])

# 浅拷贝
data_tensor = torch.from_numpy(data_numpy)
print(data_tensor)

# 深拷贝
data_tensor = torch.from_numpy(data_numpy)
data_copy = torch.tensor([2, 3, 4])
data_copy.copy_(data_tensor)
print(data_tensor)

# 使用 torch.tensor 函数
data_tensor = torch.tensor(data_numpy)
print(data_tensor)