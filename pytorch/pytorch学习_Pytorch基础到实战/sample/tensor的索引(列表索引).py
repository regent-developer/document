import torch

data = torch.randint(0, 10, [4, 5, 3])
print(data)

print(data[[0,1], [1,1], [1,1]])

print(data[[[0], [1]], [1,2]])