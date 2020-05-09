![leetcode-7-整数反转](https://upload-images.jianshu.io/upload_images/19635758-396c450877611572.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
### 分析题目
1. int number_reverse作为结果返回
2.  使用" / "求出除10后的值记为x，使用" % "求出除10后的余数记为j
3.  循环直到x==0
4. 循环过程需要判断number_reverse是否会超出Integer.MAX_VALUE，或者小于Integer.MIN_VALUE
```
class Solution {
    public int reverse(int x) {
        int number_reverse = 0;
        int j = 0;
        while(x !=0 ){
            j = x%10;
            x = x/10;
            if(number_reverse > Integer.MAX_VALUE/10 || (number_reverse == Integer.MAX_VALUE / 10 && j > Integer.MAX_VALUE % 10))
            return 0;
            if(number_reverse < Integer.MIN_VALUE/10 || (number_reverse == Integer.MIN_VALUE / 10 && j < Integer.MIN_VALUE % 10))
            return 0;
            number_reverse = number_reverse*10 + j;        
        }        
    return number_reverse;
    }
}
```
### 收获
- 使用Integer.MAX_VALUE和Integer.MIN_VALUE可以简化判断。
- 注意边界条件的判断。
