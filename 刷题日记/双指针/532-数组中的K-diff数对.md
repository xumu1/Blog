```python
class Solution:
    def findPairs(self, nums: List[int], k: int) -> int:
        nums.sort()
        res = {}
        for left in range(len(nums)):
            right = left + 1
            while right < len(nums):
                if k == abs(nums[right] - nums[left]):
                    res[nums[left]] = nums[right]
                right += 1
        return len(res)
#超出时间限制        
```

```python
#初级版
class Solution:
    def findPairs(self, nums: List[int], k: int) -> int:
        nums.sort()
        res = {}
        for index in range(len(nums)):
            tmp = nums[index]
            div = tmp + k
            if k == 0 and tmp in nums[index+1:]:
                res[tmp] = tmp
            if div in nums and nums.index(div) > index:
                res[tmp] = div
        return len(res)
#改进版
class Solution:
    def findPairs(self, nums: List[int], k: int) -> int:
        if k < 0:
            return 0
        res = {}
        for index in range(len(nums)):
            tmp = nums[index]
            div = tmp + k
            if k == 0 and tmp in nums[index+1:]:
                res[tmp] = tmp
                continue
            if k != 0 and div in nums:
                res[tmp] = div
        return len(res)
```

```
利用集合元素互异性去重，来找到无重复对.
class Solution:
    def findPairs(self, nums: List[int], k: int) -> int:
        if k < 0:
            return 0
        if k == 0:
            return len(set([i for i in nums if nums.count(i)>=2]))
        cl = [i+k for i in nums]
        return len(set(cl)&set(nums))
            

作者：plogic
链接：https://leetcode-cn.com/problems/k-diff-pairs-in-an-array/solution/py3-li-yong-ji-he-xing-zhi-chu-li-by-plogic/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```

