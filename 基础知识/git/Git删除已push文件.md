# Git删除已提交的target .idea等文件

## 1.删除远程文件

```
git rm -r --cached .idea/*
git rm -r --cached target/*
(--cached 表示本地仍旧保留)
```



## 2.提交

```
git commit -m "删除不需要的文件"
git push
```

