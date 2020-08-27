# 使用JS替换json对象中的键名

### 初始数据

```javascript
var data = [
	{index:1,desc:'text1'},
	{index:2,desc:'text2'},
	{index:3,desc:'text3'},
]
```

# 方法:使用map

```javascript
var data = data.map(function(item) {
    return {
        name: item.index,
        value: item.desc
    }
})
```



