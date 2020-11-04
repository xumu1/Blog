/*
 * @lc app=leetcode.cn id=160 lang=java
 *
 * [160] 相交链表
 */

// @lc code=start
/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode(int x) { val = x; next = null; } }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lena = findLen(headA);
        int lenb = findLen(headB);
        if (lena > lenb) {
            int c = lena - lenb;
            while (c != 0) {
                headA = headA.next;
                c--;
            }
        } else {
            int c = lenb - lena;
            while (c != 0) {
                headB = headB.next;
                c--;
            }
        }
        while (headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }

    public int findLen(ListNode head) {
        int res = 0;
        while (head != null) {
            res++;
            head = head.next;
        }
        return res;
    }
}
// @lc code=end
