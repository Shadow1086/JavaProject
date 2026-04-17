package com.it.ck.server.service;

import com.it.ck.server.pojo.NewsHeadline;
import com.it.ck.server.pojo.PageInfo;
import com.it.ck.server.pojo.vo.HeadLineDetailVo;
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

	/**
	 *  查询新闻详情
	 *
	 * @param hid       查询的新闻的hid
	 * @return {@link HeadLineDetailVo }
	 */
	public HeadLineDetailVo headlineDetail(Integer hid);

	/**
	 *  用户每查看一次曾加一次文章的pageViews访问量
	 *
	 * @param hid       用户访问的文章
	 * @return {@link Integer }
	 */
	public Integer addPageViews(Integer hid);

	/**
	 *  根据用户id查找用户创建的文章
	 *
	 * @param uid       用户id
	 * @return {@link PageInfo }<{@link HeadLinePageVo }>
	 */
	public PageInfo<HeadLinePageVo> findPageSelf(Integer uid,HeadlineQueryVo query);

	/**
	 *  根据文章的hid删除文章
	 *
	 * @param hid   待删除的文章hid
	 * @return boolean
	 */
	public boolean deleteHeadline(Integer hid);


	/**
	 *  根据文章的hid查询该文章的创建用户
	 *
	 * @param hid       文章的hid
	 * @return boolean
	 */
	public Integer findPageUid(Integer hid);
}
