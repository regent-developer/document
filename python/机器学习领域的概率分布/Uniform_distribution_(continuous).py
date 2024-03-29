# 均匀分布（连续型）
# 均匀分布是指闭区间 [a, b] 内的随机变量，且每一个变量出现的概率是相同的。
import numpy as np
from matplotlib import pyplot as plt

def uniform(x, a, b):

    y = [1 / (b - a) if a <= val and val <= b
                    else 0 for val in x]

    return x, y, np.mean(y), np.std(y)

x = np.arange(-100, 100) # define range of x
for ls in [(-50, 50), (10, 20)]:
    a, b = ls[0], ls[1]
    x, y, u, s = uniform(x, a, b)
    plt.plot(x, y, label=r'$\mu=%.2f,\ \sigma=%.2f$' % (u, s))

plt.legend()
plt.savefig('graph/uniform.png')
plt.show()