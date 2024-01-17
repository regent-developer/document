import torch

# 使用cuda方法
print('使用cuda方法')
data = torch.tensor([10, 20 ,30])
print('存储设备:', data.device)

# 如果安装的不是GPU版的pytorch或者没有NVIDIA显卡的话，会报错
data = data.cuda()
print('存储设备:', data.device)

data = data.cpu()
print('存储设备:', data.device)
    
# 直接在GPU上创建张量
print('直接在GPU上创建张量')
data = torch.tensor([10, 20, 30], device='cuda:0')
print('存储设备:', data.device)

## 使用cpu函数将张量移动到 cpu 上
data = data.cpu()
print('存储设备:', data.device)
    
# 使用to方法指定设备
print('使用to方法指定设备')
data = torch.tensor([10, 20, 30])
print('存储设备:', data.device)

data = data.to('cuda:0')
print('存储设备:', data.device)

# 存储在不同设备的张量不能运算

data1 = torch.tensor([10, 20, 30], device='cuda:0')
data2 = torch.tensor([10, 20, 30])
print(data1.device, data2.device)

data = data1 + data2
print(data)