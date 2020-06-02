```
<el-dialog 
title="修改用户权限" 
:visible.sync="editDialogVisible"
width="50%"
:close-on-click-modal="false"
:fullscreen="false">
</el-dialog>
```

如果不加':' 默认等号后面看作字符串，如果组件所需要的值也是字符串，则可以生效，但是不能动态绑定
如果加  ':' 默认等号后面看作对象，可以实现动态绑定

如果需要动态绑定的属性例如 :fullscreen="false" 没有加':' 则属性值设置为true，无论等号后面是否是其他值
