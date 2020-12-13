import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * @lc app=leetcode.cn id=15 lang=java
 *
 * [15] 三数之和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int len = nums.length;
        Arrays.sort(nums);
        if (nums == null || len < 3) {
            return res;
        }
        for (int i = 0; i < len; i++) {
            if (nums[i] > 0) {
                break;
            }
            // 去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int l = i + 1;
            int r = len - 1;
            while (l < r) {
                int val = nums[i] + nums[l] + nums[r];
                if (val == 0) {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    // 去重
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;

                    }
                    l++;
                    r--;
                } else if (val < 0) {
                    l++;
                } else if (val > 0) {
                    r--;
                }
            }
        }
        return res;
    }
}
// @lc code=end
