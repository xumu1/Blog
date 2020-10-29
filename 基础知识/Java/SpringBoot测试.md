1、pom.xml文件添加以下依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>
```

2、测试类上添加以下注解

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XXXApplication.class)
```

说明：XXXApplication为springboot项目启动类

```java
	// 前置处理
	@Before
    public void init() {
        System.out.println("开始测试-----------------");
    }
 	
 	// 后置处理
    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
```

