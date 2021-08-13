//实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，xn）。不得使用库函数，同时不需要考虑大数问题。 
//
// 
//
// 示例 1： 
//
// 
//输入：x = 2.00000, n = 10
//输出：1024.00000
// 
//
// 示例 2： 
//
// 
//输入：x = 2.10000, n = 3
//输出：9.26100 
//
// 示例 3： 
//
// 
//输入：x = 2.00000, n = -2
//输出：0.25000
//解释：2-2 = 1/22 = 1/4 = 0.25 
//
// 
//
// 提示： 
//
// 
// -100.0 < x < 100.0 
// -231 <= n <= 231-1 
// -104 <= xn <= 104 
// 
//
// 
//
// 注意：本题与主站 50 题相同：https://leetcode-cn.com/problems/powx-n/ 
// Related Topics 递归 数学 
// 👍 183 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 将 n 想像成二进制数比如 11 = 2^0 + 2^1 + 2^3
     * 即 x^11 = x^1 * x^2 * x^8
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if (x == 0) {
            return 0;
        }
        long nl = (long) n;
        double res = 1;
        if (nl < 0) {
            x = 1 / x;
            nl = -nl;
        }
        while (nl > 0) {
            if ((nl & 1) == 1) {
                res *= x;
            }
            x *= x;
            nl = nl >> 1;
        }
        return res;

    }
}
//leetcode submit region end(Prohibit modification and deletion)
