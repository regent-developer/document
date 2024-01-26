import torch
import numpy as np

data = torch.tensor([[10, 20, 30], [40, 50, 60]])
print(data.size())

# 使用view函数修改形状
new_data = data.view(3, 2)
print(new_data.shape)

# 判断张量是否使用整块内存
print(new_data.is_contiguous())  # True

# 使用transpose函数修改形状
new_data = torch.transpose(data, 0, 1)
print(new_data.is_contiguous())  # False

# 先使用contiguous函数转换为整块内存的张量，再使用 view 函数
new_data = new_data.contiguous().view(2, 3)
print(new_data.is_contiguous())  # True