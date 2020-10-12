import java.util.ArrayList;

/*
 * @lc app=leetcode.cn id=78 lang=java
 *
 * [78] 子集
 */

// @lc code=start
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        int len = nums.length;
        int count = (int) Math.pow(2, len);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int x = i;
            int index = 0;
            ArrayList<Integer> item = new ArrayList<>();
            while (x != 0) {
                if (x % 2 == 1) {
                    item.add(nums[index]);
                }
                index++;
                x /= 2;
            }
            res.add(item);
        }
        System.out.println(res.toString());
        return res;
    }
}
// @lc code=end
