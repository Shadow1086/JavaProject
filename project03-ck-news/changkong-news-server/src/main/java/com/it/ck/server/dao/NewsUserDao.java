package com.it.ck.server.dao;

import com.it.ck.server.pojo.NewsUser;

import java.util.List;

/**
 * Package: com.it.ck.server.dao
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:23
 */
public interface NewsUserDao {
	/**
	 * 根据用户名查找用户信息
	 * @param username  用户名
	 * @return          用户名查找到的用户对象
	 */
	public NewsUser findByName(String username);

	/**
	 * 用户注册
	 * @param user  注册的用户信息
	 * @return      被影响的行数，1: 注册成功，0: 注册失败
	 */
	public NewsUser register(NewsUser user);

}
