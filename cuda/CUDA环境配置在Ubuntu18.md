# CUDA环境配置在Ubuntu18

## 1. 安装NVIDIA驱动

```shell
# step1：检测驱动版本
nvidia-detector

# 安装对应版本驱动

# 查看安装后的驱动
nvidia-smi

reboot
```

## 2. 安装CUDA

* cuda对应平台各个版本下载地址：  
https://developer.nvidia.com/cuda-toolkit-archive

### NVIDIA GPU driver与CUDA 版本对应

| CUDA Toolkit     | Toolkit Driver Version     |  |
| -------- | -------- | -------- |
| - | Linux x86_64 Driver Version | Windows x86_64 Driver Version |
| CUDA 12.4 GA | >=550.54.14 | >=551.61 | 
| CUDA 12.3 Update 1| >=545.23.08| >=546.12 | 
| CUDA 12.3 GA| >=545.23.06| >=545.84 | 
| CUDA 12.2 Update 2| >=535.104.05| >=537.13 | 
| CUDA 12.2 Update 1| >=535.86.09| >=536.67 | 
| CUDA 12.2 GA| >=535.54.03| >=536.25 | 
| CUDA 12.1 Update 1| >=530.30.02| >=531.14 | 
| CUDA 12.1 GA| >=530.30.02| >=531.14 | 
| CUDA 12.0 Update 1| >=525.85.12| >=528.33 | 
| CUDA 12.0 GA| >=525.60.13| >=527.41 | 
| CUDA 11.8 GA| >=520.61.05| >=520.06 | 
| CUDA 11.7 Update 1| >=515.48.07| >=516.31 | 
| CUDA 11.7 GA| >=515.43.04| >=516.01 | 
| CUDA 11.6 Update 2| >=510.47.03| >=511.65 | 
| CUDA 11.6 Update 1| >=510.47.03| >=511.65 | 
| CUDA 11.6 GA| >=510.39.01| >=511.23 | 
| CUDA 11.5 Update 2| >=495.29.05| >=496.13 | 
| CUDA 11.5 Update 1| >=495.29.05| >=496.13 | 
| CUDA 11.5 GA| >=495.29.05| >=496.04 | 
| CUDA 11.4 Update 4| >=470.82.01| >=472.50 | 
| CUDA 11.4 Update 3| >=470.82.01| >=472.50 | 
| CUDA 11.4 Update 2| >=470.57.02| >=471.41 | 
| CUDA 11.4 Update 1| >=470.57.02| >=471.41 | 
| CUDA 11.4.0 GA| >=470.42.01| >=471.11 | 
| CUDA 11.3.1 Update 1| >=465.19.01| >=465.89 | 
| CUDA 11.3.0 GA| >=465.19.01| >=465.89 | 
| CUDA 11.2.2 Update 2| >=460.32.03| >=461.33 | 
| CUDA 11.2.1 Update 1| >=460.32.03| >=461.09 | 
| CUDA 11.2.0 GA| >=460.27.03| >=460.82 | 
| CUDA 11.1.1 Update 1| >=455.32| >=456.81 | 
| CUDA 11.1 GA| >=455.23| >=456.38 | 
| CUDA 11.0.3 Update 1| >= 450.51.06| >= 451.82 | 
| CUDA 11.0.2 GA| >= 450.51.05| >= 451.48 | 
| CUDA 11.0.1 RC| >= 450.36.06| >= 451.22 | 
| CUDA 10.2.89| >= 440.33| >= 441.22 | 
| CUDA 10.1 (10.1.105 general release, and updates)| >= 418.39| >= 418.96 | 
| CUDA 10.0.130| >= 410.48| >= 411.31 | 
| CUDA 9.2 (9.2.148 Update 1)| >= 396.37| >= 398.26 | 
| CUDA 9.2 (9.2.88)| >= 396.26| >= 397.44 | 
| CUDA 9.1 (9.1.85)| >= 390.46| >= 391.29 | 
| CUDA 9.0 (9.0.76)| >= 384.81| >= 385.54 | 
| CUDA 8.0 (8.0.61 GA2)| >= 375.26| >= 376.51 | 
| CUDA 8.0 (8.0.44)| >= 367.48| >= 369.30 | 
| CUDA 7.5 (7.5.16)| >= 352.31| >= 353.66 | 
| CUDA 7.0 (7.0.28)| >= 346.46| >= 347.62 | 

### CUAD 安装配置验证
```shell
wget https://developer.download.nvidia.com/compute/cuda/12.1.1/local_installers/cuda_12.1.1_530.30.02_linux.run

sudo sh cuda_12.1.1_530.30.02_linux.run
```

* 增加环境变量
```shell
# 修改环境变量
vi ~/.bashrc
 
# cuda path
# ln -s /usr/local/cuda-12.1 /usr/local/cuda #建立软链接
export CUDA_HOME=/usr/local/cuda
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/cuda/lib64
export PATH=$PATH:/usr/local/cuda/bin

# 配置环境变量生效
source ~/.bashrc
```

* 验证CUDA安装成功
```shell
nvcc -V
```

## 3. 安装cuDNN

* cudnn下载路径:  
https://developer.nvidia.com/rdp/cudnn-archive

* CUDA 与cuDNN 版本对应
    Download cuDNN v8.9.7 (December 5th, 2023), for CUDA 12.x

    Download cuDNN v8.9.7 (December 5th, 2023), for CUDA 11.x

    Download cuDNN v8.9.6 (November 1st, 2023), for CUDA 12.x

    Download cuDNN v8.9.6 (November 1st, 2023), for CUDA 11.x

    Download cuDNN v8.9.5 (October 27th, 2023), for CUDA 12.x

    Download cuDNN v8.9.5 (October 27th, 2023), for CUDA 11.x

    Download cuDNN v8.9.4 (August 8th, 2023), for CUDA 12.x

    Download cuDNN v8.9.4 (August 8th, 2023), for CUDA 11.x

    Download cuDNN v8.9.3 (July 11th, 2023), for CUDA 12.x

    Download cuDNN v8.9.3 (July 11th, 2023), for CUDA 11.x

    Download cuDNN v8.9.2 (June 1st, 2023), for CUDA 12.x

    Download cuDNN v8.9.2 (June 1st, 2023), for CUDA 11.x

    Download cuDNN v8.9.1 (May 5th, 2023), for CUDA 12.x

    Download cuDNN v8.9.1 (May 5th, 2023), for CUDA 11.x

    Download cuDNN v8.9.0 (April 11th, 2023), for CUDA 12.x

    Download cuDNN v8.9.0 (April 11th, 2023), for CUDA 11.x

    Download cuDNN v8.8.1 (March 8th, 2023), for CUDA 12.x

    Download cuDNN v8.8.1 (March 8th, 2023), for CUDA 11.x

    Download cuDNN v8.8.0 (February 7th, 2023), for CUDA 12.0

    Download cuDNN v8.8.0 (February 7th, 2023), for CUDA 11.x

    Download cuDNN v8.7.0 (November 28th, 2022), for CUDA 11.x

    Download cuDNN v8.7.0 (November 28th, 2022), for CUDA 10.2

    Download cuDNN v8.6.0 (October 3rd, 2022), for CUDA 11.x

    Download cuDNN v8.6.0 (October 3rd, 2022), for CUDA 10.2

    Download cuDNN v8.5.0 (August 8th, 2022), for CUDA 11.x

    Download cuDNN v8.5.0 (August 8th, 2022), for CUDA 10.2

    Download cuDNN v8.4.1 (May 27th, 2022), for CUDA 11.x

    Download cuDNN v8.4.1 (May 27th, 2022), for CUDA 10.2

    Download cuDNN v8.4.0 (April 1st, 2022), for CUDA 11.x

    Download cuDNN v8.4.0 (April 1st, 2022), for CUDA 10.2

    Download cuDNN v8.3.3 (March 18th, 2022), for CUDA 11.5

    Download cuDNN v8.3.3 (March 18th, 2022), for CUDA 10.2

    Download cuDNN v8.3.2 (January 10th, 2022), for CUDA 11.5

    Download cuDNN v8.3.2 (January 10th, 2022), for CUDA 10.2

    Download cuDNN v8.3.1 (November 22nd, 2021), for CUDA 11.5

    Download cuDNN v8.3.1 (November 22nd, 2021), for CUDA 10.2

    Download cuDNN v8.3.0 (November 3rd, 2021), for CUDA 11.5

    Download cuDNN v8.3.0 (November 3rd, 2021), for CUDA 10.2

    Download cuDNN v8.2.4 (September 2nd, 2021), for CUDA 11.4

    Download cuDNN v8.2.4 (September 2nd, 2021), for CUDA 10.2

    Download cuDNN v8.2.2 (July 6th, 2021), for CUDA 11.4

    Download cuDNN v8.2.2 (July 6th, 2021), for CUDA 10.2

    Download cuDNN v8.2.1 (June 7th, 2021), for CUDA 11.x

    Download cuDNN v8.2.1 (June 7th, 2021), for CUDA 10.2

    Download cuDNN v8.2.0 (April 23rd, 2021), for CUDA 11.x

    Download cuDNN v8.2.0 (April 23rd, 2021), for CUDA 10.2

    Download cuDNN v8.1.1 (Feburary 26th, 2021), for CUDA 11.0,11.1 and 11.2

    Download cuDNN v8.1.1 (Feburary 26th, 2021), for CUDA 10.2

    Download cuDNN v8.1.0 (January 26th, 2021), for CUDA 11.0,11.1 and 11.2

    Download cuDNN v8.1.0 (January 26th, 2021), for CUDA 10.2

    Download cuDNN v8.0.5 (November 9th, 2020), for CUDA 11.1

    Download cuDNN v8.0.5 (November 9th, 2020), for CUDA 11.0

    Download cuDNN v8.0.5 (November 9th, 2020), for CUDA 10.2

    Download cuDNN v8.0.5 (November 9th, 2020), for CUDA 10.1

    Download cuDNN v8.0.4 (September 28th, 2020), for CUDA 11.1

    Download cuDNN v8.0.4 (September 28th, 2020), for CUDA 11.0

    Download cuDNN v8.0.4 (September 28th, 2020), for CUDA 10.2

    Download cuDNN v8.0.4 (September 28th, 2020), for CUDA 10.1

    Download cuDNN v8.0.3 (August 26th, 2020), for CUDA 11.0

    Download cuDNN v8.0.3 (August 26th, 2020), for CUDA 10.2

    Download cuDNN v8.0.3 (August 26th, 2020), for CUDA 10.1

    Download cuDNN v8.0.2 (July 24th, 2020), for CUDA 11.0

    Download cuDNN v8.0.2 (July 24th, 2020), for CUDA 10.2

    Download cuDNN v8.0.2 (July 24th, 2020), for CUDA 10.1

    Download cuDNN v8.0.1 RC2 (June 26th, 2020), for CUDA 11.0

    Download cuDNN v8.0.1 RC2 (June 26th, 2020), for CUDA 10.2

    Download cuDNN v7.6.5 (November 18th, 2019), for CUDA 10.2

    Download cuDNN v7.6.5 (November 5th, 2019), for CUDA 10.1

    Download cuDNN v7.6.5 (November 5th, 2019), for CUDA 10.0

    Download cuDNN v7.6.5 (November 5th, 2019), for CUDA 9.2

    Download cuDNN v7.6.5 (November 5th, 2019), for CUDA 9.0

    Download cuDNN v7.6.4 (September 27, 2019), for CUDA 10.1

    Download cuDNN v7.6.4 (September 27, 2019), for CUDA 10.0

    Download cuDNN v7.6.4 (September 27, 2019), for CUDA 9.2

    Download cuDNN v7.6.4 (September 27, 2019), for CUDA 9.0

    Download cuDNN v7.6.3 (August 23, 2019), for CUDA 10.1

    Download cuDNN v7.6.3 (August 23, 2019), for CUDA 10.0

    Download cuDNN v7.6.3 (August 23, 2019), for CUDA 9.2

    Download cuDNN v7.6.3 (August 23, 2019), for CUDA 9.0

    Download cuDNN v7.6.2 (July 22, 2019), for CUDA 10.1

    Download cuDNN v7.6.2 (July 22, 2019), for CUDA 10.0

    Download cuDNN v7.6.2 (July 22, 2019), for CUDA 9.2

    Download cuDNN v7.6.2 (July 22, 2019), for CUDA 9.0

    Download cuDNN v7.6.1 (June 24, 2019), for CUDA 10.1

    Download cuDNN v7.6.1 (June 24, 2019), for CUDA 10.0

    Download cuDNN v7.6.1 (June 24, 2019), for CUDA 9.2

    Download cuDNN v7.6.1 (June 24, 2019), for CUDA 9.0

    Download cuDNN v7.6.0 (May 20, 2019), for CUDA 10.1

    Download cuDNN v7.6.0 (May 20, 2019), for CUDA 10.0

    Download cuDNN v7.6.0 (May 20, 2019), for CUDA 9.2

    Download cuDNN v7.6.0 (May 20, 2019), for CUDA 9.0

    Download cuDNN v7.5.1 (April 22, 2019), for CUDA 10.1

    Download cuDNN v7.5.1 (April 22, 2019), for CUDA 10.0

    Download cuDNN v7.5.1 (April 22, 2019), for CUDA 9.2

    Download cuDNN v7.5.1 (April 22, 2019), for CUDA 9.0

    Download cuDNN v7.5.0 (Feb 25, 2019), for CUDA 10.1

    Download cuDNN v7.5.0 (Feb 21, 2019), for CUDA 10.0

    Download cuDNN v7.5.0 (Feb 21, 2019), for CUDA 9.2

    Download cuDNN v7.5.0 (Feb 21, 2019), for CUDA 9.0

    Download cuDNN v7.4.2 (Dec 14, 2018), for CUDA 10.0

    Download cuDNN v7.4.2 (Dec 14, 2018), for CUDA 9.2

    Download cuDNN v7.4.2 (Dec 14, 2018), for CUDA 9.0

    Download cuDNN v7.4.1 (Nov 8, 2018), for CUDA 10.0

    Download cuDNN v7.4.1 (Nov 8, 2018), for CUDA 9.2

    Download cuDNN v7.4.1 (Nov 8, 2018), for CUDA 9.0

    Download cuDNN v7.3.1 (Sept 28, 2018), for CUDA 10.0

    Download cuDNN v7.3.1 (Sept 28, 2018), for CUDA 9.2

    Download cuDNN v7.3.1 (Sept 28, 2018), for CUDA 9.0

    Download cuDNN v7.3.0 (Sept 19, 2018), for CUDA 10.0

    Download cuDNN v7.3.0 (Sept 19, 2018), for CUDA 9.0

    Download cuDNN v7.2.1 (August 7, 2018), for CUDA 9.2

    Download cuDNN v7.1.4 (May 16, 2018), for CUDA 9.2

    Download cuDNN v7.1.4 (May 16, 2018), for CUDA 9.0

    Download cuDNN v7.1.4 (May 16, 2018), for CUDA 8.0

    Download cuDNN v7.1.3 (April 17, 2018), for CUDA 9.1

    Download cuDNN v7.1.3 (April 17, 2018), for CUDA 9.0

    Download cuDNN v7.1.3 (April 17, 2018), for CUDA 8.0

    Download cuDNN v7.1.2 (Mar 21, 2018), for CUDA 9.1 & 9.2

    Download cuDNN v7.1.2 (Mar 21, 2018), for CUDA 9.0

    Download cuDNN v7.0.5 (Dec 11, 2017), for CUDA 9.1

    Download cuDNN v7.0.5 (Dec 5, 2017), for CUDA 9.0

    Download cuDNN v7.0.5 (Dec 5, 2017), for CUDA 8.0

    Download cuDNN v7.0.4 (Nov 13, 2017), for CUDA 9.0

    Download cuDNN v6.0 (April 27, 2017), for CUDA 8.0

    Download cuDNN v6.0 (April 27, 2017), for CUDA 7.5

    Download cuDNN v5.1 (Jan 20, 2017), for CUDA 8.0

    Download cuDNN v5.1 (Jan 20, 2017), for CUDA 7.5

    Download cuDNN v5 (May 27, 2016), for CUDA 8.0

    Download cuDNN v5 (May 12, 2016), for CUDA 7.5

    Download cuDNN v4 (Feb 10, 2016), for CUDA 7.0 and later.

    Download cuDNN v3 (September 8, 2015), for CUDA 7.0 and later.

    Download cuDNN v2 (March 17,2015), for CUDA 6.5 and later.

    Download cuDNN v1 (cuDNN 6.5 R1)

* cuDNN安装配置

  * 选择与cuda版本匹配的cudnn版本,并下载tar文件
  * 解压tar
  * 执行cudnn代码到cuda
    ```shell
    cd cudnn-linux-x86_64-8.9.7.29_cuda12-archive
    sudo cp -d include/cudnn*.h /usr/local/cuda/include
    sudo cp -d lib/libcudnn* /usr/local/cuda/lib64
    sudo chmod a+r /usr/local/cuda/include/cudnn*.h /usr/local/cuda/lib64/libcudnn*

    ```
* 验证cuDNN
  ```shell
  cat /usr/local/cuda/include/cudnn_version.h | grep CUDNN_MAJOR -A 2
  ```




