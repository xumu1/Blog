import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
 * @lc app=leetcode.cn id=946 lang=java
 *
 * [946] 验证栈序列
 */

// @lc code=start
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        // 所有的元素一定是按顺序 push 进去的，重要的是怎么 pop 出来？
        // 假设当前栈顶元素值为 2，同时对应的 popped 序列中下一个要 pop 的值也为 2，那就必须立刻把这个值 pop 出来。
        // 因为之后的 push 都会让栈顶元素变成不同于 2 的其他值，这样再 pop 出来的数 popped 序列就不对应了。
        int N = pushed.length;
        Stack<Integer> stack = new Stack();
        int j = 0;
        for (int x : pushed) {
            stack.push(x);
            while (!stack.isEmpty() && j < N && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }

        return j == N;
    }
}
// @lc code=end
