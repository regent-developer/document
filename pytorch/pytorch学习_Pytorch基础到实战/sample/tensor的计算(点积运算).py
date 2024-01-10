import torch

data1 = torch.tensor([[1, 2], [2, 3], [3, 4]])
data2 = torch.tensor([[1, 2], [2, 3]])
print(data1)
#print(data1.T) # 矩阵转置
print(data2)

data = data1 @ data2
print(data)
print(data.shape)

data = torch.mm(data1, data2)
print(data)
print(data.shape)

data = torch.matmul(data1, data2)
print(data)
print(data.shape)

data = torch.matmul(torch.randn(3, 4, 5), torch.randn(5, 4))
print(data)
print(data.shape)

data = torch.matmul(torch.randn(5, 4), torch.randn(3, 4, 5))
print(data)
print(data.shape)



data1 = torch.randn(3, 4, 5)
data2 = torch.randn(3, 5, 8)

data = torch.bmm(data1, data2)
print(data)
print(data.shape)