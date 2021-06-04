//给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。 
//
// 示例: 
//
// 输入: [0,1,0,3,12]
//输出: [1,3,12,0,0] 
//
// 说明: 
//
// 
// 必须在原数组上操作，不能拷贝额外的数组。 
// 尽量减少操作次数。 
// 
// Related Topics 数组 双指针 
// 👍 1072 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 使用双指针，每次碰到一个零，就停下来，向后遍历找非零数进行替换。
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int l = 0;
        while (l < nums.length) {
            if (nums[l] != 0) {
                l++;
            } else {
                int r = l + 1;
                while (r < nums.length) {
                    if (nums[r] == 0) {
                        r++;
                    } else {
                        break;
                    }
                }
                if (r == nums.length) {
                    break;
                } else {
                    swap(nums, l, r);
                }
            }
        }
    }

    private void swap(int[] nums, int l, int r) {
        int tmp = nums[l];
        nums[l] = nums[r];
        nums[r] = tmp;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
