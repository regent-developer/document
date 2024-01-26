import torch
import numpy as np

data = torch.tensor([[10, 20, 30], [40, 50, 60]])
print(data)
print(data.shape)
print(data.size())

print(data.data.reshape(1, 6))
print(data.data.reshape(1, 6).shape)