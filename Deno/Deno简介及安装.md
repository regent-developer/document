# Deno简介及安装

Deno 是一个简单、先进且安全的 JavaScript 和 TypeScript 运行时环境，其基于 V8 引擎并采用 Rust 编程语言构建。

* 默认安全设置。除非 显式开启，否则不能访问文件、网络，也不能访问运行环境。
* 天生支持 TypeScript。
* 只有一个唯一的可执行文件。
* 自带实用工具，例如依赖检查器 (deno info)和代码格式化工具(deno fmt)。
* 有一套经过审核（审计）的标准模块， 确保与Deno兼容：deno.land/std
* 有大量的企业对使用 Deno感兴趣


https://deno.land

https://doc.deno.land/


## 安装

### windows版安装

* 通过PowerShell安装

```shell
iwr https://deno.land/install.ps1 -useb | iex
```

* 通过 Chocolatey安装

```shell
choco install deno
```

* 通过scoop安装

```shell
scoop install deno
```

运行例子
```shell
deno run https://deno.land/std/examples/welcome.ts
```
