//给定一个 没有重复 数字的序列，返回其所有可能的全排列。 
//
// 示例: 
//
// 输入: [1,2,3]
//输出:
//[
//  [1,2,3],
//  [1,3,2],
//  [2,1,3],
//  [2,3,1],
//  [3,1,2],
//  [3,2,1]
//] 
// Related Topics 回溯算法 
// 👍 1331 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 使用回溯的方法
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        fun(nums, res, new ArrayList<Integer>(), 0, nums.length);
        return res;
    }

    private void fun(int[] nums, List<List<Integer>> res, ArrayList<Integer> chain, int depth, int len) {
        if (depth == len) {
            res.add(new ArrayList<>(chain));
        }
        for (int i = 0; i < len; i++) {
            int num = nums[i];
            if (num != Integer.MIN_VALUE) {
                chain.add(num);
                nums[i] = Integer.MIN_VALUE;
                fun(nums, res, chain, depth + 1, len);
                chain.remove(chain.size() - 1);
                nums[i] = num;
            }
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
