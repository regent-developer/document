# CWE

CWE常见缺陷列表(Common Weakness Enumeration)是MITRE公司（一个非盈利机构）继CVE（Common Vulnerabilities and Exposures）之后的又一个安全漏洞词典。

通过这一词典，Mitre希望提供识别、减轻、阻止软件缺陷的通用标准。CWE也可以作为人们购买软件的安全衡量标准，尤其是在购买旨在阻止或发现具体安全问题的安全工具时。  



### CWE-20: Improper Input Validation

软件接收输入，但不会验证或者有效的验证输入是否安全。

输入验证会应用于：

- raw data： strings, numbers, parameters, file contents

- metadata：关于raw data的信息，比如headers或者大小

  

## CWE-22: Improper Limitation of a Pathname to a Restricted Directory

软件使用外部输入来构造一个路径名，该路径名用于访问受限制的父目录下的文件或目录，但软件不会正确地处理路径名中的特殊元素（比如 `../`），这些元素会导致路径名解析到受限制目录外的位置。  



## CWE-78: Improper Neutralization of Special Elements used in an OS Command

当软件用外部输入构造要执行的系统命令时，没有处理外部输入中的特殊元素导致恶意命令被执行。  



## **CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')**



## CWE-119: Improper Restriction of Operations within the Bounds of a Memory Buffer

软件对内存缓冲区执行操作，但它可以读取或写入缓冲区预期边界之外的内存位置。  



## CWE-125: Out-of-bounds Read

软件读取数组之外的数据



## CWE-190: Integer Overflow or Wraparound

当算术运算中出现增加一个非常大的数字或其它情况时容易触发。加法运算后值变负或者变小。  



## CWE-200: Exposure of Sensitive Information to an Unauthorized Actor

软件将敏感信息暴露给没有明确授权访问该信息的参与者。



## CWE-287: Improper Authentication

当一个用户声称拥有一个给定的身份时，软件不能证明或者不能充分证明这个声明是正确的。  



## CWE-400: Uncontrolled Resource Consumption

软件不能正确地控制有限资源的分配和维护，从而使参与者能够影响所消耗的资源量，最终导致可用资源的耗尽。

资源包括：内存，系统存储空间，数据库连接等等。



## CWE-416: Use After Free

释放内存后引用内存可能会导致程序崩溃、使用意外值或执行代码，这也是CTF中的热点。



## CWE-476: NULL Pointer Dereference

当应用程序引用预期有效但为`NULL`的指针时，就会发生空指针引用，通常会导致崩溃或退出。



## CWE-787: Out-of-bounds Write

软件在预期缓冲区的末尾或开头之前写入数据。也是CTF热点，`strcpy`出现的地方几乎必有它。



## CWE-798: Use of Hard-coded Credentials

将密码或者密钥等重要信息硬编码在代码里。







https://cwe.mitre.org/data/index.html

