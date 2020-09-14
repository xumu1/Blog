/*
 * @lc app=leetcode.cn id=59 lang=java
 *
 * [59] 螺旋矩阵 II
 */

// @lc code=start
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] list = new int[n][n];
        int wall = n * n;
        int x = 0;
        int y = 0;
        int index = 1;
        if (wall > 0) {
            // top
            int t = 0;
            // botton
            int b = n - 1;
            // left
            int l = 0;
            // right
            int r = n - 1;

            for (int i = y; i < r; i++) {
                list[x][i] = index++;
            }
            t--;
            for (int i = x; i < b; i++) {
                list[i][y] = index++;
            }
            r--;
            for (int i = y; i > l; i--) {
                list[x][i] = index++;
            }
            t++;
            for (int i = x; i > t; i--) {
                list[i][y] = index--;
            }
            l--;

            n--;
        }
    }
}
// @lc code=end
