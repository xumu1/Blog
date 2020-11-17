/*
 * @lc app=leetcode.cn id=231 lang=java
 *
 * [231] 2的幂
 */

// @lc code=start
class Solution {
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
        int x = n % 2;
        while (x != 1) {
            n = n / 2;
            x = n % 2;
        }
        if (n == 1) {
            return true;
        } else {
            return false;
        }
    }
}
// @lc code=end
