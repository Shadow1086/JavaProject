package Project02.CustomerManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * ClassName: CustomerListTest
 * Description: CustomerList管理类的单元测试
 * 
 * {@code @Author} Liang-ht
 * {@code @Create} 2025-12-04 00:58:00
 */
@DisplayName("CustomerList管理类测试")
public class CustomerListTest {

    private CustomerList customerList;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    @BeforeEach
    public void setUp() {
        // 在每个测试方法执行前初始化测试数据
        customerList = new CustomerList();
        customer1 = new Customer("佟刚", "男", 45, "010-56253825", "tong@abc.com");
        customer2 = new Customer("封捷", "女", 36, "010-56253825", "fengjie@ibm.com");
        customer3 = new Customer("雷丰阳", "男", 32, "010-56253825", "leify@163.com");
    }

    @Test
    @DisplayName("测试CustomerList初始化")
    public void testInitialization() {
        assertNotNull(customerList, "CustomerList对象不应为null");
        assertNotNull(customerList.customerLinkedList, "内部链表不应为null");
        assertTrue(customerList.customerLinkedList.isEmpty(), "新创建的CustomerList应该为空");
    }

    @Test
    @DisplayName("测试addCustomer方法 - 添加单个客户")
    public void testAddCustomer() {
        customerList.addCustomer(customer1);
        assertEquals(1, customerList.customerLinkedList.size(), "添加一个客户后大小应该为1");
        assertEquals(customer1, customerList.getCust(0), "第一个客户应该是customer1");
    }

    @Test
    @DisplayName("测试addCustomer方法 - 添加多个客户")
    public void testAddMultipleCustomers() {
        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);
        customerList.addCustomer(customer3);
        
        assertEquals(3, customerList.customerLinkedList.size(), "添加三个客户后大小应该为3");
        assertEquals(customer1, customerList.getCust(0), "第一个客户应该是customer1");
        assertEquals(customer2, customerList.getCust(1), "第二个客户应该是customer2");
        assertEquals(customer3, customerList.getCust(2), "第三个客户应该是customer3");
    }

    @Test
    @DisplayName("测试addCustomer方法 - 添加null客户")
    public void testAddNullCustomer() {
        // 测试添加null（根据当前实现，会被接受）
        customerList.addCustomer(null);
        assertEquals(1, customerList.customerLinkedList.size(), "添加null后大小应该为1");
        assertNull(customerList.getCust(0), "第一个元素应该是null");
    }

    @Test
    @DisplayName("测试removeCustomer方法 - 删除指定位置客户")
    public void testRemoveCustomer() {
        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);
        customerList.addCustomer(customer3);
        
        // 删除第二个客户（注意：removeCustomer的index是从1开始的）
        customerList.removeCustomer(2);
        assertEquals(2, customerList.customerLinkedList.size(), "删除后大小应该为2");
        assertEquals(customer1, customerList.getCust(0), "第一个客户应该是customer1");
        assertEquals(customer3, customerList.getCust(1), "第二个客户应该是customer3");
    }

    @Test
    @DisplayName("测试removeCustomer方法 - 删除第一个客户")
    public void testRemoveFirstCustomer() {
        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);
        customerList.addCustomer(customer3);
        
        customerList.removeCustomer(1);
        assertEquals(2, customerList.customerLinkedList.size(), "删除后大小应该为2");
        assertEquals(customer2, customerList.getCust(0), "第一个客户应该是customer2");
    }

    @Test
    @DisplayName("测试removeCustomer方法 - 删除最后一个客户")
    public void testRemoveLastCustomer() {
        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);
        customerList.addCustomer(customer3);
        
        customerList.removeCustomer(3);
        assertEquals(2, customerList.customerLinkedList.size(), "删除后大小应该为2");
        assertEquals(customer2, customerList.getCust(1), "最后一个客户应该是customer2");
    }

    @Test
    @DisplayName("测试removeCustomer方法 - 删除所有客户")
    public void testRemoveAllCustomers() {
        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);
        
        customerList.removeCustomer(1);
        customerList.removeCustomer(1); // 删除后，原来的第二个变成了第一个
        
        assertTrue(customerList.customerLinkedList.isEmpty(), "删除所有客户后应该为空");
    }

    @Test
    @DisplayName("测试getCust方法 - 获取指定位置客户")
    public void testGetCust() {
        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);
        customerList.addCustomer(customer3);
        
        assertEquals(customer1, customerList.getCust(0), "索引0应该返回customer1");
        assertEquals(customer2, customerList.getCust(1), "索引1应该返回customer2");
        assertEquals(customer3, customerList.getCust(2), "索引2应该返回customer3");
    }

    @Test
    @DisplayName("测试getCust方法 - 越界情况")
    public void testGetCustOutOfBounds() {
        customerList.addCustomer(customer1);
        
        Customer result = customerList.getCust(10);
        assertNull(result, "越界访问应该返回null");
    }

    @Test
    @DisplayName("测试getCust方法 - 空列表")
    public void testGetCustFromEmptyList() {
        Customer result = customerList.getCust(0);
        assertNull(result, "从空列表获取应该返回null");
    }

    @Test
    @DisplayName("测试printCustomerInfo方法 - 打印客户信息")
    public void testPrintCustomerInfo() {
        // 捕获System.out输出
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        try {
            // 测试空列表
            customerList.printCustomerInfo();
            String output = outContent.toString();
            assertTrue(output.contains("暂无信息"), "空列表应该显示'暂无信息'");
            
            // 重置输出流
            outContent.reset();
            
            // 添加数据后测试
            customerList.addCustomer(customer1);
            customerList.addCustomer(customer2);
            customerList.addCustomer(customer3);
            customerList.printCustomerInfo();
            
            output = outContent.toString();
            assertTrue(output.contains("客户列表"), "应该包含'客户列表'标题");
            assertTrue(output.contains("佟刚"), "应该包含customer1的信息");
            assertTrue(output.contains("封捷"), "应该包含customer2的信息");
            assertTrue(output.contains("雷丰阳"), "应该包含customer3的信息");
            assertTrue(output.contains("客户列表完成"), "应该包含完成提示");
            
        } finally {
            // 恢复System.out
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试客户信息的完整性")
    public void testCustomerDataIntegrity() {
        customerList.addCustomer(customer1);
        
        Customer retrieved = customerList.getCust(0);
        assertEquals("佟刚", retrieved.getName(), "客户姓名应该正确");
        assertEquals("男", retrieved.getSex(), "客户性别应该正确");
        assertEquals(45, retrieved.getAge(), "客户年龄应该正确");
        assertEquals("010-56253825", retrieved.getTele(), "客户电话应该正确");
        assertEquals("tong@abc.com", retrieved.getMail(), "客户邮箱应该正确");
    }

    @Test
    @DisplayName("测试修改客户信息")
    public void testModifyCustomerInfo() {
        customerList.addCustomer(customer1);
        
        // 获取客户并修改
        Customer customer = customerList.getCust(0);
        customer.setName("修改后的佟刚");
        customer.setAge(46);
        customer.setMail("newemail@abc.com");
        
        // 验证修改是否生效
        Customer retrieved = customerList.getCust(0);
        assertEquals("修改后的佟刚", retrieved.getName(), "修改后的姓名应该正确");
        assertEquals(46, retrieved.getAge(), "修改后的年龄应该正确");
        assertEquals("newemail@abc.com", retrieved.getMail(), "修改后的邮箱应该正确");
    }

    @Test
    @DisplayName("测试混合操作 - 添加、删除、查询")
    public void testMixedOperations() {
        // 添加三个客户
        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);
        customerList.addCustomer(customer3);
        assertEquals(3, customerList.customerLinkedList.size(), "初始大小应该为3");
        
        // 删除中间的客户
        customerList.removeCustomer(2);
        assertEquals(2, customerList.customerLinkedList.size(), "删除后大小应该为2");
        
        // 再添加一个客户
        Customer newCustomer = new Customer("新客户", "女", 28, "13800138000", "new@test.com");
        customerList.addCustomer(newCustomer);
        assertEquals(3, customerList.customerLinkedList.size(), "添加后大小应该为3");
        
        // 验证顺序
        assertEquals(customer1, customerList.getCust(0), "第一个应该是customer1");
        assertEquals(customer3, customerList.getCust(1), "第二个应该是customer3");
        assertEquals(newCustomer, customerList.getCust(2), "第三个应该是newCustomer");
    }

    @Test
    @DisplayName("测试大量客户数据操作")
    public void testLargeNumberOfCustomers() {
        // 添加100个客户
        for (int i = 0; i < 100; i++) {
            Customer customer = new Customer(
                "客户" + i,
                i % 2 == 0 ? "男" : "女",
                20 + i % 60,
                "138" + String.format("%08d", i),
                "customer" + i + "@test.com"
            );
            customerList.addCustomer(customer);
        }
        
        assertEquals(100, customerList.customerLinkedList.size(), "应该有100个客户");
        
        // 验证第一个和最后一个
        assertEquals("客户0", customerList.getCust(0).getName(), "第一个客户名称应该正确");
        assertEquals("客户99", customerList.getCust(99).getName(), "最后一个客户名称应该正确");
        
        // 删除前10个客户
        for (int i = 0; i < 10; i++) {
            customerList.removeCustomer(1); // 总是删除第一个
        }
        
        assertEquals(90, customerList.customerLinkedList.size(), "删除10个后应该剩90个");
        assertEquals("客户10", customerList.getCust(0).getName(), "删除后第一个客户应该是客户10");
    }

    @Test
    @DisplayName("测试边界条件 - 单个客户")
    public void testSingleCustomer() {
        customerList.addCustomer(customer1);
        assertEquals(1, customerList.customerLinkedList.size(), "大小应该为1");
        
        Customer retrieved = customerList.getCust(0);
        assertEquals(customer1, retrieved, "应该能正确获取唯一的客户");
        
        customerList.removeCustomer(1);
        assertTrue(customerList.customerLinkedList.isEmpty(), "删除后应该为空");
    }

    @Test
    @DisplayName("测试客户列表的独立性")
    public void testCustomerListIndependence() {
        // 创建两个CustomerList实例
        CustomerList list1 = new CustomerList();
        CustomerList list2 = new CustomerList();
        
        list1.addCustomer(customer1);
        list2.addCustomer(customer2);
        
        assertEquals(1, list1.customerLinkedList.size(), "list1大小应该为1");
        assertEquals(1, list2.customerLinkedList.size(), "list2大小应该为1");
        
        assertNotEquals(list1.getCust(0), list2.getCust(0), "两个列表的客户应该不同");
    }

    @Test
    @DisplayName("测试客户信息格式化输出")
    public void testCustomerInfoFormatting() {
        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);
        customerList.addCustomer(customer3);
        
        // 捕获输出
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        try {
            customerList.printCustomerInfo();
            String output = outContent.toString();
            
            // 验证输出格式
            assertTrue(output.contains("编号"), "应该包含编号列");
            assertTrue(output.contains("姓名"), "应该包含姓名列");
            assertTrue(output.contains("性别"), "应该包含性别列");
            assertTrue(output.contains("年龄"), "应该包含年龄列");
            assertTrue(output.contains("电话"), "应该包含电话列");
            assertTrue(output.contains("邮箱"), "应该包含邮箱列");
            
            // 验证编号是否正确（1, 2, 3）
            assertTrue(output.contains("1\t佟刚"), "应该包含编号1和对应客户");
            assertTrue(output.contains("2\t封捷"), "应该包含编号2和对应客户");
            assertTrue(output.contains("3\t雷丰阳"), "应该包含编号3和对应客户");
            
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试特殊字符处理")
    public void testSpecialCharacters() {
        Customer specialCustomer = new Customer(
            "张\t三",
            "男",
            25,
            "138-0013-8000",
            "zhang@测试.com"
        );
        
        customerList.addCustomer(specialCustomer);
        Customer retrieved = customerList.getCust(0);
        
        assertEquals("张\t三", retrieved.getName(), "应该正确处理包含制表符的姓名");
        assertEquals("zhang@测试.com", retrieved.getMail(), "应该正确处理包含中文的邮箱");
    }

    @Test
    @DisplayName("测试连续删除操作")
    public void testConsecutiveRemoveOperations() {
        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);
        customerList.addCustomer(customer3);
        
        // 连续删除
        customerList.removeCustomer(2); // 删除第二个
        assertEquals(2, customerList.customerLinkedList.size(), "第一次删除后大小应该为2");
        
        customerList.removeCustomer(1); // 删除第一个
        assertEquals(1, customerList.customerLinkedList.size(), "第二次删除后大小应该为1");
        
        customerList.removeCustomer(1); // 删除最后一个
        assertTrue(customerList.customerLinkedList.isEmpty(), "第三次删除后应该为空");
    }

    @Test
    @DisplayName("测试添加后立即删除")
    public void testAddThenRemoveImmediately() {
        customerList.addCustomer(customer1);
        assertEquals(1, customerList.customerLinkedList.size(), "添加后大小应该为1");
        
        customerList.removeCustomer(1);
        assertTrue(customerList.customerLinkedList.isEmpty(), "删除后应该为空");
        
        // 再次添加
        customerList.addCustomer(customer2);
        assertEquals(1, customerList.customerLinkedList.size(), "再次添加后大小应该为1");
        assertEquals(customer2, customerList.getCust(0), "应该是customer2");
    }

    @Test
    @DisplayName("测试客户对象引用")
    public void testCustomerObjectReference() {
        customerList.addCustomer(customer1);
        
        // 获取客户对象
        Customer retrieved1 = customerList.getCust(0);
        Customer retrieved2 = customerList.getCust(0);
        
        // 验证是同一个对象引用
        assertSame(retrieved1, retrieved2, "多次获取应该返回同一个对象引用");
        
        // 修改一个引用，另一个也应该改变
        retrieved1.setName("修改后的名字");
        assertEquals("修改后的名字", retrieved2.getName(), "修改一个引用应该影响另一个");
    }

    @Test
    @DisplayName("测试空客户信息")
    public void testEmptyCustomerInfo() {
        Customer emptyCustomer = new Customer("", "", 0, "", "");
        customerList.addCustomer(emptyCustomer);
        
        Customer retrieved = customerList.getCust(0);
        assertEquals("", retrieved.getName(), "空姓名应该被正确存储");
        assertEquals("", retrieved.getSex(), "空性别应该被正确存储");
        assertEquals(0, retrieved.getAge(), "0年龄应该被正确存储");
    }
}
