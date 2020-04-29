# Spring概念

Spring是分层的 Java SE/EE应用 full-stack 轻量级开源框架，以 IoC（Inverse Of Control： 反转控制）和 AOP（Aspect Oriented Programming：面向切面编程）为内核，提供了展现层 Spring MVC 和持久层 Spring JDBC 以及业务层事务管理等众多的企业级应用技术，还能整合开源世界众多 著名的第三方框架和类库，逐渐成为使用最多的Java EE 企业应用开源框架。 

# Spring优势

## 方便解耦，简化开发  

通过 Spring提供的 IoC容器，可以将对象间的依赖关系交由 Spring进行控制，避免硬编码所造 成的过度程序耦合。用户也不必再为单例模式类、属性文件解析等这些很底层的需求编写代码，可以更专注于上层的应用。 

## AOP编程的支持  

通过 Spring的 AOP 功能，方便进行面向切面的编程，许多不容易用传统OOP 实现的功能可以通过 AOP 轻松应付。 

## 声明式事务的支持  

可以将我们从单调烦闷的事务管理代码中解脱出来，通过声明式方式灵活的进行事务的管理， 提高开发效率和质量。 

## 方便程序的测试  

可以用非容器依赖的编程方式进行几乎所有的测试工作，测试不再是昂贵的操作，而是随手可 做的事情。 

## 方便集成各种优秀框架  

Spring可以降低各种框架的使用难度，提供了对各种优秀框架（Struts、Hibernate、Hessian、Quartz 等）的直接支持。 

## 降低 JavaEE API的使用难度  

Spring对 JavaEE API（如 JDBC、JavaMail、远程调用等）进行了薄薄的封装层，使这些 API 的 使用难度大为降低。 

## Java源码是经典学习范例  

Spring的源代码设计精妙、结构清晰、匠心独用，处处体现着大师对Java 设计模式灵活运用以 及对 Java技术的高深造诣。它的源代码无意是 Java 技术的最佳实践的范例

# Spring体系结构

![image-20200423151924223](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200423151924223.png)

# quick start（XML）

1. 配置jar包
2. 给配置文件beam.xml并导入约束

```xml
<?xml version="1.0" encoding="UTF-8"?> 
<beans xmlns="http://www.springframework.org/schema/beans"     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"     xsi:schemaLocation="http://www.springframework.org/schema/beans      http://www.springframework.org/schema/beans/spring-beans.xsd"> 
  <bean id="" class=""</bean>
</beans> 
```

3. 依赖注入（DI）

```xml
<!--构造函数注入：
        使用的标签:constructor-arg
        标签出现的位置：bean标签的内部
        标签中的属性
            type：用于指定要注入的数据的数据类型，该数据类型也是构造函数中某个或某些参数的类型
            index：用于指定要注入的数据给构造函数中指定索引位置的参数赋值。索引的位置是从0开始
            name：用于指定给构造函数中指定名称的参数赋值                                        常用的
            =============以上三个用于指定给构造函数中哪个参数赋值===============================
            value：用于提供基本类型和String类型的数据
            ref：用于指定其他的bean类型数据。它指的就是在spring的Ioc核心容器中出现过的bean对象

        优势：
            在获取bean对象时，注入数据是必须的操作，否则对象无法创建成功。
        弊端：
            改变了bean对象的实例化方式，使我们在创建对象时，如果用不到这些数据，也必须提供。
    -->
<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
    <constructor-arg name="name" value="泰斯特"></constructor-arg>
    <constructor-arg name="age" value="18"></constructor-arg>
    <constructor-arg name="birthday" ref="now"></constructor-arg>
</bean>
<!-- set方法注入                更常用的方式
        涉及的标签：property
        出现的位置：bean标签的内部
        标签的属性
            name：用于指定注入时所调用的set方法名称
            value：用于提供基本类型和String类型的数据
            ref：用于指定其他的bean类型数据。它指的就是在spring的Ioc核心容器中出现过的bean对象
        优势：
            创建对象时没有明确的限制，可以直接使用默认构造函数
        弊端：
            如果有某个成员必须有值，则获取对象是有可能set方法没有执行。
    -->
<bean id="accountService2" class="com.itheima.service.impl.AccountServiceImpl2">
    <property name="name" value="TEST" ></property>
    <property name="age" value="21"></property>
    <property name="birthday" ref="now"></property>
</bean>
 <!-- 复杂类型的注入/集合类型的注入
     用于给List结构集合注入的标签：
         list array set
     用于个Map结构集合注入的标签:
         map  props
     结构相同，标签可以互换
-->
<bean id="accountService3" class="com.itheima.service.impl.AccountServiceImpl3">
        <property name="myStrs">
            <set>
                <value>AAA</value>
                <value>BBB</value>
                <value>CCC</value>
            </set>
        </property>
    
        <property name="myProps">
            <map>
                <entry key="testA" value="aaa"></entry>
                <entry key="testB">
                    <value>BBB</value>
                </entry>
            </map>
        </property>
    </bean>
```

4. 方法中导入配置文件并使用

```java
public static void main(String[] args) {
        //1.获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
		//ApplicationContext ac = new FileSystemXmlApplicationContext("C:\\bean.xml");
        //2.根据id获取Bean对象
        IAccountService as  = (IAccountService)ac.getBean("accountService");
        IAccountDao adao = ac.getBean("accountDao",IAccountDao.class);
        System.out.println(as);
        System.out.println(adao);
        as.saveAccount();
```

# bean.xml配置实例

```xml
<?xml version="1.0" encoding="UTF-8"?> 
<beans xmlns="http://www.springframework.org/schema/beans"     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xsi:schemaLocation="http://www.springframework.org/schema/beans         http://www.springframework.org/schema/beans/spring-beans.xsd">     
    
 <!-- 配置 service -->  
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">   			<property name="accountDao" ref="accountDao"></property> 
    </bean> 
 
 <!-- 配置 dao -->  
    <bean id="accountDao" class="com.itheima.dao.impl.AccountDaoImpl"> 
        <property name="dbAssit" ref="dbAssit"></property> 
    </bean>  
    
 <!-- 配置 dbAssit 此处我们只注入了数据源，表明每条语句独立事务--> 
    <bean id="dbAssit" class="com.itheima.dbassit.DBAssit"> 
    	<property name="dataSource" ref="dataSource"></property> 
    </bean> 
    
 <!-- 配置数据源 -->  
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource"> 
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>  
        <property name="jdbcUrl" value="jdbc:mysql:///spring_day02"></property>   				<property name="user" value="root"></property> 
        <property name="password" value="1234"></property>  
    </bean> 
</beans>
```

# quick start（注解）

## 1. 导入maven坐标

## 2. 使用@Component 注解配置管理的资源 

   ### 常用注解：

**用于注入数据的** 

   相当于：<property name="" ref=""> <property name="" value=""> 

   **@Component** 

   **@Controller：**一般用于表现层的注解。  

   **@Service**：一般用于业务层的注解。  

   **@Repository：**一般用于持久层的注解。

   **@Autowired ：**自动按照类型注入。当使用注解注入属性时，set方法可以省略。它只能注入其他 bean 类型。当有多个 类型匹配时，使用要注入的对象变量名称作为 bean 的 id，在 spring 容器查找，找到了也可以注入成功。找不到 就报错。 

**@Qualifier ：**

   作用： 在自动按照类型注入的基础之上，再按照 Bean 的 id 注入。它在给字段注入时不能独立使用，必须和 @Autowire 一起使用；但是给方法参数注入时，可以独立使用。 属性：  value：指定 bean 的 id。 

   **@Resource** 
   作用：  直接按照 Bean 的 id 注入。它也只能注入其他 bean 类型。 属性：  name：指定 bean 的 id。

**@Value** 

   作用：  注入基本数据类型和 String 类型数据的 属性：  value：用于指定值

   **用于改变作用范围的：** 
    相当于：<bean id="" class="" scope=""> 
**@Scope** 
   作用：  指定 bean 的作用范围。 属性：  value：指定范围的值。      取值：singleton  prototype request session globalsession。

**和生命周期相关的**

   相当于：<bean id="" class="" init-method="" destroy-method="" /> 

**@PostConstruct** 
   作用：  用于指定初始化方法。 

**@PreDestroy** 

   作用：  用于指定销毁方法

```java
@Component("accountService") 
public class AccountServiceImpl implements IAccountService { 
```

## 3. 创建 spring 的 xml配置文件并开启对注解的支持 

```xml
<?xml version="1.0" encoding="UTF-8"?> <beans xmlns="http://www.springframework.org/schema/beans"  xmlns:context="http://www.springframework.org/schema/context" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"     xsi:schemaLocation="http://www.springframework.org/schema/beans         http://www.springframework.org/schema/beans/spring-beans.xsd 
        http://www.springframework.org/schema/context         http://www.springframework.org/schema/context/spring-context.xsd"> 
     
 <!-- 告知 spring 创建容器时要扫描的包 -->  
    <context:component-scan base-package="com.itheima"></context:component-scan>   
 <!-- 配置 dbAssit -->  
    <bean id="dbAssit" class="com.itheima.dbassit.DBAssit">   
        <property name="dataSource" ref="dataSource"></property> 
 	</bean>   
 <!-- 配置数据源 -->  
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">   
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>   				<property name="jdbcUrl" value="jdbc:mysql:///spring_day02"></property>   
        <property name="user" value="root"></property>   
        <property name="password" value="1234"></property>  
    </bean> 
</beans> 
```

# spring的纯注解配置 （去除bean.xml）

##  新注解说明 

**@Configuration** 
作用：  用于指定当前类是一个 spring 配置类，当创建容器时会从该类上加载注解。获取容器时需要使用 AnnotationApplicationContext(有@Configuration 注解的类.class)。 

属性：  value:用于指定配置类的字节码 

**@ComponentScan** 
作用：  用于指定 spring 在初始化容器时要扫描的包。作用和在 spring 的 xml 配置文件中的： <context:component-scan base-package="com.itheima"/>是一样的。 

属性：  basePackages：用于指定要扫描的包。和该注解中的 value 属性作用一样。 

```java
@Configuration 
@ComponentScan("com.itheima") 
public class SpringConfiguration { 
}
```

 **@Bean** 
作用：  该注解只能写在方法上，表明使用此方法创建一个对象，并且放入 spring 容器。 

属性：  name：给当前@Bean 注解方法创建的对象指定一个名称(即 bean 的 id）。 

```java
public class JdbcConfig { 
 
 /** 
  * 创建一个数据源，并存入 spring 容器中   * @return   */  
    @Bean(name="dataSource")  
    public DataSource createDataSource() {   
        try {    
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setUser("root");
            ds.setPassword("1234");
            ds.setDriverClass("com.mysql.jdbc.Driver");
            ds.setJdbcUrl("jdbc:mysql:///spring_day02");
            return ds;   
        } catch (Exception e) {    
            throw new RuntimeException(e);   
        }  
    }  
 
 /** 
  * 创建一个 DBAssit，并且也存入 spring 容器中   * @param dataSource   * @return   */  @Bean(name="dbAssit")  
    public DBAssit createDBAssit(DataSource dataSource) {   
        return new DBAssit(dataSource);  
    }  
}
```

**@PropertySource** 

作用： 用于加载.properties 文件中的配置。例如我们配置数据源时，可以把连接数据库的信息写到 properties 配置文件中，就可以使用此注解指定 properties 配置文件的位置。 

属性：  value[]：用于指定 properties 文件位置。如果是在类路径下，需要写上 classpath: 

```java
@Configuration 
@PropertySource("classpath:jdbc.properties") 
public class JdbcConfig{ 
}
```

**@Import** 
作用：  用于导入其他配置类，在引入其他配置类时，可以不用再写@Configuration 注解。当然，写上也没问 题。 属性：  value[]：用于指定其他配置类的字节码。 

```java
@Configuration 
@ComponentScan(basePackages = "com.itheima.spring") 
@Import({JdbcConfig.class}) 
public class SpringConfiguration { 
} 
```

## 通过注解获取容器： 

ApplicationContext ac =  new AnnotationConfigApplicationContext(SpringConfiguration.class); 

## 工程结构

![image-20200423185915039](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200423185915039.png)

# Spring整合Junit

## 问题

在测试类中，每个测试方法都有以下两行代码： 

```java
ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");  
IAccountService as = ac.getBean("accountService",IAccountService.class); 
```

 这两行代码的作用是获取容器，如果不写的话，直接会提示空指针异常。所以又不能轻易删掉。 

## 解决办法

​		针对上述问题，我们需要的是程序能自动帮我们创建容器。一旦程序能自动为我们创建 spring 容器，我们就 无须手动创建了，问题也就解决了。 

​		我们都知道，junit 单元测试的原理（在 web 阶段课程中讲过），但显然，junit 是无法实现的，因为它自 己都无法知晓我们是否使用了 spring 框架，更不用说帮我们创建 spring 容器了。不过好在，junit 给我们暴露 了一个注解，可以让我们替换掉它的运行器。 

​		这时，我们需要依靠 spring 框架，因为它提供了一个运行器，可以读取配置文件（或注解）来创建容器。我 们只需要告诉它配置文件在哪就行了。

## 操作

1. 导入依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.2.5.RELEASE</version>
</dependency>
```

2. 使用@RunWith 注解替换原有运行器 
3. 使用@ContextConfiguration 指定 spring 配置文件的位置 

```java
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations= {"classpath:bean.xml"}) 
public class AccountServiceTest { 
} 

@ContextConfiguration 注解：  
    locations 属性：用于指定配置文件的位置。如果是类路径下，需要用 classpath:表明  
    classes 属性：用于指定注解的类。当不使用 xml 配置时，需要用此属性指定注解类的位置
```

4. 使用@Autowired 给测试类中的变量注入数据 

```java
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations= {"classpath:bean.xml"}) 
public class AccountServiceTest {    
    
    @Autowired 
 	private IAccountService as ; 
} 
 
```

5. 为什么不把测试类（IAccountService as）配到 xml 中 

   ​		在解释这个问题之前，先解除大家的疑虑，配到 XML 中能不能用呢？ 答案是肯定的，没问题，可以使用。 那么为什么不采用配置到 xml 中的方式呢？ 这个原因是这样的：  

   ​		第一：当我们在 xml 中配置了一个 bean，spring 加载配置文件创建容器时，就会创建对象。  

   ​		第二：测试类只是我们在测试功能时使用，而在项目中它并不参与程序逻辑，也不会解决需求上的问 题，所以创建完了，并没有使用。那么存在容器中就会造成资源的浪费。  

   ​		所以，基于以上两点，我们不应该把测试配置到 xml 文件中。



# 动态代理

字节码随用随创建，随用随加载。  它与静态代理的区别也在于此。因为静态代理是字节码一上来就创建好，并完成加载。  装饰者模式就是静态代理的一种体现。 

**基于接口的动态代理** 	

提供者：JDK 官方的 Proxy 类。  要求：被代理类最少实现一个接口。 

```java
 /**
         * 动态代理：
         *  特点：字节码随用随创建，随用随加载
         *  作用：不修改源码的基础上对方法增强
         *  分类：
         *      基于接口的动态代理
         *      基于子类的动态代理
         *  基于接口的动态代理：
         *      涉及的类：Proxy
         *      提供者：JDK官方
         *  如何创建代理对象：
         *      使用Proxy类中的newProxyInstance方法
         *  创建代理对象的要求：
         *      被代理类最少实现一个接口，如果没有则不能使用
         *  newProxyInstance方法的参数：
         *      ClassLoader：类加载器
         *          它是用于加载代理对象字节码的。和被代理对象使用相同的类加载器。固定写法。
         *      Class[]：字节码数组
         *          它是用于让代理对象和被代理对象有相同方法。固定写法。
         *      InvocationHandler：用于提供增强的代码
         *          它是让我们写如何代理。我们一般都是些一个该接口的实现类，通常情况下都是匿名内部类，但不是必须的。
         *          此接口的实现类都是谁用谁写。
         */
       IProducer proxyProducer = (IProducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 作用：执行被代理对象的任何接口方法都会经过该方法
                     * 方法参数的含义
                     * @param proxy   代理对象的引用
                     * @param method  当前执行的方法
                     * @param args    当前执行方法所需的参数
                     * @return        和被代理对象方法有相同的返回值
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //提供增强的代码
                        Object returnValue = null;

                        //1.获取方法执行的参数
                        Float money = (Float)args[0];
                        //2.判断当前方法是不是销售
                        if("saleProduct".equals(method.getName())) {
                            returnValue = method.invoke(producer, money*0.8f);
                        }
                        return returnValue;
                    }
                });
        proxyProducer.saleProduct(10000f);
    }
```

**基于子类的动态代理**  

提供者：第三方的 CGLib，如果报 asmxxxx 异常，需要导入 asm.jar。  

要求：被代理类不能用 final 修饰的类（最终类）

```java
public class Client {

    public static void main(String[] args) {
        final Producer producer = new Producer();

        /**
         * 动态代理：
         *  特点：字节码随用随创建，随用随加载
         *  作用：不修改源码的基础上对方法增强
         *  分类：
         *      基于接口的动态代理
         *      基于子类的动态代理
         *  基于子类的动态代理：
         *      涉及的类：Enhancer
         *      提供者：第三方cglib库
         *  如何创建代理对象：
         *      使用Enhancer类中的create方法
         *  创建代理对象的要求：
         *      被代理类不能是最终类
         *  create方法的参数：
         *      Class：字节码
         *          它是用于指定被代理对象的字节码。
         *
         *      Callback：用于提供增强的代码
         *          它是让我们写如何代理。我们一般都是些一个该接口的实现类，通常情况下都是匿名内部类，但不是必须的。
         *          此接口的实现类都是谁用谁写。
         *          我们一般写的都是该接口的子接口实现类：MethodInterceptor
         */
        Producer cglibProducer = (Producer)Enhancer.create(producer.getClass(), new MethodInterceptor() {
            /**
             * 执行被代理对象的任何方法都会经过该方法
             * @param proxy
             * @param method
             * @param args
             *    以上三个参数和基于接口的动态代理中invoke方法的参数是一样的
             * @param methodProxy ：当前执行方法的代理对象
             * @return
             * @throws Throwable
             */
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                //提供增强的代码
                Object returnValue = null;

                //1.获取方法执行的参数
                Float money = (Float)args[0];
                //2.判断当前方法是不是销售
                if("saleProduct".equals(method.getName())) {
                    returnValue = method.invoke(producer, money*0.8f);
                }
                return returnValue;
            }
        });
        cglibProducer.saleProduct(12000f);
    }
}
```

# AOP（xml）

## 使用时要导入maven坐标，修改bean.xml约束。

AOP：全称是 Aspect Oriented Programming 即：面向切面编程。 

简单的说它就是把我们程序重复的代码抽取出来，在需要执行的时候，使用动态代理的技术，在不修改源码的
基础上，对我们的已有方法进行增强。

> **Joinpoint(连接点):**   所谓连接点是指那些被拦截到的点。在 spring 中,这些点指的是方法,因为 spring 只支持方法类型的 连接点。 
>
> **Pointcut(切入点):**   所谓切入点是指我们要对哪些 Joinpoint 进行拦截的定义。 
>
> **Advice(通知/增强):**   
>
> ​		所谓通知是指拦截到 Joinpoint 之后所要做的事情就是通知。    
>
> ​		通知的类型：前置通知,后置通知,异常通知,最终通知,环绕通知。 
>
> **Introduction(引介):**   引介是一种特殊的通知在不修改类代码的前提下, Introduction 可以在运行期为类动态地添加一些方 法或 Field。 
>
> **Target(目标对象):**   代理的目标对象。
>
> **Weaving(织入):**   是指把增强应用到目标对象来创建新的代理对象的过程。   
>
> ​		spring 采用动态代理织入，而 AspectJ 采用编译期织入和类装载期织入。 
>
> **Proxy（代理）**:   一个类被 AOP 织入增强后，就产生一个结果代理类。 
>
> **Aspect(切面):**   是切入点和通知（引介）的结合

**通俗来说**

> **连接点：**Service层的类的所有方法 
>
> **切入点：**连接点中被加强的方法就是切入点 
>
> **通知/增强：**TransactionManager中的增强的方法（进行事务控制的方法，如commit，rollback等） 
>
> **引介：** 目标对象：被代理的对象 
>
> **织入：**代理invoke方法中对目标对象进行增强的过程 
>
> **代理对象**：将目标对象增强后返回的对象 
>
> **切面：**切入点和通知（引介）的结合，自我理解是一种通知方法在invoke方法中调用的顺序的安排，即把通知方法切入进去时机和地点的安排。

## 配置步骤

### 第一步：把通知类用 bean 标签配置起来 

```xml
<!-- 配置通知 --> 
 <bean id="txManager" class="com.itheima.utils.TransactionManager">  
     <property name="dbAssit" ref="dbAssit"></property> 
</bean>
```

### 第二步：使用 aop:config 声明 aop 配置 

```xml
aop:config: 
 作用：用于声明开始 aop 的配置  
<aop:config> 
<!-- 配置的代码都写在此处 -->  
</aop:config> 
```

### 第三步：使用 aop:aspect 配置切面 

```xml
aop:aspect: 
 作用：   用于配置切面。  
 属性：   id：给切面提供一个唯一标识。   
 ref：引用配置好的通知类 bean 的 id。 
 
<aop:aspect id="txAdvice" ref="txManager"> 
  <!--配置通知的类型要写在此处--> 
</aop:aspect> 
```

###  第四步：使用 aop:pointcut 配置切入点表达式 

```xml
aop:pointcut：  
作用：   用于配置切入点表达式。就是指定对哪些类的哪些方法进行增强。  
属性：   expression：用于定义切入点表达式。   
id：用于给切入点表达式提供一个唯一标识 
<aop:pointcut expression="execution(  
               public void com.itheima.service.impl.AccountServiceImpl.transfer(    
               java.lang.String, java.lang.String, java.lang.Float) )" id="pt1"/> 
```

### 第五步：使用 aop:xxx 配置对应的通知类型 

```xml
aop:before 
作用：   用于配置前置通知。指定增强的方法在切入点方法之前执行   
属性：   method:用于指定通知类中的增强方法名称   
		ponitcut-ref：用于指定切入点的表达式的引用   
		poinitcut：用于指定切入点表达式   
执行时间点：   切入点方法执行之前执行 
<aop:before method="beginTransaction" pointcut-ref="pt1"/>  

aop:after-returning 
作用：   用于配置后置通知  
属性：   method：指定通知中方法的名称。   
pointct：定义切入点表达式   
pointcut-ref：指定切入点表达式的引用  
执行时间点：   切入点方法正常执行之后。它和异常通知只能有一个执行 
<aop:after-returning method="commit" pointcut-ref="pt1"/> 
 
aop:after-throwing 
作用：   用于配置异常通知  
属性：   method：指定通知中方法的名称。   
pointct：定义切入点表达式   
pointcut-ref：指定切入点表达式的引用  
执行时间点：   切入点方法执行产生异常后执行。它和后置通知只能执行一个 
<aop:after-throwing method="rollback" pointcut-ref="pt1"/>   

aop:after 
 作用：   用于配置最终通知  
属性：   method：指定通知中方法的名称。   
pointct：定义切入点表达式   
pointcut-ref：指定切入点表达式的引用  
执行时间点：   无论切入点方法执行时是否有异常，它都会在其后面执行。 
<aop:after method="release" pointcut-ref="pt1"/> 
```

### 实例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 配置srping的Ioc,把service对象配置进来-->
    <bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl"></bean>


    <!-- 配置Logger类 -->
    <bean id="logger" class="com.itheima.utils.Logger"></bean>

    <!--配置AOP-->
    <aop:config>
        <!-- 配置切入点表达式 id属性用于指定表达式的唯一标识。expression属性用于指定表达式内容
              此标签写在aop:aspect标签内部只能当前切面使用。
              它还可以写在aop:aspect外面，此时就变成了所有切面可用
          -->
        <aop:pointcut id="pt1" expression="execution(* com.itheima.service.impl.*.*(..))"></aop:pointcut>
        <!--配置切面 -->
        <aop:aspect id="logAdvice" ref="logger">
            <!-- 配置前置通知：在切入点方法执行之前执行
            <aop:before method="beforePrintLog" pointcut-ref="pt1" ></aop:before>-->

            <!-- 配置后置通知：在切入点方法正常执行之后值。它和异常通知永远只能执行一个
            <aop:after-returning method="afterReturningPrintLog" pointcut-ref="pt1"></aop:after-returning>-->

            <!-- 配置异常通知：在切入点方法执行产生异常之后执行。它和后置通知永远只能执行一个
            <aop:after-throwing method="afterThrowingPrintLog" pointcut-ref="pt1"></aop:after-throwing>-->

            <!-- 配置最终通知：无论切入点方法是否正常执行它都会在其后面执行
            <aop:after method="afterPrintLog" pointcut-ref="pt1"></aop:after>-->

            <!-- 配置环绕通知 详细的注释请看Logger类中-->
            <aop:around method="aroundPringLog" pointcut-ref="pt1"></aop:around>
        </aop:aspect>
    </aop:config>

</beans>
```



### 切入点表达式说明 

execution:匹配方法的执行(常用) 

execution(表达式) 表达式语法：execution([修饰符] 返回值类型 包名.类名.方法名(参数)) 
写法说明： 

全匹配方式：

```
public void com.itheima.service.impl.AccountServiceImpl.saveAccount(com.itheima.domain.Account) 
```
访问修饰符可以省略   

```
void com.itheima.service.impl.AccountServiceImpl.saveAccount(com.itheima.domain.Account) 
```
 返回值可以使用*号，表示任意返回值  

```
* com.itheima.service.impl.AccountServiceImpl.saveAccount(com.itheima.domain.Account) 
```

 包名可以使用\*号，表示任意包，但是有几级包，需要写几个*  

```
* *.*.*.*.AccountServiceImpl.saveAccount(com.itheima.domain.Account) 
```
使用..来表示当前包，及其子包   * 
```
* com..AccountServiceImpl.saveAccount(com.itheima.domain.Account) 
```
 类名可以使用*号，表示任意类  
 ```
 * com..*.saveAccount(com.itheima.domain.Account) 
 ```
 方法名可以使用*号，表示任意方法   
 ```
 * com..*.*( com.itheima.domain.Account) 
 ```
 参数列表可以使用*，表示参数可以是任意数据类型，但是必须有参数  *
```
* com..*.*(*)
```
 参数列表可以使用..表示有无参数均可，有参数可以是任意类型   
 ```
 * com..*.*(..) 
 ```
 全通配方式： 
```
* *..*.*(..) 
```
注：  通常情况下，我们都是对业务层的方法进行增强，所以切入点表达式都是切到业务层实现类。
```
execution(* com.itheima.service.impl.*.*(..))
```

### 环绕通知：

**在代码中手动控制增强方法如何运行的一种通知方法。**

```xml
配置方式: 
<aop:config>  
        <aop:pointcut expression="execution(* com.itheima.service.impl.*.*(..))" id="pt1"/>   
    	<aop:aspect id="txAdvice" ref="txManager"> 
            <!-- 配置环绕通知 -->   
            <aop:around method="transactionAround" pointcut-ref="pt1"/> 
     	</aop:aspect> 
</aop:config> 
 
aop:around：  
作用：   用于配置环绕通知  
属性：   method：指定通知中方法的名称。   
pointct：定义切入点表达式   
pointcut-ref：指定切入点表达式的引用  
说明：   它是 spring 框架为我们提供的一种可以在代码中手动控制增强代码什么时候执行的方式。  
注意：   通常情况下，环绕通知都是独立使用的 
```

环绕通知实例

```java
/** 
 * 环绕通知  * @param pjp 
 *  spring 框架为我们提供了一个接口：ProceedingJoinPoint，它可以作为环绕通知的方法参数。  
 *  在环绕通知执行时，spring 框架会为我们提供该接口的实现类对象，我们直接使用就行。  
 * @return  */ 
public Object transactionAround(ProceedingJoinPoint pjp) { 
 //定义返回值  
    Object rtValue = null;  
    try {   
        //获取方法执行所需的参数   
        Object[] args = pjp.getArgs(); 
        //前置通知：开启事务   
        beginTransaction(); 
        //执行方法   
        rtValue = pjp.proceed(args); 
        //后置通知：提交事务   
        commit();  
    }catch(Throwable e) {   
        //异常通知：回滚事务   
        rollback();   
        e.printStackTrace();  
    }finally { 
  		//最终通知：释放资源   
        release();  
    }  
    return rtValue; 
} 
 
```

# AOP（注解）

## 环境搭建

### 第一步：准备必要的代码和 jar包 （坐标）

### 第二步：在配置文件中导入 context 的名称空间 

```xml
<?xml version="1.0" encoding="UTF-8"?> 
<beans xmlns="http://www.springframework.org/schema/beans" 
xmlns:aop="http://www.springframework.org/schema/aop" xmlns:context="http://www.springframework.org/schema/context"     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"     xsi:schemaLocation="http://www.springframework.org/schema/beans         http://www.springframework.org/schema/beans/spring-beans.xsd         http://www.springframework.org/schema/aop         http://www.springframework.org/schema/aop/spring-aop.xsd 
http://www.springframework.org/schema/context         http://www.springframework.org/schema/context/spring-context.xsd"
</beans>
```

### 第三步：把资源使用注解配置 

```java
@Service("accountService") 
public class AccountServiceImpl implements IAccountService {   		
    @Autowired  
	private IAccountDao accountDao; 
} 
 
// 账户的持久层实现类  
@Repository("accountDao") public class AccountDaoImpl  implements IAccountDao { 
 
 	  @Autowired  
      private DBAssit dbAssit ; 
}
```

### 第四步：在配置文件中指定 spring 要扫描的包 

```xml
<!-- 告知 spring，在创建容器时要扫描的包 --> 
<context:component-scan base-package="com.itheima"></context:component-scan>  
```

## 配置步骤 

### 第一步：把通知类也使用注解配置 

```java
@Component("txManager") 
public class TransactionManager { 
 //定义一个 DBAssit  
 @Autowired  
 private DBAssit dbAssit ;  
 }
```

### 第二步：在通知类上使用@Aspect 注解声明为切面 

```java
@Component("txManager") 
@Aspect//表明当前类是一个切面类 
public class TransactionManager {   
 //定义一个 DBAssit  
    @Autowired  
    private DBAssit dbAssit ; 
}
```

###  第三步：在增强的方法上使用注解配置通知 

```java
@Before 
 作用：   把当前方法看成是前置通知。  
 属性：   value：用于指定切入点表达式，还可以指定切入点表达式的引用。   
 //开启事务  
 	@Before("execution(* com.itheima.service.impl.*.*(..))")  
     public void beginTransaction() {   
     try {    
         dbAssit.getCurrentConnection().setAutoCommit(false);   
     } catch (SQLException e) {   
         e.printStackTrace();   
     }  
 } 
 
 @AfterReturning 
 作用：   把当前方法看成是后置通知。  属性：   value：用于指定切入点表达式，还可以指定切入点表达式的引用 
 
 //提交事务  
     @AfterReturning("execution(* com.itheima.service.impl.*.*(..))")  
     public void commit() { 
     try {    
         dbAssit.getCurrentConnection().commit();   
     } catch (SQLException e) {    
         e.printStackTrace();   
     } 
 }   
 
 @AfterThrowing 
 	作用：   把当前方法看成是异常通知。  
    属性：    value：用于指定切入点表达式，还可以指定切入点表达式的引用  //回滚事务  
        @AfterThrowing("execution(* com.itheima.service.impl.*.*(..))")  
        public void rollback() {   
        try {    
            dbAssit.getCurrentConnection().rollback();   
        } catch (SQLException e) {    
            e.printStackTrace();   
        }  
    } 
 
@After 
 作用：   把当前方法看成是最终通知。  属性：   value：用于指定切入点表达式，还可以指定切入点表达式的引用  //释放资源  
     @After("execution(* com.itheima.service.impl.*.*(..))")  
     public void release() {   
     try {    
         dbAssit.releaseConnection();   
     } catch (Exception e) {    
         e.printStackTrace();   
     }  
 }
```

### 第四步：在 spring 配置文件中开启 spring 对注解 AOP 的支持 

```xml
<!-- 开启 spring 对注解 AOP 的支持 --> 
<aop:aspectj-autoproxy/> 
```

##  环绕通知注解配置 

```java
@Around 
作用：  把当前方法看成是环绕通知。 
属性：  value：用于指定切入点表达式，还可以指定切入点表达式的引用。 
 
@Around("execution(* com.itheima.service.impl.*.*(..))")  
public Object transactionAround(ProceedingJoinPoint pjp) { 
  //定义返回值   
    Object rtValue = null;   
    try { 
   //获取方法执行所需的参数    
        Object[] args = pjp.getArgs(); 
   //前置通知：开启事务    
        beginTransaction(); 
   //执行方法    
        rtValue = pjp.proceed(args); 
   //后置通知：提交事务    
        commit();   
    }catch(Throwable e) { 
   //异常通知：回滚事务    
        rollback();    
        e.printStackTrace();   
    }finally {    
        //最终通知：释放资源    
        release();   
    }   
    return rtValue;  
}
```

## 切入点表达式注解 

```java
@Pointcut 
作用：  指定切入点表达式 属性： 
value：指定表达式的内容 
 
@Pointcut("execution(* com.itheima.service.impl.*.*(..))") 
private void pt1() {	} 
 
引用方式：  
//环绕通知
@Around("pt1()")//注意：千万别忘了写括号  
public Object transactionAround(ProceedingJoinPoint pjp) {   
    //定义返回值   
    Object rtValue = null;   
    try { 
   		//获取方法执行所需的参数    
        Object[] args = pjp.getArgs(); 
   		//前置通知：开启事务    
        beginTransaction(); 
   		//执行方法    
        rtValue = pjp.proceed(args); 
   		//后置通知：提交事务    
        commit();   
    }catch(Throwable e) { 
        //异常通知：回滚事务    
        rollback();    
        e.printStackTrace();   
    }finally {    
        //最终通知：释放资源    
        release();   
    }   
    return rtValue;  
}
```

## 不使用 XML的配置方式 

```java
@Configuration 
@ComponentScan(basePackages="com.itheima") 
@EnableAspectJAutoProxy 
public class SpringConfiguration { }
```

> 注意：基于注解配置时，会出现事务顺序错误问题，可使用环绕通知解决。
>
> ![image-20200423235045122](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200423235045122.png)

#  JdbcTemplate 

在 spring-jdbc-5.0.2.RELEASE.jar 中，我们在导包的时候，除了要导入这个 jar 包 外，还需要导入一个 spring-tx-5.0.2.RELEASE.jar（它是和事务相关的）。 

```java
JdbcTemplate.execute("insert into account(name,money)values('eee',500)"); 
```

```java
JdbcTemplate.update("insert into account(name,money)values(?,?)","fff",5000); 
```

```
jt.update("delete from account where id = ?",6); 
```

```java
List<Account> accounts = JdbcTemplate().query("select * from account where name = ?",new BeanPropertyRowMapper<Account>(Account.class),accountName);
```

##  JdbcDaoSupport 

```java
public class AccountDaoImpl2 extends JdbcDaoSupport implements IAccountDao { 
 
 	@Override  
    public Account findAccountById(Integer id) {   
     //getJdbcTemplate()方法是从父类上继承下来的。   
     List<Account> list = getJdbcTemplate().query("select * from account where id = ? ",new AccountRowMapper(),id); 
 	return list.isEmpty()?null:list.get(0);  
}
```

```xml
<!-- 配置 dao2 --> 
<bean id="accountDao2" class="com.itheima.dao.impl.AccountDaoImpl2"> 
 	<!-- 注入 dataSource -->  
    <property name="dataSource" ref="dataSource"></property> 
</bean>
```

Dao继承 JdbcDaoSupport 的方式，只能用于基于 XML 的方式，注解用不了。 

# Spring中的事务控制 

## PlatformTransactionManager 

此接口是 spring 的事务管理器，它里面提供了我们常用的操作事务的方法，如下图： 

![image-20200424163528183](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200424163528183.png)

我们在开发中都是使用它的实现类，```org.springframework.jdbc.datasource.DataSourceTransactionManager``` 使用 Spring JDBC 或 iBatis 进行持久化数据时使用 

此接口提供的是事务具体的运行状态，方法介绍如下图： 

![image-20200424163653813](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200424163653813.png)

 

## 基于 XML 的声明式事务控制（配置方式）重点 

### 环境搭建 

#### 第一步：拷贝必要的 jar 包到工程的 lib 目录 

![image-20200424163815006](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200424163815006.png)

####  第二步：创建 spring 的配置文件并导入约束 

```xml
此处需要导入 aop 和 tx 两个名称空间 
<?xml version="1.0" encoding="UTF-8"?> 
<beans xmlns="http://www.springframework.org/schema/beans"      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"      xmlns:aop="http://www.springframework.org/schema/aop"      xmlns:tx="http://www.springframework.org/schema/tx"      xsi:schemaLocation="http://www.springframework.org/schema/beans         			
http://www.springframework.org/schema/beans/spring-beans.xsd 
http://www.springframework.org/schema/tx         http://www.springframework.org/schema/tx/spring-tx.xsd           http://www.springframework.org/schema/aop  
http://www.springframework.org/schema/aop/spring-aop.xsd"> 
</beans>
```

####  第三步：准备数据库表和实体类 

####  第四步：编写业务层接口和实现类 

#### 第五步：编写 Dao 接口和实现类 

#### 第六步：在配置文件中配置业务层和持久层对 

### 配置步骤 

####  第一步：配置事务管理器 

```xml
<!-- 配置一个事务管理器 --> 
<bean id="transactionManager" 		class="org.springframework.jdbc.datasource.DataSourceTransactionManager"> 
	<!-- 注入 DataSource -->  
    <property name="dataSource" ref="dataSource"></property> 
</bean> 
```

#### 第二步：配置事务的通知引用事务管理器

```xml
<!-- 事务的配置 --> 
<tx:advice id="txAdvice" transaction-manager="transactionManager"> </tx:advice> 
```

#### 第三步：配置事务的属性 

```xml
<!--在 tx:advice 标签内部 配置事务的属性 --> 
<tx:attributes> 
<!-- 指定方法名称：是业务核心方法   
read-only：是否是只读事务。默认 false，不只读。  
isolation：指定事务的隔离级别。默认值是使用数据库的默认隔离级别。   
propagation：指定事务的传播行为。  
timeout：指定超时时间。默认值为：-1。永不超时。  
rollback-for：用于指定一个异常，当执行产生该异常时，事务回滚。产生其他异常，事务不回滚。 没有默认值，任何异常都回滚。  
no-rollback-for：用于指定一个异常，当产生该异常时，事务不回滚，产生其他异常时，事务回 滚。没有默认值，任何异常都回滚。  -->  
    <tx:method name="*" read-only="false" propagation="REQUIRED"/>  
    <tx:method name="find*" read-only="true" propagation="SUPPORTS"/> 
</tx:attributes>
```

#### 第四步：配置 AOP 切入点表达式 

```xml
<!-- 配置 aop --> 
<aop:config> 
 	<!-- 配置切入点表达式 -->  
    <aop:pointcut expression="execution(* com.itheima.service.impl.*.*(..))" id="pt1"/> 
</aop:config>
```

#### 第五步：配置切入点表达式和事务通知的对应关系 

```xml
<!-- 在 aop:config标签内部：建立事务的通知和切入点表达式的关系 --> 
<aop:advisor advice-ref="txAdvice" pointcut-ref="pt1"/> 
```

## 基于注解的配置方式 

### 环境搭建

#### 第一步：拷贝必备的 jar 包到工程的 lib 目录 

![image-20200424164440821](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200424164440821.png)

#### 第二步：创建 spring 的配置文件导入约束并配置扫描的包 

```xml
<?xml version="1.0" encoding="UTF-8"?> <beans xmlns="http://www.springframework.org/schema/beans"     xmlns:aop="http://www.springframework.org/schema/aop"     xmlns:tx="http://www.springframework.org/schema/tx"     xmlns:context="http://www.springframework.org/schema/context"       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"        
xsi:schemaLocation="http://www.springframework.org/schema/beans              http://www.springframework.org/schema/beans/spring-beans.xsd 
http://www.springframework.org/schema/aop          http://www.springframework.org/schema/aop/spring-aop.xsd          http://www.springframework.org/schema/tx           http://www.springframework.org/schema/tx/spring-tx.xsd          http://www.springframework.org/schema/context         http://www.springframework.org/schema/context/spring-context.xsd">
    
 <!-- 配置 spring 创建容器时要扫描的包 -->  
    <context:component-scan base-package="com.itheima"></context:component-scan> 
 
 <!-- 配置 JdbcTemplate-->
 <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate"> 
  	<property name="dataSource" ref="dataSource"></property>  
 </bean> 
 
 <!-- 配置 spring 提供的内置数据源 -->  
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">   
        <property name="driverClassName" 
value="com.mysql.jdbc.Driver"></property>   
        <property name="url" 
value="jdbc:mysql://localhost:3306/spring_day02"></property>   
        <property name="username" value="root"></property>   
        <property name="password" value="1234"></property>  
    </bean>  
</beans>
```

#### 第三步：创建数据库表和实体类 

####  第四步：创建业务层接口和实现类并使用注解让 spring 管理  	

```java
@Service("accountService") 
public class AccountServiceImpl implements IAccountService {  
    @Autowired  
    private IAccountDao accountDao; 
 	//其余代码和基于 XML 的配置相同 
}
```

####  第五步：创建 Dao 接口和实现类并使用注解让 spring 管理 

### 配置步骤 

#### 第一步：配置事务管理器并注入数据源 

```xml
<!-- 配置事务管理器 -->  
<bean id="transactionManager" 
class="org.springframework.jdbc.datasource.DataSourceTransactionManager">   
    <property name="dataSource" ref="dataSource"></property>  
</bean>
```

#### 第二步：在业务层使用@Transactional 注解 

```java
@Service("accountService") 
@Transactional(readOnly=true,propagation=Propagation.SUPPORTS) 
public class AccountServiceImpl implements IAccountService {   
 	@Autowired  
    private IAccountDao accountDao; 
 
 	@Override  
    public Account findAccountById(Integer id) {   
        return accountDao.findAccountById(id);  
    } 
 
 	@Override  
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)  
    public void transfer(String sourceName, String targeName, Float money) { 
  
        //1.根据名称查询两个账户   
        Account source = accountDao.findAccountByName(sourceName);   
        Account target = accountDao.findAccountByName(targeName); 
  
        //2.修改两个账户的金额   
        source.setMoney(source.getMoney()-money);
        //转出账户减钱   
        target.setMoney(target.getMoney()+money);
        //转入账户加钱   
        //3.更新两个账户   
        accountDao.updateAccount(source);   
        //int i=1/0; 
  
        accountDao.updateAccount(target); 
   } 
} 
 
该注解的属性和 xml 中的属性含义一致。该注解可以出现在接口上，类上和方法上。 
    出现接口上，表示该接口的所有实现类都有事务支持。 
    出现在类上，表示类中所有方法有事务支持 出现在方法上，表示方法有事务支持。 
    以上三个位置的优先级：方法>类>接口
```

#### 第三步：在配置文件中开启 spring 对注解事务的支持 

```xml
<!-- 开启 spring 对注解事务的支持 --> 
<tx:annotation-driven transaction-manager="transactionManager"/>  
```

## 不使用 xml的配置方式 

```java
@Configuration 
@EnableTransactionManagement 
public class SpringTxConfiguration { 
 //里面配置数据源，配置 JdbcTemplate,配置事务管理器。在之前的步骤已经写过了。 
}
```

