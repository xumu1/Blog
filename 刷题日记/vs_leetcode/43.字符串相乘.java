import java.util.ArrayList;

/*
 * @lc app=leetcode.cn id=43 lang=java
 *
 * [43] 字符串相乘
 */

// @lc code=start
class Solution {
    public String multiply(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        return len1 >= len2 ? fun(num1, num2) : fun(num2, num1);
    }

    public String fun(String num1, String num2) {
        ArrayList<Integer> result = null;
        for (int i = num2.length() - 1; i >= 0; i--) {
            result = addStringBuffer(result, multiplication(num2.charAt(i) - '0', num1, num2.length() - 1 - i));
            // System.out.println("result: " + result);
        }
        int tmp = 0;
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < result.size() - 1; i++) {
            tmp = result.get(i) / 10;
            result.set(i, result.get(i) % 10);
            result.set(i + 1, result.get(i + 1) + tmp);
            System.out.println("result: " + result);
        }
        tmp = result.get(result.size() - 1) / 10;
        if (tmp != 0) {
            result.set(result.size() - 1, result.get(result.size() - 1) % 10);
            result.add(tmp);
        }
        int tag = 1;
        for (int i = result.size() - 1; i >= 0; i--) {
            if (tag == 1) {
                if (result.get(i) == 0) {
                    result.remove(i);
                } else {
                    tag = 0;
                }
            }
        }
        for (int i = result.size() - 1; i >= 0; i--) {
            buf.append(String.valueOf(result.get(i)));
        }
        if (buf.length() == 0)
            return "0";
        return buf.toString();
    }

    // 相加两个list
    public ArrayList<Integer> addStringBuffer(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        if (list1 == null) {
            return list2;
        } else {
            for (int i = 0; i < list1.size(); i++) {
                list2.set(i, list2.get(i) + list1.get(i));
            }
        }
        return list2;
    }

    // 把一个数和一个字符串相乘
    public ArrayList<Integer> multiplication(int x, String num, int index) {

        // System.out.println("x:" + x);
        // System.out.println("num:" + num);
        // System.out.println("index:" + index);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            list.add(0);
        }
        for (int i = num.length() - 1; i >= 0; i--) {
            int tmp = x * (num.charAt(i) - '0');
            // System.out.println("tmp:" + tmp);
            list.add(tmp);
        }
        // System.out.println("list:" + list);
        return list;
    }
}
// @lc code=end
