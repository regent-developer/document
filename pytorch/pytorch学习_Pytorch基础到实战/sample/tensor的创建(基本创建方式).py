import torch
import numpy as np
import random


# 1. 根据已有数据创建张量
def test01():
    print('根据已有数据创建张量')
    # 1. 创建张量标量
    data = torch.tensor(10)
    print(data)
    
    # 2. numpy 数组
    data = np.random.randn(2, 3)
    data = torch.tensor(data)
    print(data)

    # 3. 列表（默认：float32）
    data = [[10., 20., 30.], [40., 50., 60.]]
    data = torch.tensor(data)
    print(data)


# 2. 创建指定形状的张量
def test02():
    print('创建指定形状的张量')
    # 1. 创建2行3列的张量（默认：float32）
    data = torch.Tensor(2, 3)
    print(data)

    # 2. 列表
    data = torch.Tensor([10])
    print(data)

    data = torch.Tensor([10, 20])
    print(data) 


# 3. 使用具体类型的张量
def test03():
    print('创建具体类型的张量')
    # 1. 创建2行3列，dtype 为 int32 的张量
    data = torch.IntTensor(2, 3)
    print(data)

    # 2. 注意: 类型不正确则进行转换
    data = torch.IntTensor([2.5, 3.3])
    print(data)

    # 3. 其他的类型
    data = torch.ShortTensor()  # int16
    print(data)

    data = torch.LongTensor()   # int64
    print(data)

    data = torch.FloatTensor()  # float32
    print(data)

    data = torch.DoubleTensor() # float64
    print(data)

if __name__ == '__main__':
    test01()
    test02()
    test03()