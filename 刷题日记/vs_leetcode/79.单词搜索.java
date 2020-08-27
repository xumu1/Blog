/*
 * @lc app=leetcode.cn id=79 lang=java
 *
 * [79] 单词搜索
 */

// @lc code=start
class Solution {
    public boolean exist(char[][] board, String word) {
        char[] words = word.toCharArray();
<<<<<<< HEAD
        for (int i = 0; i <board.length ; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(dfs(board,words,i,j,0)) return true;
=======
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, words, i, j, 0)) {
                    return true;
                }
>>>>>>> 51e8fae54fedf728bf2bcb37dc569af31f06a68e
            }
        }
        return false;
    }
<<<<<<< HEAD
    public boolean dfs(char[][] board,char[] words, int i, int j, int k){
        if(i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != words[k]) return false;
        if(k == words.length - 1) return true;
        char tmp = board[i][j];
        board[i][j] = '/';
        boolean res = dfs(board, words, i+1, j, k+1) || dfs(board, words, i-1, j, k+1) || dfs(board, words, i, j+1, k+1) || dfs(board, words, i, j-1, k+1); 
=======

    public boolean dfs(char[][] board, char[] words, int i, int j, int k) {
        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != words[k]) {
            return false;
        }
        if (k == words.length - 1) {
            return true;
        }
        char tmp = board[i][j];
        board[i][j] = '/';
        boolean res = dfs(board, words, i + 1, j, k + 1) || dfs(board, words, i, j + 1, k + 1)
                || dfs(board, words, i - 1, j, k + 1) || dfs(board, words, i, j - 1, k + 1);
>>>>>>> 51e8fae54fedf728bf2bcb37dc569af31f06a68e
        board[i][j] = tmp;
        return res;
    }
}
// @lc code=end
<<<<<<< HEAD

=======
>>>>>>> 51e8fae54fedf728bf2bcb37dc569af31f06a68e
