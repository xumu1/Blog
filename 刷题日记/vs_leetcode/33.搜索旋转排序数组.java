/*
 * @lc app=leetcode.cn id=33 lang=java
 *
 * [33] 搜索旋转排序数组
 */

// @lc code=start
class Solution {
    public int search(int[] nums, int target) {
        if (nums.length == 1 && nums[0] == target)
            return 0;
        if (nums.length == 1 && nums[0] != target)
            return -1;
        int index = findIndex(nums);
        System.out.println(index);
        if (nums[index] == target) {
            return index;
        }
        int l = 0;
        int r = nums.length - 1;
        if (index != nums.length - 1) {
            if (nums[index] > target && target >= nums[0]) {
                r = index - 1;
            } else if (nums[nums.length - 1] >= target && target >= nums[index + 1]) {
                l = index + 1;
            }
        }
        return binarySearch(nums, l, r, target);
    }

    // 二分查找
    public int binarySearch(int[] nums, int l, int r, int t) {
        int mid = (l + r) / 2;
        while (l <= r) {
            if (nums[mid] == t) {
                return mid;
            } else if (nums[mid] > t) {
                r = mid - 1;
            } else if (nums[mid] < t) {
                l = mid + 1;
            }
            mid = (l + r) / 2;
        }
        return -1;
    }

    // 得到边界点
    public int findIndex(int[] nums) {
        // 处理两个边界
        if (nums[0] < nums[nums.length - 1]) {
            return nums.length - 1;
        }
        if (nums[0] > nums[1]) {
            return 0;
        }
        int l = 0;
        int r = nums.length - 1;
        int mid = (l + r) / 2;
        while (true) {
            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                return mid;
            } else if (nums[mid] < nums[mid - 1] && nums[mid] < nums[mid + 1]) {
                return mid - 1;
            } else {
                if (nums[mid] < nums[0]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            mid = (l + r) / 2;
        }
    }
}
// @lc code=end
