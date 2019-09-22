# 关于JAVA日期类的简单使用	

## Date类：

### 构造方法：

Data()：
分配 Date 对象并初始化此对象，以表示分配它的时间（精确到毫秒）。即得到计算机的目前时间。

Date(long date) ：分配 Date 对象并初始化此对象，以表示自从标准基准时间（称为“历元（epoch）”，即 1970 年 1 月 1 日 00:00:00 GMT）以来的指定毫秒数。中国在东八区，所以GMT+08:00。

		`INPUT:
		System.out.println(new Date());//无参构造函数
		System.out.println(new Date(0));//有参构造函数
		System.out.println(new Date(6000));//有参构造函数，6000ms = 6ms
		OUTPUT:
		Sun Sep 22 13:28:05 GMT+08:00 2019
		Thu Jan 01 08:00:00 GMT+08:00 1970
		Thu Jan 01 08:00:06 GMT+08:00 1970
`
### 成员方法
long getTime() ：返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。相当于System.currentTimeMillis()方法。

## DateFormat类
`java.text.DateFormat` 是日期/时间格式化子类的抽象类，我们通过这个类可以帮我们完成日期和文本之间的转换,也就是可以在Date对象与String对象之间进行来回转换。

* **格式化**：按照指定的格式，从Date对象转换为String对象。
* **解析**：按照指定的格式，从String对象转换为Date对象。

### 构造方法：
由于DateFormat为抽象类，不能直接使用，所以需要常用的子类`java.text.SimpleDateFormat`。这个类需要一个模式（格式）来指定格式化或解析的标准。构造方法为：

* `public SimpleDateFormat(String pattern)`：用给定的模式和默认语言环境的日期格式符号构造SimpleDateFormat。

参数pattern是一个字符串，代表日期时间的自定义格式。

### 格式规则

常用的格式规则为：

| 标识字母（区分大小写） | 含义   |

|----|----|

| y           | 年    |

| M           | 月    |

| d           | 日    |

| H           | 时    |

| m           | 分    |

| s           | 秒    |

> 备注：更详细的格式规则，可以参考SimpleDateFormat类的API文档0。

创建SimpleDateFormat对象的代码如：

		`java
		// 对应的日期格式如：2018-01-16 15:06:38
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		`
### 常用方法

DateFormat类的常用方法有：

- `public String format(Date date)`：将Date对象格式化为字符串。
- `public Date parse(String source)`：将字符串解析为Date对象。

#### format方法

使用format方法的代码为：

		Date date = new Date();
		// 创建日期格式化对象,在获取格式化对象时可以指定风格
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
	 	String str = df.format(date);
		System.out.println(str); // 2008年1月23日
		

#### parse方法

使用parse方法的代码为：

		        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		        String str = "2018年12月11日";
		        Date date = df.parse(str);
		        System.out.println(date); // Tue Dec 11 00:00:00 CST 2018

## 2.4 Calendar类

###  概念

日历我们都见过

`java.util.Calendar`是日历类，在Date后出现，替换掉了许多Date的方法。该类将所有可能用到的时间信息封装为静态成员变量，方便获取。日历类就是方便获取各个时间属性的。

- 小问题1：Calendar抽象类相比Date类有什么好处？

### 获取方式

Calendar为抽象类，由于语言敏感性，Calendar类在创建对象时并非直接创建，而是通过静态方法创建，返回子类对象，如下：

Calendar静态方法

* `public static Calendar getInstance()`：使用默认时区和语言环境获得一个日历

例如：	
		
		Calendar cal = Calendar.getInstance();
		System.out.println(cal);

output:

		java.util.GregorianCalendar[time=1569132643919,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="GMT+08:00",offset=28800000,dstSavings=0,useDaylight=false,transitions=0,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2019,MONTH=8,WEEK_OF_YEAR=39,WEEK_OF_MONTH=4,DAY_OF_MONTH=22,DAY_OF_YEAR=265,DAY_OF_WEEK=1,DAY_OF_WEEK_IN_MONTH=4,AM_PM=1,HOUR=2,HOUR_OF_DAY=14,MINUTE=10,SECOND=43,MILLISECOND=919,ZONE_OFFSET=28800000,DST_OFFSET=0]


### 常用方法

根据Calendar类的API文档，常用方法有：

- `public int get(int field)`：返回给定日历字段的值。
- `public void set(int field, int value)`：将给定的日历字段设置为给定值。
- `public abstract void add(int field, int amount)`：根据日历的规则，为给定的日历字段添加或减去指定的时间量。
- `public Date getTime()`：返回一个表示此Calendar时间值（从历元到现在的毫秒偏移量）的Date对象。

Calendar类中提供很多成员常量，代表给定的日历字段（即field字段）：

| 字段值        | 含义                   |

| ------------ | -------------------- |

| YEAR         | 年                    |

| MONTH        | 月（从0开始，可以+1使用）       |

| DAY_OF_MONTH | 月中的天（几号）             |

| HOUR         | 时（12小时制）             |

| HOUR_OF_DAY  | 时（24小时制）             |

| MINUTE       | 分                    |

| SECOND       | 秒                    |

| DAY_OF_WEEK  | 周中的天（周几，周日为1，可以-1使用） |

#### get/set方法

get方法用来获取指定字段的值，set方法用来设置指定字段的值，代码演示：


     	// 创建Calendar对象
        Calendar cal = Calendar.getInstance();
        // 获取年 
        int year = cal.get(Calendar.YEAR);
        // 获取月
        int month = cal.get(Calendar.MONTH) + 1;
        // 获取日
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        System.out.print(year + "年" + month + "月" + dayOfMonth + "日");
		


        Calendar cal = Calendar.getInstance();
		//设置年份
        cal.set(Calendar.YEAR, 2020);
		//技巧：一步设置年月日
		cal.set(2020，1，17);
        System.out.print(year + "年" + month + "月" + dayOfMonth + "日"); // 2020年1月17日


#### add方法

add方法可以对指定日历字段的值进行加减操作，如果第二个参数为正数则加上偏移量，如果为负数则减去偏移量。代码如：

        Calendar cal = Calendar.getInstance();
        System.out.print(year + "年" + month + "月" + dayOfMonth + "日"); // 2018年1月17日
        // 使用add方法
        cal.add(Calendar.DAY_OF_MONTH, 2); // 加2天
        cal.add(Calendar.YEAR, -3); // 减3年
        System.out.print(year + "年" + month + "月" + dayOfMonth + "日"); // 2015年1月18日;

- 小问题2：Calendar抽象类中的add方法是abstract方法，那么是由哪个类为Calendar实现的？
 
 
#### getTime方法

Calendar中的getTime方法并不是获取毫秒时刻，而是拿到对应的Date对象。

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        System.out.println(date); // Tue Jan 16 16:03:09 CST 2018


> 小贴士：
>
> ​     西方星期的开始为周日，中国为周一。
>
> ​     在Calendar类中，月份的表示是以0-11代表1-12月。
>
> ​     日期是有大小关系的，时间靠后，时间越大。


# 小问题解答
## 小问题1：Calendar抽象类相比Date类有什么好处？
>答：根据JDK_API文档解释，Date类的API不便于实现国际化。

## 小问题2：Calendar抽象类中的add方法是abstract方法，那么是由哪个类为Calendar实现的？
>答：LZ暂时未知，等待填坑。
 
