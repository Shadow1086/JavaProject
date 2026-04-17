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
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	@Getter
	private static DataSource dataSource;

	static {
		Properties properties = new Properties();
		InputStream resourceAsStream = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
		try {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection getConnection() {
		Connection connection = threadLocal.get();
		if (connection == null) {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			threadLocal.set(connection);
		}
		return connection;
	}

	public static void releaseConnectin() {
		Connection connection = threadLocal.get();
		if (null != connection) {
			try {
				connection.setAutoCommit(true);
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}finally {
				threadLocal.remove();
			}
		}
	}
}
