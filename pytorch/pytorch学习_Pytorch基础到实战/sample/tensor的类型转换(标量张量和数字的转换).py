import torch

# 当张量只包含一个元素时, 可以通过 item 函数提取出该值
data = torch.tensor([10,])
print(data.item())

data = torch.tensor(10)
print(data.item())