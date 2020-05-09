![](https://upload-images.jianshu.io/upload_images/19635758-98d67624b92b4c72.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)![](https://upload-images.jianshu.io/upload_images/19635758-119998d2f1828236.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
### 暴力方法，逐步处理字符，逐步判断大小是否过界。
```
class Solution {
    public int myAtoi(String str) {
        //整体思路：
        //设定flag，先遍历一遍，当遇到非数字时，flag=false，返回下标值，下次遍历从此序号开始
        //每次将得到的数字*10，循环得到最终数值，INT_MAX,INT_MIN作为判断条件
        
        //1.修剪字符串
        char[] chars = str.trim().toCharArray();
        if(chars == null || chars.length == 0){
            return 0;
        }
        int total = 0; //总数
        int index = 0; //序号
        boolean flag = true; //判断是否脱离数字位的标志
        int sign = 1;//表示正负
        int INT_MAX = Integer.MAX_VALUE;
        int INT_MIN = Integer.MIN_VALUE;
        //2.判断符号
        if(chars[0] == '-'){
            sign = -1;
            index++;
        }
        if(chars[0] == '+')
            index++;
       //3.当flag为true时，进入循环，false时说明已经脱离数字位
        while(flag == true && index < chars.length){
            //非数字
            if(!(chars[index] <= '9' && chars[index] >= '0')){
                flag = false;
                continue;
            }else{
                //是数字
                //判断是否过界
                if((total > INT_MAX / 10) || ((total == INT_MAX / 10) && ((chars[index]-'0') > INT_MAX % 10))){
                    return INT_MAX;
                }
                if((total < INT_MIN / 10) || ((total == INT_MIN / 10) && ((chars[index]-'0') > -1*(INT_MIN % 10)))){
                    return INT_MIN;
                }
                total = total*10 + sign*(chars[index]-'0');
                index++;
            }
        }
        return total;
    }
}
```
