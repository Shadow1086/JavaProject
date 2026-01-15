package Project02.CustomerManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * ClassName: LinklistTest
 * Description: Linklist双向链表的单元测试
 * 
 * {@code @Author} Liang-ht
 * {@code @Create} 2025-12-04 00:57:00
 */
@DisplayName("Linklist双向链表测试")
public class LinklistTest {

    private Linklist<Customer> customerList;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    @BeforeEach
    public void setUp() {
        // 在每个测试方法执行前初始化测试数据
        customerList = new Linklist<>();
        customer1 = new Customer("张三", "男", 25, "13800138000", "zhangsan@example.com");
        customer2 = new Customer("李四", "女", 30, "13900139000", "lisi@example.com");
        customer3 = new Customer("王五", "男", 35, "13700137000", "wangwu@example.com");
    }

    @Test
    @DisplayName("测试链表初始化")
    public void testInitialization() {
        assertTrue(customerList.isEmpty(), "新创建的链表应该为空");
        assertEquals(0, customerList.size(), "新创建的链表大小应该为0");
    }

    @Test
    @DisplayName("测试addFirst方法 - 在头部添加节点")
    public void testAddFirst() {
        customerList.addFirst(customer1);
        assertFalse(customerList.isEmpty(), "添加元素后链表不应为空");
        assertEquals(1, customerList.size(), "添加一个元素后大小应该为1");
        
        customerList.addFirst(customer2);
        assertEquals(2, customerList.size(), "添加第二个元素后大小应该为2");
        
        // 验证顺序：最后添加的在最前面
        assertEquals(customer2, customerList.getNode(0), "第一个节点应该是最后添加的customer2");
        assertEquals(customer1, customerList.getNode(1), "第二个节点应该是customer1");
    }

    @Test
    @DisplayName("测试addlast方法 - 在尾部添加节点")
    public void testAddLast() {
        customerList.addlast(customer1);
        assertFalse(customerList.isEmpty(), "添加元素后链表不应为空");
        assertEquals(1, customerList.size(), "添加一个元素后大小应该为1");
        
        customerList.addlast(customer2);
        customerList.addlast(customer3);
        assertEquals(3, customerList.size(), "添加三个元素后大小应该为3");
        
        // 验证顺序：按添加顺序排列
        assertEquals(customer1, customerList.getNode(0), "第一个节点应该是customer1");
        assertEquals(customer2, customerList.getNode(1), "第二个节点应该是customer2");
        assertEquals(customer3, customerList.getNode(2), "第三个节点应该是customer3");
    }

    @Test
    @DisplayName("测试removeFirst方法 - 删除头节点")
    public void testRemoveFirst() {
        customerList.addlast(customer1);
        customerList.addlast(customer2);
        customerList.addlast(customer3);
        
        Customer removed = customerList.removeFirst();
        assertEquals(customer1, removed, "删除的应该是第一个元素");
        assertEquals(2, customerList.size(), "删除后大小应该为2");
        assertEquals(customer2, customerList.getNode(0), "删除后第一个元素应该是customer2");
        
        // 继续删除
        customerList.removeFirst();
        assertEquals(1, customerList.size(), "再次删除后大小应该为1");
        
        // 删除最后一个元素
        customerList.removeFirst();
        assertTrue(customerList.isEmpty(), "删除所有元素后链表应该为空");
        assertEquals(0, customerList.size(), "删除所有元素后大小应该为0");
    }

    @Test
    @DisplayName("测试removeFirst方法 - 空链表异常")
    public void testRemoveFirstFromEmptyList() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerList.removeFirst();
        }, "从空链表删除应该抛出异常");
        
        assertEquals("链表为空，无法删除", exception.getMessage(), "异常消息应该正确");
    }

    @Test
    @DisplayName("测试removeLast方法 - 删除尾节点")
    public void testRemoveLast() {
        customerList.addlast(customer1);
        customerList.addlast(customer2);
        customerList.addlast(customer3);
        
        Customer removed = customerList.removeLast();
        assertEquals(customer3, removed, "删除的应该是最后一个元素");
        assertEquals(2, customerList.size(), "删除后大小应该为2");
        
        // 继续删除
        removed = customerList.removeLast();
        assertEquals(customer2, removed, "删除的应该是customer2");
        assertEquals(1, customerList.size(), "删除后大小应该为1");
        
        // 删除最后一个元素
        removed = customerList.removeLast();
        assertEquals(customer1, removed, "删除的应该是customer1");
        assertTrue(customerList.isEmpty(), "删除所有元素后链表应该为空");
    }

    @Test
    @DisplayName("测试removeLast方法 - 空链表异常")
    public void testRemoveLastFromEmptyList() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerList.removeLast();
        }, "从空链表删除应该抛出异常");
        
        assertEquals("链表为空，无法删除", exception.getMessage(), "异常消息应该正确");
    }

    @Test
    @DisplayName("测试removeNode方法 - 删除指定位置节点")
    public void testRemoveNode() {
        customerList.addlast(customer1);
        customerList.addlast(customer2);
        customerList.addlast(customer3);
        
        // 删除中间节点（注意：removeNode的index是从1开始的）
        customerList.removeNode(2);
        assertEquals(2, customerList.size(), "删除后大小应该为2");
        assertEquals(customer1, customerList.getNode(0), "第一个元素应该是customer1");
        assertEquals(customer3, customerList.getNode(1), "第二个元素应该是customer3");
    }

    @Test
    @DisplayName("测试removeNode方法 - 删除第一个节点")
    public void testRemoveNodeFirst() {
        customerList.addlast(customer1);
        customerList.addlast(customer2);
        customerList.addlast(customer3);
        
        customerList.removeNode(1); // 删除第一个（index从1开始）
        assertEquals(2, customerList.size(), "删除后大小应该为2");
        assertEquals(customer2, customerList.getNode(0), "第一个元素应该是customer2");
    }

    @Test
    @DisplayName("测试removeNode方法 - 删除最后一个节点")
    public void testRemoveNodeLast() {
        customerList.addlast(customer1);
        customerList.addlast(customer2);
        customerList.addlast(customer3);
        
        customerList.removeNode(3); // 删除最后一个
        assertEquals(2, customerList.size(), "删除后大小应该为2");
        assertEquals(customer2, customerList.getNode(1), "最后一个元素应该是customer2");
    }

    @Test
    @DisplayName("测试getNode方法 - 获取指定位置节点")
    public void testGetNode() {
        customerList.addlast(customer1);
        customerList.addlast(customer2);
        customerList.addlast(customer3);
        
        assertEquals(customer1, customerList.getNode(0), "索引0应该返回customer1");
        assertEquals(customer2, customerList.getNode(1), "索引1应该返回customer2");
        assertEquals(customer3, customerList.getNode(2), "索引2应该返回customer3");
    }

    @Test
    @DisplayName("测试getNode方法 - 越界情况")
    public void testGetNodeOutOfBounds() {
        customerList.addlast(customer1);
        
        // 测试越界访问
        Customer result = customerList.getNode(5);
        assertNull(result, "越界访问应该返回null");
    }

    @Test
    @DisplayName("测试toString方法 - 正向遍历")
    public void testToString() {
        assertTrue(customerList.toString().contains("[]"), "空链表toString应该包含[]");
        
        customerList.addlast(customer1);
        customerList.addlast(customer2);
        
        String result = customerList.toString();
        assertTrue(result.contains("<->"), "toString应该包含<->分隔符");
        assertTrue(result.contains("张三"), "toString应该包含customer1的信息");
        assertTrue(result.contains("李四"), "toString应该包含customer2的信息");
    }

    @Test
    @DisplayName("测试toReverseString方法 - 反向遍历")
    public void testToReverseString() {
        customerList.addlast(customer1);
        customerList.addlast(customer2);
        customerList.addlast(customer3);
        
        String result = customerList.toReverseString();
        assertTrue(result.contains("<->"), "toReverseString应该包含<->分隔符");
        assertTrue(result.contains("王五"), "toReverseString应该包含customer3的信息");
        
        // 验证反向顺序
        int pos1 = result.indexOf("张三");
        int pos2 = result.indexOf("李四");
        int pos3 = result.indexOf("王五");
        assertTrue(pos3 < pos2 && pos2 < pos1, "反向遍历应该是customer3, customer2, customer1的顺序");
    }

    @Test
    @DisplayName("测试printCustomerInfoInList方法 - 打印客户列表")
    public void testPrintCustomerInfoInList() {
        // 捕获System.out输出
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        try {
            // 测试空链表
            customerList.printCustomerInfoInList();
            String output = outContent.toString();
            assertTrue(output.contains("暂无信息"), "空链表应该显示'暂无信息'");
            
            // 重置输出流
            outContent.reset();
            
            // 添加数据后测试
            customerList.addlast(customer1);
            customerList.addlast(customer2);
            customerList.printCustomerInfoInList();
            
            output = outContent.toString();
            assertTrue(output.contains("客户列表"), "应该包含'客户列表'标题");
            assertTrue(output.contains("编号"), "应该包含'编号'列");
            assertTrue(output.contains("姓名"), "应该包含'姓名'列");
            assertTrue(output.contains("张三"), "应该包含customer1的信息");
            assertTrue(output.contains("李四"), "应该包含customer2的信息");
            assertTrue(output.contains("客户列表完成"), "应该包含完成提示");
            
        } finally {
            // 恢复System.out
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试链表的混合操作")
    public void testMixedOperations() {
        // 混合使用addFirst和addlast
        customerList.addlast(customer1);
        customerList.addFirst(customer2);
        customerList.addlast(customer3);
        
        assertEquals(3, customerList.size(), "大小应该为3");
        assertEquals(customer2, customerList.getNode(0), "第一个应该是customer2");
        assertEquals(customer1, customerList.getNode(1), "第二个应该是customer1");
        assertEquals(customer3, customerList.getNode(2), "第三个应该是customer3");
        
        // 删除中间元素
        customerList.removeNode(2);
        assertEquals(2, customerList.size(), "删除后大小应该为2");
        
        // 再添加元素
        customerList.addlast(customer1);
        assertEquals(3, customerList.size(), "添加后大小应该为3");
    }

    @Test
    @DisplayName("测试单个元素的链表操作")
    public void testSingleElementOperations() {
        customerList.addlast(customer1);
        assertEquals(1, customerList.size(), "大小应该为1");
        
        Customer removed = customerList.removeFirst();
        assertEquals(customer1, removed, "删除的应该是customer1");
        assertTrue(customerList.isEmpty(), "删除后应该为空");
        
        // 再次添加
        customerList.addFirst(customer2);
        assertEquals(1, customerList.size(), "大小应该为1");
        
        removed = customerList.removeLast();
        assertEquals(customer2, removed, "删除的应该是customer2");
        assertTrue(customerList.isEmpty(), "删除后应该为空");
    }

    @Test
    @DisplayName("测试大量数据操作")
    public void testLargeDataOperations() {
        // 添加大量数据
        for (int i = 0; i < 100; i++) {
            Customer customer = new Customer(
                "客户" + i, 
                i % 2 == 0 ? "男" : "女", 
                20 + i % 50, 
                "1380013800" + i, 
                "customer" + i + "@test.com"
            );
            customerList.addlast(customer);
        }
        
        assertEquals(100, customerList.size(), "应该有100个元素");
        
        // 验证第一个和最后一个
        assertEquals("客户0", customerList.getNode(0).getName(), "第一个客户名称应该正确");
        assertEquals("客户99", customerList.getNode(99).getName(), "最后一个客户名称应该正确");
        
        // 删除一些元素
        for (int i = 0; i < 10; i++) {
            customerList.removeFirst();
        }
        assertEquals(90, customerList.size(), "删除10个后应该剩90个");
        assertEquals("客户10", customerList.getNode(0).getName(), "删除后第一个客户应该是客户10");
    }

    @Test
    @DisplayName("测试链表的边界条件")
    public void testBoundaryConditions() {
        // 测试空链表的size和isEmpty
        assertEquals(0, customerList.size(), "空链表大小应该为0");
        assertTrue(customerList.isEmpty(), "空链表isEmpty应该返回true");
        
        // 添加一个元素
        customerList.addlast(customer1);
        assertEquals(1, customerList.size(), "添加一个元素后大小应该为1");
        assertFalse(customerList.isEmpty(), "添加元素后isEmpty应该返回false");
        
        // 删除这个元素
        customerList.removeFirst();
        assertEquals(0, customerList.size(), "删除后大小应该为0");
        assertTrue(customerList.isEmpty(), "删除后isEmpty应该返回true");
    }

    @Test
    @DisplayName("测试链表存储不同类型数据")
    public void testGenericType() {
        // 测试链表的泛型特性 - 使用String类型
        Linklist<String> stringList = new Linklist<>();
        stringList.addlast("第一个");
        stringList.addlast("第二个");
        stringList.addlast("第三个");
        
        assertEquals(3, stringList.size(), "字符串链表大小应该为3");
        assertEquals("第一个", stringList.getNode(0), "第一个元素应该正确");
        
        // 测试Integer类型
        Linklist<Integer> intList = new Linklist<>();
        intList.addlast(100);
        intList.addlast(200);
        intList.addlast(300);
        
        assertEquals(3, intList.size(), "整数链表大小应该为3");
        assertEquals(Integer.valueOf(100), intList.getNode(0), "第一个元素应该是100");
    }
}
