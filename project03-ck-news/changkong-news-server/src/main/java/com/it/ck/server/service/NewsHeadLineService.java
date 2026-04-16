package com.it.ck.server.service;

import com.it.ck.server.pojo.NewsHeadline;
import com.it.ck.server.pojo.PageInfo;
import com.it.ck.server.pojo.vo.HeadLinePageVo;
import com.it.ck.server.pojo.vo.HeadlineQueryVo;

import java.util.List;

/**
 * Package: com.it.ck.server.service
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:25
 */
public interface NewsHeadLineService {
	/**
	 *  查询新闻列表
	 * @param query     用户的查询请求
	 * @return          page的分页信息包括新闻的列表
	 */
	public PageInfo<HeadLinePageVo> findPage(HeadlineQueryVo query);
}
