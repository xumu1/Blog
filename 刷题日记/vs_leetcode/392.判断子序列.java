/*
 * @lc app=leetcode.cn id=392 lang=java
 *
 * [392] 判断子序列
 */

// @lc code=start
class Solution {
    public boolean isSubsequence(String s, String t) {
        // 如果s的第一个字符和t的第一个字符相同，那么比较s-1 t-1
        // 否则比较s t-1
        if(s == null || s.length() == 0)
            return true;
        if(t == null || t.length() == 0)
            return false;
        if(s.charAt(0) == t.charAt(0)){
            return isSubsequence(s.length() == 1 ? null : s.substring(1),t.substring(1));
        }else{
            return isSubsequence(s, t.length() == 1 ? null : t.substring(1));
        }
    }
}
// @lc code=end

