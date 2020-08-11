# linux（centos）安装jdk

1. 下载jar包解压到文件夹

2. 修改 /etc/profile

   ```
   JAVA_HOME=/usr/java/jdk1.8.0_181
   
   CLASSPATH=$JAVA_HOME/lib/
   
   PATH=$PATH:$JAVA_HOME/bin
   
   export PATH JAVA_HOME CLASSPATH
   ```

3. 刷新 /etc/profile

   ```
   source /etc/profile
   ```

   