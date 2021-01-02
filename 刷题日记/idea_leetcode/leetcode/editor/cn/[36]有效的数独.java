//判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。 
//
// 
// 数字 1-9 在每一行只能出现一次。 
// 数字 1-9 在每一列只能出现一次。 
// 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。 
// 
//
// 
//
// 上图是一个部分填充的有效的数独。 
//
// 数独部分空格内已填入了数字，空白格用 '.' 表示。 
//
// 示例 1: 
//
// 输入:
//[
//  ["5","3",".",".","7",".",".",".","."],
//  ["6",".",".","1","9","5",".",".","."],
//  [".","9","8",".",".",".",".","6","."],
//  ["8",".",".",".","6",".",".",".","3"],
//  ["4",".",".","8",".","3",".",".","1"],
//  ["7",".",".",".","2",".",".",".","6"],
//  [".","6",".",".",".",".","2","8","."],
//  [".",".",".","4","1","9",".",".","5"],
//  [".",".",".",".","8",".",".","7","9"]
//]
//输出: true
// 
//
// 示例 2: 
//
// 输入:
//[
//  ["8","3",".",".","7",".",".",".","."],
//  ["6",".",".","1","9","5",".",".","."],
//  [".","9","8",".",".",".",".","6","."],
//  ["8",".",".",".","6",".",".",".","3"],
//  ["4",".",".","8",".","3",".",".","1"],
//  ["7",".",".",".","2",".",".",".","6"],
//  [".","6",".",".",".",".","2","8","."],
//  [".",".",".","4","1","9",".",".","5"],
//  [".",".",".",".","8",".",".","7","9"]
//]
//输出: false
//解释: 除了第一行的第一个数字从 5 改为 8 以外，空格内其他数字均与 示例1 相同。
//     但由于位于左上角的 3x3 宫内有两个 8 存在, 因此这个数独是无效的。 
//
// 说明: 
//
// 
// 一个有效的数独（部分已被填充）不一定是可解的。 
// 只需要根据以上规则，验证已经填入的数字是否有效即可。 
// 给定数独序列只包含数字 1-9 和字符 '.' 。 
// 给定数独永远是 9x9 形式的。 
// 
// Related Topics 哈希表 
// 👍 457 👎 0


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean isValidSudoku(char[][] board) {
        // 1. 每一行不重复
        // 2. 每一列不重复
        // 3. 每一个格子没有重复
        ArrayList<ArrayList<Character>> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            // row
            ArrayList<Character> rowArray = new ArrayList<>();
            ArrayList<Character> colArray = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                rowArray.add(board[i][j]);
                colArray.add(board[j][i]);
            }
            list.add(rowArray);
            list.add(colArray);
        }
        for (int i = 0; i < 9; ) {
            ArrayList<Character> array1 = new ArrayList<>();
            ArrayList<Character> array2 = new ArrayList<>();
            ArrayList<Character> array3 = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                array1.add(board[i + j][0]);
                array1.add(board[i + j][1]);
                array1.add(board[i + j][2]);

                array2.add(board[i + j][3]);
                array2.add(board[i + j][4]);
                array2.add(board[i + j][5]);

                array3.add(board[i + j][6]);
                array3.add(board[i + j][7]);
                array3.add(board[i + j][8]);

            }
            list.add(array1);
            list.add(array2);
            list.add(array3);
            i = i + 3;
        }
        for (ArrayList<Character> arrayList : list) {
            if (!checkArray(arrayList)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkArray(ArrayList<Character> arrayList) {
        HashSet<Character> set = new HashSet<>();
        for (Character character : arrayList) {
            if (character != '.') {
                if (set.contains(character)) {
                    return false;
                } else {
                    set.add(character);
                }
            }
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
