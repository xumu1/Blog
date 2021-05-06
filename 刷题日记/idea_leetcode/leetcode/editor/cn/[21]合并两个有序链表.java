//将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
//
// 
//
// 示例 1： 
//
// 
//输入：l1 = [1,2,4], l2 = [1,3,4]
//输出：[1,1,2,3,4,4]
// 
//
// 示例 2： 
//
// 
//输入：l1 = [], l2 = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：l1 = [], l2 = [0]
//输出：[0]
// 
//
// 
//
// 提示： 
//
// 
// 两个链表的节点数目范围是 [0, 50] 
// -100 <= Node.val <= 100 
// l1 和 l2 均按 非递减顺序 排列 
// 
// Related Topics 递归 链表 
// 👍 1694 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-200,null);
        ListNode tail = head;
        ListNode h1 = l1;
        ListNode h2 = l2;
        while (h1 != null && h2 != null) {
            if (h1.val<=h2.val){
                // 拼接h1
                tail.next = h1;
                tail = tail.next;
                h1 = h1.next;
            }else {
                // 拼接h2
                tail.next = h2;
                tail = tail.next;
                h2 = h2.next;
            }
        }
        if (h1 != null){
            tail.next = h1;
        }
        if (h2 != null){
            tail.next = h2;
        }
        return head.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
