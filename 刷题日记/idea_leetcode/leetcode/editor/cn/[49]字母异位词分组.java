//给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。 
//
// 示例: 
//
// 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
//输出:
//[
//  ["ate","eat","tea"],
//  ["nat","tan"],
//  ["bat"]
//] 
//
// 说明： 
//
// 
// 所有输入均为小写字母。 
// 不考虑答案输出的顺序。 
// 
// Related Topics 哈希表 字符串 
// 👍 636 👎 0


import java.util.*;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // 存放排序后的字符串数组
        ArrayList<String> list = new ArrayList<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            list.add(String.valueOf(chars));
        }
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (map.get(str) != null) {
                List<String> o = map.get(str);
                o.add(strs[i]);
            }else{
                ArrayList<String> strings = new ArrayList<>();
                strings.add(strs[i]);
                map.put(str,strings);
            }
        }
        return new ArrayList<>(map.values());

    }
}
//leetcode submit region end(Prohibit modification and deletion)
