# Anaconda

## 安装（Windows）

### 查看当前驱动版本以及支持的cuda版本

```shell
nvidia-smi
```


### 安装CUDA Toolkit（根据nvidia-smi查出的cuda版本）

https://developer.nvidia.com/cuda-toolkit-archive


### 看当前安装的cuda的版本

```shell
nvcc -V 
```

### 下载url

https://www.anaconda.com/products/individual

注：64位



## 验证conda是否被安装

```shell
conda -V

conda --version
```



## 更新conda&Anaconda

```shell
conda update conda

conda update anaconda 
```



## 创建新环境

```shell
conda create -name <env_name> <package_names>

# conda create -name pytorch python=3.6
```

* 默认情况下，新创建的环境将会被保存在/Users/<user_name>/anaconda3/env目录下，其中，`<user_name>`为当前用户的用户名。

```shell
# 安装虚拟环境到指定路径的命令
conda create --prefix=D:\python36\py36 python=3.6

# 激活指定路径下的虚拟环境的命令
conda activate D:\python36\py36


# win10下激活环境
activate xxx
```


## 安装库


```shell
conda install xxx_lib

```

## 运行Python文件

```shell
# 激活环境
activate xxx

# 执行python文件
pyth


## 切换环境

```shell
activate <env_name>
```



## 退出环境至root

```shell
deactivate
```



## 显示已创建环境

```shell
conda info -e

conda env list
```



## 复制环境

```shell
conda create –name <new_env_name> –clone <copied_env_name>


# conda create –name py2 –clone python2
```



## 删除环境

```shell
conda remove –-name <env_name> -–all

# conda remove –-name python3 --all
```



## 分享环境

```shell
conda env export > environment.yaml
```



## 导入环境

```shell
conda env create -f environment.yaml
```


### 清理
```shell
conda clean -p
conda clean -t
```

## 自动开启/关闭环境

```shell
conda activate   #默认激活base环境
conda activate xxx  #激活xxx环境
conda deactivate #关闭当前环境
conda config --set auto_activate_base false  #关闭自动激活状态
conda config --set auto_activate_base true  #关闭自动激活状态
```

