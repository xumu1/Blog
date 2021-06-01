//给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回
// -1。 
//
// 你可以认为每种硬币的数量是无限的。 
//
// 
//
// 示例 1： 
//
// 
//输入：coins = [1, 2, 5], amount = 11
//输出：3 
//解释：11 = 5 + 5 + 1 
//
// 示例 2： 
//
// 
//输入：coins = [2], amount = 3
//输出：-1 
//
// 示例 3： 
//
// 
//输入：coins = [1], amount = 0
//输出：0
// 
//
// 示例 4： 
//
// 
//输入：coins = [1], amount = 1
//输出：1
// 
//
// 示例 5： 
//
// 
//输入：coins = [1], amount = 2
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 1 <= coins.length <= 12 
// 1 <= coins[i] <= 231 - 1 
// 0 <= amount <= 104 
// 
// Related Topics 动态规划 
// 👍 1286 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {


//    参考答案，使用dp循环
//    public int coinChange(int[] coins, int amount) {
//        int max = amount + 1;
//        int[] dp = new int[amount + 1];
//        Arrays.fill(dp, max);
//        dp[0] = 0;
//        for (int i = 1; i <= amount; i++) {
//            for (int j = 0; j < coins.length; j++) {
//                if (coins[j] <= i) {
//                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
//                }
//            }
//        }
//        return dp[amount] > amount ? -1 : dp[amount];
//    }

    /**
     * 使用回溯法递归出结果,必须使用dp进行优化，不然时间复杂度非常高会超时
     * F(S)=F(S−C)+1
     */
    public int coinChange(int[] coins, int amount) {
        int[] dpTable = new int[amount + 1];
        dp(coins, amount, dpTable);
        System.out.println("Arrays.toString(dpTable) = " + Arrays.toString(dpTable));
        return dpTable[amount];
    }

    private int dp(int[] coins, int amount, int[] dpTable) {
        if (amount < 0) {
            return -1;
        }
        if (amount == 0) {
            return 0;
        }
        if (dpTable[amount] != 0) {
            return dpTable[amount];
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            int tmp = dp(coins, amount - coins[i], dpTable);
            if (tmp == -1) {
                continue;
            } else {
                min = Math.min(min, tmp);
            }
        }
        if (min == Integer.MAX_VALUE) {
            dpTable[amount] = -1;
        } else {
            dpTable[amount] = min + 1;
        }
        return dpTable[amount];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
