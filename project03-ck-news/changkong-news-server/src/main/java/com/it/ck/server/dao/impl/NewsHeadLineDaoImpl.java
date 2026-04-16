package com.it.ck.server.dao.impl;

import com.it.ck.server.dao.NewsHeadLineDao;
import com.it.ck.server.pojo.vo.HeadLinePageVo;
import com.it.ck.server.pojo.vo.HeadlineQueryVo;
import com.it.ck.server.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Package: com.it.ck.server.dao.impl
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:24
 */

public class NewsHeadLineDaoImpl implements NewsHeadLineDao {
	/**
	 * 查询新闻列表
	 *
	 * @param query 用户的查询请求
	 * @return page的分页信息包括新闻的列表
	 */
	@Override
	public List<HeadLinePageVo> findPage(HeadlineQueryVo query) {
		String keyWords = query.getKeyWords();
		Integer type = query.getType();
		Integer pageNum = query.getPageNum();
		Integer pageSize = query.getPageSize();
		Integer offset = (pageNum - 1) * pageSize;
		StringBuilder sql = new StringBuilder("""
				select hid,title,type,page_views as pageViews,timestampdiff(hour,create_time,now()) as pastHours,publisher
				from news_headline
				where 1 = 1 
				""");
		List<Object> params = new ArrayList<>();
		if (type >= 1) {
			sql.append(" AND type = ? ");
			params.add(type);
		}
		if (keyWords != null && !keyWords.isBlank()) {
			sql.append(" AND title like ? ");
			params.add("%" + keyWords + "%");
		}
		sql.append(" limit ").append(offset).append(", ").append(pageSize);
		Connection connection = JDBCUtil.getConnection();
		List<HeadLinePageVo> list = new ArrayList<>();
		try (
				PreparedStatement ps = connection.prepareStatement(sql.toString());
		) {
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			try (
					ResultSet rs = ps.executeQuery();
			) {
				HeadLinePageVo page = null;
				while (rs.next()) {
					page = new HeadLinePageVo();
					page.setHid(rs.getInt(1));
					page.setTitle(rs.getString(2));
					page.setType(rs.getInt(3));
					page.setPageViews(rs.getInt(4));
					page.setPastHours(rs.getLong(5));
					page.setPublisher(rs.getInt(6));
					list.add(page);
				}
				return list;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 查询符合要求的新闻条数
	 *
	 * @param query 查询信息
	 * @return 新闻总数
	 */
	@Override
	public Integer findPageCount(HeadlineQueryVo query) {
		String keyWords = query.getKeyWords();
		Integer type = query.getType();
		StringBuilder sql = new StringBuilder("""
				SELECT COUNT(*) FROM news_headline
								WHERE 1 = 1
				""");
		List<Object> params = new ArrayList<>();
		if (type >= 1) {
			sql.append(" AND type = ? ");
			params.add(type);
		}
		if (keyWords != null && !keyWords.equals("")) {
			sql.append(" AND title like ? ");
			params.add("%" + keyWords + "%");
		}
		Connection connection = JDBCUtil.getConnection();
		try (
				PreparedStatement ps = connection.prepareStatement(sql.toString())
		) {
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return -1;
	}
}
