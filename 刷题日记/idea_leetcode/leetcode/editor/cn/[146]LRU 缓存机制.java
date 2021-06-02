//运用你所掌握的数据结构，设计和实现一个 LRU (最近最少使用) 缓存机制 。 
//
// 
// 
// 实现 LRUCache 类： 
//
// 
// LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存 
// int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。 
// void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上
//限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。 
// 
//
// 
// 
// 
//
// 进阶：你是否可以在 O(1) 时间复杂度内完成这两种操作？ 
//
// 
//
// 示例： 
//
// 
//输入
//["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
//[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
//输出
//[null, null, null, 1, null, -1, null, -1, 3, 4]
//
//解释
//LRUCache lRUCache = new LRUCache(2);
//lRUCache.put(1, 1); // 缓存是 {1=1}
//lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
//lRUCache.get(1);    // 返回 1
//lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
//lRUCache.get(2);    // 返回 -1 (未找到)
//lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
//lRUCache.get(1);    // 返回 -1 (未找到)
//lRUCache.get(3);    // 返回 3
//lRUCache.get(4);    // 返回 4
// 
//
// 
//
// 提示： 
//
// 
// 1 <= capacity <= 3000 
// 0 <= key <= 3000 
// 0 <= value <= 104 
// 最多调用 3 * 104 次 get 和 put 
// 
// Related Topics 设计 
// 👍 1360 👎 0


import java.util.*;

//leetcode submit region begin(Prohibit modification and deletion)
class LRUCache {

    public LRUCache(int capacity) {
        // LRU即最近未使用
        // 使用一个map保存数据用作缓存
        // 使用一个队列用作 LRU 缓存，按照使用时间排队
        this.capacity = capacity;
        queue = new ListNodeQueue();
        map = new HashMap<Integer, ListNode>();
    }

    private int capacity;

    private ListNodeQueue queue;

    private Map<Integer, ListNode> map;

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        int val = map.get(key).val;
        put(key, val);
        return val;
    }

    public void put(int key, int value) {
        ListNode item = new ListNode(key, value);
        if (map.containsKey(key)) {
            queue.remove(map.get(key));
            queue.addFirst(item);
            map.put(key, item);
            return;
        }
        if (capacity == queue.size()) {
            ListNode delete = queue.removeLast();
            map.remove(delete.key);
        }
        queue.addFirst(item);
        map.put(key, item);
    }

    class ListNodeQueue {
        ListNode head;
        ListNode tail;
        int count;

        public void addFirst(ListNode node) {
            if (head == null) {
                head = node;
                tail = node;
            } else {
                node.next = head;
                head.pre = node;
                head = node;
            }
            count++;
        }

        public void remove(ListNode node) {
            if (node == head && node == tail) {
                head = null;
                tail = null;
            } else if (node == head) {
                node.next.pre = null;
                head = node.next;
            } else if (node == tail) {
                node.pre.next = null;
                tail = node.pre;
            } else {
                node.pre.next = node.next;
                node.next.pre = node.pre;
            }
            count--;
        }

        public ListNode removeLast() {
            ListNode node = tail;
            remove(tail);
            return node;
        }

        public int size() {
            return count;
        }
    }

    class ListNode {
        int key;
        int val;
        ListNode pre, next;

        ListNode(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
//leetcode submit region end(Prohibit modification and deletion)
