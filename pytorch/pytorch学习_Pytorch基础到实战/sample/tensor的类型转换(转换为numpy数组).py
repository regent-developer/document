import torch

# 使用张量对象中numpy函数进行转换
data_tensor = torch.tensor([2, 3, 4])
data_numpy = data_tensor.numpy()
print('使用张量对象中numpy函数进行转换')
print(data_tensor)
print(data_numpy)

# 修改转换后数组，原数据改变
data_numpy[0] = 100
print(data_tensor)
print(data_numpy)

# 使用copy函数进行转换拷贝
data_copy = torch.tensor([2, 3, 4])
data_copy.copy_(data_tensor)

data_numpy  = data_copy.numpy()

print('使用copy函数进行转换拷贝')
print(data_tensor)
print(data_numpy)

# 修改转换后数组，原数据不变
data_numpy[0] = 1
print(data_tensor)
print(data_numpy)