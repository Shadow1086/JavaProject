package com.it.ck.server.service;

import com.it.ck.server.pojo.NewsType;

import java.util.List;

/**
 * Package: com.it.ck.server.service
 * Description:
 * <p>
 *     新闻类型 service 层接口
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:25
 */
public interface NewsTypeService {

	/**
	 *  查询所有新闻类型
	 */
	public List<NewsType> findAllTypes();
}
