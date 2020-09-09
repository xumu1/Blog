import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=46 lang=java
 *
 * [46] 全排列
 */

// @lc code=start
class Solution {
    // 回溯法 dfs
    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        List<List<Integer>> list = new ArrayList<>();
        if (len == 0)
            return list;
        boolean[] used = new boolean[nums.length];
        dfs(nums, list, 0, len, used, new ArrayList<Integer>());
        return list;
    }

    public void dfs(int[] nums, List<List<Integer>> list, int depth, int len, boolean[] used, List<Integer> chain) {
        if (depth == len) {
            list.add(new ArrayList<>(chain));
        }
        for (int i = 0; i < used.length; i++) {
            if (!used[i]) {
                used[i] = true;
                chain.add(nums[i]);
                dfs(nums, list, depth + 1, len, used, chain);
                used[i] = false;
                chain.remove(chain.size() - 1);
            }
        }
    }
}
// @lc code=end
