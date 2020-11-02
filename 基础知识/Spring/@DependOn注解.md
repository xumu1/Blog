### 用途

注解`@DependsOn`位于如下包

```java
org.springframework.context.annotation
```

该注解用于声明当前bean依赖于另外一个bean。所依赖的`bean`会被容器确保在当前`bean`实例化之前被实例化。

举例来讲，如果容器通过`@DependsOn`注解方式定义了`bean` `plant`依赖于`bean` `water`,那么容器在会确保`bean` `water`的实例在实例化`bean` `plant`之前完成。

**一般用在一个`bean`没有通过属性或者构造函数参数显式依赖另外一个`bean`，但实际上会使用到那个`bean`或者那个`bean`产生的某些结果的情况。**

### 用法

1. 直接或者间接标注在带有`@Component`注解的类上面;

- 使用`@DependsOn`注解到类层面仅仅在使用component scanning方式时才有效;
- 如果带有`@DependsOn`注解的类通过XML方式使用，该注解会被忽略，`<bean depends-on="..."/>`这种方式会生效;

1. 直接或者间接标注在带有`@Bean` 注解的方法上面;

### 用法举例

#### 注解在`@Bean`定义方法上

该例子使用方法方式定义了一个`bean entityManager`,并指出它依赖于`bean transactionManager`。虽然`bean entityManager`实例化过程中没有通过属性或者构造函数参数方式依赖于`bean transactionManager`，但是其过程中会牵涉到对`transactionManager`的使用，如果此时`transactionManager`没有被实例化，`entityManager`的实例化过程会失败。这就是一种典型的不通过属性或者构造方法参数方式依赖，但是实际上存在依赖的情况，这种情况正是注解`@DependsOn`的用武之地。

```java
    @Bean(name = "entityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Throwable {      
        LocalContainerEntityManagerFactoryBean entityManager = 
	        new LocalContainerEntityManagerFactoryBean();
	    // 省略无关的实现部分
	    // ...
        return entityManager;
    }
```



#### 引用自 https://blog.csdn.net/andy_zhang2007/article/details/78859949

