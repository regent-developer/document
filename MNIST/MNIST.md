# MNIST

MNIST 数据集来自美国国家标准与技术研究所，是NIST（National Institute of Standards and Technology）的缩小版，训练集（training set）由来自 250 个不同人手写的数字构成，其中 50% 是高中学生，50% 来自人口普查局（the Census Bureau）的工作人员，测试集（test set）也是同样比例的手写数字数据。  



## 获取MNIST

MNIST 数据集可在http://yann.lecun.com/exdb/mnist/获取，图片是以字节的形式进行存储，它包含了四个部分：

* Training set images: train-images-idx3-ubyte.gz (9.9 MB, 解压后 47 MB, 包含 60,000 个样本)
* Training set labels: train-labels-idx1-ubyte.gz (29 KB, 解压后 60 KB, 包含 60,000 个标签)
* Test set images: t10k-images-idx3-ubyte.gz (1.6 MB, 解压后 7.8 MB, 包含 10,000 个样本)
* Test set labels: t10k-labels-idx1-ubyte.gz (5KB, 解压后 10 KB, 包含 10,000 个标签)

此数据集中，训练样本：共60000个，其中55000个用于训练，另外5000个用于验证。测试样本：共10000个，验证数据比例相同。



数据集中像素值：

a）使用python读取二进制文件方法读取mnist数据集，则读进来的图像像素值为0-255之间；标签是0-9的数值。

b）采用TensorFlow的封装的函数读取mnist，则读进来的图像像素值为0-1之间；标签是0-1值组成的大小为1*10的行向量。

