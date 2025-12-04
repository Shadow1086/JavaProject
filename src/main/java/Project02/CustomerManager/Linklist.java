package Project02.CustomerManager;

public class Linklist<T> {

    private class Node {
        T data; // 节点存储的数据类型，类型为T（范型）
        Node prev; // 指向前一个节点(前驱)
        Node next; // 指向后一个节点(后继)
        // 这是一个构造方法

        Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    private Node head; // 头指针，指向链表的第一个节点
    private Node tail; // 尾指针，指向链表最后一个节点
    private int size; // 链表中节点的数量
    // 构造方法：创建一个空链表

    public Linklist() {
        head = null;
        tail = null;
        size = 0;
    }

    // 判断是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    // 获取链表的大小
    public int size() {
        return size;
    }

    /**
     * 在链表头部插入新节点
     * 
     * @param data 要插入的数据
     *             情况：
     *             1. 链表为空
     *             2. 链表不为空
     */
    public void addFirst(T data) {
        // 第一步创建新节点
        Node newNode = new Node(data);
        // 第二步：判断链表是否为空
        if (isEmpty()) {
            // 如果为空，那么这个链表的头节点和尾节点都是newNode
            head = newNode;
            tail = newNode;
        } else {
            // 如果不为空，那么将这个节点添加到头部
            // 新节点的后继就是原先的头节点
            newNode.next = head;
            // 原先的头节点的前驱就是新节点
            head.prev = newNode;
            // 更新链表的头节点为newnode
            head = newNode;
        }
        size++;
    }

    /**
     * 删除指定位置的节点
     * 
     * @param index 指定的位置
     */
    public void removeNode(int index) {
        index--;
        if (index < 0 || index > size) {
            System.out.println("输入的位置无效，请重新输入");
        }
        if (index == 0) {
            removeFirst();
            return;
        } else if (index == size - 1) {
            removeLast();
            return;
        } else {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;
            return;
        }
    }

    /**
     * 在链表最后加入尾节点
     * 
     * @param data 要插入的数据
     */
    public void addlast(T data) {
        // 1. 创建新节点
        Node newNode = new Node(data);
        // 判断是否为空
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * 删除头节点并且返回删除掉的数据
     * 
     * @return
     */
    public T removeFirst() {
        // 检查链表是否为空
        if (isEmpty()) {
            throw new RuntimeException("链表为空，无法删除");
        }
        // 保存要返回的数据
        T data = head.data;
        // 判断是否只有一个节点
        if (tail == head) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return data;

    }

    /**
     * 删除尾节点
     * 
     * @return 返回删除的数据
     */
    public T removeLast() {
        // 检查链表是否为空
        if (isEmpty()) {
            throw new RuntimeException("链表为空，无法删除");
        }
        T data = tail.data;
        if (tail == head) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return data;
    }

    /**
     * 打印用户信息，遍历每一个节点
     */
    public void printCustomerInfoInList() {
        if (!isEmpty()) {
            Node current = head;
            System.out.println("---------------------------客户列表---------------------------\n"
                    + "编号\t姓名\t性别\t年龄\t电话\t\t邮箱");
            for (int i = 0; i < size; i++) {
                System.out.println((i + 1) + "\t" + current.data.toString());
            }
            System.out.println("-------------------------客户列表完成-------------------------\n");
        } else {
            System.out.println("--------------------------------------------------\n");

            System.out.println("暂无信息!!!");
        }
    }

    /**
     * 返回目标客户
     */
    public T getNode(int index) {
        if (index >= size) {
            System.out.println("输入错误，请检查是否存在该用户");
            return null;
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * 一个个返回
     */
    /**
     * 正向遍历链表
     * 返回字符串格式：head <-> head.next <-> ... <-> tail
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append("<->");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    public String toReverseString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Node current = tail;
        while (current != null) {
            sb.append(current.data);
            if (current.prev != null) {
                sb.append("<->");
            }
            current = current.prev;
        }
        sb.append("]");
        return sb.toString();
    }

}
