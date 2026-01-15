package Project02.CustomerManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 客户管理系统集成测试类
 * 测试Customer、Linklist、CustomerList三个组件的协同工作
 */
@DisplayName("客户管理系统集成测试")
public class CustomerManagerIntegrationTest {

    private CustomerList customerList;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    @DisplayName("初始化测试环境")
    public void setUp() {
        customerList = new CustomerList();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("集成测试：完整的客户生命周期管理")
    public void testCompleteCustomerLifecycle() {
        // 1. 添加多个客户
        Customer customer1 = new Customer("张三", "男", 25, "13800138000", "zhangsan@example.com");
        Customer customer2 = new Customer("李四", "女", 30, "13900139000", "lisi@example.com");
        Customer customer3 = new Customer("王五", "男", 28, "13700137000", "wangwu@example.com");

        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);
        customerList.addCustomer(customer3);

        // 验证添加成功
        assertEquals(customer1, customerList.getCust(0));
        assertEquals(customer2, customerList.getCust(1));
        assertEquals(customer3, customerList.getCust(2));

        // 2. 修改客户信息
        Customer modifiedCustomer = customerList.getCust(1);
        modifiedCustomer.setAge(31);
        modifiedCustomer.setMail("lisi_new@example.com");

        // 验证修改成功
        assertEquals(31, customerList.getCust(1).getAge());
        assertEquals("lisi_new@example.com", customerList.getCust(1).getMail());

        // 3. 删除客户
        customerList.removeCustomer(1);

        // 验证删除后的状态
        assertEquals(customer1, customerList.getCust(0));
        assertEquals(customer3, customerList.getCust(1));

        // 4. 打印客户列表
        customerList.printCustomerInfo();
        String output = outputStream.toString();
        assertTrue(output.contains("张三"));
        assertTrue(output.contains("王五"));
        assertFalse(output.contains("李四")); // 已删除
    }

    @Test
    @DisplayName("集成测试：大量客户数据的增删改查")
    public void testLargeScaleOperations() {
        // 添加100个客户
        for (int i = 0; i < 100; i++) {
            Customer customer = new Customer(
                "客户" + i,
                i % 2 == 0 ? "男" : "女",
                20 + (i % 50),
                "138" + String.format("%08d", i),
                "customer" + i + "@example.com"
            );
            customerList.addCustomer(customer);
        }

        // 验证所有客户都添加成功
        for (int i = 0; i < 100; i++) {
            Customer customer = customerList.getCust(i);
            assertNotNull(customer);
            assertEquals("客户" + i, customer.getName());
        }

        // 修改部分客户信息
        for (int i = 0; i < 10; i++) {
            Customer customer = customerList.getCust(i);
            customer.setAge(customer.getAge() + 1);
        }

        // 验证修改成功
        for (int i = 0; i < 10; i++) {
            Customer customer = customerList.getCust(i);
            assertEquals(21 + (i % 50), customer.getAge());
        }

        // 删除部分客户（从后往前删除，避免索引变化）
        for (int i = 99; i >= 90; i--) {
            customerList.removeCustomer(i);
        }

        // 验证删除后剩余90个客户
        assertNotNull(customerList.getCust(89));
        assertThrows(IndexOutOfBoundsException.class, () -> customerList.getCust(90));
    }

    @Test
    @DisplayName("集成测试：边界条件处理")
    public void testBoundaryConditions() {
        // 空列表操作
        assertThrows(IndexOutOfBoundsException.class, () -> customerList.getCust(0));
        assertThrows(IndexOutOfBoundsException.class, () -> customerList.removeCustomer(0));

        // 添加单个客户
        Customer customer = new Customer("测试", "男", 25, "13800138000", "test@example.com");
        customerList.addCustomer(customer);

        // 验证单个客户
        assertEquals(customer, customerList.getCust(0));

        // 删除唯一的客户
        customerList.removeCustomer(0);

        // 验证列表为空
        assertThrows(IndexOutOfBoundsException.class, () -> customerList.getCust(0));
    }

    @Test
    @DisplayName("集成测试：特殊字符和中文处理")
    public void testSpecialCharactersAndChinese() {
        // 添加包含特殊字符的客户
        Customer customer1 = new Customer("张三·李四", "男", 25, "138-0013-8000", "zhang.san@example.com");
        Customer customer2 = new Customer("王五（经理）", "女", 30, "139 0013 9000", "wang_wu@example.com");
        Customer customer3 = new Customer("赵六&孙七", "男", 28, "+86-13700137000", "zhao.liu+sun.qi@example.com");

        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);
        customerList.addCustomer(customer3);

        // 验证特殊字符保存正确
        assertEquals("张三·李四", customerList.getCust(0).getName());
        assertEquals("王五（经理）", customerList.getCust(1).getName());
        assertEquals("赵六&孙七", customerList.getCust(2).getName());

        // 验证打印输出包含特殊字符
        customerList.printCustomerInfo();
        String output = outputStream.toString();
        assertTrue(output.contains("张三·李四"));
        assertTrue(output.contains("王五（经理）"));
        assertTrue(output.contains("赵六&孙七"));
    }

    @Test
    @DisplayName("集成测试：数据独立性验证")
    public void testDataIndependence() {
        // 创建客户对象
        Customer originalCustomer = new Customer("张三", "男", 25, "13800138000", "zhangsan@example.com");
        customerList.addCustomer(originalCustomer);

        // 修改原始对象
        originalCustomer.setName("李四");
        originalCustomer.setAge(30);

        // 验证列表中的对象也被修改（因为是引用传递）
        Customer storedCustomer = customerList.getCust(0);
        assertEquals("李四", storedCustomer.getName());
        assertEquals(30, storedCustomer.getAge());

        // 验证是同一个对象
        assertSame(originalCustomer, storedCustomer);
    }

    @Test
    @DisplayName("集成测试：混合操作序列")
    public void testMixedOperationSequence() {
        // 添加3个客户
        customerList.addCustomer(new Customer("客户1", "男", 25, "13800138001", "c1@example.com"));
        customerList.addCustomer(new Customer("客户2", "女", 30, "13800138002", "c2@example.com"));
        customerList.addCustomer(new Customer("客户3", "男", 28, "13800138003", "c3@example.com"));

        // 删除中间的客户
        customerList.removeCustomer(1);

        // 再添加2个客户
        customerList.addCustomer(new Customer("客户4", "女", 26, "13800138004", "c4@example.com"));
        customerList.addCustomer(new Customer("客户5", "男", 32, "13800138005", "c5@example.com"));

        // 验证当前状态：客户1、客户3、客户4、客户5
        assertEquals("客户1", customerList.getCust(0).getName());
        assertEquals("客户3", customerList.getCust(1).getName());
        assertEquals("客户4", customerList.getCust(2).getName());
        assertEquals("客户5", customerList.getCust(3).getName());

        // 修改第一个客户
        customerList.getCust(0).setAge(26);
        assertEquals(26, customerList.getCust(0).getAge());

        // 删除最后一个客户
        customerList.removeCustomer(3);

        // 验证最终状态：客户1、客户3、客户4
        assertEquals("客户1", customerList.getCust(0).getName());
        assertEquals("客户3", customerList.getCust(1).getName());
        assertEquals("客户4", customerList.getCust(2).getName());
        assertThrows(IndexOutOfBoundsException.class, () -> customerList.getCust(3));
    }

    @Test
    @DisplayName("集成测试：打印功能完整性")
    public void testPrintFunctionality() {
        // 添加多个客户
        customerList.addCustomer(new Customer("张三", "男", 25, "13800138000", "zhangsan@example.com"));
        customerList.addCustomer(new Customer("李四", "女", 30, "13900139000", "lisi@example.com"));
        customerList.addCustomer(new Customer("王五", "男", 28, "13700137000", "wangwu@example.com"));

        // 打印客户列表
        customerList.printCustomerInfo();
        String output = outputStream.toString();

        // 验证输出包含所有客户信息
        assertTrue(output.contains("张三"));
        assertTrue(output.contains("男"));
        assertTrue(output.contains("25"));
        assertTrue(output.contains("13800138000"));
        assertTrue(output.contains("zhangsan@example.com"));

        assertTrue(output.contains("李四"));
        assertTrue(output.contains("女"));
        assertTrue(output.contains("30"));
        assertTrue(output.contains("13900139000"));
        assertTrue(output.contains("lisi@example.com"));

        assertTrue(output.contains("王五"));
        assertTrue(output.contains("28"));
        assertTrue(output.contains("13700137000"));
        assertTrue(output.contains("wangwu@example.com"));
    }

    @Test
    @DisplayName("集成测试：空值和null处理")
    public void testNullAndEmptyHandling() {
        // 添加包含空字符串的客户
        Customer customer1 = new Customer("", "男", 25, "", "");
        customerList.addCustomer(customer1);

        // 验证空字符串保存正确
        assertEquals("", customerList.getCust(0).getName());
        assertEquals("", customerList.getCust(0).getTele());
        assertEquals("", customerList.getCust(0).getMail());

        // 添加包含null的客户
        Customer customer2 = new Customer(null, null, 30, null, null);
        customerList.addCustomer(customer2);

        // 验证null值保存正确
        assertNull(customerList.getCust(1).getName());
        assertNull(customerList.getCust(1).getSex());
        assertNull(customerList.getCust(1).getTele());
        assertNull(customerList.getCust(1).getMail());
    }

    @Test
    @DisplayName("集成测试：年龄边界值")
    public void testAgeBoundaryValues() {
        // 测试各种年龄值
        customerList.addCustomer(new Customer("婴儿", "男", 0, "13800138000", "baby@example.com"));
        customerList.addCustomer(new Customer("青年", "女", 18, "13800138001", "youth@example.com"));
        customerList.addCustomer(new Customer("中年", "男", 50, "13800138002", "middle@example.com"));
        customerList.addCustomer(new Customer("老年", "女", 100, "13800138003", "old@example.com"));
        customerList.addCustomer(new Customer("超龄", "男", 150, "13800138004", "super@example.com"));

        // 验证所有年龄值都正确保存
        assertEquals(0, customerList.getCust(0).getAge());
        assertEquals(18, customerList.getCust(1).getAge());
        assertEquals(50, customerList.getCust(2).getAge());
        assertEquals(100, customerList.getCust(3).getAge());
        assertEquals(150, customerList.getCust(4).getAge());
    }

    @Test
    @DisplayName("集成测试：连续删除操作")
    public void testConsecutiveDeleteOperations() {
        // 添加10个客户
        for (int i = 0; i < 10; i++) {
            customerList.addCustomer(new Customer("客户" + i, "男", 20 + i, "138" + i, "c" + i + "@example.com"));
        }

        // 连续删除前5个客户
        for (int i = 0; i < 5; i++) {
            customerList.removeCustomer(0); // 总是删除第一个
        }

        // 验证剩余5个客户
        assertEquals("客户5", customerList.getCust(0).getName());
        assertEquals("客户6", customerList.getCust(1).getName());
        assertEquals("客户7", customerList.getCust(2).getName());
        assertEquals("客户8", customerList.getCust(3).getName());
        assertEquals("客户9", customerList.getCust(4).getName());
        assertThrows(IndexOutOfBoundsException.class, () -> customerList.getCust(5));
    }

    @Test
    @DisplayName("集成测试：性能测试 - 1000个客户")
    public void testPerformanceWith1000Customers() {
        long startTime = System.currentTimeMillis();

        // 添加1000个客户
        for (int i = 0; i < 1000; i++) {
            customerList.addCustomer(new Customer(
                "客户" + i,
                i % 2 == 0 ? "男" : "女",
                20 + (i % 80),
                "138" + String.format("%08d", i),
                "customer" + i + "@example.com"
            ));
        }

        long addTime = System.currentTimeMillis() - startTime;

        // 验证所有客户
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Customer customer = customerList.getCust(i);
            assertNotNull(customer);
            assertEquals("客户" + i, customer.getName());
        }
        long getTime = System.currentTimeMillis() - startTime;

        // 删除所有客户
        startTime = System.currentTimeMillis();
        for (int i = 999; i >= 0; i--) {
            customerList.removeCustomer(i);
        }
        long deleteTime = System.currentTimeMillis() - startTime;

        // 验证列表为空
        assertThrows(IndexOutOfBoundsException.class, () -> customerList.getCust(0));

        // 输出性能数据（可选）
        System.setOut(originalOut);
        System.out.println("添加1000个客户耗时: " + addTime + "ms");
        System.out.println("查询1000个客户耗时: " + getTime + "ms");
        System.out.println("删除1000个客户耗时: " + deleteTime + "ms");
    }

    @Test
    @DisplayName("集成测试：底层链表与上层管理类的协同")
    public void testLinklistAndCustomerListIntegration() {
        // 通过CustomerList添加客户
        Customer customer1 = new Customer("张三", "男", 25, "13800138000", "zhangsan@example.com");
        Customer customer2 = new Customer("李四", "女", 30, "13900139000", "lisi@example.com");

        customerList.addCustomer(customer1);
        customerList.addCustomer(customer2);

        // 验证底层链表正确存储了数据
        assertEquals(customer1, customerList.getCust(0));
        assertEquals(customer2, customerList.getCust(1));

        // 修改客户信息
        customer1.setAge(26);

        // 验证修改反映到链表中
        assertEquals(26, customerList.getCust(0).getAge());

        // 删除客户
        customerList.removeCustomer(0);

        // 验证链表正确删除了节点
        assertEquals(customer2, customerList.getCust(0));
        assertThrows(IndexOutOfBoundsException.class, () -> customerList.getCust(1));
    }
}
