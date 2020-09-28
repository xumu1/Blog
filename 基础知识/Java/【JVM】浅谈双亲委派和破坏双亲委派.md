转自 https://www.cnblogs.com/joemsu/p/9310226.html

<div id="cnblogs_post_body" class="blogpost-body cnblogs-markdown"><blockquote><h4>目录</h4><a href="#_caption_0"><strong>一、前言</strong></a><br><a href="#_caption_1"><strong>二、双亲委派</strong></a><br>2.1、为什么需要双亲委派<br>2.2、双亲委派的实现<br><a href="#_caption_2"><strong>三、破坏双亲委派</strong></a><br>3.1、为什么需要破坏双亲委派？<br>3.2、破坏双亲委派的实现<br><a href="#_caption_3"><strong>四、总结</strong></a><br></blockquote>
    <a name="_caption_0"></a><h2 id="一、前言">一、前言</h2>
<p>笔者曾经阅读过周志明的《深入理解Java虚拟机》这本书，阅读完后自以为对jvm有了一定的了解，然而当真正碰到问题的时候，才发现自己读的有多粗糙，也体会到只有实践才能加深理解，正应对了那句话——“Talk is cheap, show me the code”。前段时间，笔者同事提出了一个关于类加载器破坏双亲委派的问题，以我们常见到的数据库驱动Driver为例，为什么要实现破坏双亲委派，下面一起来重温一下。</p>
<br>
<a name="_caption_1"></a><h2 id="二、双亲委派">二、双亲委派</h2>
<p>想要知道为什么要破坏双亲委派，就要先从什么是双亲委派说起，在此之前，我们先要了解一些概念：</p>
<ul>
<li>对于任意一个类，都需要由<strong>加载它的类加载器</strong>和这个<strong>类本身</strong>来一同确立其在Java虚拟机中的<strong>唯一性</strong>。</li>
</ul>
<p>什么意思呢？我们知道，判断一个类是否相同，通常用equals()方法，isInstance()方法和isAssignableFrom()方法。来判断，对于同一个类，如果没有采用相同的类加载器来加载，在调用的时候，会产生意想不到的结果：</p>
<pre><code class="language-java hljs"><span class="hljs-keyword">public</span> <span class="hljs-class"><span class="hljs-keyword">class</span> <span class="hljs-title">DifferentClassLoaderTest</span> </span>{

    <span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">void</span> <span class="hljs-title">main</span><span class="hljs-params">(String[] args)</span> <span class="hljs-keyword">throws</span> Exception </span>{
        ClassLoader classLoader = <span class="hljs-keyword">new</span> ClassLoader() {
            <span class="hljs-meta">@Override</span>
            <span class="hljs-keyword">public</span> Class&lt;?&gt; loadClass(String name) <span class="hljs-keyword">throws</span> ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf(<span class="hljs-string">"."</span>) + <span class="hljs-number">1</span>) + <span class="hljs-string">".class"</span>;
                InputStream stream = getClass().getResourceAsStream(fileName);
                <span class="hljs-keyword">if</span> (stream == <span class="hljs-keyword">null</span>) {
                    <span class="hljs-keyword">return</span> <span class="hljs-keyword">super</span>.loadClass(name);
                }
                <span class="hljs-keyword">try</span> {
                    <span class="hljs-keyword">byte</span>[] b = <span class="hljs-keyword">new</span> <span class="hljs-keyword">byte</span>[stream.available()];
                    <span class="hljs-comment">// 将流写入字节数组b中</span>
                    stream.read(b);
                    <span class="hljs-keyword">return</span> defineClass(name, b, <span class="hljs-number">0</span>, b.length);
                } <span class="hljs-keyword">catch</span> (IOException e) {
                    e.printStackTrace();
                }

                <span class="hljs-keyword">return</span> <span class="hljs-keyword">super</span>.loadClass(name);
            }
        };
        Object obj = classLoader.loadClass(<span class="hljs-string">"jvm.DifferentClassLoaderTest"</span>).newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj <span class="hljs-keyword">instanceof</span> DifferentClassLoaderTest);

    }

}
</code></pre>

<p>输出结果：</p>
<pre><code class="language-java hljs"><span class="hljs-class"><span class="hljs-keyword">class</span> <span class="hljs-title">jvm</span>.<span class="hljs-title">DifferentClassLoaderTest</span>
<span class="hljs-title">false</span>
</span></code></pre>
<p>如果在通过classLoader实例化的使用，直接转化成DifferentClassLoaderTest对象：</p>
<pre><code class="language-java hljs">DifferentClassLoaderTest obj = (DifferentClassLoaderTest) classLoader.loadClass(<span class="hljs-string">"jvm.DifferentClassLoaderTest"</span>).newInstance();
</code></pre>
<p>就会直接报<code> java.lang.ClassCastException:</code>，因为两者不属于同一类加载器加载，所以不能转化！</p>
<br>
<h3 id="21、为什么需要双亲委派">2.1、为什么需要双亲委派</h3>
<p>基于上述的问题：如果不是同一个类加载器加载，即时是相同的class文件，也会出现判断不想同的情况，从而引发一些意想不到的情况，为了保证相同的class文件，在使用的时候，是相同的对象，jvm设计的时候，采用了双亲委派的方式来加载类。</p>
<blockquote>
<p>双亲委派：如果一个类加载器收到了加载某个类的请求,则该类加载器并不会去加载该类,而是把这个请求委派给父类加载器,每一个层次的类加载器都是如此,因此所有的类加载请求最终都会传送到顶端的启动类加载器;只有当父类加载器在其搜索范围内无法找到所需的类,并将该结果反馈给子类加载器,子类加载器会尝试去自己加载。</p>
</blockquote>
<p>这里有几个流程要注意一下：</p>
<ol>
<li>子类先委托父类加载</li>
<li>父类加载器有自己的<strong>加载范围</strong>，范围内没有找到，则不加载，并返回给子类</li>
<li>子类在收到父类无法加载的时候，才会自己去加载</li>
</ol>
<p>jvm提供了三种系统加载器：</p>
<ol>
<li>启动类加载器（Bootstrap ClassLoader）：C++实现，在java里无法获取，<strong>负责加载&lt;JAVA_HOME&gt;/lib</strong>下的类。</li>
<li>扩展类加载器（Extension ClassLoader）： Java实现，可以在java里获取，<strong>负责加载&lt;JAVA_HOME&gt;/lib/ext</strong>下的类。</li>
<li>系统类加载器/应用程序类加载器（Application ClassLoader）：是与我们接触对多的类加载器，我们写的代码默认就是由它来加载，ClassLoader.getSystemClassLoader返回的就是它。</li>
</ol>
<p>附上三者的关系：</p>
<p><img src="https://images2018.cnblogs.com/blog/1256203/201807/1256203-20180714171531925-1737231049.png" alt="双亲委派图" loading="lazy"></p>
<br>
<h3 id="22、双亲委派的实现">2.2、双亲委派的实现</h3>
<p>双亲委派的实现其实并不复杂，其实就是一个递归，我们一起来看一下<code>ClassLoader</code>里的代码：</p>
<pre><code class="language-java hljs"><span class="hljs-keyword">protected</span> Class&lt;?&gt; loadClass(String name, <span class="hljs-keyword">boolean</span> resolve)
    <span class="hljs-keyword">throws</span> ClassNotFoundException
    {
    	<span class="hljs-comment">// 同步上锁</span>
        <span class="hljs-keyword">synchronized</span> (getClassLoadingLock(name)) {
            <span class="hljs-comment">// 先查看这个类是不是已经加载过</span>
            Class&lt;?&gt; c = findLoadedClass(name);
            <span class="hljs-keyword">if</span> (c == <span class="hljs-keyword">null</span>) {
                <span class="hljs-keyword">long</span> t0 = System.nanoTime();
                <span class="hljs-keyword">try</span> {
                	<span class="hljs-comment">// 递归，双亲委派的实现，先获取父类加载器，不为空则交给父类加载器</span>
                    <span class="hljs-keyword">if</span> (parent != <span class="hljs-keyword">null</span>) {
                        c = parent.loadClass(name, <span class="hljs-keyword">false</span>);
                    <span class="hljs-comment">// 前面提到，bootstrap classloader的类加载器为null，通过find方法来获得</span>
                    } <span class="hljs-keyword">else</span> {
                        c = findBootstrapClassOrNull(name);
                    }
                } <span class="hljs-keyword">catch</span> (ClassNotFoundException e) {
                }

                <span class="hljs-keyword">if</span> (c == <span class="hljs-keyword">null</span>) {
                    <span class="hljs-comment">// 如果还是没有获得该类，调用findClass找到类</span>
                    <span class="hljs-keyword">long</span> t1 = System.nanoTime();
                    c = findClass(name);

                    <span class="hljs-comment">// jvm统计</span>
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            <span class="hljs-comment">// 连接类</span>
            <span class="hljs-keyword">if</span> (resolve) {
                resolveClass(c);
            }
            <span class="hljs-keyword">return</span> c;
        }
    }

</code></pre>
<br>
<a name="_caption_2"></a><h2 id="三、破坏双亲委派">三、破坏双亲委派</h2>

<h3 id="31、为什么需要破坏双亲委派？">3.1、为什么需要破坏双亲委派？</h3>
<p>因为在某些情况下父类加载器需要委托子类加载器去加载class文件。受到加载范围的限制，父类加载器无法加载到需要的文件，以Driver接口为例，由于Driver接口定义在jdk当中的，而其实现由各个数据库的服务商来提供，比如mysql的就写了<code>MySQL&nbsp;Connector</code>，那么问题就来了，DriverManager（也由jdk提供）要加载各个实现了Driver接口的实现类，然后进行管理，但是DriverManager由启动类加载器加载，只能记载JAVA_HOME的lib下文件，而其实现是由服务商提供的，由系统类加载器加载，这个时候就需要启动类加载器来委托子类来加载Driver实现，从而破坏了双亲委派，这里仅仅是举了破坏双亲委派的其中一个情况。</p>
<h3 id="32、破坏双亲委派的实现">3.2、破坏双亲委派的实现</h3>
<p>我们结合Driver来看一下在spi（Service Provider Inteface）中如何实现破坏双亲委派。</p>
<p>先从DriverManager开始看，平时我们通过DriverManager来获取数据库的Connection：</p>
<pre><code class="language-java hljs">String url = <span class="hljs-string">"jdbc:mysql://localhost:3306/testdb"</span>;
Connection conn = java.sql.DriverManager.getConnection(url, <span class="hljs-string">"root"</span>, <span class="hljs-string">"root"</span>); 
</code></pre>
<p>在调用DriverManager的时候，会先初始化类，调用其中的静态块：</p>
<pre><code class="language-java hljs"><span class="hljs-keyword">static</span> {
    loadInitialDrivers();
    println(<span class="hljs-string">"JDBC DriverManager initialized"</span>);
}

<span class="hljs-function"><span class="hljs-keyword">private</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">void</span> <span class="hljs-title">loadInitialDrivers</span><span class="hljs-params">()</span> </span>{
...
<span class="hljs-comment">// 加载 Driver 的实现类</span>
AccessController.doPrivileged(<span class="hljs-keyword">new</span> PrivilegedAction&lt;Void&gt;() {
<span class="hljs-function"><span class="hljs-keyword">public</span> Void <span class="hljs-title">run</span><span class="hljs-params">()</span> </span>{

                ServiceLoader&lt;Driver&gt; loadedDrivers = ServiceLoader.load(Driver.class);
                Iterator&lt;Driver&gt; driversIterator = loadedDrivers.iterator();
                <span class="hljs-keyword">try</span>{
                    <span class="hljs-keyword">while</span>(driversIterator.hasNext()) {
                        driversIterator.next();
                    }
                } <span class="hljs-keyword">catch</span>(Throwable t) {
                }
                <span class="hljs-keyword">return</span> <span class="hljs-keyword">null</span>;
            }
        });
    ...

}
</code></pre>

<p>为了节约空间，笔者省略了一部分的代码，重点来看一下<code>ServiceLoader.load(Driver.class)</code>：</p>
<pre><code class="language-java hljs"><span class="hljs-keyword">public</span> <span class="hljs-keyword">static</span> &lt;S&gt; <span class="hljs-function">ServiceLoader&lt;S&gt; <span class="hljs-title">load</span><span class="hljs-params">(Class&lt;S&gt; service)</span> </span>{
    <span class="hljs-comment">// 获取当前线程中的上下文类加载器</span>
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    <span class="hljs-keyword">return</span> ServiceLoader.load(service, cl);
}
</code></pre>
<p>可以看到，load方法调用获取了当前线程中的上下文类加载器，那么上下文类加载器放的是什么加载器呢？</p>
<pre><code class="language-java hljs"><span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-title">Launcher</span><span class="hljs-params">()</span> </span>{
	...
    <span class="hljs-keyword">try</span> {
        <span class="hljs-keyword">this</span>.loader = Launcher.AppClassLoader.getAppClassLoader(var1);
    } <span class="hljs-keyword">catch</span> (IOException var9) {
        <span class="hljs-keyword">throw</span> <span class="hljs-keyword">new</span> InternalError(<span class="hljs-string">"Could not create application class loader"</span>, var9);
    }
    Thread.currentThread().setContextClassLoader(<span class="hljs-keyword">this</span>.loader);
    ...
}
</code></pre>
<p>在<code>sun.misc.Launcher</code>中，我们找到了答案，在Launcher初始化的时候，会获取AppClassLoader，然后将其设置为上下文类加载器，而这个AppClassLoader，就是之前上文提到的系统类加载器Application ClassLoader，所以<strong>上下文类加载器默认情况下就是系统加载器</strong>。</p>
<p>继续来看下<code>ServiceLoader.load(service, cl)</code>：</p>
<pre><code class="language-java hljs"><span class="hljs-keyword">public</span> <span class="hljs-keyword">static</span> &lt;S&gt; <span class="hljs-function">ServiceLoader&lt;S&gt; <span class="hljs-title">load</span><span class="hljs-params">(Class&lt;S&gt; service,
                                        ClassLoader loader)</span></span>{
    <span class="hljs-keyword">return</span> <span class="hljs-keyword">new</span> ServiceLoader&lt;&gt;(service, loader);
}

<span class="hljs-function"><span class="hljs-keyword">private</span> <span class="hljs-title">ServiceLoader</span><span class="hljs-params">(Class&lt;S&gt; svc, ClassLoader cl)</span> </span>{
service = Objects.requireNonNull(svc, <span class="hljs-string">"Service interface cannot be null"</span>);
<span class="hljs-comment">// ClassLoader.getSystemClassLoader()返回的也是系统类加载器</span>
loader = (cl == <span class="hljs-keyword">null</span>) ? ClassLoader.getSystemClassLoader() : cl;
acc = (System.getSecurityManager() != <span class="hljs-keyword">null</span>) ? AccessController.getContext() : <span class="hljs-keyword">null</span>;
reload();
}

<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">void</span> <span class="hljs-title">reload</span><span class="hljs-params">()</span> </span>{
providers.clear();
lookupIterator = <span class="hljs-keyword">new</span> LazyIterator(service, loader);
}
</code></pre>

<p>上面这段就不解释了，比较简单，然后就是看LazyIterator迭代器：</p>
<pre><code class="language-java hljs"><span class="hljs-keyword">private</span> <span class="hljs-class"><span class="hljs-keyword">class</span> <span class="hljs-title">LazyIterator</span> <span class="hljs-keyword">implements</span> <span class="hljs-title">Iterator</span>&lt;<span class="hljs-title">S</span>&gt;</span>{
    <span class="hljs-comment">// ServiceLoader的iterator()方法最后调用的是这个迭代器里的next</span>
    <span class="hljs-function"><span class="hljs-keyword">public</span> S <span class="hljs-title">next</span><span class="hljs-params">()</span> </span>{
        <span class="hljs-keyword">if</span> (acc == <span class="hljs-keyword">null</span>) {
            <span class="hljs-keyword">return</span> nextService();
        } <span class="hljs-keyword">else</span> {
            PrivilegedAction&lt;S&gt; action = <span class="hljs-keyword">new</span> PrivilegedAction&lt;S&gt;() {
                <span class="hljs-function"><span class="hljs-keyword">public</span> S <span class="hljs-title">run</span><span class="hljs-params">()</span> </span>{ <span class="hljs-keyword">return</span> nextService(); }
            };
            <span class="hljs-keyword">return</span> AccessController.doPrivileged(action, acc);
        }
    }
    
    <span class="hljs-function"><span class="hljs-keyword">private</span> S <span class="hljs-title">nextService</span><span class="hljs-params">()</span> </span>{
        <span class="hljs-keyword">if</span> (!hasNextService())
            <span class="hljs-keyword">throw</span> <span class="hljs-keyword">new</span> NoSuchElementException();
        String cn = nextName;
        nextName = <span class="hljs-keyword">null</span>;
        Class&lt;?&gt; c = <span class="hljs-keyword">null</span>;
        <span class="hljs-comment">// 根据名字来加载类</span>
        <span class="hljs-keyword">try</span> {
            c = Class.forName(cn, <span class="hljs-keyword">false</span>, loader);
        } <span class="hljs-keyword">catch</span> (ClassNotFoundException x) {
            fail(service,
                 <span class="hljs-string">"Provider "</span> + cn + <span class="hljs-string">" not found"</span>);
        }
        <span class="hljs-keyword">if</span> (!service.isAssignableFrom(c)) {
            fail(service,
                 <span class="hljs-string">"Provider "</span> + cn  + <span class="hljs-string">" not a subtype"</span>);
        }
        <span class="hljs-keyword">try</span> {
            S p = service.cast(c.newInstance());
            providers.put(cn, p);
            <span class="hljs-keyword">return</span> p;
        } <span class="hljs-keyword">catch</span> (Throwable x) {
            fail(service,
                 <span class="hljs-string">"Provider "</span> + cn + <span class="hljs-string">" could not be instantiated"</span>,
                 x);
        }
        <span class="hljs-keyword">throw</span> <span class="hljs-keyword">new</span> Error();          <span class="hljs-comment">// This cannot happen</span>
    }
    
    <span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">boolean</span> <span class="hljs-title">hasNext</span><span class="hljs-params">()</span> </span>{
        <span class="hljs-keyword">if</span> (acc == <span class="hljs-keyword">null</span>) {
            <span class="hljs-keyword">return</span> hasNextService();
        } <span class="hljs-keyword">else</span> {
            PrivilegedAction&lt;Boolean&gt; action = <span class="hljs-keyword">new</span> PrivilegedAction&lt;Boolean&gt;() {
                <span class="hljs-function"><span class="hljs-keyword">public</span> Boolean <span class="hljs-title">run</span><span class="hljs-params">()</span> </span>{ <span class="hljs-keyword">return</span> hasNextService(); }
            };
            <span class="hljs-keyword">return</span> AccessController.doPrivileged(action, acc);
        }
    }
    
    
    <span class="hljs-function"><span class="hljs-keyword">private</span> <span class="hljs-keyword">boolean</span> <span class="hljs-title">hasNextService</span><span class="hljs-params">()</span> </span>{
        <span class="hljs-keyword">if</span> (nextName != <span class="hljs-keyword">null</span>) {
            <span class="hljs-keyword">return</span> <span class="hljs-keyword">true</span>;
        }
        <span class="hljs-keyword">if</span> (configs == <span class="hljs-keyword">null</span>) {
            <span class="hljs-keyword">try</span> {
                <span class="hljs-comment">// 在classpath下查找META-INF/services/java.sql.Driver名字的文件夹</span>
                <span class="hljs-comment">// private static final String PREFIX = "META-INF/services/";</span>
                String fullName = PREFIX + service.getName();
                <span class="hljs-keyword">if</span> (loader == <span class="hljs-keyword">null</span>)
                    configs = ClassLoader.getSystemResources(fullName);
                <span class="hljs-keyword">else</span>
                    configs = loader.getResources(fullName);
            } <span class="hljs-keyword">catch</span> (IOException x) {
                fail(service, <span class="hljs-string">"Error locating configuration files"</span>, x);
            }
        }
        <span class="hljs-keyword">while</span> ((pending == <span class="hljs-keyword">null</span>) || !pending.hasNext()) {
            <span class="hljs-keyword">if</span> (!configs.hasMoreElements()) {
                <span class="hljs-keyword">return</span> <span class="hljs-keyword">false</span>;
            }
            pending = parse(service, configs.nextElement());
        }
        nextName = pending.next();
        <span class="hljs-keyword">return</span> <span class="hljs-keyword">true</span>;
    }

}
</code></pre>

<p>好了，这里基本就差不多完成整个流程了，一起走一遍：</p>
<p><img src="https://images2018.cnblogs.com/blog/1256203/201807/1256203-20180714171604349-2005119436.png" alt="spi加载过程" loading="lazy"></p>
<br>
<a name="_caption_3"></a><h2 id="四、总结">四、总结</h2>
<p>Driver剩余的加载过程就省略了，有兴趣的园友可以继续深入了解一下，不得不说，jvm博大精深，看起来容易，真正到了用起来才发现各种问题，也只有实践才能加深理解，最后谢谢各位园友观看，如果有描述不对的地方欢迎指正，与大家共同进步！</p>
<br>
<br>
<br>
<p>参考部分：</p>
<ul>
<li><a href="https://blog.csdn.net/yangcheng33/article/details/52631940">https://blog.csdn.net/yangcheng33/article/details/52631940</a></li>
<li>周志明-《深入理解JAVA虚拟机：JVM高级特性与最佳实践》</li>
</ul>

</div>
