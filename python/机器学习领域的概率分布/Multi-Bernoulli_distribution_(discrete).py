# Multi-Bernoulli 分布（离散型）
# Multi-Bernoulli 分布又称为范畴分布（Categorical distribution），它的类别超过 2，交叉熵的形式与该分布的负对数形式是一致的。
import random
import numpy as np
from matplotlib import pyplot as plt

def categorical(p, k):
    return p[k]

n_experiment = 100
p = [0.2, 0.1, 0.7]
x = np.arange(n_experiment)
y = []
for _ in range(n_experiment):
    pick = categorical(p, k=random.randint(0, len(p) - 1))
    y.append(pick)

u, s = np.mean(y), np.std(y)
plt.scatter(x, y, label=r'$\mu=%.2f,\ \sigma=%.2f$' % (u, s))
plt.legend()
plt.savefig('graph/categorical.png')
plt.show()