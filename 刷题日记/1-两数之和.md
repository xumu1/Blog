![leetcode-1](https://upload-images.jianshu.io/upload_images/19635758-ae54025ddb47d2a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
### 分析题目可以想到两种解法
- 暴力解法：遍历数组两边，找到相加和为target的两个整数，并返回下标。时间复杂度为O(N)。
- hash表解法：第一遍遍历数组，把数值和下标值存入HashMap中，第二遍遍历数组，查找HashMap中是否有相加凑够target的值，找到则返回，查找的时间复杂对为O(1)，总时间复杂度为O(N)。
### 写出第二种方法的代码：
```
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            map.put(nums[i],i);
        }//遍历完成
        for(int i = 0; i < nums.length; i++){
            if(map.get(target-nums[i]) != null &&map.get(target-nums[i]) != i)
                return new int[]{i,map.get(target-nums[i])};
        }
        throw new IllegalArgumentException("no two sum solution.");
    }
}
```
### 收获
- 使用hash表可以用空间换取搜索的速度，降低时间复杂度。
