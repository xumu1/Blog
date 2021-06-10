//给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。 
//
// 示例 1: 
//
// 输入: num1 = "2", num2 = "3"
//输出: "6" 
//
// 示例 2: 
//
// 输入: num1 = "123", num2 = "456"
//输出: "56088" 
//
// 说明： 
//
// 
// num1 和 num2 的长度小于110。 
// num1 和 num2 只包含数字 0-9。 
// num1 和 num2 均不以零开头，除非是数字 0 本身。 
// 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。 
// 
// Related Topics 数学 字符串 
// 👍 649 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        String res = "0";
        for (int i = num2.length() - 1; i >= 0; i--) {
            int i1 = num2.charAt(i) - '0';
            StringBuilder buf = new StringBuilder();
            for (int j = 0; j < num2.length() - 1 - i; j++) {
                buf.append("0");
            }
            int carry = 0;
            for (int j = num1.length() - 1; j >= 0; j--) {
                int i2 = num1.charAt(j) - '0';
                int tmp = i1 * i2 + carry;
                buf.append(tmp % 10);
                carry = tmp / 10;
            }
            if (carry != 0) {
                buf.append(carry % 10);
            }
            res = addStrings(res, buf.reverse().toString());
        }
            return res;
    }

    private String addStrings(String num1, String num2) {
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
