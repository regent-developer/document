# 伯努利分布（离散型）
# 伯努利分布并不考虑先验概率 P(X)，它是单个二值随机变量的分布。它由单个参数φ∈ [0, 1] 控制，φ 给出了随机变量等于 1 的概率。我们使用二元交叉熵函数实现二元分类，它的形式与对伯努利分布取负对数是一致的。
import random
import numpy as np
from matplotlib import pyplot as plt

def bernoulli(p, k):
    return p if k else 1 - p

n_experiment = 100
p = 0.6
x = np.arange(n_experiment)
y = []
for _ in range(n_experiment):
    pick = bernoulli(p, k=bool(random.getrandbits(1)))
    y.append(pick)

u, s = np.mean(y), np.std(y)
plt.scatter(x, y, label=r'$\mu=%.2f,\ \sigma=%.2f$' % (u, s))
plt.legend()
plt.savefig('graph/bernoulli.png')
plt.show()