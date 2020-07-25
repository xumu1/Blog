# JS正则：按中英文逗号 中英文分号 回车 空格分隔/切割字符串

## 一、分隔
	$(document).ready(function(){
	function foo(str){
	    //以回车，空格，英文逗号为分隔符，分割字符串，分割结果得到一个数组
	    var temp = str.split(/[\n\s+,]/g);
	      
	    console.log(temp);
	}
	 
	foo("123,  \na  a,中国,,,,\n\n\n\n\n1");
	});

测试结果：

["123", "", "", "", "a", "", "a", "中国", "", "", "", "", "", "", "", "", "1"]

## 二、改进方法，去掉字符串分割后得到的字符串数组中的空值

	$(document).ready(function() {
	function foo(str) {
		var temp = str.split(/[\n\s+,]/g);
		console.log(temp);
	}
	
	//改进方法1
	function foo2(str) {
		var temp = str.split(/[\n\s+,]/g);
		for (var i = 0; i < temp.length; i++) {
			if (temp[i] == "") {
			    //函数splice(para1,para2):删除数组中任意数量的项，从para1开始的para2个。
	            // 删除数组中空值
				temp.splice(i, 1);
				i--;
			}
		}
		console.log(temp);
	}
	foo("123,  \na  a,中国,,,,\n\n\n\n\n1");
	foo2("123,  \na  a,中国,,,,\n\n\n\n\n1");
	});

测试结果：

foo: ["123", "", "", "", "a", "", "a", "中国", "", "", "", "", "", "", "", "", "1"]

foo2: ["123", "a", "a", "中国", "1"]

## 三、终极方法，增加对中文逗号，中英文文分号的支持

```
$(document).ready(function() {
 
	function foo(str) {
		var temp = str.split(/[\n\s+,]/g);
		console.log(temp);
	}
	
	function foo2(str) {
		var temp = str.split(/[\n\s+,]/g);
		for (var i = 0; i < temp.length; i++) {
			if (temp[i] == "") {
			    //splice( para1,para2 ) : 删除数组中任意数量的项，从para1开始的para2项。
				// 删除数组中空值
				temp.splice(i, 1);
				
				i--;
			}
		}
		console.log(temp);
	}
 
	function foo3(str) {
		var temp = str.split(/[\n\s+,，；;]/g);
		for (var i = 0; i < temp.length; i++) {
			if (temp[i] == "") {
				// 删除数组中空值
				temp.splice(i, 1);
				i--;
			}
		}
		console.log(temp);
	}
 
	foo("123,  \na  a,中国,,,,\n\n\n\n\n1");
	foo2("123,  \na  a,中国,,,,\n\n\n\n\n1");
	foo3("123，  \na  a,中国,,广州；大学,,,广东；工业，大学\n\n\n\n\n1");
 
});
```

所以下面这方法，大家可以直接使用在项目中：

```
$(document).ready(function() {
 
	function foo3(str) {
		var temp = str.split(/[\n\s+,，；;]/g);
		for (var i = 0; i < temp.length; i++) {
			if (temp[i] == "") {
				// 删除数组中空值
				temp.splice(i, 1);
				i--;
			}
		}
		console.log(temp);
	}
	foo3("123，  \na  a,中国,,广州；大学,,,广东；工业，大学\n\n\n\n\n1");
});
```

转自https://blog.csdn.net/chenchunlin526/article/details/85244061/感谢分享