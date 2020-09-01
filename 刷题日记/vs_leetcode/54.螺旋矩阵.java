import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=54 lang=java
 *
 * [54] 螺旋矩阵
 */

// @lc code=start
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix.length == 0)
            return new ArrayList<>();
        int l = 0;
        int r = matrix[0].length - 1;
        int t = 0;
        int b = matrix.length - 1;
        ArrayList<Integer> res = new ArrayList<>();
        while (true) {
            for (int i = l; i <= r; i++)
                res.add(matrix[t][i]); // left to right.
            if (++t > b)
                break;
            for (int i = t; i <= b; i++)
                res.add(matrix[i][r]); // top to bottom.
            if (l > --r)
                break;
            for (int i = r; i >= l; i--)
                res.add(matrix[b][i]); // right to left.
            if (t > --b)
                break;
            for (int i = b; i >= t; i--)
                res.add(matrix[i][l]); // bottom to top.
            if (++l > r)
                break;
        }
        return res;
    }
}
// @lc code=end
