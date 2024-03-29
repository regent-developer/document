# Keras



Keras是一个由[Python](https://baike.baidu.com/item/Python/407313?fromModule=lemma_inlink)编写的开源[人工神经网络](https://baike.baidu.com/item/人工神经网络/382460?fromModule=lemma_inlink)库，可以作为[Tensorflow](https://baike.baidu.com/item/Tensorflow/18828108?fromModule=lemma_inlink)、Microsoft-CNTK和Theano的高阶[应用程序接口](https://baike.baidu.com/item/应用程序接口/10418844?fromModule=lemma_inlink)，进行[深度学习](https://baike.baidu.com/item/深度学习/3729729?fromModule=lemma_inlink)模型的设计、调试、评估、应用和可视化。  



Keras 是一个模型级（ model-level）的Python深度学习框架，keras可以方便地定义和训练几乎所有类型的深度学习模型，具有以下重要特性：  

* 相同的代码可以在**CPU或GPU**上无缝切换运行
* 具有用户友好的**API**，便于快速开发深度学习模型的原型

- 内置支持卷积网络（用于计算机视觉）、循环网络（用于序列处理）以及二者的任意
  组合
- 支持任意网络架构：多输入或多输出模型、层共享、模型共享等。这也就是说， Keras
  能够构建任意深度学习模型，无论是生成式对抗网络还是神经图灵机

## Keras和Tensorflow的关系

* keras提供了一个高层次的构建模块
* Keras 有三个后端实现方式： TensorFlow、Theano、微软认知工具包（ CNTK， Microsoft cognitive toolkit）
* NVIDIA CUDA 深度神经网络库（ cuDNN），是 在 GPU 上，TensorFlow封装的一个高度优化的深度学习运算库
* Eigen，是TensorFlow封装的一个低层次的张量运算库



## Keras的开发步骤

* 定义训练数据：输入张量和目标张量
* 定义层组成的网络（或模型），将输入映射到目标
* 配置学习过程：选择优化器、损失函数、监控指标
* 调用模型的 fit 方法在训练数据上进行迭代