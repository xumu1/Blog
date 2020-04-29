# 第一章：搭建整合环境

![image-20200426220402273](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20200426220402273.png)

## 1. 整合说明：SSM整合可以使用多种方式，我们选择XML + 注解的方式 

## 2. 整合的思路 

1. 先搭建整合的环境 
2. 先把Spring的配置搭建完成 
3. 再使用Spring整合SpringMVC框架 
4. 最后使用Spring整合MyBatis框架

## 3. 创建数据库和表结构 

```sql
create database ssm; 
use ssm; create table account(
    id int primary key auto_increment,    
    name varchar(20),    
    money double 
);
```

## 4. 创建maven的工程

### 1. 创建ssm_parent父工程（打包方式选择pom，必须的） 

### 2. 创建ssm_web子模块（打包方式是war包）

###  3. 创建ssm_service子模块（打包方式是jar包） 

### 4. 创建ssm_dao子模块（打包方式是jar包）

###  5. 创建ssm_domain子模块（打包方式是jar包）

###  6. web依赖于service，service依赖于dao，dao依赖于domain 

### 7. 在ssm_parent的pom.xml文件中引入坐标依赖

```xml
 <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <spring.version>5.0.2.RELEASE</spring.version>
    <slf4j.version>1.6.6</slf4j.version>
    <log4j.version>1.2.12</log4j.version>
    <mysql.version>5.1.6</mysql.version>
    <mybatis.version>3.4.5</mybatis.version>
  </properties>

  <dependencies>
    <!-- spring -->
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.6.8</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aop</artifactId>
      <version>${spring.version}</version>
    </dependency>

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
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>compile</scope>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>${mysql.version}</version>
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

    <dependency>
      <groupId>jstl</groupId>
      <artifactId>jstl</artifactId>
      <version>1.2</version>
    </dependency>

    <!-- log start -->
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>${log4j.version}</version>
    </dependency>

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>${slf4j.version}</version>
    </dependency>

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>${slf4j.version}</version>
    </dependency>

    <!-- log end -->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>${mybatis.version}</version>
    </dependency>

    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>1.3.0</version>
    </dependency>

    <dependency>
      <groupId>c3p0</groupId>
      <artifactId>c3p0</artifactId>
      <version>0.9.1.2</version>
      <type>jar</type>
      <scope>compile</scope>
    </dependency>
  </dependencies>

  <build>
    <finalName>ssm</finalName>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.0.0</version>
        </plugin>
        <!-- see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_war_packaging -->
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.7.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.20.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-war-plugin</artifactId>
          <version>3.2.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>2.5.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>2.8.2</version>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
 
```

### 8. 部署ssm_web的项目，只要把ssm_web项目加入到tomcat服务器中即可 

## 5. 编写实体类，在ssm_domain项目中编写

## 6. 编写dao接口

## 7. 编写service接口和实现类

# 第二章：Spring框架代码的编写

## 1. 在ssm_web项目中创建applicationContext.xml的配置文件，编写具体的配置信息。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context
	http://www.springframework.org/schema/context/spring-context.xsd
	http://www.springframework.org/schema/aop
	http://www.springframework.org/schema/aop/spring-aop.xsd
	http://www.springframework.org/schema/tx
	http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--开启注解的扫描，希望处理service和dao，controller不需要Spring框架去处理-->
    <context:component-scan base-package="cn.itcast" >
        <!--配置哪些注解不扫描-->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller" />
    </context:component-scan>

    <!--Spring整合MyBatis框架-->
    <!--配置连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql:///ssm"/>
        <property name="user" value="root"/>
        <property name="password" value="root"/>
    </bean>

    <!--配置SqlSessionFactory工厂-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
    </bean>

    <!--配置AccountDao接口所在包-->
    <bean id="mapperScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="cn.itcast.dao"/>
    </bean>

    <!--配置Spring框架声明式事务管理-->
    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource" />
    </bean>

    <!--配置事务通知-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="*" isolation="DEFAULT"/>
        </tx:attributes>
    </tx:advice>

    <!--配置AOP增强-->
    <aop:config>
        <aop:advisor advice-ref="txAdvice" pointcut="execution(* cn.itcast.service.impl.*ServiceImpl.*(..))"/>
    </aop:config>

</beans>
```

## 2. 在ssm_web项目中编写测试方法，进行测试

# 第三章：Spring整合SpringMVC框架 

## 1. 搭建和测试SpringMVC的开发环境

### 1. 在web.xml中配置DispatcherServlet前端控制器

```xml
<!--配置前端控制器-->
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--加载springmvc.xml配置文件-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <!--启动服务器，创建该servlet-->
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
```

### 2. 在web.xml中配置DispatcherServlet过滤器解决中文乱码

```xml
<!--解决中文乱码的过滤器-->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```

### 3. 创建springmvc.xml的配置文件，编写配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--开启注解扫描，只扫描Controller注解-->
    <context:component-scan base-package="cn.itcast">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller" />
    </context:component-scan>

    <!--配置的视图解析器对象-->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--过滤静态资源-->
    <mvc:resources location="/css/" mapping="/css/**" />
    <mvc:resources location="/images/" mapping="/images/**" />
    <mvc:resources location="/js/" mapping="/js/**" />

    <!--开启SpringMVC注解的支持-->
    <mvc:annotation-driven/>

</beans>
```

### 4. 测试SpringMVC的框架搭建是否成功

1. 编写index.jsp和list.jsp编写，超链接

```jsp
<a href="account/findAll">查询所有</a>
```

2. 创建AccountController类，编写方法，进行测试

```java
@Controller 
@RequestMapping("/account") 
public class AccountController {    
    //查询所有的数据     
    @RequestMapping("/findAll")  
    public String findAll() {   
        System.out.println("表现层：查询所有账户...");      
        return "list";  
    } 
} 
```

## 2. Spring整合SpringMVC的框

1. 目的：在controller中能成功的调用service对象中的方法。

2. 在项目启动的时候，就去加载applicationContext.xml的配置文件，在web.xml中配置 ContextLoaderListener监听器（该监听器只能加载WEB-INF目录下的applicationContext.xml的配置文 件）。

```xml
<!-- 配置Spring的监听器 --> 
<listener>    
   <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class> </listener> 
<!-- 配置加载类路径的配置文件 -->  
<context-param>     
    <param-name>contextConfigLocation</param-name>   
    <param-value>classpath:applicationContext.xml</param-value>  
</context-param>
```

3. 在controller中注入service对象，调用service对象的方法进行测试

```java
@Controller 
@RequestMapping("/account") public class AccountController {        
    @Autowired    
    private AccountService accoutService;  
    //查询所有的数据 
    @RequestMapping("/findAll") 
    public String findAll() {   
        System.out.println("表现层：查询所有账户...");    
        accoutService.findAll();   
        return "list";   
    } 
}
```

# 第四章：Spring整合MyBatis框架 

## 1. 搭建和测试MyBatis的环境 

1. 在web项目中编写SqlMapConﬁg.xml的配置文件，编写核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?> 
<!DOCTYPE configuration    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"    "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>   
    <environments default="mysql">       
        <environment id="mysql">       
            <transactionManager type="JDBC"/>      
            <dataSource type="POOLED">           
                <property name="driver" value="com.mysql.jdbc.Driver"/>                <property name="url" value="jdbc:mysql:///ssm"/>        
                <property name="username" value="root"/>       
                <property name="password" value="root"/>    
            </dataSource>      
        </environment>   
    </environments>      
    <!-- 使用的是注解 -->   
    <mappers>     
        <!-- <mapper class="cn.itcast.dao.AccountDao"/> -->    
        <!-- 该包下所有的dao接口都可以使用 -->    
        <package name="cn.itcast.dao"/>   
    </mappers>
</configuration>
```

2. 在AccountDao接口的方法上添加注解，编写SQL语句

```java
public interface AccountDao {       
    @Insert(value="insert into account (name,money) values (#{name},#{money})")  
    public void saveAccount(Account account);    
    @Select("select * from account")   
    public List<Account> findAll();
}
```

3. 编写测试的方法

```java

public class Demo1 {     
    @Test   
    public void run1() throws Exception {    
        // 加载配置文件      
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");        
        // 创建工厂   
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);        
        // 创建sqlSession对象    
        SqlSession session = factory.openSession();      
        // 获取代理对象  
        AccountDao dao = session.getMapper(AccountDao.class);     
        // 调用查询的方法     
        List<Account> list = dao.findAll();    
        for (Account account : list) {     
            System.out.println(account);     
        }     
        // 释放资源   
        session.close();      
        inputStream.close();  
    }     
    
        @Test 
        public void run2() throws Exception {     
        Account account = new Account();    
        account.setName("熊大");      
        account.setMoney(400d);           
        // 加载配置文件      
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");      
        // 创建工厂  
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);       
        // 创建sqlSession对象      
        SqlSession session = factory.openSession();        
        // 获取代理对象     
        AccountDao dao = session.getMapper(AccountDao.class);
        dao.saveAccount(account);          
        // 提交事务     
        session.commit();    
        // 释放资源     
        session.close();    
        inputStream.close();   
        }
}
```

## 2. Spring整合MyBatis框架

1. 目的：把SqlMapConﬁg.xml配置文件中的内容配置到applicationContext.xml配置文件中

```java
<!-- 配置C3P0的连接池对象 -->  
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">    
<property name="driverClassName" value="com.mysql.jdbc.Driver" />        
<property name="url" value="jdbc:mysql:///ssm" />     
<property name="username" value="root" />   
<property name="password" value="root" />   
</bean>
 
<!-- 配置SqlSession的工厂 -->   
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">     
<property name="dataSource" ref="dataSource" />   
</bean>       
<!-- 配置扫描dao的包 -->    
<bean id="mapperScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">     
<property name="basePackage" value="cn.itcast.dao"/>  
</bean>
 
```

2. 在AccountDao接口中添加@Repository注解 

3. 在service中注入dao对象，进行测试

4. 配置Spring的声明式事务管理

```xml
<!--配置Spring框架声明式事务管理-->
    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource" />
    </bean>

    <!--配置事务通知-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="*" isolation="DEFAULT"/>
        </tx:attributes>
    </tx:advice>

    <!--配置AOP增强-->
    <aop:config>
        <aop:advisor advice-ref="txAdvice" pointcut="execution(* cn.itcast.service.impl.*ServiceImpl.*(..))"/>
    </aop:config>
```

# 疑问：

1. 使用Spring进行数据库查询，而不是用mybatis的sqlSession得到显示地对象来查询
