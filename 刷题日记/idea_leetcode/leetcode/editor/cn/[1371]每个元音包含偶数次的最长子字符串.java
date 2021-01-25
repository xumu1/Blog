//给你一个字符串 s ，请你返回满足以下条件的最长子字符串的长度：每个元音字母，即 'a'，'e'，'i'，'o'，'u' ，在子字符串中都恰好出现了偶数次。
// 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "eleetminicoworoep"
//输出：13
//解释：最长子字符串是 "leetminicowor" ，它包含 e，i，o 各 2 个，以及 0 个 a，u 。
// 
//
// 示例 2： 
//
// 
//输入：s = "leetcodeisgreat"
//输出：5
//解释：最长子字符串是 "leetc" ，其中包含 2 个 e 。
// 
//
// 示例 3： 
//
// 
//输入：s = "bcbcbc"
//输出：6
//解释：这个示例中，字符串 "bcbcbc" 本身就是最长的，因为所有的元音 a，e，i，o，u 都出现了 0 次。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 5 x 10^5 
// s 只包含小写英文字母。 
// 
// Related Topics 字符串 
// 👍 319 👎 0


import java.util.Arrays;
import java.util.HashSet;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findTheLongestSubstring(String s) {
        char[] array = s.toCharArray();
        int len = array.length;
        // 求以chars的每个元素开头的符合条件的字符串，哪个最长
        int max = 0;
        int[] aeiou = new int[5];
        HashSet<Character> set = new HashSet<>();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');
        for (int i = 0; i < len; i++) {
            int count = 0;
            Arrays.fill(aeiou, 0);
            for (int j = i; j < len; j++) {
                if (set.contains(array[j])) {
                    count++;
                    if (array[j] == 'a') {
                        aeiou[0]++;
                    }
                    if (array[j] == 'e') {
                        aeiou[1]++;
                    }
                    if (array[j] == 'i') {
                        aeiou[2]++;
                    }
                    if (array[j] == 'o') {
                        aeiou[3]++;
                    }
                    if (array[j] == 'u') {
                        aeiou[4]++;
                    }
                    if (check(aeiou)) {
                        max = Math.max(max, count);
                    }
                } else {
                    count++;
                    if (check(aeiou)) {
                        max = Math.max(max, count);
                    }
                }
            }
        }
        return max;
    }
    // 校验
    public boolean check(int[] aeiou) {
        for (int j : aeiou) {
            if (j % 2 != 0) {
                return false;
            }
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
