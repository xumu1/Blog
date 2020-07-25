# idea使用技巧

# 设置代码提示对大小写不敏感

setting->Editor->General->Code Completion
取消勾选Match case

# 在Intelij IDEA中修改maven为国内镜像（阿里）

打开IntelliJ IDEA->Settings ->Build, Execution, Deployment -> Build Tools > Maven

或者直接搜索maven

修改settings.xml

```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          https://maven.apache.org/xsd/settings-1.0.0.xsd">

      <mirrors>
        <mirror>  
            <id>alimaven</id>  
            <name>aliyun maven</name>  
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>  
            <mirrorOf>central</mirrorOf>          
        </mirror>  
      </mirrors>
</settings>
```

