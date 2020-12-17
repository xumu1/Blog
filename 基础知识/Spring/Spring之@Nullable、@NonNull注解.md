# Spring之@Nullable、@NonNull注解

- @NonNull可以标注在方法、字段、参数之上，表示对应的值不可以为空
- @Nullable注解可以标注在方法、字段、参数之上，表示对应的值可以为空

以上两个注解在程序运行的过程中不会起任何作用，只会在IDE、编译器、FindBugs检查、生成文档的时候有做提示。

使用IDEA时，代码中使用这两个注解可以帮助避免出现（只是提示）NullPointException异常（编译时不影响通过）

