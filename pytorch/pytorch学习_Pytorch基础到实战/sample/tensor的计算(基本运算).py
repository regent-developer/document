import torch
import numpy as np

print('随机创建0~10的2行3列的随机张量：')
data = torch.randint(0, 10, [2, 3])
print(data)

# 不修改原数据
new_data = data.add(10) 
print('不修改原数据：')
print('新数据：')
print(new_data)
print('原数据：')
print(data)

# 修改原数据
data.add_(10) 
print('修改原数据：')
print(data)