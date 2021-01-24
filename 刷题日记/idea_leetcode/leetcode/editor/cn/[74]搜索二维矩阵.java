//编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性： 
//
// 
// 每行中的整数从左到右按升序排列。 
// 每行的第一个整数大于前一行的最后一个整数。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// m == matrix.length 
// n == matrix[i].length 
// 1 <= m, n <= 100 
// -104 <= matrix[i][j], target <= 104 
// 
// Related Topics 数组 二分查找 
// 👍 303 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        // 1. 先找到在第几行
        // 2. 在找在第几个
        // 注：可以使用二分法提高效率
        int m = matrix.length;
        int n = matrix[0].length;
        // 排除界外两种情况
        if (matrix[0][0] > target) {
            return false;
        }
        if (matrix[m - 1][n - 1] < target) {
            return false;
        }
        int row = -1;
        for (int i = 0; i < m; i++) {
            if (i < m - 1 && matrix[i][0] <= target && matrix[i + 1][0] > target) {
                row = i;
                break;
            }
            if (i == m - 1 && matrix[i][0] <= target) {
                row = i;
                break;
            }
        }
        // 找到了第几行，找第几列
        for (int i = 0; i < n; i++) {
            if(matrix[row][i]==target){
                return true;
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
