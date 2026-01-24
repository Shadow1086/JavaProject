# 💰 Project01 - 谷粒记账系统

> 基于 Java 和 MySQL 的企业级个人记账管理系统

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![MyBatis](https://img.shields.io/badge/MyBatis-3.5+-red.svg)](https://mybatis.org/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

---

## 📋 项目简介

谷粒记账系统是一个功能完善的个人财务管理工具，采用企业级技术架构，支持用户注册、登录、存取款、收支明细查询等核心功能。系统使用 MySQL 数据库持久化存储，MyBatis 作为 ORM 框架，HikariCP 连接池优化性能，是学习 Java 企业级开发的优秀实践项目。

### ✨ 核心特性

- 🔐 **用户系统** - 支持用户注册、登录、密码验证
- 💵 **存取款管理** - 安全的存款和取款操作，自动余额检查
- 📊 **收支明细** - 完整的交易记录查询，支持按用户筛选
- 💾 **数据持久化** - 基于 MySQL 数据库的可靠存储
- 🔒 **安全保护** - 余额不足保护、输入验证、异常处理
- 📝 **日志记录** - 使用 SLF4J + Logback 记录系统运行日志
- ⚡ **高性能** - HikariCP 连接池优化数据库访问性能

---

## 🛠️ 技术栈

### 核心技术
- **Java 21** - 编程语言
- **MySQL 8.0+** - 关系型数据库
- **MyBatis 3.5+** - ORM 持久化框架
- **HikariCP** - 高性能数据库连接池

### 辅助工具
- **SLF4J + Logback** - 日志框架
- **Dotenv** - 环境变量管理
- **JUnit 5** - 单元测试框架
- **Maven** - 项目构建工具

---

## 🚀 快速开始

### 环境要求

- JDK 21 或更高版本
- MySQL 8.0 或更高版本
- Maven 3.6 或更高版本

### 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE ledger_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 执行初始化脚本：
```bash
mysql -u your_username -p ledger_system < src/main/resources/InitTables.sql
```

3. 配置环境变量（在项目根目录创建 `.env` 文件）：
```properties
DB_URL=jdbc:mysql://localhost:3306/ledger_system
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

### 运行程序

```bash
# 在项目根目录执行
mvn clean compile
mvn exec:java
```

或者使用 IDE（IntelliJ IDEA / Eclipse）直接运行 `Main.java`

### 运行测试

```bash
# 运行所有测试
mvn test
```

---

## 📖 使用示例

### 1. 启动系统

运行程序后，系统会显示登录界面：

```
-----------------登录-------------
提示：若无账户请在输入用户名时输入0进入注册页面
请输入用户名：
```

### 2. 注册新用户

首次使用需要注册账户：

```
请输入用户名：0
确定进入注册吗？(y/n)：y
请输入用户名：张三
请输入密码：123456
注册成功，返回至登录界面重新登录
```

### 3. 登录系统

使用注册的账户登录：

```
请输入用户名：张三
请输入密码：123456
登陆成功
```

### 4. 主菜单功能

登录成功后，可以使用以下功能：

```
-----------------谷粒记账软件-----------------
1 收支明细
2 登记收入
3 登记支出
4 返回登录界面
5 退    出
请选择(1-5)：
```

**功能说明：**
- **收支明细**：查看当前用户的所有交易记录
- **登记收入**：存款操作，增加账户余额
- **登记支出**：取款操作，减少账户余额（需余额充足）
- **返回登录界面**：切换用户
- **退出**：关闭系统

---

## 🏗️ 项目结构

```
project01-account/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/project01/ledgerSystem/
│   │   │       ├── Main.java                    # 主程序入口
│   │   │       ├── dto/
│   │   │       │   └── Detail.java              # 交易明细数据传输对象
│   │   │       ├── mapper/
│   │   │       │   └── UserMapper.java          # MyBatis 用户数据访问接口
│   │   │       ├── models/
│   │   │       │   └── User.java                # 用户实体类
│   │   │       ├── service/
│   │   │       │   ├── UserService.java         # 用户服务接口
│   │   │       │   └── UserServiceImpl.java     # 用户服务实现类
│   │   │       └── util/
│   │   │           ├── DBUtil.java              # 数据库工具类
│   │   │           └── MyBatisUtil.java         # MyBatis 工具类
│   │   └── resources/
│   │       ├── InitTables.sql                   # 数据库初始化脚本
│   │       ├── mybatis-config.xml               # MyBatis 配置文件
│   │       ├── logback.xml                      # 日志配置文件
│   │       ├── hikaricp.properties              # 连接池配置文件
│   │       └── com/project01/ledgerSystem/mapper/
│   │           └── UserMapper.xml               # MyBatis SQL 映射文件
│   └── test/
│       ├── java/                                # 单元测试代码
│       └── resources/                           # 测试资源文件
├── pom.xml                                      # Maven 项目配置
└── README.md                                    # 项目文档（本文件）
```

---

## 💾 数据库设计

### 用户信息表 (user_info)

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| user_id | INT | 用户ID | 主键，自增 |
| user_name | VARCHAR(20) | 用户名 | 非空 |
| user_password | VARCHAR(20) | 账户密码 | 非空，唯一 |
| user_balance | DOUBLE | 用户余额 | 默认0 |

### 交易记录表 (transaction_records)

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| detail_id | INT | 交易ID | 主键，自增 |
| user_id | INT | 用户ID | 外键关联 |
| transaction_type | CHAR(10) | 收支类型 | 非空 |
| amount | DECIMAL(10,2) | 收支金额 | 非空 |
| transaction_time | TIMESTAMP | 操作时间 | 默认当前时间 |

---

## 💡 技术亮点

### 1. 企业级架构设计
- **分层架构**：采用经典的三层架构（表示层、业务层、数据访问层）
- **面向接口编程**：Service 层使用接口定义，便于扩展和测试
- **DTO 模式**：使用数据传输对象封装业务数据

### 2. 数据库优化
- **连接池管理**：使用 HikariCP 高性能连接池，提升数据库访问效率
- **ORM 框架**：MyBatis 实现对象关系映射，简化 SQL 操作
- **事务管理**：保证数据一致性和完整性

### 3. 日志与监控
- **统一日志**：SLF4J + Logback 实现日志记录
- **分级日志**：支持 DEBUG、INFO、WARN、ERROR 等多级别日志
- **日志持久化**：日志文件自动归档和管理

### 4. 安全性设计
- **环境变量管理**：使用 Dotenv 管理敏感配置，避免硬编码
- **输入验证**：对用户输入进行严格验证
- **异常处理**：完善的异常捕获和处理机制

---

## 📝 开发说明

### 学习路径

本项目适合以下学习阶段：
- ✅ **阶段一**：Java 基础语法和面向对象编程
- ✅ **阶段二**：JDBC 和数据库操作
- ✅ **阶段三**：MyBatis ORM 框架
- ✅ **阶段四**：连接池和性能优化
- ✅ **阶段五**：日志框架和异常处理

### 后续扩展方向

- 🔜 **Web 化改造**：使用 Spring Boot 开发 RESTful API
- 🔜 **前端开发**：使用 Vue.js 或 React 开发 Web 界面
- 🔜 **安全增强**：密码加密、JWT 认证、权限管理
- 🔜 **功能扩展**：转账、统计图表、数据导出等

---

## 👨‍💻 作者

**Liang-ht**

- GitHub: [@Liang-ht](https://github.com/Liang-ht)

---

## 📄 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。

---

<div align="center">

**Made with ❤️ by Liang-ht**

如果这个项目对你有帮助，欢迎 ⭐ Star 支持！

</div>

