# cnpm - 解决 " cnpm : 无法加载文件C:\Users\93457\AppData\Roaming\npm\cnpm.ps1，因为在此系统上禁止运行脚本。有关详细信息 。。。 "

1.在win10 系统中搜索框 输入 Windos PowerShell
选择 管理员身份运行

2，打开了powershell命令行之后,输入

*set-ExecutionPolicy RemoteSigned*

然后更改权限为A

最后通过 get-ExeutionPolicy 查看当前的状态

![image-20200725111652731](https://img2018.cnblogs.com/blog/1599581/201910/1599581-20191008133850126-985501461.png)