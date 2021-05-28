//实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。 
//
// 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。 
//
// 必须 原地 修改，只允许使用额外常数空间。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[1,3,2]
// 
//
// 示例 2： 
//
// 
//输入：nums = [3,2,1]
//输出：[1,2,3]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1,1,5]
//输出：[1,5,1]
// 
//
// 示例 4： 
//
// 
//输入：nums = [1]
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 100 
// 0 <= nums[i] <= 100 
// 
// Related Topics 数组 
// 👍 890 👎 0


import java.util.Arrays;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     *
     * 123456
     * 12346 5
     * 1235 46
     * 1236 45
     * 1236 54
     * 124 356
     * 124 36 5
     * 124 5 36
     * 124 5 6 3
     * 124 6 3 5
     * 124 6 5 3
     * ...
     * 654321
     *  find a increase line ex: i and j point, and nums[i] < nums[j] from right starting,
     *  then find a value nums[k] that larger than nums[i] from right starting,
     *  then swap nums[i] and nums[k],
     *  then sort the array from i to the end.
     *
     * two key points:
     * 1. 增加幅度尽可能小
     * 2. 将大数交换到前面后，对后面数进行排序
     */
    public void nextPermutation(int[] nums) {
        int len = nums.length;
        int index = len - 2;
        // find i and j
        while (index >= 0) {
            if (nums[index] < nums[index + 1]) {
                break;
            } else {
                index--;
            }
        }
        int i = index;
        int j = index + 1;
        if (index < 0) {
            for (int m = 0; m < len / 2; m++) {
                int tmp = nums[m];
                nums[m] = nums[len - 1 - m];
                nums[len - 1 - m] = tmp;
            }
            return;
        }
        int k = len - 1;
        while (k > i) {
            if (nums[k] > nums[i]) {
                break;
            }
            k--;
        }
        int tmp = nums[k];
        nums[k] = nums[i];
        nums[i] = tmp;
        Arrays.sort(nums, j, len);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
