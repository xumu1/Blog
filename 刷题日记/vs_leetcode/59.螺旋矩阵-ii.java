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
        // 当前点坐标
        int x = 0;
        int y = 0;
        int index = 1;
        // top
        int t = 0;
        // botton
        int b = n - 1;
        // left
        int l = 0;
        // right
        int r = n - 1;
        if (n % 2 != 0) {
            // odd
            wall--;
            list[(n - 1) / 2][(n - 1) / 2] = n * n;
        } else {
            wall--;
            list[(n + 1) / 2][(n - 1) / 2] = n * n;
        }
        while (wall > 0) {
            for (int i = y; i < r; i++) {
                list[x][i] = index++;
                System.out.println("x: y: list[x][i]1: " + x + " " + i + " " + list[x][i] + " " + wall);
                y++;
                wall--;
                if (wall <= 0) {
                    break;
                }
            }
            t++;
            for (int i = x; i < b; i++) {
                list[i][y] = index++;
                System.out.println("x: y: list[i][y]1: " + i + " " + y + " " + list[i][y] + " " + wall);
                x++;
                wall--;
                if (wall <= 0) {
                    break;
                }
            }
            r--;
            for (int i = y; i > l; i--) {
                list[x][i] = index++;
                System.out.println("x: y: list[x][i]2: " + x + " " + i + " " + list[x][i] + " " + wall);
                y--;
                wall--;
                if (wall <= 0) {
                    break;
                }
            }
            b--;
            for (int i = x; i > t; i--) {
                list[i][y] = index++;
                System.out.println("x: y: list[i][y]2: " + i + " " + y + " " + list[i][y] + " " + wall);
                x--;
                wall--;
                if (wall <= 0) {
                    break;
                }
            }
            l++;
        }
        return list;
    }
}
// @lc code=end
