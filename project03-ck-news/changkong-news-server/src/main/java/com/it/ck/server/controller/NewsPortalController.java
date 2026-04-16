package com.it.ck.server.controller;

import com.it.ck.server.common.ResultCodeEnum;
import com.it.ck.server.pojo.NewsType;
import com.it.ck.server.pojo.PageInfo;
import com.it.ck.server.pojo.vo.HeadLinePageVo;
import com.it.ck.server.pojo.vo.HeadlineQueryVo;
import com.it.ck.server.service.NewsHeadLineService;
import com.it.ck.server.service.NewsTypeService;
import com.it.ck.server.service.impl.NewsHeadLineServiceImpl;
import com.it.ck.server.service.impl.NewsTypeServiceImpl;
import com.it.ck.server.utils.Result;
import com.it.ck.server.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Package: com.it.ck.server.controller
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/15 10:49
 */
@WebServlet("/portal/*")
public class NewsPortalController extends BaseController {
	private static final NewsHeadLineService headLineService = new NewsHeadLineServiceImpl();
	private final NewsTypeService typeService = new NewsTypeServiceImpl();

	protected void findAllTypes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 查询所有的新闻类型，装为 result ,相应给客户端
		List<NewsType> newsTypes = typeService.findAllTypes();
		WebUtil.writeJson(resp, Result.ok(newsTypes));
	}


	protected void findPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HeadlineQueryVo queryVo = WebUtil.readJson(req, HeadlineQueryVo.class);
		PageInfo<HeadLinePageVo> list = headLineService.findPage(queryVo);
		Result result = Result.ok(null);
		if (list != null) {
			result = Result.build(list, ResultCodeEnum.SUCCESS);
			WebUtil.writeJson(resp,result);
		}
	}
}
