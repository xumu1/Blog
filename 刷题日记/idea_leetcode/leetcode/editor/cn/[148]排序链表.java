//给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。 
//
// 进阶： 
//
// 
// 你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？ 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [4,2,1,3]
//输出：[1,2,3,4]
// 
//
// 示例 2： 
//
// 
//输入：head = [-1,5,3,4,0]
//输出：[-1,0,3,4,5]
// 
//
// 示例 3： 
//
// 
//输入：head = []
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目在范围 [0, 5 * 104] 内 
// -105 <= Node.val <= 105 
// 
// Related Topics 排序 链表 
// 👍 1152 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

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
    public ListNode sortList(ListNode head) {
        int len = 0;
        ListNode lenTmp = head;
        while (lenTmp != null) {
            lenTmp = lenTmp.next;
            len++;
        }
        if (len < 2){
            return head;
        }
        return binarySort(head, len);
    }

    public ListNode binarySort(ListNode head, int len) {
        // 4 2 1 3
        if (head.next == null){
            return head;
        }
        int mid = (len + 1) / 2;
        ListNode midNode = head;
        for (int i = 1; i < mid; i++) {
            midNode = midNode.next;
        }
        ListNode one = head;
        ListNode two = midNode.next;
        midNode.next = null;
        ListNode oneSorted = binarySort(one, mid);
        ListNode twoSorted = binarySort(two, len - mid);
        return merge(oneSorted, twoSorted);
    }

    public ListNode merge(ListNode one, ListNode two) {
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        while (one != null && two != null) {
            if (one.val < two.val) {
                cur.next = one;
                one = one.next;
            } else{
                cur.next = two;
                two = two.next;
            }
            cur = cur.next;
        }
        if(one!=null){
            cur.next = one;
        }
        if(two!=null){
            cur.next = two;
        }
//        print(dummy.next);
        return dummy.next;
    }
    public void print(ListNode root){
        while(root != null){
            System.out.println("root.val = " + root.val);
            root = root.next;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
