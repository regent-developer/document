# React Native

## 环境搭建

### NodeJS 安装

注意：Node 的版本应大于等于 12

```shell
# 使用nrm工具切换淘宝源
npx nrm use taobao

# 如果之后需要切换回官方源可使用
npx nrm use npm
```

### JDK 安装

注意：JDK 的版本必须是 1.8

### Yarn 安装

Yarn 是 Facebook 提供的替代 npm 的工具，可以加速 node 模块的下载。

```shell
npm install -g yarn
```

### Android 开发环境安装

https://developer.android.google.cn/studio/

#### Android Studio 安装

#### Android SDK 安装

#### 环境变量配置

## 创建新工程

```shell
npx react-native init AwesomeProject --version X.XX.X
```

```shell
npx react-native init AwesomeTSProject --template react-native-template-typescript
```

## 编译并运行

```shell
yarn android

yarn react-native run-android
```
