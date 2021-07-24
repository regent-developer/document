# cordova

## 简介
Cordova是PhoneGap贡献给Apache后的开源项目，是从PhoneGap中抽出的核心代码，是驱动PhoneGap的核心引擎。Cordova提供了一组设备相关的API，通过这组API，移动应用能够以JavaScript访问原生的设备功能，如摄像头、麦克风等。Cordova还提供了一组统一的JavaScript类库，以及为这些类库所用的设备相关的原生后台代码。

## 官网

http://cordova.apache.org/

## 安装
```shell
npm install -g cordova
```

## 创建新cordova工程

```shell
cordova create MyApp
```

## 添加平台
```shell
cordova platform add android
```

## 查看支持的平台
```shell
cordova platform
```

## 运行
```shell
cordova run android
```
