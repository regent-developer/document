import torch

# 控制不计算梯度
x = torch.tensor(10, requires_grad=True, dtype=torch.float64)
print(x.requires_grad)

# 对代码进行装饰
with torch.no_grad():
    y = x ** 2
print(y.requires_grad)

# 对函数进行装饰
@torch.no_grad()
def my_func(x):
    return x ** 2
print(my_func(x).requires_grad)

# set_grad_enabled设置
torch.set_grad_enabled(False)
y = x ** 2
print(y.requires_grad)


# 累计梯度
# 定义需要求导张量
torch.set_grad_enabled(True)
x = torch.tensor([10, 20, 30, 40], requires_grad=True, dtype=torch.float64)

for _ in range(3):
    f1 = x ** 2 + 20
    f2 = f1.sum()

    # 默认张量的 grad 属性会累计历史梯度值，需要每次手动清理上次的梯度
    if x.grad is not None:
        x.grad.data.zero_()

    f2.backward()
    print(x.grad)

# 梯度下降优化最优解
x = torch.tensor(10, requires_grad=True, dtype=torch.float64)

for _ in range(5000):
    # 正向计算
    f = x ** 2
    
    # 梯度清零
    if x.grad is not None:
        x.grad.data.zero_()
        
    # 反向传播计算梯度
    f.backward()
    
    # 更新参数
    x.data = x.data - 0.001 * x.grad
    print('%.10f' % x.data)