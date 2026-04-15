package com.it.ck.server.dao.impl;

import com.it.ck.server.dao.NewsTypeDao;
import com.it.ck.server.pojo.NewsType;
import com.it.ck.server.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Package: com.it.ck.server.dao.impl
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:24
 */

public class NewsTypeDaoImpl implements NewsTypeDao {

	/**
	 * 查找所有新闻类型
	 *
	 * @return 新闻类型的list
	 */
	@Override
	public List<NewsType> findAllTypes() {
		String sql = """
				SELECT * FROM news_type;
				""";
		Connection connection = JDBCUtil.getConnection();
		List<NewsType> list = new ArrayList<>();
		try (
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
		) {
			while (rs.next()) {
				NewsType newsTypes = new NewsType();
				newsTypes.setTid(rs.getInt("tid"));
				newsTypes.setTname(rs.getString("tname"));
				list.add(newsTypes);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}
}
