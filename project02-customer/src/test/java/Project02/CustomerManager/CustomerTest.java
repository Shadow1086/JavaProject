package Project02.CustomerManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ClassName: CustomerTest
 * Description: Customer实体类的单元测试
 * 
 * {@code @Author} Liang-ht
 * {@code @Create} 2025-12-04 00:56:00
 */
@DisplayName("Customer实体类测试")
public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        // 在每个测试方法执行前初始化测试数据
        customer = new Customer("张三", "男", 25, "13800138000", "zhangsan@example.com");
    }

    @Test
    @DisplayName("测试Customer构造函数")
    public void testConstructor() {
        assertNotNull(customer, "Customer对象不应为null");
        assertEquals("张三", customer.getName(), "姓名应该正确初始化");
        assertEquals("男", customer.getSex(), "性别应该正确初始化");
        assertEquals(25, customer.getAge(), "年龄应该正确初始化");
        assertEquals("13800138000", customer.getTele(), "电话应该正确初始化");
        assertEquals("zhangsan@example.com", customer.getMail(), "邮箱应该正确初始化");
    }

    @Test
    @DisplayName("测试getName和setName方法")
    public void testNameGetterAndSetter() {
        customer.setName("李四");
        assertEquals("李四", customer.getName(), "姓名应该被正确设置");
        
        // 测试空字符串
        customer.setName("");
        assertEquals("", customer.getName(), "应该允许设置空字符串");
        
        // 测试null
        customer.setName(null);
        assertNull(customer.getName(), "应该允许设置null");
    }

    @Test
    @DisplayName("测试getSex和setSex方法")
    public void testSexGetterAndSetter() {
        assertTrue(customer.setSex("女"), "设置性别应该返回true");
        assertEquals("女", customer.getSex(), "性别应该被正确设置");
        
        // 测试其他值
        assertTrue(customer.setSex("保密"), "应该允许设置其他性别值");
        assertEquals("保密", customer.getSex(), "性别应该被正确设置");
    }

    @Test
    @DisplayName("测试getAge和setAge方法")
    public void testAgeGetterAndSetter() {
        assertTrue(customer.setAge(30), "设置年龄应该返回true");
        assertEquals(30, customer.getAge(), "年龄应该被正确设置");
        
        // 测试边界值
        assertTrue(customer.setAge(1), "应该允许设置最小年龄");
        assertEquals(1, customer.getAge(), "年龄应该为1");
        
        assertTrue(customer.setAge(120), "应该允许设置最大年龄");
        assertEquals(120, customer.getAge(), "年龄应该为120");
        
        // 测试负数（根据当前实现，会被接受）
        assertTrue(customer.setAge(-1), "根据当前实现，负数年龄会被接受");
        assertEquals(-1, customer.getAge(), "年龄应该为-1");
        
        // 测试超大值
        assertTrue(customer.setAge(200), "根据当前实现，超大年龄会被接受");
        assertEquals(200, customer.getAge(), "年龄应该为200");
    }

    @Test
    @DisplayName("测试getTele和setTele方法")
    public void testTeleGetterAndSetter() {
        customer.setTele("13900139000");
        assertEquals("13900139000", customer.getTele(), "电话应该被正确设置");
        
        // 测试不同格式的电话
        customer.setTele("010-12345678");
        assertEquals("010-12345678", customer.getTele(), "应该支持座机格式");
        
        customer.setTele("");
        assertEquals("", customer.getTele(), "应该允许设置空字符串");
    }

    @Test
    @DisplayName("测试getMail和setMail方法")
    public void testMailGetterAndSetter() {
        customer.setMail("newemail@example.com");
        assertEquals("newemail@example.com", customer.getMail(), "邮箱应该被正确设置");
        
        // 测试不同格式的邮箱
        customer.setMail("user.name+tag@example.co.uk");
        assertEquals("user.name+tag@example.co.uk", customer.getMail(), "应该支持复杂邮箱格式");
        
        customer.setMail("");
        assertEquals("", customer.getMail(), "应该允许设置空字符串");
    }

    @Test
    @DisplayName("测试toString方法")
    public void testToString() {
        String expected = "张三\t男\t25\t13800138000\t\tzhangsan@example.com";
        assertEquals(expected, customer.toString(), "toString应该返回正确格式的字符串");
        
        // 测试包含特殊字符的情况
        Customer specialCustomer = new Customer("李\t四", "女", 30, "139-0013-9000", "li@test.com");
        String result = specialCustomer.toString();
        assertNotNull(result, "toString不应返回null");
        assertTrue(result.contains("李\t四"), "toString应该包含姓名");
    }

    @Test
    @DisplayName("测试Customer对象的完整性")
    public void testCustomerIntegrity() {
        // 创建多个Customer对象，确保它们是独立的
        Customer customer1 = new Customer("王五", "男", 28, "13700137000", "wangwu@test.com");
        Customer customer2 = new Customer("赵六", "女", 32, "13600136000", "zhaoliu@test.com");
        
        assertNotEquals(customer1.getName(), customer2.getName(), "不同对象的姓名应该不同");
        assertNotEquals(customer1.getAge(), customer2.getAge(), "不同对象的年龄应该不同");
        
        // 修改一个对象不应影响另一个对象
        customer1.setName("修改后的王五");
        assertNotEquals(customer1.getName(), customer2.getName(), "修改一个对象不应影响另一个对象");
    }

    @Test
    @DisplayName("测试极端情况 - 所有字段为null或空")
    public void testExtremeCase() {
        Customer emptyCustomer = new Customer(null, null, 0, null, null);
        assertNull(emptyCustomer.getName(), "姓名可以为null");
        assertNull(emptyCustomer.getSex(), "性别可以为null");
        assertEquals(0, emptyCustomer.getAge(), "年龄可以为0");
        assertNull(emptyCustomer.getTele(), "电话可以为null");
        assertNull(emptyCustomer.getMail(), "邮箱可以为null");
    }

    @Test
    @DisplayName("测试中文字符处理")
    public void testChineseCharacters() {
        Customer chineseCustomer = new Customer(
            "欧阳修", 
            "男", 
            45, 
            "010-56253825", 
            "ouyang@古代.中国"
        );
        
        assertEquals("欧阳修", chineseCustomer.getName(), "应该正确处理中文姓名");
        assertEquals("古代", chineseCustomer.getMail().split("@")[1].split("\\.")[0], 
                     "应该正确处理中文邮箱");
    }
}
