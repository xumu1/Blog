//输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。 
//
// 
//
// 示例： 
//
// 
//输入：nums = [1,2,3,4]
//输出：[1,3,2,4] 
//注：[3,1,2,4] 也是正确的答案之一。 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 50000 
// 1 <= nums[i] <= 10000 
// 
// 👍 126 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 使用两个指针 l  r
     * l指向奇数，r指向偶数
     * 当 l < r && l 是奇，r是偶，交换 l r，then l++，r++
     *
     * 方法2：
     * 使用两个指针分别从左和右向中间紧逼，进行交换。
     */
    public int[] exchange(int[] nums) {
        int l = 0;
        int r = 0;
        // 找到第一个偶数
        while (l < nums.length && nums[l] % 2 != 0) {
            l++;
            r++;
        }
        while (l < nums.length && r < nums.length) {
            if (nums[r] % 2 == 0) {
                r++;
            } else {
                swap(nums, l, r);
                l++;
                while (l < nums.length && nums[l] % 2 == 1) {
                    l++;
                }
                r++;
                while (r < nums.length && nums[r] % 2 == 0) {
                    r++;
                }
            }
        }
        return nums;
    }

    private void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
