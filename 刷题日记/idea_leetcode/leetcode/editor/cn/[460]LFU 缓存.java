//请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。 
//
// 实现 LFUCache 类： 
//
// 
// LFUCache(int capacity) - 用数据结构的容量 capacity 初始化对象 
// int get(int key) - 如果键存在于缓存中，则获取键的值，否则返回 -1。 
// void put(int key, int value) - 如果键已存在，则变更其值；如果键不存在，请插入键值对。当缓存达到其容量时，则应该在插入新项之
//前，使最不经常使用的项无效。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最近最久未使用 的键。 
// 
//
// 注意「项的使用次数」就是自插入该项以来对其调用 get 和 put 函数的次数之和。使用次数会在对应项被移除后置为 0 。 
//
// 为了确定最不常使用的键，可以为缓存中的每个键维护一个 使用计数器 。使用计数最小的键是最久未使用的键。 
//
// 当一个键首次插入到缓存中时，它的使用计数器被设置为 1 (由于 put 操作)。对缓存中的键执行 get 或 put 操作，使用计数器的值将会递增。 
//
// 
//
// 示例： 
//
// 
//输入：
//["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "g
//et"]
//[[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
//输出：
//[null, null, null, 1, null, -1, 3, null, -1, 3, 4]
//
//解释：
//// cnt(x) = 键 x 的使用计数
//// cache=[] 将显示最后一次使用的顺序（最左边的元素是最近的）
//LFUCache lFUCache = new LFUCache(2);
//lFUCache.put(1, 1);   // cache=[1,_], cnt(1)=1
//lFUCache.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
//lFUCache.get(1);      // 返回 1
//                      // cache=[1,2], cnt(2)=1, cnt(1)=2
//lFUCache.put(3, 3);   // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小
//                      // cache=[3,1], cnt(3)=1, cnt(1)=2
//lFUCache.get(2);      // 返回 -1（未找到）
//lFUCache.get(3);      // 返回 3
//                      // cache=[3,1], cnt(3)=2, cnt(1)=2
//lFUCache.put(4, 4);   // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用
//                      // cache=[4,3], cnt(4)=1, cnt(3)=2
//lFUCache.get(1);      // 返回 -1（未找到）
//lFUCache.get(3);      // 返回 3
//                      // cache=[3,4], cnt(4)=1, cnt(3)=3
//lFUCache.get(4);      // 返回 4
//                      // cache=[3,4], cnt(4)=2, cnt(3)=3 
//
// 
//
// 提示： 
//
// 
// 0 <= capacity, key, value <= 104 
// 最多调用 105 次 get 和 put 方法 
// 
//
// 
//
// 进阶：你可以为这两种操作设计时间复杂度为 O(1) 的实现吗？ 
// Related Topics 设计 
// 👍 391 👎 0


import java.util.HashMap;

//leetcode submit region begin(Prohibit modification and deletion)
class LFUCache {

    private HashMap<Integer, ListNode> map;
    private HashMap<Integer, DoubleLinkedList> frequentMap;
    private int capacity;
    private int minFrequnt = 1;

    public LFUCache(int capacity) {
        this.map = new HashMap<>(capacity, 1);
        this.frequentMap = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        addFrequent(key);
        return map.get(key).val;
    }

    public void put(int key, int value) {
        if (capacity<=0){
            return;
        }
        ListNode node = new ListNode(key, value);
        if (map.containsKey(key)) {
            editOldNode(node);
        } else {
            if (size() == capacity) {
                removeOldest();
            }
            map.put(key, node);
            addNewNode(node);
        }
    }

    public void addFrequent(int key) {
        ListNode node = map.get(key);
        removeFrequentMapNode(node);
        node.frequency++;
        addFrequentMapNode(node);
    }

    public void removeOldest() {
        while (frequentMap.get(minFrequnt) == null || frequentMap.get(minFrequnt).count == 0) {
            minFrequnt++;
        }
        DoubleLinkedList list = frequentMap.get(minFrequnt);
        ListNode remove = list.tail.pre;
        remove.pre.next = list.tail;
        list.tail.pre = remove.pre;
        list.count--;
        map.remove(remove.key);
    }

    public void editOldNode(ListNode node) {
        ListNode oldNode = map.get(node.key);
        oldNode.val = node.val;
        addFrequent(node.key);
    }

    public void removeFrequentMapNode(ListNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
        frequentMap.get(node.frequency).count--;
    }

    public void addFrequentMapNode(ListNode node) {
        if (!frequentMap.containsKey(node.frequency)) {
            frequentMap.put(node.frequency, new DoubleLinkedList());
        }
        DoubleLinkedList list = frequentMap.get(node.frequency);
        list.count++;
        node.next = list.head.next;
        node.next.pre = node;
        list.head.next = node;
        node.pre = list.head;
    }

    public void addNewNode(ListNode node) {
        if (!frequentMap.containsKey(1)) {
            frequentMap.put(1, new DoubleLinkedList());
        }
        DoubleLinkedList list = frequentMap.get(1);
        list.count++;
        node.next = list.head.next;
        node.next.pre = node;
        node.pre = list.head;
        list.head.next = node;
        minFrequnt = 1;
    }

    private int size() {
        return map.size();
    }

    class DoubleLinkedList {
        ListNode head;
        ListNode tail;
        int count;

        DoubleLinkedList() {
            head = new ListNode(-1, -1);
            tail = new ListNode(-1, -1);
            head.next = tail;
            tail.pre = head;
            head.pre = null;
            tail.next = null;
            count = 0;
        }
    }

    class ListNode {
        int key;
        int val;
        int frequency;
        ListNode pre;
        ListNode next;

        ListNode(int key, int val) {
            this.key = key;
            this.val = val;
            this.frequency = 1;
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
//leetcode submit region end(Prohibit modification and deletion)
