package com.it.ck.server.dao;

import com.it.ck.server.pojo.NewsType;

import java.util.List;

/**
 * Package: com.it.ck.server.dao
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:24
 */
public interface NewsTypeDao {
	/**
	 * 查找所有新闻类型
	 * @return      新闻类型的list
	 */
	public List<NewsType> findAllTypes();

}
