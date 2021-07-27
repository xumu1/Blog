//给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。 
//
// 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。 
//
// 
//
// 示例 1： 
//
// 
//输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "AB
//CCED"
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SE
//E"
//输出：true
// 
//
// 示例 3： 
//
// 
//输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "AB
//CB"
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// m == board.length 
// n = board[i].length 
// 1 <= m, n <= 6 
// 1 <= word.length <= 15 
// board 和 word 仅由大小写英文字母组成 
// 
//
// 
//
// 进阶：你可以使用搜索剪枝的技术来优化解决方案，使其在 board 更大的情况下可以更快解决问题？ 
// Related Topics 数组 回溯 矩阵 
// 👍 966 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        int[][] status = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, i, j, word, m, n, 0, status)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, int i, int j, String word, int m, int n, int index, int[][] status) {
        if (!check(i, j, m, n, status)) {
            return false;
        }
        if (word.charAt(index) != board[i][j]) {
            return false;
        }
        index++;
        if (index == word.length()) {
            return true;
        }
        status[i][j] = -1;
        boolean dfs1 = dfs(board, i + 1, j, word, m, n, index, status);
        boolean dfs2 = dfs(board, i - 1, j, word, m, n, index, status);
        boolean dfs3 = dfs(board, i, j + 1, word, m, n, index, status);
        boolean dfs4 = dfs(board, i, j - 1, word, m, n, index, status);
        status[i][j] = 0;
        return dfs1 || dfs2 || dfs3 || dfs4;
    }

    // 检查是否越界
    private boolean check(int i, int j, int m, int n, int[][] status) {
        if (i < 0 || i >= m || j < 0 || j >= n || status[i][j] == -1) {
            return false;
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
