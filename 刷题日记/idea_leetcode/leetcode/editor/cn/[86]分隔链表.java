//给你一个链表和一个特定值 x ，请你对链表进行分隔，使得所有小于 x 的节点都出现在大于或等于 x 的节点之前。 
//
// 你应当保留两个分区中每个节点的初始相对位置。 
//
// 
//
// 示例： 
//
// 
//输入：head = 1->4->3->2->5->2, x = 3
//输出：1->2->2->4->3->5
// 
// Related Topics 链表 双指针 
// 👍 357 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

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
    public ListNode partition(ListNode head, int x) {
        ListNode res = new ListNode(x - 1);
        res.next = head;
        ListNode l = res;
        ListNode r = head;
        while (r != null && r.val < x) {
            r = r.next;
        }
        while (l.next != r) {
            l = l.next;
        }
        while (r != null) {
            if (r.next != null && r.next.val < x) {
                ListNode tmp = r.next;
                r.next = tmp.next;
                tmp.next = l.next;
                l.next = tmp;
                l = l.next;
            } else {
                r = r.next;
            }
        }
        return res.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
