# Anaconda

## 安装（Windows）

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
```



## 切换环境

