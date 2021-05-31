//给定一个只包含数字的字符串，用以表示一个 IP 地址，返回所有可能从 s 获得的 有效 IP 地址 。你可以按任何顺序返回答案。 
//
// 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。 
//
// 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312" 
//和 "192.168@1.1" 是 无效 IP 地址。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "25525511135"
//输出：["255.255.11.135","255.255.111.35"]
// 
//
// 示例 2： 
//
// 
//输入：s = "0000"
//输出：["0.0.0.0"]
// 
//
// 示例 3： 
//
// 
//输入：s = "1111"
//输出：["1.1.1.1"]
// 
//
// 示例 4： 
//
// 
//输入：s = "010010"
//输出：["0.10.0.10","0.100.1.0"]
// 
//
// 示例 5： 
//
// 
//输入：s = "101023"
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
// 👍 580 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        ArrayList<Integer> chain = new ArrayList<>();
        dfs(res, s, chain, 0, 0);
        return res;
    }

    public void dfs(List<String> res, String s, ArrayList<Integer> chain, int depth, int start) {
        if (depth == 4) {
            if (start == s.length()) {
                res.add(buildString(chain));
            }
            return;
        } else {
            if (start >= s.length()) {
                return;
            }
        }
        if (s.charAt(start) == '0') {
            chain.add(0);
            dfs(res, s, chain, depth + 1, start + 1);
            chain.remove(chain.size() - 1);
        } else {
            for (int i = 1; i <= 3 && start + i <= s.length(); i++) {
                int item = Integer.parseInt(s.substring(start, start + i));
                if (item > 255) {
                    return;
                }
                chain.add(item);
                dfs(res, s, chain, depth + 1, start + i);
                chain.remove(chain.size() - 1);
            }

        }

    }

    public String buildString(ArrayList<Integer> list) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            buffer.append(list.get(i));
            if (i == 3) {
                break;
            }
            buffer.append(".");
        }
        return buffer.toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
