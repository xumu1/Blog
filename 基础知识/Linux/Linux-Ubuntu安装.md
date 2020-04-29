# 一、下载Ubuntu镜像

下载地址：[http://www.ubuntu.com](http://www.ubuntu.com/)
![](https://upload-images.jianshu.io/upload_images/19635758-70da44233e5c6bb7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

点击download，选择Ubuntu Desktop：
![](https://upload-images.jianshu.io/upload_images/19635758-9a7881206a2adc75.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
推荐使用LTS长期支持版本。
![](https://upload-images.jianshu.io/upload_images/19635758-12e2aa58819247a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
点击Download进行镜像下载

# 二、使用rufus进行启动盘制作
未来会在blog写一篇专门介绍使用方法，请先根据网上教程进行制作。

# 三、连接U盘准备安装
启动电脑，因为制作了U盘启动器，可以直接进入Ubuntu安装界面
1. 首先选择语言为中文(简体)，点继续；
![](https://upload-images.jianshu.io/upload_images/19635758-ba208f11ee017cf7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2. 键盘布局已经自动选择好了，你可以选择不改变；
![](https://upload-images.jianshu.io/upload_images/19635758-d489920619d142f1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
3. 接着无线选项中，选择我现在不想连接wifi无线网络；
![](https://upload-images.jianshu.io/upload_images/19635758-0f4b62ca11f72208.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
4. 在更新和其他软件中，选择最小安装以加快安装速度；
![](https://upload-images.jianshu.io/upload_images/19635758-15123a25cebc02ed.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
5. 在安装类型中，选择其他选项；
![](https://upload-images.jianshu.io/upload_images/19635758-4e96162d9603c95d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
6. 分区建议 256GB SSD硬盘为例
- /boot/ 大概256MB
- swap交换区 大概2GB
- / 根目录50G
- /home 剩下的所有空间
统一使用逻辑分区，Ext4文件系统，网上也有很多分区推荐，也可根据自己喜好分配。
7. 时区选择以自动选择为Shanghai，输入电脑的用户名、电脑名称和密码，等待安装完成后，即可进入新的Ubuntu系统。

# 常见问题
- 安装时提示错误：
如果你使用的不是LTS版本请换LTS版本再重装一边，或者更换分区表配置再试一下。也有可能是U盘的问题，换一个U盘试一下。建议使用uefi+gpt格式的启动U盘。




