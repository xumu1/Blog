/*
 * @lc app=leetcode.cn id=343 lang=java
 *
 * [343] 整数拆分
 */

// @lc code=start
class Solution {
    public int integerBreak(int n) {
        int[] res = new int[n+4];
        res[1] = 1;
        res[2] = 2;
        res[3] = 3;
        for (int i = 4; i <= n; i++) {
            System.out.println(i);
            int max = 0;
            for (int j = 1; j <= i/2; j++) {
                max = Math.max(res[i-j]*res[j],max);
            }
            res[i] = max;
            System.out.println("res"+i+":"+res[i]);

        }
        res[1] = 0;
        res[2] = 1;
        res[3] = 2;
        return res[n];
    }
}
// @lc code=end

