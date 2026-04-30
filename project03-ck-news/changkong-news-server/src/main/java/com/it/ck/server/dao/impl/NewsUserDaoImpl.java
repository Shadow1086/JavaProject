package com.it.ck.server.dao.impl;

import com.it.ck.server.dao.NewsUserDao;
import com.it.ck.server.pojo.NewsUser;
import com.it.ck.server.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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
	public NewsUser findByName(String username) {

		String sql = """
				SELECT
				    uid , username , user_pwd AS userPwd , nick_name AS nickName 
						FROM news_user
							WHERE username = ?;
				""";
		List<NewsUser> userList = null;
		Connection connection = JDBCUtil.getConnection();
		NewsUser user = null;
		try (
				PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setString(1, username);
			try (
					ResultSet rs = ps.executeQuery()
			) {
				while (rs.next()) {
					user = new NewsUser();
					user.setUid(rs.getInt("uid"));
					user.setUsername(rs.getString("username"));
					user.setUserPwd(rs.getString("userPwd"));
					user.setNickName(rs.getString("nickName"));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return user;
	}

	/**
	 * 用户注册
	 *
	 * @param user 注册的用户信息
	 * @return 被影响的行数，1: 注册成功，0: 注册失败
	 */
	@Override
	public NewsUser register(NewsUser user) {
		String sql = """
				INSERT INTO news_user VALUES(DEFAULT,?,?,?)
				""";
		Connection connection = JDBCUtil.getConnection();
		try (
				PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getUserPwd());
			String nickName = Objects.equals(user.getNickName(), "") || user.getNickName() == null
					? ""
					: user.getNickName();
			ps.setString(3, nickName);
			int row = ps.executeUpdate();
			if (row > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						user.setUid(rs.getInt(1));
					}
				}
				return user;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
