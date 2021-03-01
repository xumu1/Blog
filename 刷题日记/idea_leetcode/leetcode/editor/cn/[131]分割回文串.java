//给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。 
//
// 返回 s 所有可能的分割方案。 
//
// 示例: 
//
// 输入: "aab"
//输出:
//[
//  ["aa","b"],
//  ["a","a","b"]
//] 
// Related Topics 深度优先搜索 动态规划 回溯算法 
// 👍 500 👎 0


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<String>> partition(String s) {
        char[] array = s.toCharArray();
        ArrayList<List<String>> res = new ArrayList<>();
        dfs(array, new LinkedList<String>(), 0, res);
        return res;
    }

    public void dfs(char[] array, LinkedList<String> chain, int index, ArrayList<List<String>> res) {
        if (index == array.length) {
            res.add(new LinkedList<>(chain));
            return;
        }
        for (int i = index; i < array.length; i++) {
            if (!checkString(array, index, i)) {
                continue;
            }
            chain.addLast(new String(array, index, i + 1 - index));
            dfs(array, chain, i + 1, res);
            chain.remove(chain.size() - 1);
        }
    }

    public boolean checkString(char[] charArray, int head, int tail) {
        while (head < tail) {
            if (charArray[head] != charArray[tail]) {
                return false;
            }
            head++;
            tail--;
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
