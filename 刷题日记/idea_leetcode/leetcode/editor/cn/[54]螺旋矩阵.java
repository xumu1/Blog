//给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。 
//
// 
//
// 示例 1： 
//
// 
//输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
//输出：[1,2,3,6,9,8,7,4,5]
// 
//
// 示例 2： 
//
// 
//输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
//输出：[1,2,3,4,8,12,11,10,9,5,6,7]
// 
//
// 
//
// 提示： 
//
// 
// m == matrix.length 
// n == matrix[i].length 
// 1 <= m, n <= 10 
// -100 <= matrix[i][j] <= 100 
// 
// Related Topics 数组 
// 👍 770 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 使用类似dfs的方法，类似贪吃蛇一样向前涌动，向右走不动了向下走，向下走不动了向左走，向左走不动了向上走，向上走不动了向右走，一直到尽头。
     * 遍历过的地方将值置为 Integer.MIN_VALUE
     *
     * @param matrix
     * @return
     */
    public static String move;
    public static Integer x;
    public static Integer y;

    public List<Integer> spiralOrder(int[][] matrix) {
        x = 0;
        y = -1;
        move = "right";
        ArrayList<Integer> ans = new ArrayList<>();
        boolean result = cal(matrix.length, matrix[0].length, matrix);
        while (result) {
            ans.add(matrix[x][y]);
            matrix[x][y] = Integer.MIN_VALUE;
            result = cal(matrix.length, matrix[0].length, matrix);
        }
        return ans;
    }

    private boolean cal(int xLength, int yLength, int[][] matrix) {
        if ("right".equals(move)) {
            if (y + 1 < yLength && matrix[x][y + 1] != Integer.MIN_VALUE) {
                y = y + 1;
                return true;
            } else if (x + 1 < xLength && matrix[x + 1][y] != Integer.MIN_VALUE) {
                x = x + 1;
                move = "down";
                return true;
            }
        }
        if ("left".equals(move)) {
            if (y - 1 >= 0 && matrix[x][y - 1] != Integer.MIN_VALUE) {
                y = y - 1;
                return true;
            } else if (x - 1 >= 0 && matrix[x - 1][y] != Integer.MIN_VALUE) {
                x = x - 1;
                move = "up";
                return true;
            }
        }
        if ("up".equals(move)) {
            if (x - 1 >= 0 && matrix[x - 1][y] != Integer.MIN_VALUE) {
                x = x - 1;
                return true;
            } else if (y + 1 < yLength && matrix[x][y + 1] != Integer.MIN_VALUE) {
                y = y + 1;
                move = "right";
                return true;
            }
        }
        if ("down".equals(move)) {
            if (x + 1 < xLength && matrix[x + 1][y] != Integer.MIN_VALUE) {
                x = x + 1;
                return true;
            } else if (y - 1 >= 0 && matrix[x][y - 1] != Integer.MIN_VALUE) {
                y = y - 1;
                move = "left";
                return true;
            }
        }
        return false;

    }
}
//leetcode submit region end(Prohibit modification and deletion)
