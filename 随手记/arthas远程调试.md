# 安装

curl -O https://arthas.aliyun.com/arthas-boot.jar

# 启动 arthas

java -jar arthas-boot.jar

# 使用 watch 观测函数

让你能方便的观察到指定方法的调用情况。能观察到的范围为：返回值、抛出异常、入参，通过编写 OGNL 表达式进行对应变量的查看。
特殊用法请参考：https://github.com/alibaba/arthas/issues/71

OGNL 表达式官网：https://commons.apache.org/proper/commons-ognl/language-guide.html

```
$ watch demo.MathGame primeFactors "{params,returnObj}" -x 2
```

# dashboard

当前系统的实时数据面板

参数说明

参数名称 参数说明

[i:] 刷新实时数据的时间间隔 (ms)，默认 5000ms

[n:] 刷新实时数据的次数

## 数据说明

ID: Java 级别的线程 ID，注意这个 ID 不能跟 jstack 中的 nativeID 一一对应

NAME: 线程名

GROUP: 线程组名

PRIORITY: 线程优先级, 1~10 之间的数字，越大表示优先级越高

STATE: 线程的状态

CPU%: 线程消耗的 cpu 占比，采样 100ms，将所有线程在这 100ms 内的 cpu 使用量求和，再算出每个线程的 cpu 使用占比。

TIME: 线程运行总时间，数据格式为分：秒

INTERRUPTED: 线程当前的中断位状态

DAEMON: 是否是 daemon 线程
