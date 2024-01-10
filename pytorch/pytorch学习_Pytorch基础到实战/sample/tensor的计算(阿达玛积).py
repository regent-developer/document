import torch

data1 = torch.tensor([[1, 2], [3, 4]])
data2 = torch.tensor([[5, 6], [7, 8]])
print(data1)
print(data2)

data = torch.mul(data1, data2)
print(data)

data = data1.mul(data2)
print(data)

data = data1 * data2
print(data)