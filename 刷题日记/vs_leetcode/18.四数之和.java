import java.util.Arrays;

/*
 * @lc app=leetcode.cn id=18 lang=java
 *
 * [18] 四数之和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || len < 4) {
            return res;
        }
        Arrays.sort(nums);
        for (int i = 0; i < len; i++) {
            // 去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < len; j++) {
                // 去重
                if (nums[j] == nums[j - 1] && (j - i) > 1) {
                    continue;
                }
                int l = j + 1;
                int r = len - 1;
                while (l < r) {
                    int tmp = nums[i] + nums[j] + nums[l] + nums[r];
                    if (tmp == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        while (l < r && nums[l] == nums[l + 1]) {
                            l++;
                        }
                        while (l < r && nums[r] == nums[r - 1]) {
                            r--;
                        }
                        l++;
                        r--;
                    } else if (tmp < target) {
                        l++;
                    } else if (tmp > target) {
                        r--;
                    }
                }
            }
        }
        return res;
    }
}
// @lc code=end
