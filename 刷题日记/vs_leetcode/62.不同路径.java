/*
 * @lc app=leetcode.cn id=62 lang=java
 *
 * [62] 不同路径
 */

// @lc code=start
class Solution {
    public int uniquePaths(int m, int n) {
        int[] res = new int[m];
        for (int i = 0; i < res.length; i++) {
            res[i] = 1;
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = 1; j < res.length; j++) {
                res[j] += res[j - 1];

            }
        }
        return res[m - 1];
    }
}
// @lc code=end
