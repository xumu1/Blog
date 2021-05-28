//请判断一个链表是否为回文链表。 
//
// 示例 1: 
//
// 输入: 1->2
//输出: false 
//
// 示例 2: 
//
// 输入: 1->2->2->1
//输出: true
// 
//
// 进阶： 
//你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？ 
// Related Topics 链表 双指针 
// 👍 989 👎 0


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
    public boolean isPalindrome(ListNode head) {
        int len = 0;
        ListNode lenTmp = head;
        while (lenTmp != null) {
            lenTmp = lenTmp.next;
            len++;
        }
        if (len<2){
            return true;
        }
        int mid = (len+1)/2;
        ListNode midNode = head;
        for (int i = 1; i < mid; i++) {
            midNode = midNode.next;
        }
        ListNode one = head;
        ListNode two =  reverse(midNode.next);
        midNode.next = null;
        return compare(one, two);
    }
    public boolean compare(ListNode one,ListNode two){
        while (two!=null){
            if (one.val != two.val){
                return false;
            }
            one = one.next;
            two = two.next;
        }
        return true;
    }
    public ListNode reverse(ListNode root){
        ListNode pre = null;
        ListNode cur = root;
        while (cur != null){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
