import torch
from sklearn.datasets import make_regression
import matplotlib.pyplot as plt
from sklearn.manifold import TSNE
import random

plt.rcParams['font.sans-serif']=['SimHei'] #用来正常显示中文标签
plt.rcParams['axes.unicode_minus']=False #用来正常显示负号

# 数据集函数
def create_dataset():

    x, y, coef = make_regression(n_samples=100,
                                 n_features=1,
                                 noise=10,
                                 coef=True,
                                 bias=14.5,
                                 random_state=0)

    # 转换为张量
    x = torch.tensor(x)
    y = torch.tensor(y)

    return x, y, coef


# 构建数据加载器
def data_loader(x, y, batch_size):

    data_len = len(y)
    data_index = list(range(data_len))
    random.shuffle(data_index)
    batch_number = data_len // batch_size

    for idx in range(batch_number):

        start = idx * batch_size
        end = start + batch_size

        batch_train_x = x[start: end]
        batch_train_y = y[start: end]

        yield batch_train_x, batch_train_y

# 模型参数
w = torch.tensor(0.1, requires_grad=True, dtype=torch.float64)
b = torch.tensor(0.0, requires_grad=True, dtype=torch.float64)

# 假设函数
def linear_regression(x):
    return w * x + b

# 损失函数
def square_loss(y_pred, y_true):
    return (y_pred - y_true) ** 2

# 优化方法
def sgd(lr=0.01):
    # 使用批量样本的平均梯度
    w.data = w.data - lr * w.grad.data / 16
    b.data = b.data - lr * b.grad.data / 16

# 训练函数
def train():

    # 加载数据集
    x, y, coef = create_dataset()
    # 定义训练参数
    epochs = 100
    learning_rate = 0.01

    # 存储损失
    epoch_loss = []
    total_loss = 0.0
    train_sample = 0

    for _ in range(epochs):

        for train_x, train_y in data_loader(x, y, 16):

            # 训练数据送入模型
            y_pred = linear_regression(train_x)

            # 计算损失值
            loss = square_loss(y_pred, train_y.reshape(-1, 1)).sum()
            total_loss += loss.item()
            train_sample += len(train_y)

            # 梯度清零
            if w.grad is not None:
                w.grad.data.zero_()

            if b.grad is not None:
                b.grad.data.zero_()

            # 反向传播
            loss.backward()

            # 更新参数
            sgd(learning_rate)

            print('loss: %.10f' % (total_loss / train_sample))

        epoch_loss.append(total_loss / train_sample)


    # 绘制拟合直线
    print(coef, w.data.item())
    plt.scatter(x, y)

    x = torch.linspace(x.min(), x.max(), 1000)
    y1 = torch.tensor([v * w + 14.5 for v in x])
    y2 = torch.tensor([v * coef + 14.5 for v in x])

    plt.plot(x, y1, label='训练')
    plt.plot(x, y2, label='真实')
    plt.grid()
    plt.legend()
    plt.show()

    # 打印损失变化曲线
    plt.plot(range(epochs), epoch_loss)
    plt.title('损失变化曲线')
    plt.grid()
    plt.show()


train()