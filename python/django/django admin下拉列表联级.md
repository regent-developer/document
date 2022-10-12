# django admin下拉列表联级的实现方法

问题：django admin框架自动生成，下拉列表不能进行级联  

解决方法：通过扩展template模板使用ajax进行下拉列表级联操作  

* 扩展template文件

在template文件夹下新建admin文件夹，创建change_form.html(新增功能的扩展template)，在该文件中添加ajax的js函数  

```html
{% extends "admin/change_form.html" %}  -- 扩展原有页面
{% load i18n admin_urls static admin_modify %}
{% block after_field_sets %}  -- 扩展对应模块


<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
$("#id_level1").change(function() {   
      var id = $(this).val();
         {#alert(id);#}

      $.ajax({
        url: '/getLevel2',              
            data:{"id":id},    
            type: 'GET',                 
            dataType: 'json',
            success: function (data) {    
                var content='<option value= -1> --------- </option>';
                    $.each(data, function(i, item){
                        content+='<option value='+item.id+'>'+item.name+'</option>';
                    });
                $('#id_level2').html(content) 
        },
      })
	   .fail(function(){            
            var content='';
            content+='<option value= task> --------- </option>';
            $('#id_level2').html(content)
		  });
    });
</script>
{% endblock %}
```

* views.py中添加级联数据取得处理

```python
from django.http import JsonResponse

def getLevel2(request):
    if request.method == 'GET':
        id = request.GET.get('id')
        if web:
            data = list(Category.objects.filter(relationship_id=id).values("name", "id"))
            return JsonResponse(data, safe=False)
```

* urls.py中添加路由处理

```python
from apps.index import views -- 定义取得处理的包名
path('getLevel2/', views.getLevel2, name='getLevel2'),
```