# vscode设置

## 现象
vscode出现Module ‘XXX’ has no ‘XXX’ memberpylint(no-member)的错误提示，但是编译没有错误。

## 解决
* 打开setting.json,并按下面修改该文件
```json

{
    "editor.codeActionsOnSave": 2,
    "python.pythonPath": "C:\\ProgramData\\Anaconda3\\python.exe",
    "python.linting.pylintPath":"C:\\ProgramData\\Anaconda3\\pkgs\\pylint-2.1.1-py37_0\\Scripts\\pylint"
}

```




