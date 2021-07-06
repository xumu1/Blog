//给定一个二叉树，编写一个函数来获取这个树的最大宽度。树的宽度是所有层中的最大宽度。这个二叉树与满二叉树（full binary tree）结构相同，但一些节
//点为空。 
//
// 每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。 
//
// 示例 1: 
//
// 
//输入: 
//
//           1
//         /   \
//        3     2
//       / \     \  
//      5   3     9 
//
//输出: 4
//解释: 最大值出现在树的第 3 层，宽度为 4 (5,3,null,9)。
// 
//
// 示例 2: 
//
// 
//输入: 
//
//          1
//         /  
//        3    
//       / \       
//      5   3     
//
//输出: 2
//解释: 最大值出现在树的第 3 层，宽度为 2 (5,3)。
// 
//
// 示例 3: 
//
// 
//输入: 
//
//          1
//         / \
//        3   2 
//       /        
//      5      
//
//输出: 2
//解释: 最大值出现在树的第 2 层，宽度为 2 (3,2)。
// 
//
// 示例 4: 
//
// 
//输入: 
//
//          1
//         / \
//        3   2
//       /     \  
//      5       9 
//     /         \
//    6           7
//输出: 8
//解释: 最大值出现在树的第 4 层，宽度为 8 (6,null,null,null,null,null,null,7)。
// 
//
// 注意: 答案在32位有符号整数的表示范围内。 
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 
// 👍 232 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.LinkedList;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution {
    /**
     * 使用一个
     *
     * @param root
     * @return
     */
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Node node = new Node(1, root, null, null);
        link(node);
        LinkedList<Node> list = new LinkedList<>();
        list.addLast(node);
        int res = 0;
        while (!list.isEmpty()) {
            res = Math.max(res, list.getLast().number - list.getFirst().number + 1);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Node first = list.pollFirst();
                if (first.left != null) {
                    list.addLast(first.left);
                }
                if (first.right != null) {
                    list.addLast(first.right);
                }
            }
        }
        return res;
    }

    private void link(Node root) {
        if (root == null) {
            return;
        }
        if (root.node.left != null) {
            root.left = new Node(root.number * 2, root.node.left, null, null);
        }
        if (root.node.right != null) {
            root.right = new Node(root.number * 2 + 1, root.node.right, null, null);
        }
        link(root.left);
        link(root.right);
    }

}

class Node {
    int number;
    TreeNode node;
    Node left;
    Node right;

    public Node(int number, TreeNode node, Node left, Node right) {
        this.number = number;
        this.node = node;
        this.left = left;
        this.right = right;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
