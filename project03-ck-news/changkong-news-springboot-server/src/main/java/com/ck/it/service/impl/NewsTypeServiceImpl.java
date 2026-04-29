package com.ck.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ck.it.common.Result;
import com.ck.it.mapper.NewsTypeMapper;
import com.ck.it.pojo.NewsType;
import com.ck.it.service.NewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liang-ht
 * @description 针对表【news_type】的数据库操作Service实现
 * @createDate 2026-04-28 18:12:08
 */
@Service
public class NewsTypeServiceImpl extends ServiceImpl<NewsTypeMapper, NewsType>
		implements NewsTypeService {

	@Autowired
	private NewsTypeMapper mapper;

	@Override
	public Result<List<NewsType>> findAllTypes() {
		List<NewsType> types = mapper.selectList(null);
		return Result.ok(types);
	}
}




