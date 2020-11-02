## spring容器的bean什么时候被实例化

Spring什么时候实例化bean，首先要分2种情况 

 第一：如果你使用BeanFactory作为Spring Bean的工厂类，则所有的bean都是在第一次使用该Bean的时候实例化 



 第二：如果你使用ApplicationContext作为Spring Bean的工厂类，则又分为以下几种情况： 
    （1）：如果bean的scope是singleton的，并且lazy-init为false（默认是false，所以可以不用设置），则 ApplicationContext启动的时候就实例化该Bean，并且将实例化的Bean放在一个map结构的缓存中，下次再使 用该 Bean的时候，直接从这个缓存中取 
    （2）：如果bean的scope是singleton的，并且lazy-init为true，则该Bean的实例化是在第一次使用该Bean的时候进 行实例化 
    （3）：如果bean的scope是prototype的，则该Bean的实例化是在第一次使用该Bean的时候进行实例化 



## 引用 https://blog.csdn.net/java_gchsh/article/details/78111200