/*
 * @lc app=leetcode.cn id=5 lang=java
 *
 * [5] 最长回文子串
 */

// @lc code=start
class Solution {
    // 中心向外扩散思想
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0)
            return "";
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            int tmp1 = fun(s, i, i);
            int tmp2 = fun(s, i, i + 1);
            int len = Math.max(tmp1, tmp2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public int fun(String s, int left, int right) {
        while (left >= 0 && right <= s.length() - 1 && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
}
// @lc code=end
