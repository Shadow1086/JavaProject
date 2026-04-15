package com.it.ck.server.service.impl;

import com.it.ck.server.dao.NewsTypeDao;
import com.it.ck.server.dao.impl.NewsTypeDaoImpl;
import com.it.ck.server.pojo.NewsType;
import com.it.ck.server.pojo.NewsUser;
import com.it.ck.server.service.NewsTypeService;

import java.util.List;

/**
 * Package: com.it.ck.server.service.impl
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:26
 */

public class NewsTypeServiceImpl implements NewsTypeService {
	private final NewsTypeDao typeDao = new NewsTypeDaoImpl();

	@Override
	public List<NewsType> findAllTypes(){
		return typeDao.findAllTypes();
	}
}
