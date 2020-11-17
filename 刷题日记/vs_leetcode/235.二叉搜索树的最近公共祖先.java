import javax.swing.tree.TreeNode;

/*
 * @lc app=leetcode.cn id=235 lang=java
 *
 * [235] 二叉搜索树的最近公共祖先
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode(int x) { val = x; } }
 */

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        int l = p.val >= q.val ? q.val : p.val;
        int r = p.val >= q.val ? p.val : q.val;
        return rec(root, l, r);
    }

    public TreeNode rec(TreeNode root, int l, int r) {
        if (root.val >= l && root.val <= r) {
            return root;
        }
        if (root.val < l) {
            return rec(root.right, l, r);
        } else {
            return rec(root.left, l, r);
        }
    }
}
// @lc code=end
