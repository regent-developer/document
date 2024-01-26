import torch

data = torch.randint(0, 10, [4, 5, 2])
print(data)

print(data[0, :, :])

print(data[:, 0, :])

print(data[:, :, 0])