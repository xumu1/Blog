/*
 * @lc app=leetcode.cn id=746 lang=java
 *
 * [746] 使用最小花费爬楼梯
 */

// @lc code=start
class Solution {
    // 思路1.dp数组代表到达当前点需要的最小消费和
    private int[] dp;
    public int minCostClimbingStairs(int[] cost) {
        dp = new int[cost.length];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for(int i = 2; i < cost.length; i++){
            dp[i] = cost[i] + Math.min(dp[i-1], dp[i-2]);
        }
        return Math.min(dp[cost.length-2],dp[cost.length-1]);
    }


    // // 思路2.递归(超时)
    // public int minCostClimbingStairs(int[] cost) {
    //     return Math.min(dp(cost,0,0),dp(cost,0,1));
    // }
    // // 从当前点消费到底的最低费用
    // public int dp(int[] cost,int sum, int n){
    //     if(n == cost.length - 1 || n == cost.length - 2){
    //         return cost[n];
    //     }
    //     return Math.min(dp(cost,sum+cost[n],n+1),dp(cost,sum+cost[n],n+2)) + cost[n];
    // }
}
// @lc code=end

