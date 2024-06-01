# Spring MVC的核心组件

## DispatcherServlet

前端控制器：作为整个Spring MVC应用的入口点，DispatcherServlet负责接收所有的Web请求，并将这些请求派发给相应的处理器。
请求处理流程的初始化：DispatcherServlet不仅处理请求的接收，还负责初始化Spring应用的上下文以及调用其他组件来处理请求。

## Controller

处理请求：Controller是处理特定请求的组件，它包含了处理用户请求的逻辑。在Spring MVC中，Controller通常由一系列的方法组成，这些方法通过注解或配置与特定的URL模式关联。
模型数据的封装：Controller还负责创建数据模型，这些数据模型随后被传递给视图用于展示。

## HandlerMapping

请求到处理方法的映射：HandlerMapping负责将请求映射到对应的Controller中的处理方法。这种映射关系可以根据URL模式、请求参数等多种因素进行配置。
支持灵活的URL到方法的解析：这使得开发者能够方便地改变URL结构而不需要修改代码，增强了应用的可维护性和灵活性。

## HandlerAdapter

调用控制器方法：HandlerAdapter负责实际调用Controller中的方法，并处理返回的模型和视图信息。它支持多种类型的控制器，包括基于注解的控制器、基于接口的控制器等。
适配不同类型的控制器：这种适配机制使得Spring MVC能够灵活处理各种类型的控制器实现，提高了框架的适用性。

## ViewResolver

解析视图：ViewResolver负责将逻辑视图名解析为具体的视图技术，如JSP页面、HTML页面或其他类型的文档。
支持多种视图技术：这种机制允许开发者根据需要选择不同的视图技术，从而提供灵活的UI层实现。
