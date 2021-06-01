//给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。 
//
// 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [0]
//输出：[[],[0]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10 
// -10 <= nums[i] <= 10 
// nums 中的所有元素 互不相同 
// 
// Related Topics 位运算 数组 回溯算法 
// 👍 1204 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 使用回溯法，有几个要点：
     * 1. 使用一个index，朝前看，不朝后看。防止重复，或者出现 1 3 2 这种情况。
     * 2. 每步的选择有两种：1：空  2：选
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        ArrayList<Integer> chain = new ArrayList<>();
        dfs(nums, 0,res,chain);
        return res;
    }
    public void dfs(int[] nums, int index, List<List<Integer>> res, ArrayList<Integer> chain){
        if (index == nums.length){
            res.add(new ArrayList<>(chain));
            return;
        }
        dfs(nums,index+1,res,chain);
        chain.add(nums[index]);
        dfs(nums,index+1,res,chain);
        chain.remove(chain.size()-1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
