//将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。 
//
// 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下： 
//
// L   C   I   R
//E T O E S I I G
//E   D   H   N
// 
//
// 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。 
//
// 请你实现这个将字符串进行指定行数变换的函数： 
//
// string convert(string s, int numRows); 
//
// 示例 1: 
//
// 输入: s = "LEETCODEISHIRING", numRows = 3
//输出: "LCIRETOESIIGEDHN"
// 
//
// 示例 2: 
//
// 输入: s = "LEETCODEISHIRING", numRows = 4
//输出: "LDREOEIIECIHNTSG"
//解释:
//
//L     D     R
//E   O E   I I
//E C   I H   N
//T     S     G 
// Related Topics 字符串 
// 👍 951 👎 0


import java.util.ArrayList;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        ArrayList<StringBuffer> res = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            res.add(new StringBuffer());
        }
        int len = s.length();
        int index = 0;
        // 正向
        boolean revTag = true;
        for (int i = 0; i < len; i++) {
            res.get(index).append(s.charAt(i));
            if (revTag) {
                if (index == numRows - 1) {
                    revTag = false;
                    index--;
                } else {
                    index++;
                }
            } else {
                if (index == 0) {
                    revTag = true;
                    index++;
                } else {
                    index--;
                }
            }
        }
        StringBuffer result = new StringBuffer();
        for (StringBuffer buffer : res) {
            result.append(buffer);
        }
        return result.toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
