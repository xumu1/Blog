# Fastjson quick start

## maven

```
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>x.x.x</version>
</dependency>
```

##  Java 对象转 JSON字符串/JSON对象

JSON.toJSONString() 将 Java 对象转换换为 JSON 字符串

(JSONObject)JSON.toJSON() 将 Java 对象转换换为 JSONObject 对象

我们还可以自定义输出，并控制字段的排序，日期显示格式，序列化标记等。

接下来我们更新 bean 并添加几个字段：

```java
@JSONField(name="AGE", serialize=false)
private int age;
 
@JSONField(name="LAST NAME", ordinal = 2)
private String lastName;
 
@JSONField(name="FIRST NAME", ordinal = 1)
private String firstName;
 
@JSONField(name="DATE OF BIRTH", format="dd/MM/yyyy", ordinal = 3)
private Date dateOfBirth;
```

- *format* 参数用于格式化 *date* 属性。
- 默认情况下， FastJson 库可以序列化 Java bean 实体， 但我们可以使用 *serialize* 指定字段不序列化。
- 使用 *ordinal* 参数指定字段的顺序

## BeanToArray 序列化功能：

```
String jsonOutput= JSON.toJSONString(listOfPersons, SerializerFeature.BeanToArray);
```
输出结果为：
```
输出结果为：
[
    [
        15,
        1469003271063,
        "John Doe"
    ],
    [
        20,
        1469003271063,
        "Janette Doe"
    ]
]
```

## 创建 JSON 对象

```java
@Test
public void whenGenerateJson_thanGenerationCorrect() throws ParseException {
    JSONArray jsonArray = new JSONArray();
    for (int i = 0; i < 2; i++) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("AGE", 10);
        jsonObject.put("FULL NAME", "Doe " + i);
        jsonObject.put("DATE OF BIRTH", "2016/12/12 12:12:12");
        jsonArray.add(jsonObject);
    }
    String jsonOutput = jsonArray.toJSONString();
}
```

输出结果：

```
[
   {
      "AGE":"10",
      "DATE OF BIRTH":"2016/12/12 12:12:12",
      "FULL NAME":"Doe 0"
   },
   {
      "AGE":"10",
      "DATE OF BIRTH":"2016/12/12 12:12:12",
      "FULL NAME":"Doe 1"
   }
]
```

## JSON 字符串转换为 Java 对象

我们可以使用 JSON.parseObject() 将 JSON 字符串转换为 Java 对象。

```java
Person person = new Person(20, "John", "Doe", new Date());
String jsonObject = JSON.toJSONString(person);
Person newPerson = JSON.parseObject(jsonObject, Person.class);
```

注意反序列化时为对象时，必须要有默认无参的构造函数，否则会报异常:

**@JSONField deserialize** 可选项可以指定字段不反序列化。

```
@JSONField(name = "DATE OF BIRTH", deserialize=false)
private Date dateOfBirth;
```

## 后记

JSONObject代表json对象，JSONArray代表json对象数组，

JSON代表JSONObject和JSONArray的转化。JSON是JSONObject和JSONArray的父类。

JSON这个类主要是实现转化用的，最后的数据获取，还是要通过上面的JSONObject和JSONArray来实现。

> JSON类之toJSONString()方法，实现json对象和javabean对象转化为json 字符串

> JSON类之parseObject()方法，实现json字符串转换为json对象或javabean对象

> JSON类之parseArray()将json字符串转化为json对象数组或转化成包含泛型的List

> JSON类之toJSON()方法，实现javabean对象转化为json对象

> JSON类之toJavaObject()方法，实现json对象转化为javabean对象



## 参考链接

Fastjson 简明教程https://www.runoob.com/w3cnote/fastjson-intro.html

Json详解以及fastjson使用教程https://blog.csdn.net/srj1095530512/article/details/82529759