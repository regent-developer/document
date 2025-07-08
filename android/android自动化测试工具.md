# android自动化测试工具

## 1、ADB（官方）
adb（即Android Debug Bridge）是一个Android的基础调试工具，主要用于设备连接管理、应用管理、存储管理、信息访问等。是开发和测试Android设备的必备工具。
## 2、Instrumentation（官方）
Instrumentation是Android的基础自动化框架，也是Android最早期的自动化框架，主要用于单元测试。它的原理是通过开发一个独立的测试APP，然后同时部署测试APP和被测试APP，并把这2个APP运行在同一个进程内，使得测试APP作为一个Spy的形式来运行，这样测试APP访问被测APP的对象就像访问自身对象一样简单。由于权限问题，并不是所有的独立APP都可以和被测APP运行在同一个进程，所以必须要在被测APP项目中来开发Instrumentation测试用例；也就必须要有项目源码，因此它比较适合单元测试，虽然它也可以用来检测UI界面。
## 3、Espresso（官方）
基于Instrumentation框架封装的更高级的自动化框架，旨在专门简化UI自动化用例的编写。它主要的优化点在于封装了Instrumentation框架的底层API，使得新封装的方法更便于开发UI自动化用例；同时也增加了自动处理异步操作（如网络请求），避免手动等待；另外它的执行性能也是其它框架不可比拟的。由于它是基于Instrumentation框架，因此也必须要有源码，同时也只能测试指定的被测APP，无法测试其它APP。
## 4、Robotium
是一款基于Instrumentation框架的工具，但是它的特点是不依赖于源码，只需要对被测APP进行一致性签名即可，对代码也没有侵入性。同时也对Instrumentation的API进行了简易化封装，使得更加容易的开发自动化测试用例。它同时支持Native、Hybrid应用。它没有Espresso的异步等待能力；也只能测试单APP应用；但是却可以脱离源码可以测试三方或者闭源的APP；对部分UI组件支持不足，在长期的UI兼容性支持上有一定风险。
## 5、Selendroid
一款基于Instrumentation的自动化框架，专注于支持原生应用、混合应用和移动 Web 的 UI 测试。从名称就能看出它和Selenium有渊源，它继承了Selenium的通信协议和架构，整个框架分client、server、standalone、webdriver-app。它重点在于支持Android平台的全类型应用，包括Web网站。但也只能测试单一APP应用；不依赖与被测应用的源码，只需统一签名即可，并且由standalone来提供支持。
## 6、Robolectric
一款专注于在 JVM 上模拟 Android 运行时环境的开源测试框架，旨在解决传统 Android 测试对设备/模拟器的依赖问题。也就是说使用这个框架测试，不需要启动设备、安装apk等操作，直接在JVM中执行测试代码。而测试代码执行时调用的API则是由框架模拟的代码，也就是框架重写了一套Android类的代码来模拟Android系统，然后让测试代码来调用它进行测试和验证。它主要提供了一个模拟环境而非在真实的设备上测试，模拟的内容无法也不可能完全覆盖所有的真实设备的功能；因此有些用例仍然需要再通过其它UI框架来补充。
## 7、UiAutomator（官方）
Google 官方推出的 Android 自动化测试框架，专为黑盒测试设计，支持跨应用操作（如系统通知栏、设置菜单等）。它的测试代码运行在独立的进程中，通过 Android 系统的 AccessibilityService 获取当前界面的控件树（AccessibilityNodeInfo），解析 UI 元素的层级结构和属性。纯黑盒测试需要源码，无需统一签名，直接可测试第三方应用；同时还可以对包括系统应用在内的多应用同时进行测试，跨应用测试是它相比于Espresso的核心特点。
## 8、Maestro
一款专注于移动端（iOS/Android）及 Web 应用自动化测试的开源框架，其设计核心在于通过极简语法与智能底层机制解决传统 UI 测试的痛点（如脆弱性、高维护成本）。它的特点是通过 声明式语法 与 智能容错引擎 重构了 UI 自动化测试流程，尤其适合追求快速迭代与低维护成本的团队。其局限主要体现在真机兼容性与复杂交互支持。只能支持单APP测试，不支持iOS真机设备，对于复杂操作仍需要开发脚本来实现。
## 9、Compose TestingAndroid 
官方为声明式 UI 框架 Jetpack Compose 设计的测试工具，其核心设计紧密贴合 Compose 的响应式编程模型，显著提升了 UI 自动化测试的效率和可靠性。与Espresso一样它也具有同步机制，并且其声明式API封装比Espresso更加直观；支持直接测试单个 @Composable 函数，无需启动完整 Activity，比较适合纯 Compose 应用的快速回归测试。不足之处是开发人员需理解 Compose 的重组机制和状态驱动模型；对复杂交互场景支持不足；非 Compose 的异步任务需要显式处理。
## 10、AppiumAppium
是可测试Android、iOS、Windows应用，覆盖原生、混合（Hybrid）及Web应用的框架。并且支持跨平台部署，多语言开发等特点。根据不同的测试设备，底层适配不同的设备驱动，且都不依赖于源码包，无需重新签名；支持真机、模拟器、云测平台（如Sauce Labs），适配不同测试需求；扩展WebDriver协议，兼容Selenium生态工具（如Allure报告、TestNG断言）。不足之处，支持图形类App测试，执行效率不高，环境搭建复杂，单MacOS仅支持单iOS会话。
## 11、AirTest&POCOAirTest
是网易开源的一款基于图像识别的跨平台 UI 自动化测试框架，支持 Android、iOS、Windows 平台及游戏引擎（Unity/Cocos2dx）；POCO也是由网易开发的基于UI控件层级的自动化框架，支持原生应用、游戏引擎及微信小程序。它们可以同时在项目中混合使用，以便达到最佳的效果。Airtest + Poco 组合，兼顾图像鲁棒性与控件精准性，尤其适合 游戏/混合应用 的全链路测试。
## 12、SolopiSoloPi
是蚂蚁金服开源的一款无线化、非侵入式的移动端自动化测试工具，专为Android系统设计。它的特点是脱离PC限制直接在手机设备上进行测试，支持录制与回放机制并可转换为Appium脚本；支持系统性能指标监控和性能压测模拟；支持一机多控进行兼容性测试的能力。不足之处，不支持iOS设备；部分机型（如Redmi）需特殊配置权限，偶发系统UI崩溃；复杂操作需要扩展开发脚本。
## 13、MoblyMobly 
是由 Google 工程师开发的 ​​开源 Python 测试框架​​，专为满足​​多设备交互​​、​​复杂环境测试​​和​​自定义硬件集成​​需求而设计。其核心定位是解决传统测试框架难以覆盖的跨设备协作与异构系统验证难题。它的特点是支持跨设备测试，尤其是Android、IoT、非标准硬件、各种环境模拟。结合 adb 命令与 Mobly Snippet Lib，深度操作 Android 设备（如调用 UI Automator/Espresso），覆盖功能、性能及兼容性测试。