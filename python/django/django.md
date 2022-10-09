# Django

Django 是一个由 Python 编写的一个开放源代码的 Web 应用框架。  

使用 Django，只要很少的代码，Python 的程序开发人员就可以轻松地完成一个正式网站所需要的大部分内容，并进一步开发出全功能的 Web 服务 Django 本身基于 MVC 模型，即 Model（模型）+ View（视图）+ Controller（控制器）设计模式，MVC 模式使后续对程序的修改和扩展简化，并且使程序某一部分的重复利用成为可能。    

Django 的 MTV 模式本质上和 MVC 是一样的，也是为了各组件间保持松耦合关系，只是定义上有些许不同，Django 的 MTV 分别是指：  

* M 表示模型（Model）：编写程序应有的功能，负责业务对象与数据库的映射(ORM)。
* T 表示模板 (Template)：负责如何把页面(html)展示给用户。
* V 表示视图（View）：负责业务逻辑，并在适当时候调用 Model和 Template。

URL 分发器，它的作用是将一个个 URL 的页面请求分发给不同的 View 处理，View 再调用相应的 Model 和 Template。  


## 安装
* 安装最新版本django
```shell
pip install django
```

* 安装指定版本django
```shell
pip install django==3.0.6
```

## 验证安装
```python
>>> import django
>>> django.get_version()
```

## 配置环境变量

在Python解释器目录下的Scripts文件夹中可找到一个django-admin.exe文件，这是Django的核心管理程序，将它加入操作系统的环境变量。

```shell
django-admin help
```

## 创建项目

```shell
django-admin startproject djangoProject
```

## 运行
```shell
python manage.py runserver
```