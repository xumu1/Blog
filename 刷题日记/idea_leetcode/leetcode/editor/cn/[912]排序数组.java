//给你一个整数数组 nums，请你将该数组升序排列。 
//
// 
//
// 
// 
//
// 示例 1： 
//
// 输入：nums = [5,2,3,1]
//输出：[1,2,3,5]
// 
//
// 示例 2： 
//
// 输入：nums = [5,1,1,2,0,0]
//输出：[0,0,1,1,2,5]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 50000 
// -50000 <= nums[i] <= 50000 
// 
// 👍 297 👎 0


import java.util.Arrays;
import java.util.Random;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public int[] sortArray(int[] nums) {
//        Arrays.sort(nums);
        if(nums == null || nums.length == 0){
            return nums;
        }
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    public void quickSort(int[] nums, int p, int q) {
        if (p < q) {
            int mid = division(nums, p, q);
            quickSort(nums, p, mid - 1);
            quickSort(nums, mid + 1, q);
        }
    }

    public int division(int[] nums, int p, int q) {
        int randomIndex = new Random().nextInt(q - p + 1) + p;
        swap(nums, p, randomIndex);
        int div = nums[q];
        int i = p - 1;
        for (int j = p; j < q; j++) {
            if (nums[j] < div) {
                i++;
                swap(nums, i, j);
            }
        }
        i++;
        swap(nums, q, i);
        return i;
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[j];
        nums[j] = nums[i];
        nums[i] = tmp;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
