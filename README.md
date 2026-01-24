# 🎓 Java 学习项目集合

> 记录 Java 学习过程中的各个实战项目 - 多模块 Maven 项目

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue.svg)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

---

## 📋 项目简介

这是一个 Java 学习项目的集合仓库，采用 Maven 多模块架构管理。每个子模块都是独立的学习项目，涵盖不同的技术栈和应用场景。通过这些项目，逐步掌握 Java 企业级开发的各个方面，从基础语法到数据库操作，从数据结构到 Web 开发。

### 🎯 学习目标

- 掌握 Java 核心语法和面向对象编程
- 熟悉企业级开发常用框架和工具
- 理解软件架构设计和最佳实践
- 积累实战项目经验

---

## 📚 项目列表

### [Project01 - 谷粒记账系统](project01-account/) ✅ 已完成

> 基于 Java 和 MySQL 的企业级个人记账管理系统

**版本**：v2.0.0
**架构**：三层架构（Mapper + Service + Model）
**技术栈**：MySQL + MyBatis + HikariCP + SLF4J/Logback

**核心功能：**
- 🔐 用户注册和登录系统
- 💵 存款和取款管理
- 📊 收支明细查询
- 💾 MySQL 数据库持久化
- 🔒 余额不足保护
- ⚡ HikariCP 连接池优化
- 📝 完善的日志记录

**技术亮点：**
- 企业级分层架构设计
- MyBatis ORM 框架应用
- HikariCP 高性能连接池
- 统一日志管理（SLF4J + Logback）
- 环境变量安全管理（Dotenv）

[📖 查看详细文档](project01-account/README.md)

---

### [Project02 - 客户管理系统](project02-customer/) ✅ 已完成

> 基于链表实现的客户信息管理系统

**版本**：v1.0.0
**架构**：MVC 架构
**技术栈**：Java 核心 + 自定义链表数据结构

**核心功能：**
- 👥 客户信息增删改查
- 🔗 基于链表的数据存储
- 📋 客户列表展示
- 🔍 客户信息查询

**技术亮点：**
- 自定义链表数据结构实现
- MVC 架构模式应用
- 面向对象设计实践

[📖 查看详细文档](project02-customer/)

---

### Project03 - 待开发 🚧

> 规划中...

**状态**：🚧 待开发

---

## 🏗️ 项目结构

```
Project/
├── pom.xml                              # Maven 父项目配置
├── README.md                            # 项目总览文档（本文件）
├── .gitignore                           # Git 忽略文件
├── .env                                 # 环境变量配置（需自行创建）
│
├── project01-account/                   # 子模块1：谷粒记账系统
│   ├── pom.xml                         # 模块配置
│   ├── README.md                       # 模块文档
│   └── src/
│       ├── main/
│       │   ├── java/                   # Java 源代码
│       │   └── resources/              # 配置文件和资源
│       └── test/                       # 测试代码
│
├── project02-customer/                  # 子模块2：客户管理系统
│   ├── pom.xml                         # 模块配置
│   └── src/
│       ├── main/
│       │   └── java/                   # Java 源代码
│       └── test/                       # 测试代码
│
└── project03/                           # 子模块3：待开发
    ├── pom.xml                         # 模块配置
    └── src/
```

### 多模块架构优势

- **统一依赖管理**：父 POM 统一管理所有依赖版本
- **独立开发部署**：各模块可独立编译、测试、运行
- **代码复用**：共享通用工具类和配置
- **清晰的项目边界**：每个模块职责明确

---

## 🚀 快速开始

### 环境要求

- **Java**：JDK 21 或更高版本
- **Maven**：3.6 或更高版本
- **MySQL**：8.0 或更高版本（仅 project01 需要）
- **操作系统**：Windows / macOS / Linux

### 克隆项目

```bash
git clone https://github.com/your-username/Project.git
cd Project
```

### 编译所有模块

```bash
# 清理并编译所有子模块
mvn clean compile

# 或者只编译特定模块
mvn clean compile -pl project01-account
```

### 运行项目

#### 运行 Project01（谷粒记账系统）

```bash
# 1. 配置数据库（参考 project01-account/README.md）
# 2. 创建 .env 文件配置数据库连接
# 3. 运行项目
cd project01-account
mvn exec:java
```

#### 运行 Project02（客户管理系统）

```bash
cd project02-customer
mvn exec:java
```

### 运行测试

```bash
# 运行所有模块的测试
mvn test

# 运行特定模块的测试
mvn test -pl project01-account
mvn test -pl project02-customer
```

---

## 🛠️ 技术栈

### 核心技术

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 21 | 编程语言 |
| Maven | 3.9+ | 项目构建工具 |
| MySQL | 8.4.0 | 关系型数据库 |
| MyBatis | 3.5.19 | ORM 持久化框架 |
| HikariCP | 5.1.0 | 高性能数据库连接池 |

### 辅助工具

| 技术 | 版本 | 说明 |
|------|------|------|
| SLF4J | 2.0.17 | 日志门面 |
| Logback | 1.5.21 | 日志实现 |
| Dotenv | 3.0.0 | 环境变量管理 |
| JUnit 5 | 5.10.1 | 单元测试框架 |

### 依赖管理

项目采用 Maven 父 POM 统一管理所有依赖版本，确保各子模块使用一致的依赖版本，避免版本冲突。

---

## 📝 开发规范

### Git 提交信息格式

```
<type>(<scope>): <subject>
```

**类型（type）：**
- ✨ `feat`: 新功能
- 🐛 `fix`: 修复 bug
- 📝 `docs`: 文档更新
- 💄 `style`: 代码格式调整
- ♻️ `refactor`: 代码重构
- ⚡️ `perf`: 性能优化
- ✅ `test`: 测试相关
- 🔧 `chore`: 构建/工具链相关

**范围（scope）：**
- `project01`: 项目1相关
- `project02`: 项目2相关
- `project03`: 项目3相关
- `all`: 影响所有项目

**示例：**
```bash
git commit -m "✨ feat(project01): 添加用户登录功能"
git commit -m "🐛 fix(project02): 修复链表删除节点错误"
git commit -m "📝 docs(all): 更新 README 文档"
```

---

## 📊 项目统计

| 项目 | 状态 | 版本 | 主要技术 |
|------|------|------|----------|
| project01-account | ✅ 已完成 | v2.0.0 | MySQL + MyBatis + HikariCP |
| project02-customer | ✅ 已完成 | v1.0.0 | Java 核心 + 自定义链表 |
| project03 | 🚧 待开发 | - | 规划中 |

---

## 👨‍💻 作者

**Liang-ht**

- GitHub: [@Liang-ht](https://github.com/Liang-ht)

---

## 📄 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。

---

## 🤝 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m '✨ feat(project01): 添加某个功能'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

---

<div align="center">

**⭐ 如果这个项目对你有帮助，请给一个 Star！⭐**

Made with ❤️ by Liang-ht

</div>
