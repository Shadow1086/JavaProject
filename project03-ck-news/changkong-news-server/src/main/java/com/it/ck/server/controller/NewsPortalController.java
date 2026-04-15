package com.it.ck.server.controller;

import com.it.ck.server.pojo.NewsType;
import com.it.ck.server.service.NewsTypeService;
import com.it.ck.server.service.impl.NewsTypeServiceImpl;
import com.it.ck.server.utils.Result;
import com.it.ck.server.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.it.ck.server.controller
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/15 10:49
 */
@WebServlet("/portal/*")
public class NewsPortalController extends BaseController{
	private final NewsTypeService typeService = new NewsTypeServiceImpl();

	protected void findAllTypes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 查询所有的新闻类型，装为 result ,相应给客户端
		List<NewsType> newsTypes = typeService.findAllTypes();
		WebUtil.writeJson(resp,Result.ok(newsTypes));
	}
}
