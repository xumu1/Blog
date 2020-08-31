/*
 * @lc app=leetcode.cn id=50 lang=java
 *
 * [50] Pow(x, n)
 */

// @lc code=start
class Solution {
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (x == 1) {
            return 1;
        }
        long N = n;
        if (n > 0) {
            return dp(x, N);
        } else {
            return 1 / dp(x, -N);
        }
    }

    public double dp(double x, long n) {
        if (n == 0) {
            return 1;
        }
        if (n % 2 == 0) {
            // 偶数
            double item = dp(x, n / 2);
            return item * item;
        } else {
            // 奇数
            double item = dp(x, (n - 1) / 2);
            return item * item * x;
        }
    }
}
// @lc code=end
