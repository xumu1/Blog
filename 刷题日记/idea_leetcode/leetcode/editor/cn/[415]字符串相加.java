//给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。 
//
// 
//
// 提示： 
//
// 
// num1 和num2 的长度都小于 5100 
// num1 和num2 都只包含数字 0-9 
// num1 和num2 都不包含任何前导零 
// 你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式 
// 
// Related Topics 字符串 
// 👍 364 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String addStrings(String num1, String num2) {
        int res = 0;
        StringBuffer buf = new StringBuffer();
        char[] shortChars = num1.length() > num2.length() ? num2.toCharArray() : num1.toCharArray();
        char[] longChars = num1.length() > num2.length() ? num1.toCharArray() : num2.toCharArray();
        int shortIndex = shortChars.length - 1;
        int longIndex = longChars.length - 1;
//        System.out.println("shortChars = " + Arrays.toString(shortChars));
//        System.out.println("longChars = " + Arrays.toString(longChars));
        while (shortIndex >= 0) {
            int tmp = shortChars[shortIndex] + longChars[longIndex] - '0' - '0' + res;
            res = tmp / 10;
            buf.append(tmp % 10);
            shortIndex--;
            longIndex--;
        }
        while (longIndex >= 0) {
            int tmp = longChars[longIndex] - '0' + res;
            res = tmp / 10;
            buf.append(tmp % 10);
            longIndex--;
        }
        if (res != 0){
            buf.append(res);
        }
//        System.out.println("buf.toString() = " + buf.toString());
        return buf.reverse().toString();


    }
}
//leetcode submit region end(Prohibit modification and deletion)
