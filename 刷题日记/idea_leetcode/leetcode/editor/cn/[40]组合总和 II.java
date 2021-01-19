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
        System.out.println(Arrays.toString(array));
        ArrayList<Integer> objects = new ArrayList<Integer>();
        objects.add(0);
        dp(0,objects , target, res);
        return res;
    }

    private void dp(int step, ArrayList<Integer> chain, int target, List<List<Integer>> res) {
        if (step == len || target <= 0) {
            return;
        }
        if (array[step] == target) {
            chain.add(array[step]);
            System.out.println(chain.toString());
            System.out.println("===" + array[step] + "===\t" + step);
            ArrayList<Integer> integers = new ArrayList<>(chain);
            integers.remove(0);
            res.add(integers);
            chain.remove(chain.size() - 1);
            // 不选
            dp(step + 1, chain, target, res);
        } else {
            int i = array[step];

            // 选这个数
            if (step > 0 && array[step] == array[step - 1] && chain.get(chain.size() - 1) != array[step - 1]) {
//                System.out.println(array[step]+"\t"+step);
                chain.add(i);
                // 不选
                dp(step + 1, chain, target, res);
                chain.remove(chain.size() - 1);
            } else {
                chain.add(i);
                // 选
                dp(step + 1, chain, target - i, res);
                chain.remove(chain.size() - 1);
                // 不选
                dp(step + 1, chain, target, res);
            }

        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
