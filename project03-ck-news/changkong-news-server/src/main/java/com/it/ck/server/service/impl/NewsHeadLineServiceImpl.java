package com.it.ck.server.service.impl;

import com.it.ck.server.dao.NewsHeadLineDao;
import com.it.ck.server.dao.impl.NewsHeadLineDaoImpl;
import com.it.ck.server.pojo.PageInfo;
import com.it.ck.server.pojo.vo.HeadLineDetailVo;
import com.it.ck.server.pojo.vo.HeadLinePageVo;
import com.it.ck.server.pojo.vo.HeadlineQueryVo;
import com.it.ck.server.service.NewsHeadLineService;

import java.util.List;

/**
 * Package: com.it.ck.server.service.impl
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:26
 */

public class NewsHeadLineServiceImpl implements NewsHeadLineService {
	private static NewsHeadLineDao dao = new NewsHeadLineDaoImpl();

	/**
	 * 查询新闻列表
	 *
	 * @param query 用户的查询请求
	 * @return page的分页信息包括新闻的列表
	 */
	@Override
	public PageInfo<HeadLinePageVo> findPage(HeadlineQueryVo query) {
		List<HeadLinePageVo> list = dao.findPage(query);
		Integer totalSize = dao.findPageCount(query);
		Integer pageSize = query.getPageSize();
		Integer pageNum = query.getPageNum();
		Integer totalPage = (totalSize+pageSize-1)/pageSize;

		return new PageInfo<HeadLinePageVo>(
				pageNum,pageSize,totalSize,totalPage,list
		);
	}

	/**
	 * 查询新闻详情
	 *
	 * @param hid 查询的新闻的hid
	 * @return {@link HeadLineDetailVo }
	 */
	@Override
	public HeadLineDetailVo headlineDetail(Integer hid) {
		dao.addPageViews(hid);
		return dao.headlineDetail(hid);
	}

	/**
	 * 用户每查看一次曾加一次文章的pageViews访问量
	 *
	 * @param hid 用户访问的文章
	 * @return {@link Integer }
	 */
	@Override
	public Integer addPageViews(Integer hid) {
		return dao.addPageViews(hid);
	}

	/**
	 * 根据用户id查找用户创建的文章
	 *
	 * @param uid 用户id
	 * @return {@link PageInfo }<{@link HeadLinePageVo }>
	 */
	@Override
	public PageInfo<HeadLinePageVo> findPageSelf(Integer uid,HeadlineQueryVo query) {
		List<HeadLinePageVo> list = dao.findPageSelf(uid,query);
		Integer totalSize = dao.findPageCount(query);
		Integer pageSize = query.getPageSize();
		Integer pageNum = query.getPageNum();
		Integer totalPage = (totalSize+pageSize-1)/pageSize;

		return new PageInfo<HeadLinePageVo>(
				pageNum,pageSize,totalSize,totalPage,list
		);
	}

	/**
	 * 根据文章的hid删除文章
	 *
	 * @param hid 待删除的文章hid
	 * @return boolean
	 */
	@Override
	public boolean deleteHeadline(Integer hid) {
		return dao.deleteHeadline(hid);
	}

	/**
	 * 根据文章的hid查询该文章的创建用户
	 *
	 * @param hid 文章的hid
	 * @return boolean
	 */
	@Override
	public Integer findPageUid(Integer hid) {
		return dao.findPageUid(hid);
	}
}
