/*
 * @lc app=leetcode.cn id=21 lang=java
 *
 * [21] 合并两个有序链表
 */

// @lc code=start

//Definition for singly-linked list.
// public class ListNode {
//     int val;
//     ListNode next;

//     ListNode() {
//     }

//     ListNode(int val) {
//         this.val = val;
//     }

//     ListNode(int val, ListNode next) {
//         this.val = val;
//         this.next = next;
//     }
// }

class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        return rc(l1, l2);
    }

    public ListNode rc(ListNode l1, ListNode l2) {

        if (l1 == null && l2 == null)
            return null;
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        ListNode node = null;
        if (l1.val <= l2.val) {
            node = new ListNode(l1.val);
            node.next = rc(l1.next, l2);
        } else {
            node = new ListNode(l2.val);
            node.next = rc(l1, l2.next);
        }
        return node;
    }
}
// @lc code=end
