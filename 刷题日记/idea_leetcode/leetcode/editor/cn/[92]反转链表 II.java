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
        if (m == n) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode front = dummy;
        int len = n - m + 1;
        while (m > 1) {
            front = front.next;
            m--;
        }
        ListNode start = front.next;
        front.next = reverse(start, len);
        return dummy.next;
    }

    private ListNode reverse(ListNode start, int len) {
        ListNode pre = null;
        ListNode cur = start;
        ListNode next = start.next;
        while (len > 1) {
            cur.next = pre;
            len--;
            pre = cur;
            cur = next;
            next = next.next;
        }
        cur.next = pre;
        start.next = next;
        return cur;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
