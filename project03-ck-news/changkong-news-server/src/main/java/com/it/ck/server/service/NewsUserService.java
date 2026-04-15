package com.it.ck.server.service;

import com.it.ck.server.pojo.NewsUser;

import java.util.List;

/**
 * Package: com.it.ck.server.service
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:25
 */
public interface NewsUserService {
	/**
	 * 根据用户名查找用户信息
	 * @param username  用户名
	 * @return          用户名查找到的用户对象
	 */
	public List<NewsUser> findByName(String username);
}
