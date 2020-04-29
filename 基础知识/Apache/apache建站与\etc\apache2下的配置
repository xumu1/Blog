安装apache服务器，默认配置存放在/etc/apache2/文件夹下  

![1.png](https://upload-images.jianshu.io/upload_images/19635758-347f4031bd5d890e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 分别介绍每个每个文件夹的含义  

### apache.conf

apache2.conf是主配置文件，当apache2服务器启动时，就将零散的配置文件以Includinng方式组合在一起。这个文件不是真正的具体配置文件，它只是把各个零散的配置文件以inluceding方式包含进来。apache2.conf其实就是负责调用其他配置文件的。下面图的前几句include语句证明了这个事情。

![image](https://upload-images.jianshu.io/upload_images/19635758-580346c4e580a50c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

上图下部分内容是对apche服务器可以访问的文件目录进行权限配置，如果需要新建一个网站，那么需要把网站的路径写成Directory标签写入apache.conf文件。例如上图的myzoo就是我个人的一个实验网站。一般配置为  

<Directory />  

        Options Indexes FollowSymLinks    

        AllowOverride None

        Require all granted   

        RewritenEngine On

</Directory>

如果出现错误，注意要打开RewritenEngine mod  

### ports.conf

![image](https://upload-images.jianshu.io/upload_images/19635758-63846f382eb10570.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

ports.cnf记录了服务器使用的端口号，默认监听Listen80，建立自己的网站需要在这里添加网站的端口号，否则无法访问。<IfModule ssl_module> Listen 443 </IfModule> 表示当ssl_module启动时，进行443端口的监听。

### 几个重要文件夹

conf-avaliable 可用配置

conf-enabled 已开启配置，其中放的是conf-avaliable的软链接

mods-avaliable 可用模块

mods-enabled 已开启模块，其中放的是mods-avaliable的软链接

sites-avaliable 可用网站，其中存放网站的配置信息，例如目录，端口

sites-enable 已启用的网站，其中放的是sites-avaliable的软链接

![image](https://upload-images.jianshu.io/upload_images/19635758-10a4d2cf7e75c91b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

sites-avaliable/000-default.conf记录了网站使用的端口:80，管理者邮箱webmaster@localhost，网站根目录/home/xumu/myzoo，错误日志信息地址。

## 现在我们来回顾一下建立一个简易的个人网站需要进行那些配置。  

- （告诉服务器自己要建立一个网站，即添加一个配置文件）

1.创建站点配置文件，设定网站端口，网站目录地址，可以模仿000-default.conf 来进行编写。

- （告诉服务器自己要用的端口，让服务器准备好端口监听）

2.修改/etc/apache2/ports.conf，添加新建网站的端口号进行监听。

- （允许服务器访问自己网站的目录资源）

3.修改/etc/apache2/apache.conf，添加网站目录访问权限。

4.开启自己的网站。命令：sudo a2ensite 网站配置文件 start

5.apache服务器重启，命令：service apache2 restart
