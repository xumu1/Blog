1. git clone -b branch xxx 拉取指定分支的代码
2. git add xxx
3. git add . 添加本地所有文件
4. git commit -m "message"
5. git remote -v 查看远程分支
6. git push origin master
7. git branch -a 查看所有分支
8. git checkout -b dev 创建dev分支
9. git status 查看git状态
10. git checkout master 切换到master分支
11. git merge dev 合并dev分支（处于master分支）
12. git branch -d dev 删除本地dev分支
13. git branch -D dev 强制删除本地dev分支
14. git push origin --delete dev   可以删除远程分支dev  
15. git push -u origin dev 上传远程dev分支（上传本地后进行）
16. git reset --hard head^返回到上个分支
17. git log 查看日志
18. git reflog 查看日志
19. git reset --hard xxx(分支代号)
20. 当我想从远程仓库里拉取一条本地不存在的分支时：
git checkout -b 本地分支名 origin/远程分支名
这个将会自动创建一个新的本地分支，并与指定的远程分支关联起来。
21. git fetch是将远程主机的最新内容拉到本地，用户在检查了以后决定是否合并到工作本机分支中。而git pull 则是将远程主机的最新内容拉下来后直接合并，即：git pull = git fetch + git merge，这样可能会产生冲突，需要手动解决。
22. git remote update origin -p git 更新远程分支列表 

git config --global user.name "yourname"

git config --global user.email“your@email.com"

需要对电脑进行配置
