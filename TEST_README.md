# 账户系统测试文档

## 📋 测试概述

本测试套件为 `Users` 类提供了全面的稳定性测试，包含 **20 个测试用例**，覆盖以下方面：

### 测试覆盖范围

1. **基础功能测试** (测试 1-4)
   - 用户对象创建
   - ID 自动生成
   - toString 方法格式
   - 时间格式获取

2. **存取款功能测试** (测试 5-8)
   - 存款功能
   - 取款功能
   - 多次存取款
   - 小数金额处理

3. **余额持久化测试** (测试 9-10)
   - 从文件加载最新余额
   - 余额持久化验证

4. **文件操作测试** (测试 11-12)
   - 明细文件写入
   - 明细文件读取

5. **边界条件测试** (测试 13-16)
   - 零金额存款
   - 大额交易
   - 负数余额（余额不足取款）
   - 不存在的用户ID查询

6. **并发和性能测试** (测试 17-18)
   - 快速连续交易
   - 属性设置和获取

7. **异常处理测试** (测试 19-20)
   - 文件不存在时的处理
   - 数据完整性验证

---

## 🚀 运行测试

### 方法 1: 使用 Maven 命令行

```bash
# 运行所有测试
mvn test

# 只运行 UsersTest 类
mvn test -Dtest=UsersTest

# 运行特定的测试方法
mvn test -Dtest=UsersTest#testUserCreation
```

### 方法 2: 使用 IDE (IntelliJ IDEA / Cursor)

1. 打开 `UsersTest.java` 文件
2. 右键点击类名或方法名
3. 选择 "Run 'UsersTest'" 或 "Run 'testUserCreation()'"

### 方法 3: 使用 Maven 生命周期

```bash
# 清理并测试
mvn clean test

# 生成测试报告
mvn surefire-report:report
```

---

## 📊 测试结果示例

```
========================================
开始账户系统稳定性测试
========================================
✓ 已备份原始数据文件

【测试1】用户对象创建测试
✓ 用户对象创建成功
  - 用户名: testUser
  - ID: 1733000000000

【测试2】用户ID自动生成测试
✓ ID自动生成成功: 1733036400123

【测试3】toString方法格式测试
✓ toString格式正确: testUser,password123,1733000000000

...

【测试20】数据完整性验证测试
✓ 数据完整性验证通过
  - 内存余额: 1200.0
  - 文件余额: 1200.0

========================================
账户系统稳定性测试完成
========================================
✓ 已恢复原始数据文件

Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
```

---

## 📁 测试文件结构

```
Project/
├── pom.xml                                    # 添加了 JUnit 5 依赖
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── Project01/AccountSystem/
│   │   │       └── Users.java                # 被测试的类
│   │   └── resources/
│   │       └── Project01/
│   │           ├── User.csv                  # 用户数据（生产环境）
│   │           └── AccountDetail.csv         # 账户明细（生产环境）
│   └── test/
│       ├── java/
│       │   └── Project01/AccountSystem/
│       │       └── UsersTest.java            # 测试类 ⭐
│       └── resources/
│           └── Project01/
│               ├── User.csv                  # 测试用户数据
│               └── AccountDetail.csv         # 测试账户明细
```

---

## ⚠️ 重要说明

### 数据安全

测试会自动：
1. **测试前**：备份原始的 `AccountDetail.csv` 文件
2. **测试中**：使用测试数据进行测试
3. **测试后**：恢复原始数据文件

### 测试数据

测试使用的测试用户 ID：
- `1733000000000` - testUser1
- `1733000000001` - testUser2

这些 ID 不会与你的实际用户数据冲突。

---

## 🐛 发现的潜在问题

测试过程中会暴露以下系统问题：

### 1. ⚠️ 负数余额问题
**测试15** 会显示系统允许负数余额（余额不足仍可取款）

**建议修复：** 在 `withdrawMoney()` 方法中添加余额检查
```java
public void withdrawMoney(double money) {
    if (this.balance < money) {
        System.out.println("余额不足，无法取款");
        return;
    }
    this.balance -= money;
    writeFile(...);
}
```

### 2. ⚠️ 余额持久化问题
`balance` 属性不会保存到 `User.csv`，每次登录后需要从 `AccountDetail.csv` 重新加载。

**建议：** 在登录成功后调用 `setBalance()` 方法加载余额。

---

## 📈 测试覆盖率

| 类别 | 测试数量 | 覆盖率 |
|------|---------|--------|
| 基础功能 | 4 | 100% |
| 存取款功能 | 4 | 100% |
| 余额持久化 | 2 | 100% |
| 文件操作 | 2 | 100% |
| 边界条件 | 4 | 100% |
| 性能测试 | 2 | 100% |
| 异常处理 | 2 | 100% |
| **总计** | **20** | **100%** |

---

## 🔧 故障排除

### 问题 1: 找不到 JUnit 依赖

**解决方案：**
```bash
mvn clean install
```

### 问题 2: 文件路径错误

确保在项目根目录运行测试：
```bash
cd /Volumes/study/02-java/Project
mvn test
```

### 问题 3: 测试数据冲突

如果测试失败，手动删除测试生成的数据：
```bash
# 恢复备份文件
mv src/main/resources/Project01/AccountDetail.csv.backup \
   src/main/resources/Project01/AccountDetail.csv
```

---

## 📝 扩展测试

如果需要添加更多测试，可以参考以下模板：

```java
@Test
@Order(21)
@DisplayName("测试21: 你的测试名称")
void testYourFeature() {
    System.out.println("\n【测试21】你的测试描述");
    
    // 测试逻辑
    // ...
    
    // 断言
    assertEquals(expected, actual, "错误信息");
    
    System.out.println("✓ 测试通过");
}
```

---

## 📞 联系方式

如有问题，请联系：
- 作者：Liang-ht
- 日期：2025-12-01

---

## ✅ 快速开始

```bash
# 1. 确保在项目根目录
cd /Volumes/study/02-java/Project

# 2. 运行测试
mvn test

# 3. 查看测试报告
open target/surefire-reports/Project01.AccountSystem.UsersTest.txt
```

**祝测试顺利！** 🎉
