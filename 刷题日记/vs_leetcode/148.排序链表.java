import java.util.List;

/*
 * @lc app=leetcode.cn id=148 lang=java
 *
 * [148] 排序链表
 */

// @lc code=start
/**
  Definition for singly-linked list.
 */
//  public class ListNode {
//       int val;
//       ListNode next;
//       ListNode() {}
//       ListNode(int val) { this.val = val; }
//       ListNode(int val, ListNode next) { this.val = val; this.next = next; }
//   }
 
class Solution {
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head; 
        ListNode right = head.next;
        ListNode left = head;
        ListNode res = head;
        int min = head.val;
        return dp(left,right,res,min);
    }
    public ListNode dp(ListNode left,ListNode right,ListNode res,int min){
        if(right == null) return res;
        if(left.val <= right.val){
            left = left.next;
            right = right.next;
            return dp(left,right,res,min);
        }else{
            ListNode tmp = right;
            right = right.next;
            res = insert(res,tmp,min);
            min = res.val;
            left.next = right;
            return dp(left, right, res, min);
        }
    }
    public ListNode insert(ListNode res,ListNode node,int min){
        if(node.val <= min){
            node.next = res;
            return node;
        }else{
            ListNode tmp = res;
            while(tmp.val < node.val && tmp.next.val < node.val){
                tmp = tmp.next;
            }
            node.next = tmp.next;
            tmp.next = node;
            return res;
        }
    }
}
// @lc code=end

