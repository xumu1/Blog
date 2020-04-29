1.编辑apache2.conf文件
添加如下代码
```
AddType application/x-httpd-php .php
DirectoryIndex index.php index.htm index.html
```

2.查看是否缺少apache的php模块，在/etc/apche2/mods-avaliable/下查看是否有php.conf，没有的话需要安装：
```
sudo apt install libapache2-mod-php7.2    //安装模块
sudo a2enmod php7.2        //启动模块
```
3.安装php-mysqli
```
sudo apt install php-mysqli
```
4.重新设置chrome或者firfox的配置，恢复默认配置
