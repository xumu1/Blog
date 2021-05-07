# 使用typora的开发者工具

类似于chorme的开发者页面

需求：一键设置文档代码块的语言格式

1. 尝试设置指定元素的属性

```
let a = document.getElementsByTagName("pre")
a.forEach((item)=>{item.setAttribute("lang","java")})
```

结果是效果一般，需要再选中代码框按下回车才生效

2. 尝试跟踪回车的事件

   

--- todo