//反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。 
//
// 说明: 
//1 ≤ m ≤ n ≤ 链表长度。 
//
// 示例: 
//
// 输入: 1->2->3->4->5->NULL, m = 2, n = 4
//输出: 1->4->3->2->5->NULL 
// Related Topics 链表 
// 👍 660 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.List;

/**
 * Definition for singly-linked list.
 */
//public class ListNode {
//    int val;
//    ListNode next;
//
//    ListNode() {
//    }
//
//    ListNode(int val) {
//        this.val = val;
//    }
//
//    ListNode(int val, ListNode next) {
//        this.val = val;
//        this.next = next;
//    }
//}

class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode front = head;
        ListNode end = head;
        ListNode pre1 = null;
        ListNode pre2 = null;
        while (m > 1) {
            if (m == 2) {
                pre1 = front;
            }
            front = front.next;
            m--;
        }
        while (n > 1) {
            end = end.next;
            n--;
        }
        pre2 = end.next;
        reverse(front, end);
        if (pre1 == null) {
            front.next = pre2;
            return end;
        } else {
            pre1.next = end;
            front.next = pre2;
            return head;
        }
    }

    private void reverse(ListNode front, ListNode end) {
        if (front == end) {
            return;
        }
        if (front.next == end) {
            end.next = front;
        }
        end.next = null;
        ListNode pre = front;
        ListNode tmp = pre.next;
        ListNode next = tmp.next;
        while (next != null) {
            tmp.next = pre;
            pre = tmp;
            tmp = next;
            next = next.next;
        }
        tmp.next = pre;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
