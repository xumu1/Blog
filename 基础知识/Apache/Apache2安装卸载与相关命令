# Apache2安装
ubuntu下安装命令：
sudo apt update
sudo apt install apache2
安装完成之后，在浏览器中输入：127.0.0.1:80(默认端口80，不写80也可以），即可看到html文件下的网页内容；

apache默认开机启动
- 启动apache服务
sudo service apache2 start
- 重新启动apache服务
sudo service apache2 restart
- 停止apache服务
sudo service apache2 stop
- 查看apache状态
sudo service apache2 status
sudo systemctl service status apache2

# 卸载
sudo apt-get –purge remove apache2
sudo apt-get –purge remove apache2-common
sudo apt-get –purge remove apache2-utils
sudo apt-get autoremove apache2
–purge 是不保留配置文件的意思
删掉/etc/apache2文件夹:
sudo rm -rf /etc/apache2
删掉/var/www文件夹:
sudo rm -rf /var/www
删掉/etc/init.d/apache2文件:
sudo rm -rf /etc/init.d/apache2
