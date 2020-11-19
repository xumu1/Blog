/*
 * @lc app=leetcode.cn id=236 lang=java
 *
 * [236] 二叉树的最近公共祖先
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

class Solution {
    public static TreeNode sp;
    public static TreeNode sq;
    public static TreeNode res;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        sp = p;
        sq = q;
        count(root, 0);
        return res;
    }

    public int count(TreeNode root, int c) {
        if (root == null) {
            return c;
        }
        System.out.println(root.val + "1");
        if (root == sp || root == sq) {
            return c + 1;
        }
        int l = count(root.left, c);
        int r = count(root.right, c);
        if (l == 1 && r == 1) {
            System.out.println(root.val);
            res = root;
        }
        return l + r;
    }
    // public int count(TreeNode root, int c) {
    // if (root == null) {
    // return c;
    // }
    // int l = count(root.left, c);
    // int r = count(root.right, c);
    // if (root == sp || root == sq) {
    // if (l != 0 || r != 0) {
    // res = root;
    // }
    // return c + 1;
    // }
    // if (l == 1 && r == 1) {
    // res = root;
    // }
    // return l + r;
    // }
}
// @lc code=end
