/*
 * @lc app=leetcode.cn id=61 lang=java
 *
 * [61] 旋转链表
 */

// @lc code=start

// public class ListNode {
//     int val;
//     ListNode next;

//     ListNode(int x) {
//         val = x;
//     }
// }

class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null)
            return null;
        if (k == 0)
            return head;
        // calculate length
        int len = 1;
        ListNode tmp = head;
        while (tmp.next != null) {
            len++;
            tmp = tmp.next;
        }
        k = k % len;
        if (k == 0)
            return head;
        int step = len - k;
        ListNode left = head;
        ListNode right = head;
        right = right.next;
        for (int i = 0; i < step - 1; i++) {
            left = left.next;
            right = right.next;
        }
        left.next = null;
        tmp.next = head;
        return right;
    }
}
// @lc code=end
