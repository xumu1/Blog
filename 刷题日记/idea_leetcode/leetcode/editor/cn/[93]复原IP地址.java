//给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。 
//
// 有效的 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。 
//
// 例如："0.1.2.201" 和 "192.168.1.1" 是 有效的 IP 地址，但是 "0.011.255.245"、"192.168.1.312"
// 和 "192.168@1.1" 是 无效的 IP 地址。 
//
// 
//
// 示例 1： 
//
// 输入：s = "25525511135"
//输出：["255.255.11.135","255.255.111.35"]
// 
//
// 示例 2： 
//
// 输入：s = "0000"
//输出：["0.0.0.0"]
// 
//
// 示例 3： 
//
// 输入：s = "1111"
//输出：["1.1.1.1"]
// 
//
// 示例 4： 
//
// 输入：s = "010010"
//输出：["0.10.0.10","0.100.1.0"]
// 
//
// 示例 5： 
//
// 输入：s = "101023"
//输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
// 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 3000 
// s 仅由数字组成 
// 
// Related Topics 字符串 回溯算法 
// 👍 493 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<String> restoreIpAddresses(String s) {
        ArrayList<String> res = new ArrayList<>();
        dfs(res, new ArrayList<String>(), s, 4, 0);
        return res;
    }

    public void dfs(ArrayList<String> res, ArrayList<String> chain, String reminder, int count, int depth) {
        if (depth == 4) {
            if (reminder == null || reminder.length() == 0) {
                res.add(String.join(".", chain));
            }
            return;
        }
        if (reminder.length() >= 1) {
            String s1 = reminder.substring(0, 1);
            chain.add(s1);
            dfs(res, chain, reminder.substring(1), count - 1, depth + 1);
            chain.remove(chain.size() - 1);
            if (reminder.length() >= 2) {
                String s2 = reminder.substring(0, 2);
                if (s2.charAt(0) == '0') {
                    return;
                } else {
                    chain.add(s2);
                    dfs(res, chain, reminder.substring(2), count - 1, depth + 1);
                    chain.remove(chain.size() - 1);
                }
            }
            if (reminder.length() >= 3) {
                String s3 = reminder.substring(0, 3);
                if (Integer.parseInt(s3) <= 255) {
                    chain.add(s3);
                    dfs(res, chain, reminder.substring(3), count - 1, depth + 1);
                    chain.remove(chain.size() - 1);
                }
            }
        }


    }

    ;
}
//leetcode submit region end(Prohibit modification and deletion)
