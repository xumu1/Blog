//给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。 
//
// 在「杨辉三角」中，每个数是它左上方和右上方的数的和。 
//
// 
//
// 
//
// 示例 1: 
//
// 
//输入: numRows = 5
//输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
// 
//
// 示例 2: 
//
// 
//输入: numRows = 1
//输出: [[1]]
// 
//
// 
//
// 提示: 
//
// 
// 1 <= numRows <= 30 
// 
// Related Topics 数组 动态规划 
// 👍 547 👎 0


import java.util.ArrayList;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        ArrayList<Integer> pre = null;
        for (int i = 0; i < numRows; i++) {
            if (i == 0) {
                ArrayList<Integer> tmp = new ArrayList<>();
                tmp.add(1);
                res.add(tmp);
                pre = tmp;
                continue;
            }
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                tmp.add(getValue(pre, j - 1) + getValue(pre, j));
            }
            res.add(tmp);
            pre = tmp;
        }
        return res;
    }

    private int getValue(ArrayList<Integer> list, int index) {
        if (index < 0 || index >= list.size()) {
            return 0;
        }
        return list.get(index);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
