import java.util.ArrayList;
import java.util.List;

/*
 * @lc app=leetcode.cn id=89 lang=java
 *
 * [89] 格雷编码
 */

// @lc code=start
class Solution {
    public List<Integer> grayCode(int n) {
        List<Integer> res = new ArrayList<>();
        res.add(0);
        if (n == 0)
            return res;
        res.add(1);
        for (int i = 2; i <= n; i++) {
            int tmp = (int) Math.pow(2, i - 1);
            int len = res.size() - 1;
            for (int j = len; j >= 0; j--) {
                res.add(res.get(j) + tmp);
            }
        }
        return res;
    }
}
// @lc code=end
