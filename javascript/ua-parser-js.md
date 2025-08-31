# ua-parser-js

ua-parser-js 是一个 JavaScript 库，用来解析 User Agent 字符串，提取其中的有用信息。它是轻量级的、无依赖的，可运行在浏览器和 Node.js 中。
你可以通过它快速获取浏览器名称和版本、操作系统信息、设备类型（如手机、平板、桌面）以及渲染引擎（如 WebKit 或 Blink）等关键数据。

## 安装
npm install ua-parser-js

## 引用

### ES 模块
import UAParser from "ua-parser-js";

### CommonJS
const UAParser = require("ua-parser-js");

## 使用
const uaString = navigator.userAgent;
const parser = new UAParser(uaString); // 把 UA 字符串传进去
const result = parser.getResult();
console.log(result);

const browser = parser.getBrowser();
console.log(browser.name); // Chrome
console.log(browser.version); // 123.0.0.0

const os = parser.getOS();
console.log(os.name); // Windows
console.log(os.version); // 10