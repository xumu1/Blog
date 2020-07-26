/*
 * @lc app=leetcode.cn id=70 lang=java
 *
 * [70] 爬楼梯
 */

// @lc code=start
class Solution {

    public int climbStairs(int n) {
        int[] dp = new int[n+2];
        dp[1] = 1;
        dp[2] = 2;
        if(n < 3)
            return dp[n];
        for(int i = 3; i < n+1; i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
}
// @lc code=end

