# ksh

Ksh（Korn Shell）是一种Unix（包括Linux）操作系统中常用的命令行解释器。它是由David Korn开发的，提供了一些强大的功能和灵活性。

## 安装
```shell
sudo apt install ksh
```

## 切换至ksh

在Linux系统中，默认的Shell通常是Bash
```shell
$ ksh
```

## 显示当前shell的版本
```shell
$ echo ${.sh.version}
```

## 管道和重定向
ksh支持管道和重定向，可以将一个命令的输出作为另一个命令的输入，或将输出重定向到文件中。
```shell
# 管道
$ command1 | command2

# 输出重定向
$ command > file_name

# 输入重定向
$ command < file_name
```

## 变量
```shell
variable_name=value
```

## 变量的使用
```shell
echo $variable_name
```

## 特殊变量
```shell
$0 – 脚本的名称
$1, $2, … – 脚本的参数
$# – 脚本参数的个数
$? – 上个命令的返回值
$! – 上个命令的PID（进程ID）
```

## 条件判断
```shell
if condition
then
    command
elif condition
then
    command
else
    command
fi
```

## case语句
```shell
case variable in
    pattern1)
        command1
        ;;
    pattern2)
        command2
        ;;
    *)
        command
        ;;
esac
```

## test命令
```shell
test condition
```

## 循环
```shell
for variable in values
do
    command
done
```

## while循环
```shell
while condition
do
    command
done
```

## until循环
```shell
until condition
do
    command
done
```

## 函数
```shell
function function_name {
    command
}
```

## 数组
```shell
array_name=(value1 value2 value3)
```

## 数组的访问
```shell
echo ${array_name[index]}
```

## 数组的长度
```shell
echo ${#array_name[@]}
```