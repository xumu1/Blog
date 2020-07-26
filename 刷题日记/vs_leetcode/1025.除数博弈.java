/*
 * @lc app=leetcode.cn id=1025 lang=java
 *
 * [1025] 除数博弈
 */

// @lc code=start
class Solution {
    public boolean divisorGame(int N) {
        if(N%2 == 0){
            // 偶数
            return true;
        }else{
            return false;
        }
    }
}
// @lc code=end

