//给你一个字符串 s，找到 s 中最长的回文子串。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
// 
//
// 示例 2： 
//
// 
//输入：s = "cbbd"
//输出："bb"
// 
//
// 示例 3： 
//
// 
//输入：s = "a"
//输出："a"
// 
//
// 示例 4： 
//
// 
//输入：s = "ac"
//输出："a"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s 仅由数字和英文字母（大写和/或小写）组成 
// 
// Related Topics 字符串 动态规划 
// 👍 3263 👎 0


import java.util.HashMap;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = fun(s, i, i);
            int len2 = fun(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start + 1) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public int fun(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
    /*

    由外向内扩散效率过差，应使用由内而外的方法

    public static int head;
    public static int tail;
    public static HashMap<Integer, Integer> map;

    public String longestPalindrome(String s) {
        head = 0;
        tail = 1;
        map = new HashMap<Integer, Integer>();
        fun(s.toCharArray(), 0, s.length());
        return s.substring(head, tail);
    }

    public void fun(char[] s, int start, int end) {
        if (end - start <= tail - head) {
            return;
        }
        int palindrome = isPalindrome(s, start, end);
        if (palindrome == -1) {
            head = start;
            tail = end;
        } else if (palindrome == 0){
            fun(s, start + 1, end);
            fun(s, start, end - 1);
        }
    }

    public int isPalindrome(char[] s, int start, int end) {
        if (map.getOrDefault(start,-1) == end){
            return -2;
        }
        int t1 = start;
        int t2 = end;
        end--;
        while (start < end) {
            if (s[start] != s[end]) {
                map.put(t1,t2);
                return 0;
            }
            start++;
            end--;
        }
        map.put(t1,t2);
        return -1;
    }
     */
}
//leetcode submit region end(Prohibit modification and deletion)
