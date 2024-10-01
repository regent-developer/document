# Python Selenium

Python 的 Selenium 库是一个强大的工具，用于自动化浏览器操作，常用于网页测试、爬虫等场景。

## 安装 Selenium 和 WebDriver

```bash
pip install selenium
```
WebDriver 是一个浏览器的驱动程序，它允许 Selenium 控制浏览器。

对于不同的浏览器，需要下载相应的 WebDriver：

Chrome: ChromeDriver(https://sites.google.com/a/chromium.org/chromedriver/)
Firefox: GeckoDriver(https://github.com/mozilla/geckodriver)
Edge: EdgeDriver(https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/)


## 启动一个浏览器会话

```python
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait

driver = webdriver.Chrome('/path/to/chromedriver')  # 指定 WebDriver 的路径
driver.get('http://www.example.com')  # 打开网页

```

## 元素定位
```python
# 通过 ID 定位
element = driver.find_element_by_id('id')

# 通过名称定位
element = driver.find_element_by_name('name')

# 通过类名定位
elements = driver.find_elements_by_class_name('class_name')

# 通过标签名定位
elements = driver.find_elements_by_tag_name('tag_name')

# 通过链接文本完全匹配定位
element = driver.find_element_by_link_text('link_text')

# 通过链接文本部分匹配定位
element = driver.find_element_by_partial_link_text('partial_link_text')

# 通过 XPath 定位
element = driver.find_element_by_xpath('//div[@class="myclass"]')

# 通过 CSS 选择器定位
element = driver.find_element_by_css_selector('div.myclass')
```
## 元素交互
```python
# 输入文本
element.send_keys('some text')

# 点击元素
element.click()

# 清除文本输入框的内容
element.clear()

```

## 等待 (Implicit Wait 和 Explicit Wait)
```python
driver.execute_script('window.scrollTo(0, document.body.scrollHeight);')  # 滚动到页面底部

```

## 执行 JavaScript
```python
driver.execute_script('window.scrollTo(0, document.body.scrollHeight);')  # 滚动到页面底部

```

## 管理窗口和标签页

```python
# 获取当前窗口句柄
current_window = driver.current_window_handle

# 切换到新窗口
for handle in driver.window_handles:
    if handle != current_window:
        driver.switch_to.window(handle)

# 关闭当前窗口
driver.close()

# 关闭浏览器
driver.quit()
```

## 截屏
```python
driver.save_screenshot('screenshot.png')

```

## XPath语法详解

XPath（XML Path Language）是一种在 XML 和 HTML 文档中查找信息的语言。Selenium 支持使用 XPath 来定位页面元素，这使得元素定位更加灵活和强大。以下是 XPath 的常用语法和一些示例，这些可以帮助你更有效地在使用 Selenium 时定位元素。

### 基本语法
* 选择节点

    / 从根节点选取。
    // 从匹配选择的当前节点选择文档中的节点，而不考虑它们的位置。
    . 选取当前节点。
    .. 选取当前节点的父节点。

* 谓词（Predicates）

    [] 用来查找特定的节点或者包含某个特定值的节点。
    例如：//input[@type='text'] 选取所有 type 属性为 text 的 input 元素。

* 通配符

    匹配任何元素节点。
    例如：//* 选取文档中的所有元素。

* 选择多个路径

    | 用于合并两个或多个 XPath 查询。
    例如：//div | //p 选取所有 div 和 p 元素。

### 常用函数
* text() 获取节点的文本内容。
    例如：//a[text()='点击这里'] 选取文本内容为“点击这里”的 a 元素。
* contains() 判断某个属性或文本是否包含某个字符串。
    例如：//div[contains(@class, 'important')] 选取类属性包含 important 的 div 元素。
* starts-with() 判断某个属性或文本是否以某个字符串开始。
    例如：//input[starts-with(@id, 'login')] 选取其 id 属性以 login 开头的 input 元素。
* last() 选择最后一个元素。
    例如：//(//a)[last()] 选取最后一个 a 元素。
* position() 返回节点在其父节点中的位置。
    例如：//li[position()=3] 选取第三个 li 元素。

### 高级用法
* 轴（Axes）

    ancestor 选取当前节点的所有祖先元素（父、祖父等）。
    descendant 选取当前节点的所有后代元素（子、孙等）。
    following 选取文档中当前节点的结束标签之后的所有节点。
    preceding 选取文档中当前节点的开始标签之前的所有节点。

    例如：
    //div/ancestor::form 选取所有包含 div 的 form 祖先元素。
    //div/following::input 选取所有在文档中出现在 div 元素之后的 input 元素。
    通过理解和运用这些 XPath 表达式，你可以更加精确地定位 HTML 文档中的元素，这对于使用 Selenium 进行web自动化测试和数据抓取是非常有用的。


