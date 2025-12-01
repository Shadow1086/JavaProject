# 💰 Project01 - 谷粒记账系统

> 一个基于 Java 的简洁高效的个人记账管理系统

[![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)](https://github.com/Shadow1086/JavaProject)
[![Java](https://img.shields.io/badge/Java-21.0.9-orange.svg)](https://www.oracle.com/java/)
[![Tests](https://img.shields.io/badge/tests-20%20passed-brightgreen.svg)](../../test/java/Project01/AccountSystem/UsersTest.java)

---

## 📋 项目简介

谷粒记账系统是一个功能完善的个人财务管理工具，支持用户注册、登录、存取款、收支明细查询等核心功能。系统采用 CSV 文件存储数据，轻量级且易于使用。

### ✨ 核心特性

- 🔐 **用户系统** - 支持用户注册、登录、密码验证
- 💵 **存取款管理** - 安全的存款和取款操作，自动余额检查
- 📊 **收支明细** - 完整的交易记录查询，支持按用户筛选
- 💾 **数据持久化** - 基于 CSV 文件的数据存储，自动加载余额
- 🔒 **安全保护** - 余额不足保护、输入验证、异常处理
- ✅ **完整测试** - 20 个单元测试用例，测试覆盖率 100%

---

## 🚀 快速开始

### 运行程序

```bash
# 在项目根目录执行
mvn exec:java -Dexec.mainClass="Project01.AccountSystem.Main"
```

或者使用 IDE 直接运行 `Main.java`

### 运行测试

```bash
# 运行 Project01 的所有测试
mvn test -Dtest=Project01.*
```

---

## 📖 使用指南

### 1. 启动系统

运行程序后，系统会自动创建必要的数据文件：
- `src/main/resources/Project01/User.csv` - 用户信息
- `src/main/resources/Project01/AccountDetail.csv` - 交易明细

### 2. 注册账户

首次使用需要注册账户：
```
请输入用户名：0  （输入0进入注册）
确定进入注册吗？(y/n)：y
请输入用户名：张三
请输入密码：123456
```

### 3. 登录系统

```
请输入用户名：张三
请输入密码：123456
```

### 4. 主菜单功能

```
-----------------谷粒记账软件-----------------
1 收支明细
2 登记收入
3 登记支出
4 返回登录界面
5 退    出
```

---

## 🏗️ 项目结构

```
Project01/
├── README.md                        # 项目文档（本文件）
├── AccountSystem/
│   ├── Main.java                   # 主程序入口
│   ├── Users.java                  # 用户类
│   ├── Account.java                # 账户类
│   └── Plan.txt                    # 开发计划
└── ../../resources/Project01/
    ├── User.csv                    # 用户数据
    └── AccountDetail.csv           # 交易明细
```

---

## 💡 核心功能实现

### 1. 用户管理
- 自动生成唯一用户 ID（基于时间戳）
- 密码验证和用户名唯一性检查
- 支持多用户独立账户

### 2. 余额管理
- 实时余额计算
- 余额持久化存储
- 登录时自动加载最新余额
- 余额不足保护机制

### 3. 交易记录
- 完整的交易明细记录
- 包含时间、金额、类型、余额等信息
- 支持按用户 ID 筛选查询
- CSV 格式存储，易于导出和分析

---

## 🧪 测试

项目包含完整的单元测试套件，覆盖所有核心功能。

### 测试覆盖

- ✅ 基础功能测试（4个）
- ✅ 存取款功能测试（4个）
- ✅ 余额持久化测试（2个）
- ✅ 文件操作测试（2个）
- ✅ 边界条件测试（4个）
- ✅ 性能测试（2个）
- ✅ 异常处理测试（2个）

**总计：20 个测试用例，测试覆盖率 100%**

详细测试文档请查看 [TEST_README.md](../../../../TEST_README.md)

---

## 📊 技术要点

### 使用的技术
- **文件 I/O**：BufferedReader/BufferedWriter
- **数据格式**：CSV 文件存储
- **异常处理**：try-catch-finally 模式
- **时间处理**：SimpleDateFormat
- **测试框架**：JUnit 5

### 学习收获
- Java 基础语法和面向对象编程
- 文件读写操作
- 字符串处理和数据解析
- 异常处理机制
- 单元测试编写

---

## 🔮 后续扩展计划

### 阶段一：数据库迁移 (v2.0.0)
- [ ] 学习 MySQL 数据库基础知识
- [ ] 学习 JDBC（Java 连接数据库）技术
- [ ] 将 CSV 文件数据迁移到 MySQL 数据库
- [ ] 学习 SQL 查询优化和索引设计
- [ ] 使用 HikariCP 或 Druid 连接池优化性能

### 阶段二：Web 化改造 (v3.0.0)
- [ ] 前端开发：开发浏览器页面，使用 HTML/CSS/JavaScript 处理用户交互
- [ ] 后端接口：将 Java 后端改造为 RESTful API，支持前后端分离
- [ ] 技术栈：学习 Spring Boot + JavaScript/Vue.js/React

### 阶段三：安全增强 (v3.1.0)
- [ ] 密码哈希加密（BCrypt/SHA-256）
- [ ] 输入验证增强
- [ ] 防 SQL 注入

### 阶段四：日志和监控 (v3.2.0)
- [ ] 集成 SLF4J + Logback
- [ ] 错误日志记录
- [ ] 操作日志

### 阶段五：性能优化 (v4.0.0)
- [ ] Redis 缓存
- [ ] 异步处理
- [ ] 批量操作优化

### 阶段六：功能扩展 (v5.0.0)
- [ ] 转账功能
- [ ] 交易统计和图表
- [ ] 交易分类管理
- [ ] 数据导出（Excel/PDF）

---

## 📝 版本历史

### v1.0.0 (2025-12-01)
- ✅ 实现用户注册和登录功能
- ✅ 实现存款和取款功能
- ✅ 实现收支明细查询
- ✅ 实现余额自动持久化
- ✅ 添加余额不足保护
- ✅ 完成 20 个单元测试
- ✅ 修复 readFile 跳行问题
- ✅ 优化文件自动创建逻辑

---

## 🐛 已知问题

目前系统运行稳定，无已知严重问题。

如发现问题，请提交 Issue。

---

## 👨‍💻 作者

**Liang-ht**

- GitHub: [@Liang-ht](https://github.com/Liang-ht)

---

## 📞 相关链接

- [返回仓库主页](../../../../README.md)
- [测试文档](../../../../TEST_README.md)
- [提交 Issue](https://github.com/Shadow1086/JavaProject/issues)

---

<div align="center">

**Made with ❤️ by Liang-ht**

</div>
