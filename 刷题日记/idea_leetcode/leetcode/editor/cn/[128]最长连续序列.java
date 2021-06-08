//给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。 
//
// 
//
// 进阶：你可以设计并实现时间复杂度为 O(n) 的解决方案吗？ 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [100,4,200,1,3,2]
//输出：4
//解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。 
//
// 示例 2： 
//
// 
//输入：nums = [0,3,7,2,5,8,4,6,0,1]
//输出：9
// 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 104 
// -109 <= nums[i] <= 109 
// 
// Related Topics 并查集 数组 
// 👍 796 👎 0


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 准备一个hashmap，先把元素都塞进去
     * 拿到一个元素 num 后，先判断 num-1 在集合中不，在的话就不用计算
     * 随后遍历 num+1 num+2 ..... 一直到找不到元素
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int max = 0;
        for (int first : set) {
            if (!set.contains(first - 1)) {
                int tmpLen = 1;
                int tmp = first + 1;
                while (set.contains(tmp)) {
                    tmpLen++;
                    tmp++;
                }
                max = Math.max(max, tmpLen);
            }
        }
        return max;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
