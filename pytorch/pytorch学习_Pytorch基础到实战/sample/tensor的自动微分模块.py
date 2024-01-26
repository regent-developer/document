import torch

# 单标量梯度的计算
# 定义需要求导的张量，张量的值类型必须是浮点类型
print('单标量梯度的计算')
x = torch.tensor(10, requires_grad=True, dtype=torch.float64)
print(x)

# 微分是 f'(x) = 2x
f = x ** 2 + 20

# 自动微分
f.backward()

# 打印x变量的梯度
# backward函数计算的梯度值会存储在张量的 grad 变量中
print(x.grad)
print(x)
    
# 单向量梯度的计算
print('单向量梯度的计算')
# 定义需要求导张量
x = torch.tensor([10, 20, 30, 40], requires_grad=True, dtype=torch.float64)
   
# 变量经过中间运算
# 微分是 f1'(x) = 2x
f1 = x ** 2 + 20
 
print(f1)
# 由于求导的结果必须是标量，而f的结果是: tensor([120., 420., 920., 1620.])，不能直接自动微分。需要将结果计算为标量才能进行计算
f2 = f1.mean()

# 自动微分
f2.backward()
print(f2)

# 打印 x 变量的梯度
print(x.grad)

# 多标量梯度计算
print('多标量梯度计算')
# 定义需要计算梯度的张量
x1 = torch.tensor(10, requires_grad=True, dtype=torch.float64)
x2 = torch.tensor(20, requires_grad=True, dtype=torch.float64)

# 经过中间的计算
y = x1**2 + x2**2 + x1*x2

# 将输出结果变为标量
y = y.sum()

# 自动微分
y.backward()

# 打印两个变量的梯度
print(x1.grad, x2.grad)


# 多向量梯度计算
print('多向量梯度计算')
# 定义需要计算梯度的张量
x1 = torch.tensor([10, 20], requires_grad=True, dtype=torch.float64)
x2 = torch.tensor([30, 40], requires_grad=True, dtype=torch.float64)

# 经过中间的计算
y = x1 ** 2 + x2 ** 2 + x1 * x2

# 将输出结果变为标量
y = y.sum()

# 自动微分
y.backward()

# 打印两个变量的梯度
print(x1.grad, x2.grad)