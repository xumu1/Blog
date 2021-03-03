//给定一个非空字符串 s 和一个包含非空单词的列表 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。 
//
// 说明： 
//
// 
// 拆分时可以重复使用字典中的单词。 
// 你可以假设字典中没有重复的单词。 
// 
//
// 示例 1： 
//
// 输入: s = "leetcode", wordDict = ["leet", "code"]
//输出: true
//解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
// 
//
// 示例 2： 
//
// 输入: s = "applepenapple", wordDict = ["apple", "pen"]
//输出: true
//解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
//     注意你可以重复使用字典中的单词。
// 
//
// 示例 3： 
//
// 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
//输出: false
// 
// Related Topics 动态规划 
// 👍 851 👎 0


import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        int len = s.length();
        // table： 代表从字符串index开始到字符串最后，是否可以拆分，1：ok，0：null，-1：fall
        int[] table = new int[len];
        return dp(0, 0, len, s, wordDict, table);
    }

    public boolean dp(int l, int r, int len, String s, List<String> wordDict, int[] table) {
        if (l >= len) {
            return true;
        }
        if (table[l] == 1) {
            return true;
        }
        if (table[l] == -1) {
            return false;
        }
        for (int i = r + 1; i <= len; i++) {
            if (wordDict.contains(s.substring(l, i)) && dp(i, i, len, s, wordDict, table)) {
                table[l] = 1;
                return true;
            }else {
                table[l] = -1;
            }
        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
