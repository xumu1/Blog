## 1 JPDA (Java platform debug Architecture)体系介绍

   我们在调试的过程中，一般需要一些方法来观察和测试运行态中的环境信息，比如变量、状态、jvm 状态、堆栈信息等。这些通过JPDA都可以搞到。

## 2 JPDA结构

![image-20200922195930823](https://upload-images.jianshu.io/upload_images/3763302-1164cf8d6949cd2f.png?imageMogr2/auto-orient/strip|imageView2/2/w/475/format/webp)

JPDA定义了一个完整独立的体系，它由三个相对独立的层次共同组成，而且规定了三者之间的交互方式。这三个层次由低到高依次是Java 虚拟机工具接口（JVMTI），Java 调试线协议（JDWP）以及 Java 调试接口（JDI）。这三个模块把调试过程分解成几个很自然的概念：调试者（debugger）和被调试者（debuggee），以及他们中间的通信器。

## 3 JPDA三层结构的交互

![image-20200922195930823](https://upload-images.jianshu.io/upload_images/3763302-eb66d4f25896a07d.png?imageMogr2/auto-orient/strip|imageView2/2/w/439/format/webp)

基本交互可以分为如下步骤：

#### 3.1、jvmti 相关的步骤

- jvm启动，注册JVMTI：jvm启动时，会检查对应的jvm参数，加载jvmti。
- jvm启动， jdwp以agent的方式加载，并且设置对应的callback函数。启动jdwp的监听socket ；并且会初始化一个eventQueue实例，用户后续跟jdi 进行event交互。event 共有18种。（这个过程可以在启动时加载，也可以jvm其中之后以attach的方式加载 [你假笨的jvm attach机制实现](http://lovestblog.cn/blog/2014/06/18/jvm-attach/)）。具体的jvm启动过程可以看一些我的这篇文章：[jvm启动](https://www.jianshu.com/p/ae2625d29753) ；

#### 3.2、JDI相关的步骤

上述步骤主要描述了一下jvmti 相关的启动，下面讲一下JDI相关的步骤（以断点调试为例）：

- 从Intellij建立Remote debug, 此时就是建立了一个socket连接（上面步骤中jdwp agent 已经建立了socket ，等待客户端的连接）
- 建立完连接，第一件事情就是双方进行handshake 交互，彼此之间确认对方使用的都是jdwp协议。
- Intellij 设置断点，就是通过jdi 的eventRequestManage 生成一个类型为breakpoint event 并且带有filter（保证只在特定地方生效）的eventRequest， 然后按照jdwp协议组装成command ，发送给目标jvm.

#### 3.3、目标jvm的处理

- 目标jvm接收到来自调试端的command 指令，会解析指令
- 目标jvm 运行，当触发breakpoint event 且满足调试端的filter 规则时  ，将这个breakpoint event加入到breakpoint eventSet 中
- 然后 eventQueue 实例会将eventSet 按照FIFO的顺序返回给调试端。当然返回之前会将结果按照jdwp协议组装成reply 命令返回。

## 4 总结

本文讲述了JPDA的概述，组成部分，重点讲述了组成部分之间的交互流程。后面的文章将展开讲述这几个组成部分。
 本系列其他文章链接：
 [jvm启动流程图](https://www.jianshu.com/p/ae2625d29753)
 [jvmti agent的加载与回调函数的执行分析](https://www.jianshu.com/p/8775c1542b52)
 [java debug 体系-jdi](https://www.jianshu.com/p/e641ea08a2fc)
 [java debug 体系-jdwp](https://www.jianshu.com/p/463b3bc7d586)
 [java debug 体系-JVMTI](https://www.jianshu.com/p/e59c4eed44a2)

#### 参考文献

1、[第 1 部分，JPDA 体系概览](https://www.ibm.com/developerworks/cn/java/j-lo-jpda1/index.html?ca=drs-)
 2、[JPDA#2：架构源码浅析](https://blog.csdn.net/kisimple/article/details/43512725)
 3、[jvm启动流程图](https://www.jianshu.com/p/ae2625d29753)



作者：链人成长chainerup
链接：https://www.jianshu.com/p/86ec47435cfc
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。