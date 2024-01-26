import torch
data = torch.randint(0, 10, [2, 3], dtype=torch.float64)
print(data)

# 平均值
print(data.mean())

# 和
print(data.sum())

# 平方
print(data.pow(2))

# 平方根
print(data.sqrt())

# 指数
print(data.exp()) # e^n 次方

# 对数
print(data.log()) # 以e为底