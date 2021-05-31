//给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。 
//
// 示例 1: 
//
// 输入: 1->2->3->3->4->4->5
//输出: 1->2->5
// 
//
// 示例 2: 
//
// 输入: 1->1->1->2->3
//输出: 2->3 
// Related Topics 链表 
// 👍 437 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for singly-linked list.
 */
//class ListNode {
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
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        // 使用两个节点，pre 和 tmp
        ListNode res = new ListNode(-1);
        res.next = head;
        ListNode ppre = res;
        ListNode pre = head;
        ListNode tmp = head.next;
        while (tmp != null) {
            if (tmp.val == pre.val) {
                // 找到最后一个重复的节点
                if (tmp.next != null && tmp.val == tmp.next.val) {
                    tmp = tmp.next;
                } else {
                    pre = tmp.next;
                    tmp = pre == null ? null : pre.next;
                    ppre.next = pre;
                }
            } else {
                tmp = tmp.next;
                pre = pre.next;
                ppre = ppre.next;
            }
        }
        if (pre != null) {
            ppre.next = pre;
        }
        return res.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
