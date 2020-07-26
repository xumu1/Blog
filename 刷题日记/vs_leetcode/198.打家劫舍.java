/*
 * @lc app=leetcode.cn id=198 lang=java
 *
 * [198] 打家劫舍
 */

// @lc code=start
class Solution {
    private int[] pb;
    public int rob(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }else if(nums.length == 1){
            return nums[0];
        }else if(nums.length == 2){
            return Math.max(nums[0], nums[1]);
        }else if(nums.length == 3){
            return Math.max(nums[1],nums[0]+nums[2]);
        }
        pb = new int[nums.length + 3];
        pb[0] = nums[0];
        pb[1] = nums[1];
        pb[2] = nums[2] + nums[0];
        for(int i = 3; i < nums.length; i++){
            pb[i] = nums[i] + Math.max(pb[i-2],pb[i-3]);
        }
        return Math.max(pb[nums.length-1],pb[nums.length-2]);
    }
}
// @lc code=end

