import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * @lc app=leetcode.cn id=17 lang=java
 *
 * [17] 电话号码的字母组合
 */

// @lc code=start
class Solution {
    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {
            return new ArrayList<>();
        }
        Map<Integer, String[]> ready = new HashMap();
        ready.put(2, new String[] { "a", "b", "c" });
        ready.put(3, new String[] { "d", "e", "f" });
        ready.put(4, new String[] { "g", "h", "i" });
        ready.put(5, new String[] { "j", "k", "l" });
        ready.put(6, new String[] { "m", "n", "o" });
        ready.put(7, new String[] { "p", "q", "r", "s" });
        ready.put(8, new String[] { "t", "u", "v" });
        ready.put(9, new String[] { "w", "x", "y", "z" });
        ArrayList<String> res = new ArrayList<>();
        int len = digits.length();
        dp(new StringBuffer(), 0, digits, res, ready);
        return res;
    }

    public void dp(StringBuffer link, Integer index, String s, ArrayList<String> res, Map<Integer, String[]> ready) {
        if (index == s.length()) {
            res.add(link.toString());
            return;
        }
        int num = Integer.parseInt(s.substring(index, index + 1));
        String[] st = ready.get(num);
        for (String item : st) {
            link.append(item);
            dp(link, index + 1, s, res, ready);
            link.delete(index, index + 1);
        }
    }
}
// @lc code=end
