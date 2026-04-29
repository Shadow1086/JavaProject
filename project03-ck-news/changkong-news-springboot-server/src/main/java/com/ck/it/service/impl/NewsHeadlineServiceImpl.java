package com.ck.it.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ck.it.common.Result;
import com.ck.it.common.ResultCodeEnum;
import com.ck.it.exception.BusinessException;
import com.ck.it.mapper.NewsHeadlineMapper;
import com.ck.it.pojo.NewsHeadline;
import com.ck.it.pojo.dto.RequestPage;
import com.ck.it.pojo.dto.RequestPublish;
import com.ck.it.pojo.vo.DetailVo;
import com.ck.it.pojo.vo.HeadlinePageVo;
import com.ck.it.pojo.vo.PageInfoVo;
import com.ck.it.service.NewsHeadlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author liang-ht
 * @description 针对表【news_headline】的数据库操作Service实现
 * @createDate 2026-04-28 18:03:20
 */
@Service
@RequiredArgsConstructor
public class NewsHeadlineServiceImpl extends ServiceImpl<NewsHeadlineMapper, NewsHeadline>
		implements NewsHeadlineService {
	@Autowired
	private NewsHeadlineMapper mapper;

	/**
	 * 首页数据分页查询
	 * 1. 进行分页数据查询
	 * 2. 分页数据拼接到result中即可
	 * <p>
	 * 注意：1. 自定义语句，2. 返回的结果List<Map>
	 *
	 * @param request 请求的分页信息
	 * @return 封装的分页信息对象
	 */
	@Override
	public Result<PageInfoVo<HeadlinePageVo>> findNewsPage(RequestPage request) {
		IPage<HeadlinePageVo> page = new Page<>(request.getCurrentPage(),
				request.getPageSize());

		IPage<HeadlinePageVo> resultPage = mapper.selectMyPage(page, request);

		PageInfoVo<HeadlinePageVo> pageInfoVo = new PageInfoVo<>();

		pageInfoVo.setCurrentPage((int) page.getCurrent());
		pageInfoVo.setPageSize((int) page.getSize());
		pageInfoVo.setTotal((int) page.getTotal());
		pageInfoVo.setPageList(page.getRecords());

		return Result.ok(pageInfoVo);
	}

	@Override
	public DetailVo showHeadlineDetail(Long hid) {
		DetailVo detail = mapper.showHeadlineDetail(hid);

		NewsHeadline headline = new NewsHeadline();
		headline.setHid(detail.getHid());
		headline.setVersion(detail.getVersion());
		headline.setPageViews(detail.getPageViews() + 1);
		mapper.updateById(headline);

		detail.setPageViews(detail.getPageViews() + 1);
		return detail;
	}

	@Override
	public boolean publish(RequestPublish request, Long uid) {
		NewsHeadline headline = new NewsHeadline();

		headline.setTitle(request.getTitle());
		headline.setArticle(request.getArticle());
		headline.setType(request.getType());
		headline.setPageViews(0);
		headline.setPublisher(uid);
		headline.setCreateTime(new Date());
		headline.setUpdateTime(new Date());

		return save(headline);
	}

	@Override
	public boolean updateData(NewsHeadline headline) {
		Integer version = mapper.selectById(headline.getHid()).getVersion();
		headline.setVersion(version);
		headline.setUpdateTime(new Date());

		int rows = mapper.updateById(headline);
		return rows == 1;
	}

	@Override
	public boolean headlinePermission(Long hid, Long userId) {
		NewsHeadline headline = getById(hid);
		if(headline!=null){
			Long publisher = headline.getPublisher();
			return Objects.equals(publisher, userId);
		}else{
			throw new BusinessException(ResultCodeEnum.HEADLINE_NOT_FOUND);
		}
	}
}




