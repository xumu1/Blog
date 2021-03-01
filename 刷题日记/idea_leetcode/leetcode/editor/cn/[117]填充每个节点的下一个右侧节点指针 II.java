//给定一个二叉树 
//
// struct Node {
//  int val;
//  Node *left;
//  Node *right;
//  Node *next;
//} 
//
// 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。 
//
// 初始状态下，所有 next 指针都被设置为 NULL。 
//
// 
//
// 进阶： 
//
// 
// 你只能使用常量级额外空间。 
// 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。 
// 
//
// 
//
// 示例： 
//
// 
//
// 输入：root = [1,2,3,4,5,null,7]
//输出：[1,#,2,3,#,4,5,7,#]
//解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。 
//
// 
//
// 提示： 
//
// 
// 树中的节点数小于 6000 
// -100 <= node.val <= 100 
// 
//
// 
//
// 
// 
// Related Topics 树 深度优先搜索 
// 👍 370 👎 0


import java.util.LinkedList;

//leetcode submit region begin(Prohibit modification and deletion)
/*
// Definition for a Node.
*/
//class Node {
//    public int val;
//    public Node left;
//    public Node right;
//    public Node next;
//
//    public Node() {}
//
//    public Node(int _val) {
//        val = _val;
//    }
//
//    public Node(int _val, Node _left, Node _right, Node _next) {
//        val = _val;
//        left = _left;
//        right = _right;
//        next = _next;
//    }
//}

class Solution {
    // 队列升级版：使用链表
    public Node connect(Node root) {
        if (root == null){
            return root;
        }
        Node top = root;
        while (top!=null){
            Node tmpHead = new Node(0);
            Node tmp = tmpHead;
            while (top!=null){
                if (top.left!=null){
                    tmp.next = top.left;
                    tmp = tmp.next;
                }
                if (top.right!=null){
                    tmp.next = top.right;
                    tmp = tmp.next;
                }
                top = top.next;
            }
            top = tmpHead.next;
        }
        return root;
    }
    // 垃圾但好理解的方法
//    public Node connect(Node root) {
//        if (root == null){
//            return root;
//        }
//        LinkedList<Node> queue = new LinkedList<>();
//        queue.add(root);
//        root.next = null;
//        int i = 1;
//        while (!queue.isEmpty()) {
//            for (int j = 1; j <= i; j++) {
//                Node pop = queue.pop();
//                if (pop.left != null) {
//                    queue.add(pop.left);
//                }
//                if (pop.right !=null){
//                    queue.add(pop.right);
//                }
//            }
//            for (int j = 0; j < queue.size() - 1; j++) {
//                queue.get(j).next = queue.get(j + 1);
//            }
//            i = queue.size();
//        }
//        return root;
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
