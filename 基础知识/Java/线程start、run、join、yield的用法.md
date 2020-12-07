1. start（）方法来启动线程，真正实现了多线程运行，这时无需等 bai 待 run 方法体代码执行完毕而直接继续执行下面的代码：

2. run（）方法当作普通方法的方式调用，程序还是要顺序执行，还是要等待 run 方法体执行完毕后才可继续执行下面的代码：而如果直接用 Run 方法，这只是调用一个方法而已，程序中依然只有主线程--这一个线程，其程序执行路径还是只有一条，这样就没有达到写线程的目的。

3. join()方法表示当前线程等待使用此方法的线程完成再进行下面的代码流程。代码举例：

```
@Test
public void test1() throws InterruptedException {
    long l1 = System.currentTimeMillis();
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("1");
            long l = System.currentTimeMillis();
            try {
                Thread.sleep(2000);
                System.out.println("2");
                System.out.println(System.currentTimeMillis()-l +" .");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    thread.start();
    System.out.println(System.currentTimeMillis()-l1 +" ..");
    thread.join();
    System.out.println(System.currentTimeMillis() - l1+"...");
}
```

结果:

```
1 ..
1
//等待两秒
2
2001 .
2003...

Process finished with exit code 0
```

如果不使用 thread.join();
结果：

```
1 ..
1...
1

Process finished with exit code 0
```

很明显，如果没有使用 join（），主线程没有等待 thread 线程运行完成再结束，直接就结束运行了。



4. Thread.yield() 方法，使当前线程由执行状态，变成为就绪状态，让出cpu时间，在下一个线程执行时候，此线程有可能被执行，也有可能没有被执行。

   什么时候使用yield:

```
Yield is a heuristic attempt to improve relative progressionbetween threads that would otherwise over-utilise a CPU. Its use should be combined with detailed profiling and benchmarking to ensure that it actually has the desired effect.
 Yield是一种启发式尝试，旨在提高线程之间的相对进程否则会过度利用CPU。 使用应与详细的性能分析和基准测试结合使用，以确保确实具有所需的效果。
```

