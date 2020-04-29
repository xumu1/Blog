# SpringMVC 在三层架构的位置 

![image-20200425181826578](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200425181826578.png)

# SpringMVC 的优势 

1、清晰的角色划分：  

​	前端控制器（DispatcherServlet）  

​	请求到处理器映射（HandlerMapping）  

​	处理器适配器（HandlerAdapter）  

​	视图解析器（ViewResolver）  

​	处理器或页面控制器（Controller）  

​	验证器（ Validator）  

​	命令对象（Command  请求参数绑定到的对象就叫命令对象）  

​	表单对象（Form Object 提供给表单展示和提交到的对象就叫表单对象）。 

2、分工明确，而且扩展点相当灵活，可以很容易扩展，虽然几乎不需要。 

3、由于命令对象就是一个 POJO，无需继承框架特定 API，可以使用命令对象直接作为业务对象。 

4、和 Spring 其他框架无缝集成，是其它 Web 框架所不具备的。 

5、可适配，通过 HandlerAdapter 可以支持任意的类作为处理器。 

6、可定制性，HandlerMapping、ViewResolver 等能够非常简单的定制。 

7、功能强大的数据验证、格式化、绑定机制。 

8、利用 Spring 提供的 Mock 对象能够非常简单的进行 Web 层单元测试。 

9、本地化、主题的解析的支持，使我们更容易进行国际化和主题的切换。 

10、强大的 JSP 标签库，使 JSP 编写更容易。 ………………还有比如RESTful风格的支持、简单的文件上传、约定大于配置的契约式编程支持、基于注解的零配 置支持等等。

# quick start

## 1.创建一个web工程

## 2.导入maven坐标

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <spring.version>5.0.2.RELEASE</spring.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>servlet-api</artifactId>
      <version>2.5</version>
      <scope>provided</scope>
    </dependency>

    <dependency>
      <groupId>javax.servlet.jsp</groupId>
      <artifactId>jsp-api</artifactId>
      <version>2.0</version>
      <scope>provided</scope>
    </dependency>

  </dependencies>
```

## 3.配置核心的控制器（配置DispatcherServlet） 

1. 在web.xml配置文件中核心控制器DispatcherServlet

```xml
 <!--配置前端控制器-->
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
```

2. 编写springmvc.xml的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 开启注解扫描 -->
    <context:component-scan base-package="cn.itcast"/>

    <!-- 视图解析器对象 -->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!-- 开启SpringMVC框架注解的支持 -->
    <mvc:annotation-driven/>

</beans>
```

3. 编写index.jsp和HelloController控制器类 
4. 启动Tomcat服务器，进行测试

**入门案例的流程图**

![image-20200425192152043](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200425192152043.png)

# 入门案例中涉及的组件 

##  DispatcherServlet：前端控制器 

用户请求到达前端控制器，它就相当于 mvc 模式中的 c，dispatcherServlet 是整个流程控制的中心，由 它调用其它组件处理用户的请求，dispatcherServlet 的存在降低了组件之间的耦合性

## Handler：处理器 

它就是我们开发中要编写的具体业务控制器。由 DispatcherServlet 把用户请求转发到 Handler。由 Handler 对具体的用户请求进行处理。 

##  HandlAdapter：处理器适配器 

通过 HandlerAdapter 对处理器进行执行，这是适配器模式的应用，通过扩展适配器可以对更多类型的处理 器进行执行

## View Resolver：视图解析器 

View Resolver 负责将处理结果生成 View 视图，View Resolver 首先根据逻辑视图名解析成物理视图名 即具体的页面地址，再生成 View 视图对象，最后对 View 进行渲染将处理结果通过页面展示给用户。 

## View：视图 

SpringMVC 框架提供了很多的 View 视图类型的支持，包括：jstlView、freemarkerView、pdfView 等。我们最常用的视图就是 jsp。 一般情况下需要通过页面标签或页面模版技术将模型数据通过页面展示给用户，需要由程序员根据业务需求开
发具体的页面。 

##  \<mvc:annotation-driven>说明 

在 SpringMVC 的各个组件中，处理器映射器、处理器适配器、视图解析器称为 SpringMVC 的三大组件。

使用\<mvc:annotation-driven> 自动加载 RequestMappingHandlerMapping （处理映射器）和 RequestMappingHandlerAdapter （ 处 理 适 配 器 ） ， 可 用 在 SpringMVC.xml 配 置 文 件 中 使 用 \<mvc:annotation-driven>替代注解处理器和适配器的配置。 它就相当于在 xml 中配置了： 

```xml
<!-- 上面的标签相当于 如下配置--> 

<!-- Begin --> 
<!-- HandlerMapping --> 
<bean 
class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerM
apping"></bean> 

<bean 
class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"></bean>

<!-- HandlerAdapter --> 
<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerA dapter"></bean> 

<bean class="org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter"></bean> 

<bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"></bean> 

<!-- HadnlerExceptionResolvers --> 
<bean 
class="org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExcept ionResolver"></bean> 

<bean class="org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolv er"></bean> 

<bean class="org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver" ></bean> 
<!-- End --> 
注意：  
	一般开发中，我们都需要写上此标签（虽然从入门案例中看，我们不写也行，随着课程的深入，该标签还 有具体的使用场景）。 
明确：  
	我们只需要编写处理具体业务的控制器以及视图。 
```

## SpringMVC框架基于组件方式执行流程

![image-20200425195435284](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200425195435284.png)

#  RequestMapping 注解 

# 请求参数的绑定 

## 请求参数中文乱码的解决 

```xml
在 web.xml 中配置一个过滤器  
<!--配置解决中文乱码的过滤器-->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <!-- 设置过滤器中的属性值 --> 
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
      <!-- 启动过滤器 -->    
    <init-param>     
      <param-name>forceEncoding</param-name>   
      <param-value>true</param-value>    
    </init-param>   
  </filter>
<!-- 过滤所有请求 --> 
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```

```xml
在 springmvc 的配置文件中可以配置，静态资源不过滤： 
<!-- location 表示路径，mapping 表示文件，**表示该目录下的文件以及子目录的文件 -->   <mvc:resources location="/css/" mapping="/css/**"/>   
<mvc:resources location="/images/" mapping="/images/**"/>   
<mvc:resources location="/scripts/" mapping="/javascript/**"/> 
```

```xml
get 请求方式：  
tomacat 对 GET和 POST 请求处理方式是不同的，GET请求的编码问题，要改 tomcat 的 server.xml 配置文件，如下： 
<Connector connectionTimeout="20000" port="8080"protocol="HTTP/1.1" redirectPort="8443"/> 
改为： 
<Connector connectionTimeout="20000" port="8080"protocol="HTTP/1.1" redirectPort="8443"  
   useBodyEncodingForURI="true"/> 
如果遇到 ajax 请求仍然乱码，请把：  
useBodyEncodingForURI="true"改为 URIEncoding="UTF-8" 即可。
```



## POJO 类中包含集合类型参数 

```java
实体类代码： 
/** 
 * 用户实体类
 */ 
public class User implements Serializable {    
    private String username;  
    private String password;  
    private Integer age;  
    private List<Account> accounts;  
    private Map<String,Account> accountMap;    
    //getters and setters 
 
 	@Override  
    public String toString() {   
        return "User [username=" + username + ", password=" + password + ", age=" + age + ",\n accounts=" + accounts 
    + ",\n accountMap=" + accountMap + "]"; 
    }
}
```

```jsp
jsp 代码： 
<!-- POJO 类包含集合类型演示 --> 
<form action="account/updateAccount" method="post"> 
 	用户名称：<input type="text" name="username" ><br/>  
    用户密码：<input type="password" name="password" ><br/>  
    用户年龄：<input type="text" name="age" ><br/>  
    账户 1 名称：<input type="text" name="accounts[0].name" ><br/>  
    账户 1 金额：<input type="text" name="accounts[0].money" ><br/>  
    账户 2 名称：<input type="text" name="accounts[1].name" ><br/>  
    账户 2 金额：<input type="text" name="accounts[1].money" ><br/>  
    账户 3 名称：<input type="text" name="accountMap['one'].name" ><br/>  
    账户 3 金额：<input type="text" name="accountMap['one'].money" ><br/>  
    账户 4 名称：<input type="text" name="accountMap['two'].name" ><br/>  
    账户 4 金额：<input type="text" name="accountMap['two'].money" ><br/>  
    <input type="submit" value=" 保存 "> 
</form>
```

```java
控制器代码： 
@RequestMapping("/updateAccount") 
public String updateAccount(User user) {  
    System.out.println("更新了账户。。。。"+user);  
    return "success"; 
} 
```

# 自定义类型转换器 

## 使用步骤 

### 第一步：定义一个类，实现 Converter 接口，该接口有两个泛型。 

```java
public interface Converter<S, T> 
{
    //S:表示接受的类型，T：表示目标类型  
    /** 
  	*  实现类型转换的方法  
   */  
    @Nullable  
    T convert(S source); 
} 
//自定义类型转换器 
public class StringToDateConverter implements Converter<String, Date> { 
 //用于把 String 类型转成日期类型
 	@Override  
    public Date convert(String source) {   
        DateFormat format = null;   
        try {    
            if(StringUtils.isEmpty(source)) {     
                throw new NullPointerException("请输入要转换的日期");    
            }    
            format = new SimpleDateFormat("yyyy-MM-dd");    
            Date date = format.parse(source);    
            return date;   
        } catch (Exception e) {    
            throw new RuntimeException("输入日期有误");   
        }  
    } 
}
```

### 第二步：在 spring配置文件中配置类型转换器。 

```xml
<!-- 配置类型转换器工厂 --> 
<bean id="converterService"   class="org.springframework.context.support.ConversionServiceFactoryBean"> 
 <!-- 给工厂注入一个新的类型转换器 -->      
    <property name="converters"> 
      <array> 
       <!-- 配置自定义类型转换器 -->       
          <bean class="com.itheima.web.converter.StringToDateConverter"></bean> 
      </array>      
    </property> 
</bean> 
```

### 第三步：在 annotation-driven标签中引用配置的类型转换服务 

```xml
<!-- 引用自定义类型转换器 --> 
<mvc:annotation-driven conversion-service="converterService"></mvc:annotation-driven> 
```

# 使用ServletAPI 对象作为方法参数 

```
SpringMVC 还支持使用原始 ServletAPI 对象作为控制器方法的参数。
支持原始 ServletAPI 对象有： 
HttpServletRequest  
HttpServletResponse 
HttpSession 
java.security.Principal 
Locale InputStream  
OutputStream  
Reader  
Writer 
我们可以把上述对象，直接写在控制的方法参数中使用
```

# 常用注解 

##  RequestParam 

作用：  把请求中指定名称的参数给控制器中的形参赋值。 

属性：  value：请求参数中的名称。  required：请求参数中是否必须提供此参数。默认值：true。表示必须提供，如果不提供将报错。 

```java
@RequestMapping("/useRequestParam") 
public String useRequestParam(@RequestParam("name")String username,       @RequestParam(value="age",required=false)Integer age){ 
    System.out.println(username+","+age);  
    return "success"; 
}
```

![image-20200425232332701](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200425232332701.png)

##  RequestBody 

作用：  

用于获取请求体内容。直接使用得到是 key=value&key=value...结构的数据。  get 请求方式不适用。 

属性：  required：是否必须有请求体。默认值是:true。当取值为 true 时,get 请求方式会报错。如果取值 为 false，get 请求得到是 null。 

```jsp
post 请求 jsp代码： 
<!-- request body 注解 --> 
<form action="springmvc/useRequestBody" method="post"> 
 	用户名称：<input type="text" name="username" ><br/>  
    用户密码：<input type="password" name="password" ><br/>  
    用户年龄：<input type="text" name="age" ><br/> 
 <input type="submit" value=" 保存 "> 
</form> 

get 请求 jsp代码： 
<a href="springmvc/useRequestBody?body=test">requestBody 注解 get 请求</a> 
```

```java
//控制器代码： 
@RequestMapping("/useRequestBody") 
public String useRequestBody(@RequestBody(required=false) String body){  System.out.println(body);  
 return "success"; 
} 
```

![image-20200425232649744](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200425232649744.png)

##  PathVaribale 

作用：  

用于绑定 url 中的占位符。例如：请求 url 中 /delete/{id}，这个{id}就是 url 占位符。  url 支持占位符是 spring3.0 之后加入的。是 springmvc 支持 rest 风格 URL 的一个重要标志。 

属性： 

 value：用于指定 url 中占位符名称。  required：是否必须提供占位符。 

```jsp
jsp 代码： 
<!-- PathVariable 注解 --> 
<a href="springmvc/usePathVariable/100">pathVariable 注解</a> 
```

```java
控制器代码
@RequestMapping("/usePathVariable/{id}") 
public String usePathVariable(@PathVariable("id") Integer id){  
    System.out.println(id);  
    return "success"; 
}
```

![image-20200425232949893](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200425232949893.png)

## RequestHeader 

作用：  

用于获取请求消息头。 

属性： 

 value：提供消息头名称  required：是否必须有此消息头 

注：  在实际开发中一般不怎么用。 

```java
@RequestMapping("/useRequestHeader") 
public String useRequestHeader(@RequestHeader(value="Accept-Language",required=false)String requestHeader){  
    System.out.println(requestHeader);  
    return "success"; 
} 
```

## CookieValue 

作用：  用于把指定 cookie 名称的值传入控制器方法参数。 

属性：  value：指定 cookie 的名称。  required：是否必须有此 cookie。 

```jsp
jsp 中的代码： 
<!-- CookieValue 注解 --> 
<a href="springmvc/useCookieValue">绑定 cookie 的值</a> 
```

```java
@RequestMapping("/useCookieValue") 
public String useCookieValue(@CookieValue(value="JSESSIONID",required=false)String cookieValue){  
    System.out.println(cookieValue);  
    return "success"; 
}
```

## ModelAttribute 

作用：  该注解是 SpringMVC4.3 版本以后新加入的。它可以用于修饰方法和参数。  

出现在方法上，表示当前方法会在控制器的方法执行之前，先执行。它可以修饰没有返回值的方法，也可以修饰有具体返回值的方法。  

出现在参数上，获取指定的数据给参数赋值。 

属性：  value：用于获取数据的 key。key 可以是 POJO 的属性名称，也可以是 map 结构的 key。 

应用场景：  当表单提交数据不是完整的实体类数据时，保证没有提交数据的字段使用数据库对象原来的数据。

例如：   我们在编辑一个用户时，用户有一个创建信息字段，该字段的值是不允许被修改的。在提交表单数 据是肯定没有此字段的内容，一旦更新会把该字段内容置为 null，此时就可以使用此注解解决问题。

### ModelAttribute 修饰方法不带返回值 

```java
@ModelAttribute 
public void showModel(String username,Map<String,User> map) { 
 	//模拟去数据库查询  
    User user = findUserByName(username); 
	System.out.println("执行了 showModel 方法"+user);  
    map.put("abc",user); 
} 
  //模拟修改用户方法  
@RequestMapping("/updateUser") 
public String testModelAttribute(@ModelAttribute("abc")User user) {  System.out.println("控制器中处理请求的方法：修改用户："+user);  
 return "success"; 
}
```

### ModelAttribute 修饰方法带返回值 

```java
@ModelAttribute 
public User showModel(String username) {  
    //模拟去数据库查询  
    User abc = findUserByName(username); 
 
    System.out.println("执行了 showModel 方法"+abc);  
    return abc; 
}   
 
//模拟修改用户方法 
@RequestMapping("/updateUser") 
public String testModelAttribute(User user) {  
    System.out.println("控制器中处理请求的方法：修改用户："+user);  
    return "success"; 
} 
  
```

## SessionAttribute 

作用：  用于多次执行控制器方法间的参数共享。

 属性：  value：用于指定存入的属性名称  type：用于指定存入的数据类型。 

```java
@Controller("sessionAttributeController") 
@RequestMapping("/springmvc") 
@SessionAttributes(value ={"username","password"},types={Integer.class})  
public class SessionAttributeController {    
    /** 
  * 把数据存入 SessionAttribute   
  * @param model   
  * @return 
  *  Model 是 spring 提供的一个接口，该接口有一个实现类 ExtendedModelMap   
  *  该类继承了 ModelMap，而 ModelMap 就是 LinkedHashMap 子类   */
    
    @RequestMapping("/testPut")    
    public String testPut(Model model){           
        model.addAttribute("username", "泰斯特");           
        model.addAttribute("password","123456");           
        model.addAttribute("age", 31);   
        //跳转之前将数据保存到 username、password 和 age 中，
        //因为注解@SessionAttribute 中有 这几个参数           
        return "success";       
    }   
        
    @RequestMapping("/testGet")       
    public String testGet(ModelMap model){
        System.out.println(model.get("username")+";"+
                           model.get("password")+";"+model.get("a ge"));           
    return "success";       
	}   

    @RequestMapping("/testClean")        
    public String complete(SessionStatus sessionStatus){         
        sessionStatus.setComplete();            
        return "success";        
    }   
    } 
```

![image-20200426004419047](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200426004419047.png)

#  HiddentHttpMethodFilter 

修改请求的方式

#  返回值分类 

## 字符串 

controller 方法返回字符串可以指定逻辑视图名，通过视图解析器解析为物理视图地址。

## void 

```java
我们知道 Servlet 原始 API 可以作为控制器中方法的参数： 
@RequestMapping("/testReturnVoid") 
public void testReturnVoid(HttpServletRequest request,HttpServletResponse response) throws Exception {
    
} 
在controller 方法形参上可以定义 request和 response，使用 request 或 response 指定响应结果： 
    1、使用 request 转向页面，如下
    	request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request, 
response); 
 	2、也可以通过 response 页面重定向： 
        response.sendRedirect("testRetrunString") 
 
	3、也可以通过 response 指定响应结果，例如响应 json 数据： 	
        response.setCharacterEncoding("utf-8"); 
		response.setContentType("application/json;charset=utf-8"); 	
		response.getWriter().write("json 串"); 
```

## ModelAndView 

是 SpringMVC 为我们提供的一个对象，该对象也可以用作控制器方法的返回值。 (可以理解为简化了model存储和跳转页面的操作)

```java
@RequestMapping("/testReturnModelAndView") 
public ModelAndView testReturnModelAndView() {  
    ModelAndView mv = new ModelAndView();  
    mv.addObject("username", "张三");  
    mv.setViewName("success"); 
    return mv; 
}
```

# 转发和重定向

```java
//转发
return "forward:/WEB-INF/pages/success.jsp"; 
//需要注意的是，如果用了 formward：则路径必须写成实际视图 url，不能写逻辑视图。 
//它相当于“request.getRequestDispatcher("url").forward(request,response)”。使用请求 转发，既可以转发到 jsp，也可以转发到其他的控制器方法
```

```java
//重定向
return "redirect:testReturnModelAndView"; 
//它相当于“response.sendRedirect(url)”。
//需要注意的是，如果是重定向到 jsp 页面，则 jsp 页面不 能写在 WEB-INF 目录中，否则无法找到。 
```

# ResponseBody响应json数据 

## 设置静态资源不过滤

```xml
<!-- 设置静态资源不过滤 -->    
<mvc:resources location="/css/" mapping="/css/**"/>  <!-- 样式 -->    
<mvc:resources location="/images/" mapping="/images/**"/>  <!-- 图片 -->    
<mvc:resources location="/js/" mapping="/js/**"/>  <!-- javascript -->
```

## 使用@RequestBody获取请求体数据

```javascript
// 页面加载    
// 页面加载    
$(function(){        
    // 绑定点击事件        
    $("#btn").click(function(){            
        $.ajax({                
            url:"user/testJson",                
            contentType:"application/json;charset=UTF-8",                
            data:'{"addressName":"aa","addressNum":100}',                
            dataType:"json",                
            type:"post",                
            success:function(data){                    
                alert(data);                    
                alert(data.addressName);                
            }            
        });        
    });    
});
```

```java
@RequestMapping("/testJson")    
public void testJson(@RequestBody String body) {        
    System.out.println(body);    
}
```

## 使用@RequestBody注解把json的字符串转换成JavaBean的对象

```javascript
 // 页面加载    
 // 页面加载    
 $(function(){        
     // 绑定点击事件        
     $("#btn").click(function(){           
         $.ajax({                
         url:"user/testJson",                
         contentType:"application/json;charset=UTF-8",
         data:'{"addressName":"aa","addressNum":100}',                
         dataType:"json",                
         type:"post",                
         success:function(data){                    
             alert(data);                    
             alert(data.addressName);                
         }            
         });        
     });    
 });        
```

```java
//获取请求体的数据
@RequestMapping("/testJson")    
public void testJson(@RequestBody Address address) {        
    System.out.println(address);    
}
```

## 使用@ResponseBody注解把JavaBean对象转换成json字符串，直接响应 

```java
@RequestMapping("/testJson")    
public @ResponseBody Address testJson(@RequestBody Address address) {        
    System.out.println(address);        
    address.setAddressName("上海");        
    return address;    
}
```

##  json字符串和JavaBean对象互相转换的过程中，需要使用jackson的jar包

```xml
 <dependency>            
     <groupId>com.fasterxml.jackson.core</groupId>            
     <artifactId>jackson-databind</artifactId>            
     <version>2.9.0</version>        
</dependency>        
<dependency>            
    <groupId>com.fasterxml.jackson.core</groupId>            
    <artifactId>jackson-core</artifactId>            
    <version>2.9.0</version>        
</dependency>        
<dependency>            
    <groupId>com.fasterxml.jackson.core</groupId>            
    <artifactId>jackson-annotations</artifactId>            
    <version>2.9.0</version>        
</dependency>
```

# SpringMVC实现文件上传 

## 文件上传的必要前提 
1. form 表单的 enctype 取值必须是：multipart/form-data      (默认值是:application/x-www-form-urlencoded)     enctype:是表单请求正文的类型 
2. method 属性取值必须是 Post 
3. 提供一个文件选择域\<input type="file" /> 

![image-20200426154307237](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200426154307237.png)

## 具体步骤

1. SpringMVC框架提供了MultipartFile对象，该对象表示上传的文件，要求变量名称必须和表单ﬁle标签的 name属性名称相同。 
2. 代码如下

```java
//SpringMVC方式的文件上传       
@RequestMapping(value="/fileupload2")    
public String fileupload2(HttpServletRequest request,MultipartFile upload) throws Exception {        
    System.out.println("SpringMVC方式的文件上传...");        
    // 先获取到要上传的文件目录        
    String path = request.getSession().getServletContext().getRealPath("/uploads");        
    // 创建File对象，一会向该路径下上传文件        
    File file = new File(path);        
    // 判断路径是否存在，如果不存在，创建该路径        
    if(!file.exists()) {            
        file.mkdirs();        
    }        
    // 获取到上传文件的名称        
    String filename = upload.getOriginalFilename();        
    String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();        
    // 把文件的名称唯一化        
    filename = uuid+"_"+filename;        
    // 上传文件        
    upload.transferTo(new File(file,filename));        
    return "success";    
}
```

3. 配置文件解析器对象

```xml
<!-- 配置文件解析器对象，要求id名称必须是multipartResolver -->    
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">        
    <property name="maxUploadSize" value="10485760"/>    
</bean>
```

## SpringMVC跨服务器方式文件上传

1. 搭建图片服务器 
   1. 根据文档配置tomcat9的服务器，现在是2个服务器 
   2. 导入资料中day02_springmvc5_02image项目，作为图片服务器使用 

2. 实现SpringMVC跨服务器方式文件上传 
   1. 导入开发需要的jar包

```xml
<dependency>            
    <groupId>com.sun.jersey</groupId>            
    <artifactId>jersey-core</artifactId>            
    <version>1.18.1</version>        
</dependency>        
<dependency>            
    <groupId>com.sun.jersey</groupId>            
    <artifactId>jersey-client</artifactId>            
    <version>1.18.1</version>        
</dependency>
```

​		2. 编写文件上传的JSP页面

```jsp
<h3>跨服务器的文件上传</h3>        
<form action="user/fileupload3" method="post" enctype="multipart/form-data">        
    选择文件：<input type="file" name="upload"/><br/>        
    <input type="submit" value="上传文件"/>    
</form>
```

​		3.编写控制器

```java
@RequestMapping(value="/fileupload3")
public String fileupload3(MultipartFile upload) throws Exception {        
    System.out.println("SpringMVC跨服务器方式的文件上传...");                
    // 定义图片服务器的请求路径        
    String path = "http://localhost:9090/day02_springmvc5_02image/uploads/";                
    // 获取到上传文件的名称        
    String filename = upload.getOriginalFilename();        
    String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();        
    // 把文件的名称唯一化        
    filename = uuid+"_"+filename;        
    // 向图片服务器上传文件                
    // 创建客户端对象        
    Client client = Client.create();        
    // 连接图片服务器        
    WebResource webResource = client.resource(path+filename);        
    // 上传文件        
    webResource.put(upload.getBytes());        
    return "success";    
}
```

# SpringMVC的异常处理

![image-20200426165050704](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200426165050704.png)



## 1.异常处理思路 

1. Controller调用service，service调用dao，异常都是向上抛出的，最终有DispatcherServlet找异常处理器进 行异常的处理。 2. SpringMVC的异常处理 

## 2. SpringMVC的异常处理

### 1.自定义异常类

```java
package cn.itcast.exception;
public class SysException extends Exception{
 
    private static final long serialVersionUID = 4055945147128016300L;        
    // 异常提示信息  
    private String message;   
    public String getMessage() {     
        return message; 
    }  
    public void setMessage(String message) {    
        this.message = message;   
    }  
    public SysException(String message) {   
        this.message = message;   
    }
}
```

### 2.自定义异常处理器

```java
package cn.itcast.exception;
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerExceptionResolver; 
import org.springframework.web.servlet.ModelAndView;
 
/** * 异常处理器 * @author rt */ 
public class SysExceptionResolver implements HandlerExceptionResolver{
 
    //跳转到具体的错误页面的方法 
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) {       
        ex.printStackTrace();      
        SysException e = null;     
        // 获取到异常对象     
        if(ex instanceof SysException) {     
            e = (SysException) ex;    
        }else {        
            e = new SysException("请联系管理员");  
        }     
        ModelAndView mv = new ModelAndView();   
        // 存入错误的提示信息    
        mv.addObject("message", e.getMessage());    
        // 跳转的Jsp页面      
        mv.setViewName("error");     
        return mv;   
    }
}
```

### 3.配置异常处理器

```xml
<!-- 配置异常处理器 --> 
<bean id="sysExceptionResolver" class="cn.itcast.exception.SysExceptionResolver">
```

#  SpringMVC中的拦截器 

Spring MVC 的处理器拦截器类似于 Servlet 开发中的过滤器 Filter，用于对处理器进行预处理和后处理。 

用户可以自己定义一些拦截器来实现特定的功能。 

谈到拦截器，还要向大家提一个词——拦截器链（Interceptor Chain）。拦截器链就是将拦截器按一定的顺 序联结成一条链。在访问被拦截的方法或字段时，拦截器链中的拦截器就会按其之前定义的顺序被调用。 

说到这里，可能大家脑海中有了一个疑问，这不是我们之前学的过滤器吗？是的它和过滤器是有几分相似，

但是也有区别，接下来我们就来说说他们的区别：  

**过滤器**是 servlet 规范中的一部分，任何 java web 工程都可以使用。  

**拦截器**是 SpringMVC 框架自己的，只有使用了 SpringMVC 框架的工程才能用。  

**过滤器**在 url-pattern 中配置了/*之后，可以对所有要访问的资源拦截。 

 **拦截器**它是只会拦截访问的控制器方法，如果访问的是 jsp，html,css,image 或者 js 是不会进行拦 截的。 

它也是 AOP 思想的具体应用。 

我们要想自定义拦截器， 要求必须实现：HandlerInterceptor 接口。 

![image-20200426164221562](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200426164221562.png)

## 自定义拦截器步骤

### 1.创建类，实现HandlerInterceptor接口，重写需要的方法

```java
 package cn.itcast.demo1;
 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.web.servlet.HandlerInterceptor;
 
//自定义拦截器1
public class MyInterceptor1 implements HandlerInterceptor{
 
    /**    
    * controller方法执行前，进行拦截的方法  
    * return true放行   
    * return false拦截  
    * 可以使用转发或者重定向直接跳转到指定的页面。   
    */   
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {     
        System.out.println("拦截器执行了...");     
        return true; 
    }
 
}
```

### 2.在springmvc.xml中配置拦截器类

```xml
<!-- 配置拦截器 -->   
<mvc:interceptors>     
    <mvc:interceptor>    
        <!-- 哪些方法进行拦截 -->       
        <mvc:mapping path="/user/*"/>     
        <!-- 哪些方法不进行拦截   <mvc:exclude-mapping path=""/>  -->        
        <!-- 注册拦截器对象 -->    
        <bean class="cn.itcast.demo1.MyInterceptor1"/>    
    </mvc:interceptor>    </mvc:interceptors>
 
```

##  HandlerInterceptor接口中的方法 

1. preHandle方法是controller方法执行前拦截的方法 
   1. 可以使用request或者response跳转到指定的页面 
   2. return true放行，执行下一个拦截器，如果没有拦截器，执行controller中的方法。 
   3. return false不放行，不会执行controller中的方法。 

2. postHandle是controller方法执行后执行的方法，在JSP视图执行前。 
   1. 可以使用request或者response跳转到指定的页面 
   2. 如果指定了跳转的页面，那么controller方法跳转的页面将不会显示。 
3. postHandle方法是在JSP执行后执行 
   1. request或者response不能再跳转页面了

## 配置多个拦截器 

1. 再编写一个拦截器的类 
2.  配置2个拦截器

```xml
 <!-- 配置拦截器 -->   
<mvc:interceptors>    
    <mvc:interceptor>       
        <!-- 哪些方法进行拦截 -->      
        <mvc:mapping path="/user/*"/>      
        <!-- 哪些方法不进行拦截   <mvc:exclude-mapping path=""/>     -->
  <!-- 注册拦截器对象 -->    
        <bean class="cn.itcast.demo1.MyInterceptor1"/>    
    </mvc:interceptor>        
    <mvc:interceptor>       
        <!-- 哪些方法进行拦截 -->     
        <mvc:mapping path="/**"/>          
        <!-- 注册拦截器对象 -->       
        <bean class="cn.itcast.demo1.MyInterceptor2"/>   
    </mvc:interceptor>  
</mvc:interceptors>
 
```

