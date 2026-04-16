package com.it.ck.server.dao;

import com.it.ck.server.pojo.NewsHeadline;
import com.it.ck.server.pojo.PageInfo;
import com.it.ck.server.pojo.vo.HeadLinePageVo;
import com.it.ck.server.pojo.vo.HeadlineQueryVo;

import java.util.List;

/**
 * Package: com.it.ck.server.dao
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:23
 */
public interface NewsHeadLineDao {
	/**
	 *  查询新闻列表
	 * @param query     用户的查询请求
	 * @return          page的分页信息包括新闻的列表
	 */
	public List<HeadLinePageVo> findPage(HeadlineQueryVo query);

	/**
	 * 查询符合要求的新闻条数
	 * @param query     查询信息
	 * @return          新闻总数
	 */
	public Integer findPageCount(HeadlineQueryVo query);
}
