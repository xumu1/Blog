# MyBatis学习笔记

# quick start（XML方式配置）

1. 导入依赖

   ```xml
   <dependency>  
   	<groupId>org.mybatis</groupId>   
   	<artifactId>mybatis</artifactId>   
   	<version>3.4.5</version>  
   </dependency>   
   ```

2. 创建SqlMapConfig.xml配置文件

   常用需要配置的项有：

    ```xml
   1.  <properties resource="" url="">
          <property name="" value=""/>
       </properties>
   
   2.  <settings>
           <setting name="" value=""/>
       </settings>
   
   3.  <typeAliases>
           <package name=""/>
           <typeAlias type="" alias=""></typeAlias>
       </typeAliases>
   
   4.  <environments default="mysql">
           <environment id="mysql">
               <transactionManager type="JDBC"></transactionManager>
               <dataSource type="POOLED">
                   <property name="driver" value="${dirver}"/>
                   <property name="url" value="${url}"/>
                   <property name="username" value="${username}"/>
                   <property name="password" value="${password}"/>
               </dataSource>
           </environment>
       </environments>
   
   5.<mappers>
           <mapper resource="com/xumu/learnMybatis/dao/IUserDao.xml"/>
           <mapper class="com.xumu.learnMybatis.dao.IUserDao"/>
           <package name="com.xumu.learnMybatis.dao"/>
       </mappers>
    ```

3. 编写Dao接口的方法

4. 配置Dao.xml文件

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE mapper 
   PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"   
   "http://mybatis.org/dtd/mybatis-3-mapper.dtd"> 
   <mapper namespace=""> 
   	<!-- 配置查询所有操作 --> 
   	<select id="" resultType="" parameterType="" resultMap="">  
   		select * from user 
   	</select> 
   </mapper>
   ```

   5.测试

   ```java
   //引入配置，得到sqlSession
   InputStreamis = Resources.getResourceAsStream("SqlMapConfig.xml");
   SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
   SqlSessionFactory factory = factoryBuilder.build(is);
   SqlSession sqlSession = factory.openSession();
   IUserDao mapper = sqlSession.getMapper(IUserDao.class);
   //使用mapper的方法操作数据库
   ```

   

# 常用特性（xml配置，注解方式在后面介绍）

## 缓存

- 一级缓存：SqlSession 级别的缓存，只要 SqlSession 没有 flush 或 close，它就存在。 一级缓存是 SqlSession 范围的缓存，当调用 SqlSession 的修改，添加，删除，commit()，close()等方法时，就会清空一级缓存。 
- 二级缓存： mapper 映射级别的缓存，多个 SqlSession 去操作同一个 Mapper 映射的 sql 语句，多个 SqlSession 可以共用二级缓存，二级缓存是跨 SqlSession 的。 

一级缓存自带，二级缓存需要开启，在xml配置中，需要三处配置：

1. SqlMapConfig.xml中setting中开启二级缓存

```xml
<settings> 
 <!-- 开启二级缓存的支持 --> 
 <setting name="cacheEnabled" value="true"/>
</settings> 
因为 cacheEnabled 的取值默认就为 true，所以这一步可以省略不配置。为 true 代表开启二级缓存；为 false 代表不开启二级缓存。
```

2. 配置相关的 Mapper 映射文件 

```xml
<cache>标签表示当前这个 mapper 映射将使用二级缓存，区分的标准就看 mapper 的 namespace 值。 
<?xml version="1.0" encoding="UTF-8"?> <!DOCTYPE mapper     PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"     "http://mybatis.org/dtd/mybatis-3-mapper.dtd"> 
<mapper namespace="com.itheima.dao.IUserDao"> 
    <!-- 开启二级缓存的支持 --> 
    <cache></cache> 
</mapper> 
```

3. 配置 statement 上面的 useCache 属性 

```xml
<!-- 根据 id 查询 --> 
<select id="findById" resultType="user" parameterType="int" useCache="true">  
    select * from user where id = #{uid} 
</select> 

将UserDao.xml映射文件中的<select>标签中设置 useCache=”true”代表当前这个 statement 要使用二级缓存，如果不使用二级缓存可以设置为 false。注意：针对每次查询都需要最新的数据 sql，要设置成 useCache=false，禁用二级缓存。 
```



## 动态SQL

1. if标签

```xml
<select id="findByUser" resultType="user" parameterType="user"> 
	select * from user where 1=1 
	<if test="username!=null and username != '' ">  
 		and username like #{username}  
 	</if> 
 	<if test="address != null">  
 		and address like #{address}
 	</if>  
 </select> 
注意：<if>标签的 test 属性中写的是对象的属性名，如果是包装类的对象要使用 OGNL 表达式的写法。 另外要注意 where 1=1 的作用~！ 
```

2. where标签

```xml
<!-- 根据用户信息查询 -->  
<select id="findByUser" resultType="user" parameterType="user">   
    <include refid="defaultSql"></include>   
    <where> 
   		<if test="username!=null and username != '' ">     
       		and username like #{username}    
   		</if> 
   		<if test="address != null">     
       		and address like #{address}    
        </if>   
    </where>  
</select>
```

3. foreach标签

```xml
<!-- 查询所有用户在 id 的集合之中 -->  
<select id="findInIds" resultType="user" parameterType="queryvo">  
    <!--  select * from user where id in (1,2,3,4,5); --> 
  	<include refid="defaultSql"></include>   
   	<where> 
   		<if test="ids != null and ids.size() > 0">     
       		<foreach collection="ids" open="id in ( " close=")" item="uid"  separator=",">      				#{uid}     
       		</foreach>    
  		</if>   
    </where>  
</select> 

SQL 语句：    
	select 字段 from user where id in (?) 
<foreach>标签用于遍历集合，它的属性：  
collection:代表要遍历的集合元素，注意编写时不要写#{}  
open:代表语句的开始部分  
close:代表结束部分 
item:代表遍历集合的每个元素，生成的变量名  
sperator:代表分隔符 
```

## 多表查询

### 1对1

1.定义一个包含两个实体的新类

2.使用resultMap配置

```xml
<resultMap type="account" id="accountMap"> 
    <id column="aid" property="id"/>  
    <result column="uid" property="uid"/>  
    <result column="money"  property="money"/> 
    <!-- 它是用于指定从表方的引用实体属性的 -->  
    <association property="user" javaType="user">  
        <id column="id" property="id"/>  
        <result column="username" property="username"/>  
        <result column="sex" property="sex"/>   
        <result column="birthday" property="birthday"/>  
        <result column="address" property="address"/> 
    </association>  
</resultMap>   
```

### 1对多

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper   
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  
   "http://mybatis.org/dtd/mybatis-3-mapper.dtd"> 
<mapper namespace="com.itheima.dao.IUserDao">   
 <resultMap type="user" id="userMap">  
     <id column="id" property="id"></id> 
     <result column="username" property="username"/>  
     <result column="address" property="address"/> 
     <result column="sex" property="sex"/>  
     <result column="birthday" property="birthday"/>  
     <!-- collection 是用于建立一对多中集合属性的对应关系 ofType 用于指定集合元素的数据类型  --> 
     <collection property="accounts" ofType="account">  
         <id column="aid" property="id"/> 
         <result column="uid" property="uid"/>   
         <result column="money" property="money"/> 
     </collection> 
 </resultMap> 
 
 <!-- 配置查询所有操作 --> 
    <select id="findAll" resultMap="userMap"> 
        select u.*,a.id as aid ,a.uid,a.money from user u left outer join account a on u.id =a.uid  </select> 
</mapper> 
collection：部分定义了用户关联的账户信息。表示关联查询结果集 
property="accList" ：关联查询的结果集存储在 User 对象的上哪个属性。 
ofType="account" ：指定关联查询的结果集中的对象类型即List中的对象类型。此处可以使用别名，也可以使用全限定名。 
```

### 多对多

可以看成两个1对多

## 延迟加载

### 使用association

1. 开启延迟加载

```xml
<settings> 
    <setting name="lazyLoadingEnabled" value="true"/>  
    <setting name="aggressiveLazyLoading" value="false"/>
</settings>
```

2. 配置resultMap

```xml
<resultMap type="account" id="accountMap">  
    <id column="aid" property="id"/> 
    <result column="uid" property="uid"/>  
    <result column="money" property="money"/> 
    <!-- 它是用于指定从表方的引用实体属性的 --> 
    <association property="user"javaType="user" select="com.itheima.dao.IUserDao.findById"column="uid">  
    </association> 
</resultMap>   
```

### 使用Collection 

1. 开启延迟加载

```xml
<settings> 
    <setting name="lazyLoadingEnabled" value="true"/>  
    <setting name="aggressiveLazyLoading" value="false"/>
</settings>
```

2. 配置resultMap

```xml
<resultMap type="user" id="userMap">  
    <id column="id" property="id"></id>  
    <result column="username" property="username"/> 
    <result column="address" property="address"/>  
    <result column="sex" property="sex"/>  
    <result column="birthday" property="birthday"/> 
    <!-- collection 是用于建立一对多中集合属性的对应关系    ofType 用于指定集合元素的数据类型    select 是用于指定查询账户的唯一标识（账户的 dao 全限定类名加上方法名称）    column 是用于指定使用哪个字段的值作为条件查询    --> 
  	<collection property="accounts" ofType="account"      select="com.itheima.dao.IAccountDao.findByUid"     column="id">  
    </collection> 
</resultMap> 
 
<collection>标签： 
     主要用于加载关联的集合对象 
select 属性：  
     用于指定查询 account 列表的 sql 语句，所以填写的是该 sql 映射的 id 
column 属性：  
     用于指定 select 属性的 sql 语句的参数来源，上面的参数来自于 user 的 id 列，所以就写成 id 这一个字段名
```

# 注解方式配置Mybatis

## 常用注解

@Insert:实现新增 

@Update:实现更新 

@Delete:实现删除 

@Select:实现查询 

@Result:实现结果集封装 

@Results:可以与@Result 一起使用，封装多个结果集 

@ResultMap:实现引用

@Results 定义的封装 

@One:实现一对一结果集封装 

@Many:实现一对多结果集封装 

@SelectProvider: 实现动态 SQL 映射 

@CacheNamespace:实现注解二级缓存的使用 

## 根据实例快速复习

```java
@Select("select * from user")  
@Results(id="userMap",    
value= {     
    @Result(id=true,column="id",property="userId"),
    @Result(column="username",property="userName"),
    @Result(column="sex",property="userSex"),
    @Result(column="address",property="userAddress"),
    @Result(column="birthday",property="userBirthday")    
})  
List<User> findAll(); 
```

```java
@Select("select * from user where id = #{uid} ")  
@ResultMap("userMap")  
User findById(Integer userId); 
```

```java
@Insert("insert into user(username,sex,birthday,address)values(#{username},#{sex},#{birthday},#{address} )") @SelectKey(keyColumn="id",keyProperty="id",resultType=Integer.class,before = false, statement = { "select last_insert_id()" })  
int saveUser(User user);   
```

```java
@Update("update user set username=#{username},address=#{address},sex=#{sex},birthday=#{birthday} where id =#{id} ")  
int updateUser(User user); 
```

```java
@Delete("delete from user where id = #{uid} ")  
int deleteUser(Integer userId);   
```

```java
@Select("select * from user where username like #{username} ")  
List<User> findByName(String name); 
```

## 配置SqlMapConfig.xml

```xml
<!-- 配置映射信息 -->  
<mappers>
  <!-- 配置 dao 接口的位置，它有两种方式 第一种：使用 mapper 标签配置 class 属性 第二种：使用 package 标签，直接指定 dao 接口所在的包    --> 
   <package name="com.itheima.dao"/>  
</mappers> 
```

## 多表查询

实现复杂关系映射之前我们可以在映射文件中通过配置<resultMap>来实现，在使用注解开发时我们需要借助@Results 注解，@Result 注解，@One 注解，@Many 注解。 

注意：聚集元素用来处理“一对多”的关系。需要指定映射的 Java 实体类的属性，属性的 javaType （一般为 ArrayList）但是注解中可以不定义； 

### 1对1

```java
@Select("select * from account")  
@Results(id="accountMap",    
         value= {     
             @Result(id=true,column="id",property="id"),
             @Result(column="uid",property="uidjava"),
             @Result(column="money",property="money"),
             @Result(column="uid",property="user",
                     one=@One(select="com.itheima.dao.IUserDao.findById",
                              fetchType=FetchType.LAZY))    
         })  
List<Account> findAll(); 
```

### 1对多

```java
@Select("select * from user")  
@Results(id="userMap",    
         value= {     
             @Result(id=true,column="id",property="userId"),
             @Result(column="username",property="userName"),
             @Result(column="sex",property="userSex"),
             @Result(column="address",property="userAddress"),
             @Result(column="birthday",property="userBirthday"),
             @Result(column="id",property="accounts",
                     many=@Many(
                         select="com.itheima.dao.IAccountDao.findByUid",
                         fetchType=FetchType.LAZY)       
                    )    
         })  
List<User> findAll();

@Many: 
 相当于<collection>的配置  select 属性：代表将要执行的 sql 语句  fetchType 属性：代表加载方式，一般如果要延迟加载都设置为 LAZY 的值 
```

## 开启二级缓存

1. 在 SqlMapConfig.xml 中开启二级缓存支持 

```xml
<!-- 配置二级缓存 --> 
<settings> 
    <!-- 开启二级缓存的支持 -->  
    <setting name="cacheEnabled" value="true"/> 
</settings>
```

2. 在持久层接口中使用注解配置二级缓存 

```java
@CacheNamespace(blocking=true)//mybatis 基于注解方式实现配置二级缓存 
public interface IUserDao {
    
} 
 
```





















