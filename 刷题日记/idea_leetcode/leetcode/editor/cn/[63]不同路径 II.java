//一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。 
//
// 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。 
//
// 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？ 
//
// 
//
// 网格中的障碍物和空位置分别用 1 和 0 来表示。 
//
// 
//
// 示例 1： 
//
// 
//输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
//输出：2
//解释：
//3x3 网格的正中间有一个障碍物。
//从左上角到右下角一共有 2 条不同的路径：
//1. 向右 -> 向右 -> 向下 -> 向下
//2. 向下 -> 向下 -> 向右 -> 向右
// 
//
// 示例 2： 
//
// 
//输入：obstacleGrid = [[0,1],[0,0]]
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// m == obstacleGrid.length 
// n == obstacleGrid[i].length 
// 1 <= m, n <= 100 
// obstacleGrid[i][j] 为 0 或 1 
// 
// Related Topics 数组 动态规划 
// 👍 480 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // 使用动态规划
        // m 行 n 列
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        if(obstacleGrid[m-1][n-1] == 1){
            return 0;
        }
        // 使用一个dp数组，每一个元素的含义是：到终点有多少种路径。
        Integer[][] array = new Integer[m][n];
        array[m - 1][n - 1] = 1;
        // 处理最右侧列
        for (int i = m - 2; i >= 0; i--) {
            if (obstacleGrid[i][n - 1] == 1 || array[i + 1][n - 1] == 0) {
                array[i][n - 1] = 0;
            } else {
                array[i][n - 1] = 1;
            }
        }
        // 处理最后一行
        for (int i = n - 2; i >= 0; i--) {
            if (obstacleGrid[m - 1][i] == 1 || array[m - 1][i + 1] == 0) {
                array[m - 1][i] = 0;
            } else {
                array[m - 1][i] = 1;
            }
        }
        return dp(0, 0, array, obstacleGrid);
    }
    // 返回这个节点有多少路径到终点
    public int dp(int i, int j, Integer[][] array, int[][] obstacleGrid) {
//        if (i >= obstacleGrid.length || j >= obstacleGrid[0].length) {
//            return 0;
//        }
        if (array[i][j] != null) {
            return array[i][j];
        }
        if (obstacleGrid[i][j] == 1) {
            array[i][j] = 0;
            return 0;
        } else {
            array[i][j] = dp(i + 1, j, array, obstacleGrid) + dp(i, j + 1, array, obstacleGrid);
            return array[i][j];
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
