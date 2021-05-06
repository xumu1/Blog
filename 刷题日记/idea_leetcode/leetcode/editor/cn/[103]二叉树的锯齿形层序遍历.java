//给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。 
//
// 例如： 
//给定二叉树 [3,9,20,null,null,15,7], 
//
// 
//    3
//   / \
//  9  20
//    /  \
//   15   7
// 
//
// 返回锯齿形层序遍历如下： 
//
// 
//[
//  [3],
//  [20,9],
//  [15,7]
//]
// 
// Related Topics 栈 树 广度优先搜索 
// 👍 381 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.*;

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
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        LinkedList<List<Integer>> res = new LinkedList<>();
        if (root == null) {
            return res;
        }
        LinkedList<Integer> tmp;
        boolean isOrder = true;
        LinkedList<TreeNode> quque = new LinkedList<>();
        quque.offer(root);
        while (quque.size() > 0) {
            tmp = new LinkedList<Integer>();
            int size = quque.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = quque.poll();
                if (isOrder) {
                    tmp.offerLast(poll.val);
                } else {
                    tmp.offerFirst(poll.val);
                }
                if (poll.left != null) {
                    quque.add(poll.left);
                }
                if (poll.right != null) {
                    quque.add(poll.right);
                }
            }
            res.add(new LinkedList<>(tmp));
            isOrder = !isOrder;
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
