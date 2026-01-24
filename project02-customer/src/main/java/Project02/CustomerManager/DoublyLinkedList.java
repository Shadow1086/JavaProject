package Project02.CustomerManager;

/**
 * 双向链表实现
 * 
 * @param <T> 泛型参数，表示链表中存储的数据类型
 *            使用时指定具体类型，如：
 *            DoublyLinkedList<Integer> 存储整数
 *            DoublyLinkedList<String> 存储字符串
 */

public class DoublyLinkedList<T> {
    
    /**
     * 节点内部类
     * 每个节点包含三部分：
     * 1. data - 存储的数据
     * 2. prev - 指向前一个节点的引用
     * 3. next - 指向后一个节点的引用
     * 
     * 结构图：
     *      ┌──────┬──────┬──────┐
     * <────│ prev │ data │ next │────>
     *      └──────┴──────┴──────┘
     */
    private class Node {
        T data;      // 节点存储的数据，类型为 T（泛型）
        Node prev;   // 指向前一个节点（前驱）
        Node next;   // 指向后一个节点（后继）
        
        /**
         * 节点构造方法
         * @param data 要存储的数据
         */
        Node(T data) {
            this.data = data;
            this.prev = null;  // 新节点的前驱默认为空
            this.next = null;  // 新节点的后继默认为空
        }
    }
    
    // ==================== 链表的成员变量 ====================
    
    private Node head;  // 头指针，指向链表第一个节点
    private Node tail;  // 尾指针，指向链表最后一个节点
    private int size;   // 链表中节点的数量
    
    /**
     * 构造方法 - 创建一个空链表
     * 
     * 初始状态：
     * head = null （没有头节点）
     * tail = null （没有尾节点）
     * size = 0    （没有元素）
     */
    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    
    // ==================== 基本操作方法 ====================
    
    /**
     * 判断链表是否为空
     * @return 如果链表为空返回 true，否则返回 false
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * 获取链表的大小
     * @return 链表中节点的数量
     */
    public int size() {
        return size;
    }
    
    // ==================== 插入操作 ====================
    
    /**
     * 在链表头部插入新节点
     * @param data 要插入的数据
     * 
     * 情况1：链表为空
     * 插入前：head = null, tail = null
     * 插入后：head ──> [A] <── tail
     * 
     * 情况2：链表不为空
     * 插入前：head ──> [B] <──> [C] <── tail
     * 插入后：head ──> [A] <──> [B] <──> [C] <── tail
     */
    public void addFirst(T data) {
        // 第一步：创建新节点
        Node newNode = new Node(data);
        
        // 第二步：判断链表是否为空
        if (head == null) {
            // 链表为空：新节点既是头也是尾
            // 
            //   head ──> [newNode] <── tail
            //
            head = newNode;
            tail = newNode;
        } else {
            // 链表不为空：将新节点插入到头部
            //
            // 插入前：
            //   head ──> [原头节点] <──> [...] <── tail
            //
            // 插入后：
            //   head ──> [newNode] <──> [原头节点] <──> [...] <── tail
            //
            
            // 2.1 新节点的 next 指向原来的头节点
            newNode.next = head;
            
            // 2.2 原头节点的 prev 指向新节点
            head.prev = newNode;
            
            // 2.3 更新 head 指针，指向新节点
            head = newNode;
        }
        
        // 第三步：节点数量加 1
        size++;
    }
    
    /**
     * 在链表尾部插入新节点
     * @param data 要插入的数据
     * 
     * 情况1：链表为空
     * 插入前：head = null, tail = null
     * 插入后：head ──> [A] <── tail
     * 
     * 情况2：链表不为空
     * 插入前：head ──> [A] <──> [B] <── tail
     * 插入后：head ──> [A] <──> [B] <──> [C] <── tail
     */
    public void addLast(T data) {
        // 第一步：创建新节点
        Node newNode = new Node(data);
        
        // 第二步：判断链表是否为空
        if (tail == null) {
            // 链表为空：新节点既是头也是尾
            head = newNode;
            tail = newNode;
        } else {
            // 链表不为空：将新节点插入到尾部
            //
            // 插入前：
            //   head ──> [...] <──> [原尾节点] <── tail
            //
            // 插入后：
            //   head ──> [...] <──> [原尾节点] <──> [newNode] <── tail
            //
            
            // 2.1 新节点的 prev 指向原来的尾节点
            newNode.prev = tail;
            
            // 2.2 原尾节点的 next 指向新节点
            tail.next = newNode;
            
            // 2.3 更新 tail 指针，指向新节点
            tail = newNode;
        }
        
        // 第三步：节点数量加 1
        size++;
    }
    
    // ==================== 删除操作 ====================
    
    /**
     * 删除链表头部的节点
     * @return 被删除节点的数据
     * @throws RuntimeException 如果链表为空
     * 
     * 情况1：链表只有一个节点
     * 删除前：head ──> [A] <── tail
     * 删除后：head = null, tail = null
     * 
     * 情况2：链表有多个节点
     * 删除前：head ──> [A] <──> [B] <──> [C] <── tail
     * 删除后：head ──> [B] <──> [C] <── tail
     */
    public T removeFirst() {
        // 第一步：检查链表是否为空
        if (head == null) {
            throw new RuntimeException("链表为空，无法删除");
        }
        
        // 第二步：保存要返回的数据
        T data = head.data;
        
        // 第三步：判断是否只有一个节点
        if (head == tail) {
            // 只有一个节点：删除后链表变空
            //
            // 删除前：head ──> [A] <── tail
            // 删除后：head = null, tail = null
            //
            head = null;
            tail = null;
        } else {
            // 有多个节点：删除头节点
            //
            // 删除前：
            //   head ──> [A] <──> [B] <──> [...] <── tail
            //            ↑
            //         要删除
            //
            // 删除后：
            //   head ──> [B] <──> [...] <── tail
            //
            
            // 3.1 head 指向第二个节点
            head = head.next;
            
            // 3.2 新头节点的 prev 设为 null（断开与原头节点的连接）
            head.prev = null;
        }
        
        // 第四步：节点数量减 1
        size--;
        
        // 第五步：返回被删除的数据
        return data;
    }
    
    /**
     * 删除链表尾部的节点
     * @return 被删除节点的数据
     * @throws RuntimeException 如果链表为空
     * 
     * 情况1：链表只有一个节点
     * 删除前：head ──> [A] <── tail
     * 删除后：head = null, tail = null
     * 
     * 情况2：链表有多个节点
     * 删除前：head ──> [A] <──> [B] <──> [C] <── tail
     * 删除后：head ──> [A] <──> [B] <── tail
     */
    public T removeLast() {
        // 第一步：检查链表是否为空
        if (tail == null) {
            throw new RuntimeException("链表为空，无法删除");
        }
        
        // 第二步：保存要返回的数据
        T data = tail.data;
        
        // 第三步：判断是否只有一个节点
        if (head == tail) {
            // 只有一个节点：删除后链表变空
            head = null;
            tail = null;
        } else {
            // 有多个节点：删除尾节点
            //
            // 删除前：
            //   head ──> [...] <──> [B] <──> [C] <── tail
            //                                 ↑
            //                              要删除
            //
            // 删除后：
            //   head ──> [...] <──> [B] <── tail
            //
            
            // 3.1 tail 指向倒数第二个节点
            tail = tail.prev;
            
            // 3.2 新尾节点的 next 设为 null（断开与原尾节点的连接）
            tail.next = null;
        }
        
        // 第四步：节点数量减 1
        size--;
        
        // 第五步：返回被删除的数据
        return data;
    }
    
    // ==================== 遍历和输出 ====================
    
    /**
     * 正向遍历链表，生成字符串表示
     * @return 链表的字符串形式
     * 
     * 例如：[A <-> B <-> C]
     */
    @Override
    public String toString() {
        // 使用 StringBuilder 拼接字符串（效率更高）
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        // 从头节点开始遍历
        Node current = head;
        
        // 当 current 不为 null 时，继续遍历
        //
        // 遍历过程：
        //   head ──> [A] <──> [B] <──> [C] <── tail
        //             ↑
        //          current（从这里开始）
        //
        //   第1次循环：current 指向 A，输出 A
        //   第2次循环：current 指向 B，输出 B
        //   第3次循环：current 指向 C，输出 C
        //   第4次循环：current 为 null，退出循环
        //
        while (current != null) {
            // 添加当前节点的数据
            sb.append(current.data);
            
            // 如果不是最后一个节点，添加分隔符
            if (current.next != null) {
                sb.append(" <-> ");
            }
            
            // 移动到下一个节点
            current = current.next;
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * 反向遍历链表，生成字符串表示
     * @return 链表的反向字符串形式
     * 
     * 例如：原链表 [A <-> B <-> C]
     *       反向输出 [C <-> B <-> A]
     */
    public String toReverseString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        // 从尾节点开始遍历
        Node current = tail;
        
        // 当 current 不为 null 时，继续遍历
        //
        // 遍历过程：
        //   head ──> [A] <──> [B] <──> [C] <── tail
        //                               ↑
        //                            current（从这里开始）
        //
        //   第1次循环：current 指向 C，输出 C
        //   第2次循环：current 指向 B，输出 B
        //   第3次循环：current 指向 A，输出 A
        //   第4次循环：current 为 null，退出循环
        //
        while (current != null) {
            sb.append(current.data);
            
            if (current.prev != null) {
                sb.append(" <-> ");
            }
            
            // 移动到前一个节点（反向遍历）
            current = current.prev;
        }
        
        sb.append("]");
        return sb.toString();
    }
}