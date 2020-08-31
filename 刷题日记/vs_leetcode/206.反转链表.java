/*
 * @lc app=leetcode.cn id=206 lang=java
 *
 * [206] 反转链表
 */

// @lc code=start

// Definition for singly-linked list. 
// public class ListNode {
//     int val;
//     ListNode next;

//     ListNode(int x) {
//         val = x;
//     }
// }

class Solution {
    public static ListNode result;

    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        rc(head.next).next = head;
        head.next = null;
        return Solution.result;
    }

    public ListNode rc(ListNode node) {
        if (node.next == null) {
            Solution.result = node;
            return node;
        }
        rc(node.next).next = node;
        return node;
    }
}
// @lc code=end
