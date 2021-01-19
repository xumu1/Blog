//给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。 
//
// candidates 中的数字可以无限制重复被选取。 
//
// 说明： 
//
// 
// 所有数字（包括 target）都是正整数。 
// 解集不能包含重复的组合。 
// 
//
// 示例 1： 
//
// 输入：candidates = [2,3,6,7], target = 7,
//所求解集为：
//[
//  [7],
//  [2,2,3]
//]
// 
//
// 示例 2： 
//
// 输入：candidates = [2,3,5], target = 8,
//所求解集为：
//[
//  [2,2,2,2],
//  [2,3,3],
//  [3,5]
//] 
//
// 
//
// 提示： 
//
// 
// 1 <= candidates.length <= 30 
// 1 <= candidates[i] <= 200 
// candidate 中的每个元素都是独一无二的。 
// 1 <= target <= 500 
// 
// Related Topics 数组 回溯算法 
// 👍 1122 👎 0


import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public static int[] array;
    public static int len;
    public static int tag;


    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 使用回溯法
        List<List<Integer>> res = new ArrayList<>();
        array = candidates;
        len = candidates.length;
        tag = target;
        dp(new ArrayList<>(), 0,res,0);
        return res;
    }

    private void dp(ArrayList<Integer> chain, int chainNumber,List<List<Integer>> res,int index) {
        if (chainNumber > tag) {
            return;
        }
        for (int i = index; i < len; i++) {
            int num = array[i];
            chain.add(num);
            if (chainNumber + num == tag) {
                res.add(new ArrayList<>(chain));
            }
            dp(chain, num + chainNumber,res,i);
            chain.remove(chain.size() - 1);

        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
