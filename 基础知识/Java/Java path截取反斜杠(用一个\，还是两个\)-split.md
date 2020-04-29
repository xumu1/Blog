转载[https://blog.csdn.net/qq_19446965/article/details/81043442](https://blog.csdn.net/qq_19446965/article/details/81043442)

例如：切割String path= “E:\dds\fdfs\doc.c”
path.split("\\") 编译通过，但是结果出错，切割出空，警告类似错误：Invalid escape sequence (valid ones are  \b  \t  \n  \f  \r  \"  \'  \\ )等

正确方法 ：
path.split("\\\\"); 
 

原因如下： 

java需要调用正则，java和正则都需要转义，即两层转义：当碰见特殊字符\,^,'','等需要进行转义。

再看例子用一个\切割：正则regex应该为\\，因为在java中\\表示一个\，而regex中\\也表示\，所以java的\\\\表示为regex的\\，即实际的一个\。

String temp[] = path.split("\\\\"); 

 

 

在使用java中的split拆分特殊字符的时候，

 "11.1890".split("\.")  根本得不到结果。

 正确："11.1890".split("\\.") 

含义：正则需要 \. ，而其中的 \ ，java需要\\，所以最后是\\.

 

如果：String path= “E:\\dds\\fdfs\\doc.c”

则需要8个\,   即String temp[] = path.split("\\\\\\\\"); 

 
