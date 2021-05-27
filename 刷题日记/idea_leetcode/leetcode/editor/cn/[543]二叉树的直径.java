//给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。 
//
// 
//
// 示例 : 
//给定二叉树 
//
//           1
//         / \
//        2   3
//       / \     
//      4   5    
// 
//
// 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。 
//
// 
//
// 注意：两结点之间的路径长度是以它们之间边的数目表示。 
// Related Topics 树 
// 👍 710 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public static int res;
    public int diameterOfBinaryTree(TreeNode root) {
        /**
         * 递归求节点两边最长距离，相加和 res 比大小
         */
        res = 0;
        recursion(root);
        return res;
    }
    public int recursion(TreeNode root){
        if (root == null){
            return 0;
        }
        int l = recursion(root.left);
        int r = recursion(root.right);
        res = Math.max(res,l+r);
        return Math.max(l,r)+1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
