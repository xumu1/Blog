//给定一个单链表 L：L0→L1→…→Ln-1→Ln ， 
//将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→… 
//
// 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。 
//
// 示例 1: 
//
// 给定链表 1->2->3->4, 重新排列为 1->4->2->3. 
//
// 示例 2: 
//
// 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3. 
// Related Topics 链表 
// 👍 587 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public void reorderList(ListNode head) {
        int len = 0;
        ListNode tmp = head;
        while (tmp != null) {
            len++;
            tmp = tmp.next;
        }
        if (len <= 2) {
            return;
        }
        int mid = (len + 1) / 2;
        ListNode midNode = head;
        for (int i = 1; i < mid; i++) {
            midNode = midNode.next;
        }
        ListNode one = head;
        ListNode two = reverseList(midNode.next);
        midNode.next = null;
        bind(one, two);
    }

    public void bind(ListNode one, ListNode two) {
        ListNode oneNext;
        ListNode twoNext;
        while (two != null) {
            oneNext = one.next;
            twoNext = two.next;
            one.next = two;
            two.next = oneNext;
            one = oneNext;
            two = twoNext;
        }
    }

    public ListNode reverseList(ListNode root) {
        ListNode pre = null;
        ListNode cur = root;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
