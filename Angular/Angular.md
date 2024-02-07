# Angular



Angular 是一个基于 [TypeScript](https://www.typescriptlang.org/) 构建的开发平台。它包括：

- 一个基于组件的框架，用于构建可伸缩的 Web 应用
- 一组完美集成的库，涵盖各种功能，包括路由、表单管理、客户端-服务器通信等
- 一套开发工具，可帮助你开发、构建、测试和更新代码



## 环境搭建

* Node.js安装
* 安装Angular CLI

```shell
npm install -g @angular/cli
```



* 创建工程

```shell
ng new my-app
```

* 运行工程

```shell
ng serve --open
```

* 创建组件

```shell
ng generate component <component-name>
```

>  生成以该组件命名的文件夹
>  组件文件 `<component-name>.component.ts`
>  模板文件 `<component-name>.component.html`
>  CSS 文件，`<component-name>.component.css`
>  测试文件 `<component-name>.component.spec.ts`



> `手动方式创建组件`
>
> * 创建组件文件 `<component-name>.component.ts`
>
>   ```typescript
>   import { Component } from '@angular/core';
>   @Component({
>       // CSS选择器
>       selector: 'app-component-overview',
>       //  HTML模板
>       templateUrl: './component-overview.component.html',
>       // 样式
>       styleUrls: ['./component-overview.component.css']
>   })
>   
>   export class ComponentOverviewComponent {
>   
>   }
>   
>   ```
>
>   
>
> * 







