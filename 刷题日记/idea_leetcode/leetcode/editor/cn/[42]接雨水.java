//给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。 
//
// 
//
// 示例 1： 
//
// 
//
// 
//输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
//输出：6
//解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 
// 
//
// 示例 2： 
//
// 
//输入：height = [4,2,0,3,2,5]
//输出：9
// 
//
// 
//
// 提示： 
//
// 
// n == height.length 
// 0 <= n <= 3 * 104 
// 0 <= height[i] <= 105 
// 
// Related Topics 栈 数组 双指针 动态规划 
// 👍 2115 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int trap(int[] height) {
        if (height.length == 0) {
            return 0;
        }
        int res = 0;
        int[] leftMaxArray = new int[height.length];
        int[] rightMaxArray = new int[height.length];
        leftMaxArray[0] = 0;
        rightMaxArray[height.length - 1] = 0;
        for (int i = 1; i < leftMaxArray.length; i++) {
            leftMaxArray[i] = Math.max(height[i - 1], leftMaxArray[i - 1]);
        }
        for (int i = rightMaxArray.length - 2; i >= 0; i--) {
            rightMaxArray[i] = Math.max(height[i + 1], rightMaxArray[i + 1]);
        }
        for (int i = 1; i < height.length - 1; i++) {
            int leftMax = leftMaxArray[i];
            int rightMax = rightMaxArray[i];
            int tmp = Math.min(leftMax, rightMax);
            if (tmp > height[i]) {
                res = res + tmp - height[i];
            }
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
