//给定一个二叉树的根节点 root ，返回它的 中序 遍历。 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [1,null,2,3]
//输出：[1,3,2]
// 
//
// 示例 2： 
//
// 
//输入：root = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：root = [1]
//输出：[1]
// 
//
// 示例 4： 
//
// 
//输入：root = [1,2]
//输出：[2,1]
// 
//
// 示例 5： 
//
// 
//输入：root = [1,null,2]
//输出：[1,2]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目在范围 [0, 100] 内 
// -100 <= Node.val <= 100 
// 
//
// 
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？ 
// Related Topics 栈 树 哈希表 
// 👍 848 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 */
//public class TreeNode {
//    int val;
//    TreeNode left;
//    TreeNode right;
//    TreeNode() {}
//    TreeNode(int val) { this.val = val; }
//    TreeNode(int val, TreeNode left, TreeNode right) {
//        this.val = val;
//        this.left = left;
//        this.right = right;
//    }
//}

class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        // 1. 递归
//        ArrayList<Integer> res = new ArrayList<>();
//        recursive(res,root);
//        return res;

        // 2. 递推
        ArrayList<Integer> res = new ArrayList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
        // all left node into stack
        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            res.add(pop.val);
            if (pop.right != null) {
                TreeNode tmp = pop.right;
                while (tmp != null) {
                    stack.push(tmp);
                    tmp = tmp.left;
                }
            }
        }
        return res;
    }
//    public void recursive(ArrayList<Integer> res, TreeNode root){
//        if (root==null){
//            return;
//        }
//        recursive(res,root.left);
//        res.add(root.val);
//        recursive(res,root.right);
//    }
}
//leetcode submit region end(Prohibit modification and deletion)
