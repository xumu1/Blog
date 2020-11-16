import java.util.PriorityQueue;

/*
 * @lc app=leetcode.cn id=215 lang=java
 *
 * [215] 数组中的第K个最大元素
 */

// @lc code=start
class Solution {
    public int findKthLargest(int[] nums, int k) {
        // 整一个k大小的小根堆
        int len = nums.length;
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(len, (a, b) -> a - b);
        for (int i = 0; i < len; i++) {
            queue.add(nums[i]);
        }
        for (int i = 0; i < len - k; i++) {
            queue.poll();
        }
        return queue.peek();
    }
}
// @lc code=end
