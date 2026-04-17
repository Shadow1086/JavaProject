package com.it.ck.server.dao.impl;

import com.it.ck.server.dao.NewsHeadLineDao;
import com.it.ck.server.pojo.PageInfo;
import com.it.ck.server.pojo.vo.HeadLineDetailVo;
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
//		Integer publister = query.getPublister();
		Integer offset = (pageNum - 1) * pageSize;
		// SQL 语句
		StringBuilder sql = new StringBuilder("""
				SELECT hid,title,type,page_views AS pageViews,TIMESTAMPDIFF(HOUR,update_time,NOW()) AS pastHours,publisher
				FROM news_headline
				WHERE 1 = 1
				""");
		List<Object> params = new ArrayList<>();
		if ( type != null && type >= 1 ) {
			sql.append(" AND type = ? ");
			params.add(type);
		}
		if (keyWords != null && !keyWords.isBlank()) {
			sql.append(" AND title LIKE ? ");
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
		if ( type != null && type >= 1 ) {
			sql.append(" AND type = ? ");
			params.add(type);
		}
		if (keyWords != null && !keyWords.equals("")) {
			sql.append(" AND title like ? ");
			params.add("%" + keyWords + "%");
		}
		if (query.getPublisher() != null) {
			Integer publisher = query.getPublisher();
			if (publisher >= 1) {
				sql.append(" AND publisher = ?");
				params.add(publisher);
			}
		}

		sql.append(" AND is_deleted != 1 ");
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

	/**
	 * 查询新闻详情
	 *
	 * @param hid 查询的新闻的hid
	 * @return {@link HeadLineDetailVo }
	 */
	@Override
	public HeadLineDetailVo headlineDetail(Integer hid) {
		String sql = """
				SELECT nh.hid,nh.title,nh.article,
				       nh.type,ns.tname,nh.page_views AS pageViews,
				       TIMESTAMPDIFF(HOUR, nh.update_time, NOW()) AS pastHours,
				nh.publisher , nu.nick_name FROM news_headline nh
				LEFT JOIN news_type ns ON nh.type = ns.tid
				LEFT JOIN news_user nu ON nh.publisher = nu.uid
				WHERE nh.hid = ?
				""";
		Connection connection = JDBCUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, hid);
			try (ResultSet rs = ps.executeQuery()) {
				HeadLineDetailVo detail = new HeadLineDetailVo();
				while (rs.next()) {
					detail.setHid(rs.getInt(1));
					detail.setTitle(rs.getString(2));
					detail.setArticle(rs.getString(3));
					detail.setType(rs.getInt(4));
					detail.setTypeName(rs.getString(5));
					detail.setPageViews(rs.getInt(6));
					detail.setPastHours(rs.getLong(7));
					detail.setPublisher(rs.getInt(8));
					detail.setAuthor(rs.getString(9));
				}
				return detail;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 用户每查看一次曾加一次文章的pageViews访问量
	 *
	 * @param hid 用户访问的文章
	 * @return {@link Integer }
	 */
	@Override
	public Integer addPageViews(Integer hid) {
		String sql = """
				UPDATE news_headline SET page_views = page_views+1
				WHERE hid = ?
				""";
		Connection connection = JDBCUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, hid);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据用户id查找用户创建的文章
	 *
	 * @param uid 用户id
	 * @return {@link PageInfo }<{@link HeadLinePageVo }>
	 */
	@Override
	public List<HeadLinePageVo> findPageSelf(Integer uid, HeadlineQueryVo query) {
		String keyWords = query.getKeyWords();
		Integer type = query.getType();
		Integer pageNum = query.getPageNum();
		Integer pageSize = query.getPageSize();

		Integer offset = (pageNum - 1) * pageSize;

		StringBuilder sql = new StringBuilder("""
				SELECT nh.hid,
					nh.title,
					nh.type,
					nh.page_views AS pageViews,
					TIMESTAMPDIFF(HOUR,nh.update_time,NOW()) AS pastHours,
					nu.uid
				FROM news_headline nh
				LEFT JOIN news_user nu ON nh.publisher = nu.uid
				WHERE nu.uid = ?
				""");
		List<Object> params = new ArrayList<>();
		if (keyWords!=null && !keyWords.isBlank()) {
			params.add("%" + keyWords + "%");
			sql.append(" AND nh.title like ? ");
		}
		if ( type != null && type >= 1 ) {
			params.add(type);
			sql.append(" AND nh.type = ? ");
		}
		sql.append(" AND nh.is_deleted != 1 ");
		sql.append(" LIMIT ").append(offset).append(" , ").append(pageSize);
		Connection connection = JDBCUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
			ps.setObject(1, uid);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 2, params.get(i));
			}
			List<HeadLinePageVo> list = new ArrayList<>();
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					HeadLinePageVo page = new HeadLinePageVo();
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
	 * 根据文章的hid查询该文章的创建用户
	 *
	 * @param hid 文章的hid
	 * @return boolean
	 */
	@Override
	public Integer findPageUid(Integer hid) {
		String sql = """
				SELECT publisher FROM news_headline WHERE hid = ?
				""";
		Connection conn = JDBCUtil.getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hid);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.getInt(1);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据文章的hid删除文章
	 *
	 * @param hid 待删除的文章hid
	 * @return boolean
	 */
	@Override
	public boolean deleteHeadline(Integer hid) {
		String sql = """
				UPDATE news_headline SET is_deleted = 1 WHERE hid = ?
				""";
		Connection conn = JDBCUtil.getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, hid);
			int i = ps.executeUpdate();
			if (i == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}
}
