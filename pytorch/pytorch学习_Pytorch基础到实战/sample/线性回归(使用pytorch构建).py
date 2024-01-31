import torch
from torch.utils.data import TensorDataset
from torch.utils.data import DataLoader
import torch.nn as nn
import torch.optim as optim
from sklearn.datasets import make_regression
import matplotlib.pyplot as plt


# 构建数据集
def create_dataset():

    x, y, coef = make_regression(n_samples=100,
                                 n_features=1,
                                 noise=10,
                                 coef=True,
                                 bias=14.5,
                                 random_state=0)

    # 将构建数据转换为张量类型
    x = torch.tensor(x)
    y = torch.tensor(y)

    return x, y, coef


def train():

    # 构建数据集
    x, y, coef = create_dataset()
    # 构建数据集对象
    dataset = TensorDataset(x, y)
    # 构建数据加载器
    dataloader = DataLoader(dataset, batch_size=16, shuffle=True)
    # 构建模型
    model = nn.Linear(in_features=1, out_features=1)
    # 构建损失函数
    criterion = nn.MSELoss()
    # 优化方法
    optimizer = optim.SGD(model.parameters(), lr=1e-2)
    # 初始化训练参数
    epochs = 100

    for _ in range(epochs):

        for train_x, train_y in dataloader:

            # 将一个batch的训练数据送入模型
            y_pred = model(train_x.type(torch.float32))
            # 计算损失值
            loss = criterion(y_pred, train_y.reshape(-1, 1).type(torch.float32))
            # 梯度清零
            optimizer.zero_grad()
            # 自动微分(反向传播)
            loss.backward()
            # 更新参数
            optimizer.step()


    # 绘制拟合直线
    plt.scatter(x, y)
    x = torch.linspace(x.min(), x.max(), 1000)
    y1 = torch.tensor([v * model.weight + model.bias for v in x])
    y2 = torch.tensor([v * coef + 14.5 for v in x])

    plt.plot(x, y1, label='训练')
    plt.plot(x, y1, label='真实')
    plt.grid()
    plt.legend()
    plt.show()


if __name__ == '__main__':
    train()