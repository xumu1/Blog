//给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。 
//
// 说明: 叶子节点是指没有子节点的节点。 
//
// 示例: 
//给定如下二叉树，以及目标和 sum = 22， 
//
//               5
//             / \
//            4   8
//           /   / \
//          11  13  4
//         /  \    / \
//        7    2  5   1
// 
//
// 返回: 
//
// [
//   [5,4,11,2],
//   [5,8,4,5]
//]
// 
// Related Topics 树 深度优先搜索 
// 👍 426 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.ArrayList;
import java.util.List;

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
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        if (root == null){
            return res;
        }
        dfs(root,targetSum,res,new ArrayList<Integer>());
        return res;
    }
    public void dfs(TreeNode root, int targetSum, ArrayList<List<Integer>> res, ArrayList<Integer> chain){
        if (root == null){
            return;
        }
        if (root.left==null&&root.right==null){
            if (targetSum==root.val){
                chain.add(root.val);
                res.add(new ArrayList<Integer>(chain));
                chain.remove(chain.size()-1);
            }
            return;
        }
        chain.add(root.val);
        dfs(root.left,targetSum-root.val,res,chain);
        dfs(root.right,targetSum-root.val,res,chain);
        chain.remove(chain.size()-1);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
