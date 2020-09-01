/*
 * @lc app=leetcode.cn id=101 lang=java
 *
 * [101] 对称二叉树
 */

// @lc code=start

//Definition for a binary tree node.
// public class TreeNode {
//     int val;
//     TreeNode left;
//     TreeNode right;

//     TreeNode(int x) {
//         val = x;
//     }
// }

class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return rc(root.left, root.right);
    }

    public boolean rc(TreeNode left, TreeNode right) {
        if (left == null && right == null)
            return true;
        if (left == null || right == null || left.val != right.val)
            return false;
        return rc(left.left, right.right) && rc(left.right, right.left);
    }
}
// @lc code=end
