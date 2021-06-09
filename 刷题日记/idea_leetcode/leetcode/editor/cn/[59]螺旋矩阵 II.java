//给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 3
//输出：[[1,2,3],[8,9,4],[7,6,5]]
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：[[1]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 20 
// 
// Related Topics 数组 
// 👍 428 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        place r = new place(0, -1, 'r', n, res);
        r.walk();
        return res;
    }

    class place {
        int n;
        int x;
        int y;
        char nextStep;
        int[][] res;
        int count;

        place(int x, int y, char nextStep, int n, int[][] res) {
            this.x = x;
            this.y = y;
            this.nextStep = nextStep;
            this.n = n;
            this.res = res;
            this.count = 0;
        }

        public void walk() {
            int nextX = x;
            int nextY = y;
            if (nextStep == 'r') {
                nextY++;
            }
            if (nextStep == 'l') {
                nextY--;
            }
            if (nextStep == 't') {
                nextX--;
            }
            if (nextStep == 'd') {
                nextX++;
            }
            if (valid(nextX, nextY, n)) {
                x = nextX;
                y = nextY;
                count++;
                res[x][y] = count;
            } else {
                if (nextStep == 'r') {
                    nextStep = 'd';
                } else if (nextStep == 'l') {
                    nextStep = 't';
                } else if (nextStep == 't') {
                    nextStep = 'r';
                } else if (nextStep == 'd') {
                    nextStep = 'l';
                }
            }
            if (count != n * n) {
                walk();
            }
        }

        private boolean valid(int nextX, int nextY, int n) {
            if (nextX >= n || nextX < 0 || nextY >= n || nextY < 0 || res[nextX][nextY] != 0) {
                return false;
            }
            return true;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
