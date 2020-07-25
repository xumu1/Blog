# 为什么我们要使用Async、Await关键字

## **Async/Await关键字**

![img](https://images2017.cnblogs.com/blog/426766/201708/426766-20170824203839386-616762258.png)

1. 调用异步方法AccesstheWebAsync
2. 创建HttpClient实例，并使用HttpClient获取异步数据。
3. 利用Task执行获取数据方法（假设获取数据需要很长时间），不阻塞当前线程，getStringTask代表进行中的任务。
4. 因为getStringTask还没有使用await 关键字，使之可以继续执行不依赖于其返回结果的其他任务，同步执行DoIndependentWork。
5. 当同步任务DoIndependentWork执行完毕之后，返回调用给AccessTheWebAsync线程。
6. 使用await强制等待getStringTask完成，并获取基于Task<String>类型的返回值。(如果getStringTask在同步方法DoIndependentWork执行之前完成，调用会返回给AccessTheWebAsync线程，调用await将会执行不必要的挂起操作)
7. 当获取web数据之后，返回结果记录在Task中并返回给await调用处（当然，返回值并没有在第二行返回）。
8. 获取数据并返回计算结果。 

# 刨根问底

以上是官方给的说明文档，例子详尽表达清楚，但是有一个问题没有解决（被证明）：

 

\1. 当线程在await处返回给线程池之后，该线程是否“真的”被其他请求所消费?

\2. 服务器线程资源是一定的，是谁在真正执行Await所等待的操作，或者说异步IO操作？

\3. 如果使用IO线程执行异步IO操作，相比线程池的线程有什么优势？或者说异步比同步操作优势在哪里？

 

前提条件：

 

\1. 相对Console应用程序来说，可以使用ThreadPool的*SetMaxThread*来模拟当前进程所支持的最大工作线程和IO线程数。

\2. 通过ThreadPool的*GetAvailableThreads*可以获得当前进程工作线程和IO线程的可用数量。

\3. ThreadPool是基于进程的，每一个进程有一个线程池，IIS Host的进程可以单独管理线程池。

\4. 如果要真正意义上的模拟异步IO线程操作文件需要设置*FileOptions.Asynchronous，*而不是仅仅是使用BeginXXX一类的方法，详情请参考[^1]的异步IO线程。

\5. 在验证同步和异步调用时，执行的任务数量要大于当前最大工作线程的2倍，这样才可以测出当Await释放工作线程后，其他请求可继续利用该线程。

 

 

结论：

 

\1.  Await使用异步IO线程来执行，异步操作的任务，释放工作线程回线程池。

\2.  线程池分为工作线程和异步IO线程，分别执行不同级别的任务。

\3.  使用Await来执行异步操作效率并不总是高于同步操作，需要根据异步执行长短来判断。

\4.  当工作线程和IO线程相互切换时，会有一定性能消耗。

 


各位可以Clone代码，并根据Commit去Review代码，相信大家能理解代码意图，如果不能，请留言，我改进：）

 

 

[GitHubRepo](https://github.com/Cuiyansong/Why-To-Use-Async-Await-In-DotNet.git)

 

```
using System;
using System.Diagnostics;
using System.IO;
using System.Threading;
using System.Threading.Tasks;
 
namespace AsyncAwaitConsole
{
    class Program
    {
        static int maxWorkerThreads;
        static int maxAsyncIoThreadNum;
        const string UserDirectory = @"files\";
        const int BufferSize = 1024 * 4;
 
        static void Main(string[] args)
        {
            AppDomain.CurrentDomain.ProcessExit += (sender, eventArgs) =>
            {
                Directory.Delete("files", true);
            };
 
            maxWorkerThreads = Environment.ProcessorCount;
            maxAsyncIoThreadNum = Environment.ProcessorCount;
            ThreadPool.SetMaxThreads(maxWorkerThreads, maxAsyncIoThreadNum);
 
            LogRunningTime(() =>
            {
                for (int i = 0; i < Environment.ProcessorCount * 2; i++)
                {
                   Task.Factory.StartNew(SyncJob, new {Id = i});
                }
            });
 
            Console.WriteLine("===========================================");
 
            LogRunningTime(() =>
            {
                for (int i = 0; i < Environment.ProcessorCount * 2; i++)
                {
                    Task.Factory.StartNew(AsyncJob, new { Id = i });
                }
            });
 
            Console.ReadKey();
        }
 
        static void SyncJob(dynamic stateInfo)
        {
            var id = (long)stateInfo.Id;
            Console.WriteLine("Job Id: {0}, sync starting...", id);
 
            using (FileStream sourceReader = new FileStream(UserDirectory + "BigFile.txt", FileMode.Open, FileAccess.Read, FileShare.Read, BufferSize))
            {
                using (FileStream destinationWriter = new FileStream(UserDirectory + $"CopiedFile-{id}.txt", FileMode.OpenOrCreate, FileAccess.ReadWrite, FileShare.None, BufferSize))
                {
                    CopyFileSync(sourceReader, destinationWriter);
                }
            }
            Console.WriteLine("Job Id: {0}, completed...", id);
        }
 
        static async Task AsyncJob(dynamic stateInfo)
        {
            var id = (long)stateInfo.Id;
            Console.WriteLine("Job Id: {0}, async starting...", id);
 
            using (FileStream sourceReader = new FileStream(UserDirectory + "BigFile.txt", FileMode.Open, FileAccess.Read, FileShare.Read, BufferSize, FileOptions.Asynchronous))
            {
                using (FileStream destinationWriter = new FileStream(UserDirectory + $"CopiedFile-{id}.txt", FileMode.OpenOrCreate, FileAccess.ReadWrite, FileShare.None, BufferSize, FileOptions.Asynchronous))
                {
                    await CopyFilesAsync(sourceReader, destinationWriter);
                }
            }
            Console.WriteLine("Job Id: {0}, async completed...", id);
        }
 
        static async Task CopyFilesAsync(FileStream source, FileStream destination)
        {
            var buffer = new byte[BufferSize + 1];
            int numRead;
            while ((numRead = await source.ReadAsync(buffer, 0, buffer.Length)) != 0)
            {
                await destination.WriteAsync(buffer, 0, numRead);
            }
        }
 
        static void CopyFileSync(FileStream source, FileStream destination)
        {
            var buffer = new byte[BufferSize + 1];
            int numRead;
            while ((numRead = source.Read(buffer, 0, buffer.Length)) != 0)
            {
                destination.Write(buffer, 0, numRead);
            }
        }
 
        static void LogRunningTime(Action callback)
        {
            var awailableWorkingThreadCount = 0;
            var awailableAsyncIoThreadCount = 0;
 
            var watch = Stopwatch.StartNew();
            watch.Start();
 
            callback();
 
            while (awailableWorkingThreadCount != maxWorkerThreads)
            {
                Thread.Sleep(500);
                ThreadPool.GetAvailableThreads(out awailableWorkingThreadCount, out awailableAsyncIoThreadCount);
 
                Console.WriteLine("[Alive] working thread: {0}, async IO thread: {1}", awailableWorkingThreadCount, awailableAsyncIoThreadCount);
            }
 
            watch.Stop();
            Console.WriteLine("[Finsih] current awailible working thread is {0} and used {1}ms", awailableWorkingThreadCount, watch.ElapsedMilliseconds);
        }
    }
}
```

*注：Async/Await并没有创建新的线程，而是基于当前同步上线文的线程，相比Thread/Task或者是基于线程的BackgroundWorker使用起来更方便。Async关键字的作用是标识在Await处需要等待方法执行完成，过多的await不会导致编译器错误，但如果没有await时，方法将转换为同步方法.*  

# 基于IIS Host的应用程序 

![img](https://images2017.cnblogs.com/blog/426766/201708/426766-20170824203559527-299951795.png)

 \1. IIS 可以托管ThreadPool，通过在IIS Application Pool中增加，并且可以设置Working Thread 和 Async IO Thread 数目。

\2. 服务端接受请求并从线程池中获取当前闲置的线程进行处理，如果是同步处理请求，当前线程等待处理完成然后返回给线程池. 服务器线程数量有限，当超过IIS所能处理的最大请求时，将返回503错误。

\3. 服务端接受请求并异步处理请求时，当遇到异步IO类型操作时，当前线程返回给线程池。当异步操作完成时，从线程池中拿到新的线程并继续执行任务，直至完成后续任务[^7]。

 

例如，在MVC Controller中加入awaitable方法，证明当遇到阻塞任务时，当前线程立即返回线程池。当阻塞任务完成时，将从线程池中获取新的线程执行后续任务：

 

 　　　*;*

        var availableWorkingThreadCount = 0
        var availableAsyncIoThreadCount = 0;
    
        ThreadPool.GetAvailableThreads(out availableWorkingThreadCount, out availableAsyncIoThreadCount);
    
        AddErrors(new IdentityResult(string.Format("[IIS Host] Thread Id {0}, ThreadPool Thread: {1}",
    
        Thread.CurrentThread.ManagedThreadId, Thread.CurrentThread.IsThreadPoolThread)));
        AddErrors(new IdentityResult(string.Format("[IIS Host] current working thread: {0}, current async thread: {1}", availableWorkingThreadCount, availableAsyncIoThreadCount)));
        xHttpClient httpClient = new HttpClient();​    var response = httpClient.GetStringAsync("https://msdn.microsoft.com/en-us/library/system.threading.thread.isthreadpoolthread(v=vs.110).aspx");
        await response;
        AddErrors(new IdentityResult(string.Format("[IIS Host] Thread Id {0}, ThreadPool Thread: {1}",
        Thread.CurrentThread.ManagedThreadId, Thread.CurrentThread.IsThreadPoolThread)));

 

*[IIS Host] Thread Id 4, ThreadPool Thread: True*

*[IIS Host] current working thread: 4094, current async thread: 1000*

*[IIS Host] Thread Id 9, ThreadPool Thread: True*

 



结论：

- 同步方法应用场景：

- - 请求处理非常快
  - 代码简洁大于代码效率
  - 主要是基于CPU耗时操作

 

- 异步方法应用场景：

- - 基于Network或者I/O类型操作，而非CPU耗时操作
  - 当阻塞操作成为瓶颈时，通过异步方法能使IIS处理更多的请求
  - 并行化处理比代码简洁更重要
  - 提供一种机制可以让用户取消长时间运行的请求 

 

#  更多线程优化

Stephen Cleary 介绍了三种异步编程模型的规范[^5]:

\1. Avoid Async Void， void和task<T>将产生不同的异常类型

\2. 总是使用Async关键字

\3. 使用Task.WaitXXX 代替Task.WhenXXX

\4. Configure context 尽量不要捕捉线程上下文，使用Task.ConfigureAwait(false)

 

# **引用**

[^1] 《CLR via C# Edition3》 25章线程基础

[^2]百科-蜜蜂舞：http://baike.baidu.com/link?url=ixwDjgocRIg4MJGTQyR3mUC1fspHZtfPYEtADfJAJdC6X0xIVU4lJUe2iVvCNHEj3JeE1JalBCNyyPcVMdhaoyBFz_xXcLPMEJ_2iUcHjithF8_F8A9yI61EAzpmpYR4

[^3] 异步编程模型：https://msdn.microsoft.com/en-us/library/mt674882.aspx

[^4] C# Async、Await关键字：https://msdn.microsoft.com/library/hh191443(vs.110).aspx

[^5] Task Best Practice[Stephen Cleary]: https://msdn.microsoft.com/en-us/magazine/jj991977.aspx

[^6] 异步编程模型最佳实践中文翻译版：http://www.cnblogs.com/farb/p/4842920.html

[^7] 同步vs异步Controller：[https://msdn.microsoft.com/en-us/library/ee728598%28v=vs.100%29.aspx](https://msdn.microsoft.com/en-us/library/ee728598(v=vs.100).aspx)

[^8] IIS 优化： https://docs.microsoft.com/en-us/aspnet/mvc/overview/performance/using-asynchronous-methods-in-aspnet-mvc-4

 

作者：Stephen Cui
出处：[http://www.cnblogs.com/cuiyansong](https://www.cnblogs.com/cuiyansong/p/URL)

*版权声明：文章属于本人及博客园共有，凡是没有标注[转载]的，请在文章末尾加入我的博客地址。*