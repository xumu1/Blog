//给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。 
//
// 说明：每次只能向下或者向右移动一步。 
//
// 
//
// 示例 1： 
//
// 
//输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
//输出：7
//解释：因为路径 1→3→1→1→1 的总和最小。
// 
//
// 示例 2： 
//
// 
//输入：grid = [[1,2,3],[4,5,6]]
//输出：12
// 
//
// 
//
// 提示： 
//
// 
// m == grid.length 
// n == grid[i].length 
// 1 <= m, n <= 200 
// 0 <= grid[i][j] <= 100 
// 
// Related Topics 数组 动态规划 
// 👍 764 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int minPathSum(int[][] grid) {
        // 类似63题，同样使用dp数组
        int m = grid.length;
        int n = grid[0].length;
        Integer[][] table = new Integer[m][n];
        table[m - 1][n - 1] = grid[m - 1][n - 1];
        // 处理边界
        for (int i = m - 2; i >= 0; i--) {
            table[i][n - 1] = grid[i][n - 1] + table[i + 1][n - 1];
        }
        for (int i = n - 2; i >= 0; i--) {
            table[m - 1][i] = grid[m - 1][i] + table[m - 1][i + 1];
        }
        return dp(0, 0, table, grid);
    }

    public int dp(int i, int j, Integer[][] table, int[][] grid) {
        if (table[i][j] != null) {
            return table[i][j];
        }
        table[i][j] = Math.min(dp(i + 1, j, table, grid), dp(i, j + 1, table, grid)) + grid[i][j];
        return table[i][j];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
