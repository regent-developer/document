# tensor的主要函数

|函数名|功能|使用要点或适用的情况|
|---|---|---|
| torch.tensor     | 直接创建Tensor      |  一般用于简单Tensor的创建|     
| torch.from_numpy |     从NumPy中创建   |  基于已有的NumPy数据进行Tensor创建|   
|  torch.zeros     |  创建零矩阵Tensor   |  特殊形状或者数值分布的Tensor的创建 |                       
|  torch.ones      | 创建全一矩阵Tensor  |   特殊形状或者数值分布的Tensor的创建 |                      
|  torch.rand      | 创建随机分布Tensor  | 特殊形状或者数值分布的Tensor的创建  | 
|  torch.randn     | 创建随机分布Tensor  |  特殊形状或者数值分布的Tensor的创建  |                     
|  torch.normal    | 创建随机分布Tensor  |  特殊形状或者数值分布的Tensor的创建  |                  
|  torch.randint   | 创建随机分布Tensor  |  特殊形状或者数值分布的Tensor的创建  |                  
|  tensor.shape    |   获取Tensor形状   |      注意区分函数和属性的区别      | 
|  tensor.size     |    获取Tensor形状  |      注意区分函数和属性的区别     |  
|  tensor.permute  |    Tensor转秩      |  transpose每次只能转换两个维度   |
|  tensor.transpose|     Tensor维度交换  |  transpose每次只能转换两个维度     |                      
|  tensor.view     |   Tensor变形        |   view不能处理内存不连续Tensor的结构 | 
|  tensor.reshape  |     Tensor变形       | reshape可以处理内存不连续Tensor的结构|
|  tensor.squeeze  |    Tensor删除维度     |          ---           |
|  tensor.unsqueeze|     Tensor增加维度    |           ---           |
|  torch.cat       |  Tensor拼接           |    不增加维度          |
|  torch.stack     |   Tensor堆叠          |     增加维度           |
|  torch.chunk     | Tensor尽可能平均切分   |          ---           |
|  torch.split     | Tensor按固定大小切分   |          ---           |
|  torch.unbind    |   Tensor按维度切分     |         ---           |
|  torch.index_select|     Tensor选择       |   基于给定的索引来进行数据提取  |    
|  torch.masked_select|    Tensor选择               |  通过一些判断条件来进行选择|