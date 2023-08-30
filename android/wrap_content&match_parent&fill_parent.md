# wrap_content&match_parent&fill_parent

①wrap是扩展空间，并且强制占用整个空间，属性和内容大小刚好，不给其他控件留地方。

②match指“填充满”父容器，有自动调节功能，它就是强制性的使它的大小等同于父控件的大小。

③fill指“填满”，充满了整个父控件。



* wrap_content

设置一个视图的尺寸为wrap_content将强制性地使视图扩展以显示全部内容。布局元素将根据内容更改大小。

* match_parent

其实从Android 2.2开始FILL_PARENT改名为MATCH_PARENT ，从API Level为8开始我们可以直接用MATCH_PARENT来代替FILL_PARENT

* fill_parent

设置一个构件的布局为fill_parent将强制性地使构件扩展，以填充布局单元内尽可能多的空间。