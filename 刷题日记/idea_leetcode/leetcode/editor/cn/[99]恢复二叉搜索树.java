//给你二叉搜索树的根节点 root ，该树中的两个节点被错误地交换。请在不改变其结构的情况下，恢复这棵树。 
//
// 进阶：使用 O(n) 空间复杂度的解法很容易实现。你能想出一个只使用常数空间的解决方案吗？ 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [1,3,null,null,2]
//输出：[3,1,null,null,2]
//解释：3 不能是 1 左孩子，因为 3 > 1 。交换 1 和 3 使二叉搜索树有效。
// 
//
// 示例 2： 
//
// 
//输入：root = [3,1,4,null,null,2]
//输出：[2,1,4,null,null,3]
//解释：2 不能在 3 的右子树中，因为 2 < 3 。交换 2 和 3 使二叉搜索树有效。 
//
// 
//
// 提示： 
//
// 
// 树上节点的数目在范围 [2, 1000] 内 
// -231 <= Node.val <= 231 - 1 
// 
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 
// 👍 495 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

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
    private TreeNode one;
    private TreeNode two;
    private Integer tmp;

    public void recoverTree(TreeNode root) {
        one = null;
        two = null;
        recursion(root);
        System.out.println(1);
        one.val = two.val;
        System.out.println(2);
        two.val = tmp;
    }

    private void recursion(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            return;
        }
        recursion(root.left);
        if (root.left != null && root.left.val > root.val) {
            if (one == null) {
                one = root.left;
                tmp = one.val;
                one.val = Integer.MIN_VALUE;
                System.out.println("one = " + one.val);
            } else {
                two = root;
                System.out.println("two = " + two.val);
            }

        }
        if (root.right != null && root.right.val < root.val) {
            if (one == null) {
                one = root;
                tmp = one.val;
                one.val = Integer.MIN_VALUE;
                System.out.println("one = " + one.val);
            } else {
                two = root.right;
                System.out.println("two = " + two.val);
            }
        }
        recursion(root.right);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
