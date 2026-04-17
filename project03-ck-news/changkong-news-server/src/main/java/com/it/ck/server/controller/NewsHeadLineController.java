package com.it.ck.server.controller;

import com.it.ck.server.pojo.NewsHeadline;
import com.it.ck.server.pojo.PageInfo;
import com.it.ck.server.pojo.vo.HeadLineDetailVo;
import com.it.ck.server.pojo.vo.HeadLinePageVo;
import com.it.ck.server.pojo.vo.HeadlineQueryVo;
import com.it.ck.server.service.NewsHeadLineService;
import com.it.ck.server.service.impl.NewsHeadLineServiceImpl;
import com.it.ck.server.utils.JDBCUtil;
import com.it.ck.server.utils.Result;
import com.it.ck.server.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
 * {@code @Create} 2026-2026/4/15 10:48
 */
@WebServlet("/headline/*")
public class NewsHeadLineController extends BaseController{
	private static NewsHeadLineService service = new NewsHeadLineServiceImpl();


	protected void headlineDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer hid = WebUtil.readJson(req, Integer.class);
//		Result<HeadLineDetailVo> result = Result.ok(null);
		HeadLineDetailVo detail = service.headlineDetail(hid);
		if(detail!=null){
			WebUtil.writeJson(resp,Result.ok(detail));
			JDBCUtil.releaseConnectin();
		}else{
			WebUtil.writeJson(resp,Result.ok("未知错误"));
		}
	}
}
