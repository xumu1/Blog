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
        StringBuffer buf = new StringBuffer();
        int index1 = num1.length() - 1;
        int index2 = num2.length() - 1;
        int carry = 0;
        while (index1 >= 0 || index2 >= 0) {
            int i1 = index1 >= 0 ? num1.charAt(index1) - '0' : 0;
            int i2 = index2 >= 0 ? num2.charAt(index2) - '0' : 0;
            buf.append((i1 + i2 + carry) % 10);
            carry = (i1 + i2 + carry) / 10;
            index1--;
            index2--;
        }
        if (carry == 1) {
            buf.append("1");
        }
        return buf.reverse().toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
