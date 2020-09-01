/*
 * @lc app=leetcode.cn id=226 lang=java
 *
 * [226] 翻转二叉树
 */

// @lc code=start

//Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

class Solution {
    public TreeNode invertTree(TreeNode root) {
        // 翻转左右子树
        rc(root);
        return root;
    }

    public void rc(TreeNode node) {
        if (node == null) {
            return;
        }
        TreeNode tmp = node.left;
        node.left = node.right;
        node.right = tmp;
        rc(node.left);
        rc(node.right);
        return;
    }
}
// @lc code=end
