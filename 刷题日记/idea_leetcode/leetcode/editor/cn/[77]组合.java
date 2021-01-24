//给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。 
//
// 示例: 
//
// 输入: n = 4, k = 2
//输出:
//[
//  [2,4],
//  [3,4],
//  [2,3],
//  [1,2],
//  [1,3],
//  [1,4],
//] 
// Related Topics 回溯算法 
// 👍 472 👎 0


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        // 使用回溯法
        ArrayList<List<Integer>> res = new ArrayList<>();
        dfs(res, new ArrayList<Integer>(), n, k, 0, 1);
        return res;
    }

    private void dfs(ArrayList<List<Integer>> res, ArrayList<Integer> chain, int n, int k, int deep, int index) {
        if (deep == k) {
            if (chain.size() == k) {
                res.add(new ArrayList<>(chain));
            }
            return;
        }
        for (int i = index; i <= n; i++) {
            chain.add(i);
            dfs(res, chain, n, k, deep + 1, i + 1);
            chain.remove(chain.size() - 1);
        }

    }
}
//leetcode submit region end(Prohibit modification and deletion)
