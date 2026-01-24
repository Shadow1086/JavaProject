package com.project01.ledgerSystem.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyBatis 工具类
 * 负责初始化 SqlSessionFactory 并复用 HikariCP 数据源
 */
public class MyBatisUtil {

    private static final SqlSessionFactory sqlSessionFactory;
    private static final Logger logger  = LoggerFactory.getLogger(MyBatisUtil.class);
    static {
        try {
            // 1. 加载 MyBatis 配置文件
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);

            // 2. 构建基础配置（不包含数据源）
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
            Configuration configuration = factory.getConfiguration();

            // 3. 手动注入 HikariCP 数据源
            // 创建环境对象，使用 DBUtil 中已初始化的 HikariCP 数据源
            Environment environment = new Environment(
                "development",                          // 环境ID
                new JdbcTransactionFactory(),           // 事务管理器
                DBUtil.dataSource                       // 复用 HikariCP 数据源
            );

            // 4. 将环境设置到配置中
            configuration.setEnvironment(environment);

            // 5. 重新构建 SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

            logger.info("MyBatis 初始化成功，已复用 HikariCP 数据源");

        } catch (IOException e) {
            throw new RuntimeException("MyBatis 配置文件加载失败", e);
        }
    }

    /**
     * 获取 SqlSession（自动提交模式）
     * @return SqlSession 对象
     */
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);
    }

    /**
     * 获取 SqlSession（手动提交模式）
     * @param autoCommit 是否自动提交
     * @return SqlSession 对象
     */
    public static SqlSession getSqlSession(boolean autoCommit) {
        return sqlSessionFactory.openSession(autoCommit);
    }

    /**
     * 获取 Mapper 接口实例
     * @param mapperClass Mapper 接口类
     * @param <T> Mapper 类型
     * @return Mapper 实例
     */
    public static <T> T getMapper(Class<T> mapperClass) {
        SqlSession session = getSqlSession();
        return session.getMapper(mapperClass);
    }
}
