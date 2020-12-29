//数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。 
//
// 
//
// 示例： 
//
// 输入：n = 3
//输出：[
//       "((()))",
//       "(()())",
//       "(())()",
//       "()(())",
//       "()()()"
//     ]
// 
// Related Topics 字符串 回溯算法 
// 👍 1490 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public static List<String> res;

    public List<String> generateParenthesis(int n) {
        int l = 0;
        int r = 0;
        String link = "";
        res = new ArrayList<>();
        recv(link, l, r, n);
        return res;
    }

    public void recv(String link, int l, int r, int len) {
        if (l > len || r > len || r > l) {
            return;
        }
        if (l == len && r == len) {
            res.add(link);
            return;
        }
        recv(link + "(", l + 1, r, len);
        recv(link + ")", l, r + 1, len);

    }
}
//leetcode submit region end(Prohibit modification and deletion)
