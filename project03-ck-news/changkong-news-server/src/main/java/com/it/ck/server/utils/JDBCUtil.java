package com.it.ck.server.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.Getter;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Package: com.it.ck.server.utils
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 18:17
 */

public class JDBCUtil {
	private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();
	@Getter
	private static DataSource dataSource;

	static {
		Properties properties = loadDataSourceProperties();
		try {
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *  获取配置文件
	 *      如果是本地，就是db-local.properties
	 *      如果是服务器，就是db.properties
	 *
	 * @return {@link Properties }
	 */
	private static Properties loadDataSourceProperties() {
		String env = System.getProperty("ck.env", "prod").trim().toLowerCase();
		String fileName = "local".equals(env) ? "db-local.properties" : "db.properties";

		Properties properties = new Properties();
		try (InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream(fileName)) {
			if (inputStream == null) {
				throw new RuntimeException("未找到配置文件：" + fileName);
			}
			properties.load(inputStream);
			return properties;
		} catch (IOException e) {
			throw new RuntimeException("读取配置文件失败：" + fileName, e);
		}
	}

	/**
	 *  获取数据库连接
	 *
	 * @return {@link Connection }
	 */
	public static Connection getConnection() {
		Connection connection = THREAD_LOCAL.get();
		if (connection == null) {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				throw new RuntimeException("获取数据库连接失败：", e);
			}
			THREAD_LOCAL.set(connection);
		}
		return connection;
	}

	/**
	 *  释放数据库连接
	 *
	 */
	public static void releaseConnectin() {
		Connection connection = THREAD_LOCAL.get();
		if (null != connection) {
			try {
				connection.setAutoCommit(true);
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("释放数据库连接失败", e);
			} finally {
				THREAD_LOCAL.remove();
			}
		}
	}
}
