package com.it.ck.server.dao.impl;

import com.it.ck.server.dao.NewsUserDao;
import com.it.ck.server.pojo.NewsUser;
import com.it.ck.server.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Package: com.it.ck.server.dao.impl
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:24
 */

public class NewsUserDaoImpl implements NewsUserDao {
	/**
	 * 根据用户名查找用户信息
	 *
	 * @param username 用户名
	 * @return 用户名查找到的用户对象
	 */
	@Override
	public List<NewsUser> findByName(String username) {

		String sql = """
				SELECT
				    uid , username , user_pwd AS userPwd , nick_name AS nickName 
						FROM news_user
							WHERE username = ?;
				""";
		List<NewsUser> userList = null;
		Connection connection = JDBCUtil.getConnection();
		try (
				PreparedStatement ps = connection.prepareStatement(sql);
		) {
			userList = new ArrayList<>();
			ps.setString(1, username);
			try (
					ResultSet rs = ps.executeQuery()
			) {
				NewsUser user = null;
				while (rs.next()) {
					user = new NewsUser();
					user.setUid(rs.getInt("uid"));
					user.setUsername(rs.getString("username"));
					user.setUserPwd(rs.getString("userPwd"));
					user.setNickName(rs.getString("nickName"));
					userList.add(user);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.releaseConnectin();
		}
		return userList;
	}
}
