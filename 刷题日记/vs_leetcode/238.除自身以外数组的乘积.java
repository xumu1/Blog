/*
 * @lc app=leetcode.cn id=238 lang=java
 *
 * [238] 除自身以外数组的乘积
 */

// @lc code=start
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        int k = 1;
        for (int i = 0; i < res.length; i++) {
            res[i] = k;
            k *= nums[i];
        }
        k = 1;
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = res[i] * k;
            k *= nums[i];
        }
        return res;
    }
}
// @lc code=end
