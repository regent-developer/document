# Win10安装bun

Bun 是一个 JavaScript 运行时，主要使用 Zig 编程语言编写。它旨在解决 Node.js 的局限性，同时提供更精简、更高效的开发体验。

然而，Bun 并不像多年来出现的许多 JavaScript 运行时一样，只是另一种 JavaScript 运行时： 它是一个一体化工具包，旨在彻底改变开发人员使用 JavaScript 和 TypeScript 的方式。Bun 扩展了为苹果 Safari 提供动力的 JavaScriptCore 引擎，可以实现更快的启动时间和更好的内存使用率。

与 Node.js 不同，Bun 的运行不依赖 npm 或外部依赖项。相反，它有一个内置的标准库，为各种协议和模块提供功能，包括环境变量、HTTP、WebSocket、文件系统等。

它还提供对 TypeScript 的开箱即用支持。由于 Bun 在内部转译了每个 JavaScript 或 TypeScript 源文件，因此你可以直接编译和运行 TypeScript 文件，而无需额外的配置或转译。

* 以管理员身份打开powershell
```powershell
powershell -c "irm bun.sh/install.ps1 | iex"
```

* 脚本会自动下载最新版 bun.exe 并放到 `%USERPROFILE%\.bun\bin`，同时把该目录写进用户 PATH；安装完 重启终端 生效。

* 检查安装是否成功
```powershell
bun --version
```