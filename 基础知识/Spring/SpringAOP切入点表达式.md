```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--创建service-->
    <bean id="accountService" class="com.sunny.service.Impl.AccountServiceImpl"></bean>
 
    <!--创建记录日志的工具类（切面类、通知类）-->
    <bean id="logger" class="com.sunny.utils.Logger"></bean>
 
    <!--
    Aop配置
    1、aop：pointcut 配合切入点表达式
     作用：Spring在创建容器的时候，对符合切入点表达式的类自动生成代理对象。
    2、aop:aspect  配置切面类
     ref  引用的切面类(日志工具类)
     aop:before 前置通知，在执行目标对象方法之前执行
        method：对应Logger切面类的方法
        pointcut-ref：对应切入点表达式对象
    -->
    <aop:config>
        <!--配置切入点表达式-->
        <aop:pointcut id="pt" expression="execution(* com.sunny.service.Impl.AccountServiceImpl.save())"/>
        <!--配置切面-->
        <aop:aspect ref="logger">
            <!--前置通知，在执行目标对象方法之前执行-->
            <aop:before method="printLog" pointcut-ref="pt"/>
        </aop:aspect>
    </aop:config>
</beans>
```

### 语法：

​         						访问修饰符      				返回值类型（必填）   		包和类        							  方法（必填）

  execution(		modifiers-pattern?		 ret-type-pattern 				declaring-type-pattern?		name-pattern(param-pattern) throws-pattern?) 

```
<!--
 切入点表达式：
第一：语法
            访问修饰符          返回值类型（必填）     包和类               方法（必填）
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)
        throws-pattern?)
第二：作用
     对符合切入点表达式的类，会自动生成代理对象。
第三：应用
1.最全的写法
拦截返回void，指定类的指定方法，参数必须有两个：int、String
execution(public void com.sunny.service.Impl.AccountServiceImpl.save(int,java.lang.String))
2.省略访问修饰符，返回值任意的指定类的save方法，无参数
execution(*  com.sunny.service.Impl.AccountServiceImpl.save())
3.拦截com包下所有的类、以及其子包下所有的类的save（）方法
execution(void  com..*.save())  包名与类名或方法名称都可以使用*
4.拦截save（）方法/拦截所有方法
execution(* save())  拦截save（）
execution(* *())  拦截所有方法
5.不拦截save（）方法
!execution(* save())
  not execution(* save())  注意not前面要有空格
6.拦截save（）方法或者update（）方法
execution(* save()) || execution(* update()))
execution(* save()) or execution(* update()))
7.拦截所有方法，参数任意，但必须有参数
execution(* *(*))
8.拦截所有方法，参数任意，参数可有可无
execution(* *(..))
9.对IOC容器中以Service结尾的类，生成代理对象
bean(*Service)
10.最常用
execution(* com.sunny..*ServiceImpl.*(..))
表示com.sunny包及其子包下所有的以ServiceImpl结尾的类生成代理对象
-->
```

> **最常用:
>   execution(\* com.itheima..\*ServiceImpl.\*(..))
>   表示com.itheima包及其所有子包下所有的以ServiceImpl结尾的类生成代理对象。** 

### 2.1 常用标签说明

> 1. <aop:config> 作用：声明aop配置
>
> 2. <aop:pointcut> 作用：配置切入点表达式  属性：id：唯一标识切入点表达式名称 expression：定义切入点表达式
>
> 3. <aop:aspect> 作用：配置切面 属性：id：唯一标识切面的名称 ref：引用切面类（通知类）bean的id
>
> 4. <aop:before> 作用：配置前置通知（在执行目标对象方法之前执行） 属性：method:指定通知方法名称 pointcut：定义切入点表达式 pointcut-ref:引用切入点表达式的id。
> 5. <aop:returning> 作用：配置后置通知 属性：method：指定通知方法名称 pointcut：定义切入点表达式 point-ref:引用切入点表达式的id
> 6. <aop:after-throwing> 作用：配置异常通知 属性：method：指定通知方法名称 pointcut:定义切入点表达式 pointcut-ref:引用切入点表达式的id
> 7. <aop:after> 作用：配置最终通知 属性：method：指定通知方法名称 pointcut：定义切入点表达式 pointcut-ref:引用切入点表达式的id
> 8.  <aop:around> 作用：配置环绕通知 属性：method：指定通知方法名称 pointcut：定义切入点表达式 pointcut-ref:引用切入点表达式的id