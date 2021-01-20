//给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。 
//
// candidates 中的每个数字在每个组合中只能使用一次。 
//
// 说明： 
//
// 
// 所有数字（包括目标数）都是正整数。 
// 解集不能包含重复的组合。 
// 
//
// 示例 1: 
//
// 输入: candidates = [10,1,2,7,6,1,5], target = 8,
//所求解集为:
//[
//  [1, 7],
//  [1, 2, 5],
//  [2, 6],
//  [1, 1, 6]
//]
// 
//
// 示例 2: 
//
// 输入: candidates = [2,5,2,1,2], target = 5,
//所求解集为:
//[
//  [1,2,2],
//  [5]
//] 
// Related Topics 数组 回溯算法 
// 👍 474 👎 0


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public static int[] array;
    public static int len;


    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // 使用回溯法
        List<List<Integer>> res = new ArrayList<>();
        array = candidates;
        len = candidates.length;
        Arrays.sort(array);
        dfs(new ArrayList<Integer>(), target, res, 0);
        return res;
    }

    private void dfs(ArrayList<Integer> chain, int target, List<List<Integer>> res, int start) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(chain));
        }
        for (int i = start; i < len; i++) {
            if (i > start && array[i] == array[i - 1]) {
                continue;
            }
            int num = array[i];
            chain.add(num);
            dfs(chain, target - num, res, i + 1);
            chain.remove(chain.size() - 1);
        }


    }
}
//leetcode submit region end(Prohibit modification and deletion)
