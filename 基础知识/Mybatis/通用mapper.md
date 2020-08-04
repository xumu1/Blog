# 通用mapper

## 1. 配置通用Mapper

在pom.xml文件中，加入

```
<!-- 通用mapper -->
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
<version>1.2.3</version>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

 GmallUserManageApplication.java 中增加注解

```java
@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.usermanage.mapper")
public class GmallOrderServiceApplication {

   public static void main(String[] args) {
      SpringApplication.run(GmallOrderServiceApplication.class, args);
   }
}
//注意通用mapper是tk.mybatis.mapper
```

## 2.配置数据源

在application.properties中

```
spring.datasource.url=jdbc:mysql://localhost:3306/gmall?characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=123123
```

## 3.代码开发

| 包           | 类                    | 说明       |
| ------------ | --------------------- | ---------- |
| controller   | UserManageController  | web        |
| service      | UserManageService     | 接口       |
| service.impl | UserManageServiceImpl | 实现类     |
| bean         | UserInfo              | 实体bean   |
| mapper       | UserInfoMapper        | mapper接口 |
|              |                       |            |

 

## 4. 配置通用mapper的主键和主键返回策略

@Id
@GeneratedValue(strategy = GenerationType.***\**IDENTITY\**\***)

## 5.  Mapper

```
public interface UserInfoMapper extends Mapper<UserInfo> {
}
```

