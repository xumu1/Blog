引用 https://www.jianshu.com/p/b69146fff96f

1.String：
String类是不可变的，所谓不可变意思就是创建一个类后任何对String的改变都 会引发新的String对象的生成；让我们举个例子来看看就明白了：

String str ="abc";
str = str +"def";//这一步jvm会再次创建一个String对象

第二次其实jvm又生成了一个String类，而不是直接覆盖原来的"abc"，因此我们说String类是不可改变类。这一种特性会带来一个问题，每次拼接都要创建都要创建一次对象，当我们要拼接大量字符串的时候，效率会变得非常非常慢。

2.StringBuffer
StringBuffer不同于String的是StringBuffer是可变的，一样的我们来举个例子看看：

StringBuffer sb =new StringBuffer("abc");
sb.append("efg");//并没有创建一个新的对象

这里第二步并没有产生一个新的对象，而是在原来的基础上追加字符串，这种方式在拼接字符串的时候效率肯定比String要高得多。

3.StringBuilder

StringBuffer和StringBuilder类的区别也是如此，他们的原理和操作基本相同，区别在于StringBuffer支持并发操作，线性安全的，适 合多线程中使用。StringBuilder不支持并发操作，线性不安全的，不适合多线程中使用。新引入的StringBuilder类不是线程安全的，但其在单线程中的性能比StringBuffer高。

点评：上面大概介绍了String、StringBuffer、StringBuilder的区别，那么我们要处理字符串的时候要选择哪个呢？总结如下：

1.如果要操作少量的数据用 String

2.单线程操作字符串缓冲区下操作大量数据用StringBuilder

3.多线程操作字符串缓冲区下操作大量数据用StringBuffer
