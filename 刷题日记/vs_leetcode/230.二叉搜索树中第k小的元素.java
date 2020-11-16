import java.util.ArrayList;

/*
 * @lc app=leetcode.cn id=230 lang=java
 *
 * [230] 二叉搜索树中第K小的元素
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 */

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    public static int count;
    public static int res;
    public static int sk;

    public int kthSmallest(TreeNode root, int k) {
        sk = k;
        count = 0;
        res = -1;
        func(root);
        return res;
    }

    public void func(TreeNode root) {
        if (root == null)
            return;
        func(root.left);
        if (++count == sk) {
            res = root.val;
            return;
        }
        func(root.right);
    }
}
// @lc code=end
