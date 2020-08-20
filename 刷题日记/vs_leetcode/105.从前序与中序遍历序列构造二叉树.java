import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * @lc app=leetcode.cn id=105 lang=java
 *
 * [105] 从前序与中序遍历序列构造二叉树
 */

// @lc code=start

// Definition for a binary tree node.
// public class TreeNode {
//     int val;
//     TreeNode left;
//     TreeNode right;

//     TreeNode(int x) {
//         val = x;
//     }
// }

class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        ArrayList<Integer> inorderArray = new ArrayList<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderArray.add(inorder[i]);
        }
        ArrayList<Integer> preorderArray = new ArrayList<>();
        for (int i = 0; i < preorder.length; i++) {
            preorderArray.add(preorder[i]);
        }
        return dp(preorderArray, inorderArray);
    }

    public TreeNode dp(ArrayList<Integer> preorder, ArrayList<Integer> inorder) {
        if (preorder.size() == 0 && inorder.size() == 0) {
            return null;
        }
        int first = preorder.get(0);
        int index = inorder.indexOf(first);

        ArrayList<Integer> preorderArray1 = new ArrayList<>();
        ArrayList<Integer> preorderArray2 = new ArrayList<>();
        for (int i = 1; i <= index; i++) {
            preorderArray1.add(preorder.get(i));
        }
        for (int i = index + 1; i < preorder.size(); i++) {
            preorderArray2.add(preorder.get(i));
        }

        ArrayList<Integer> inorderArray1 = new ArrayList<>();
        ArrayList<Integer> inorderArray2 = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            inorderArray1.add(inorder.get(i));
        }
        for (int i = index + 1; i < inorder.size(); i++) {
            inorderArray2.add(inorder.get(i));
        }

        TreeNode result = new TreeNode(first);
        result.left = dp(preorderArray1, inorderArray1);
        result.right = dp(preorderArray2, inorderArray2);
        return result;
    }
}
// @lc code=end
