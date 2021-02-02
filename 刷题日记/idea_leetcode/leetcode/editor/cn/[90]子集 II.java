//给定一个可能包含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。 
//
// 说明：解集不能包含重复的子集。 
//
// 示例: 
//
// 输入: [1,2,2]
//输出:
//[
//  [2],
//  [1],
//  [1,2,2],
//  [2,2],
//  [1,2],
//  []
//] 
// Related Topics 数组 回溯算法 
// 👍 376 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        int len = nums.length;
        Arrays.sort(nums);
        dfs(res, nums, len, 0, new ArrayList<Integer>());
        return res;
    }

    public void dfs(ArrayList<List<Integer>> res, int[] nums, int len, int depth, ArrayList<Integer> chain) {
        if (depth == len) {
            res.add(new ArrayList<>(chain));
            return;
        }
        int num = nums[depth];
        if (!chain.contains(num)) {
            dfs(res, nums, len, depth + 1, chain);
        }
        chain.add(num);
        dfs(res, nums, len, depth + 1, chain);
        chain.remove(chain.size() - 1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
