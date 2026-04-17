package com.it.ck.server.controller;

import com.it.ck.server.common.ResultCodeEnum;
import com.it.ck.server.pojo.vo.HeadLineDetailVo;
import com.it.ck.server.service.NewsHeadLineService;
import com.it.ck.server.service.impl.NewsHeadLineServiceImpl;
import com.it.ck.server.utils.JDBCUtil;
import com.it.ck.server.utils.JwtHelper;
import com.it.ck.server.utils.Result;
import com.it.ck.server.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Package: com.it.ck.server.controller
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/15 10:48
 */
@WebServlet("/headline/*")
public class NewsHeadLineController extends BaseController {
	private static NewsHeadLineService service = new NewsHeadLineServiceImpl();


	/**
	 * 查询文章所有信息返回给前端
	 */
	protected void headlineDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer hid = WebUtil.readJson(req, Integer.class);
		HeadLineDetailVo detail = service.headlineDetail(hid);
		if (detail != null) {
			WebUtil.writeJson(resp, Result.ok(detail));
			JDBCUtil.releaseConnectin();
		} else {
			WebUtil.writeJson(resp, Result.ok("未知错误"));
		}
	}


	protected void deleteHeadline(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer hid = Integer.parseInt(req.getParameter("hid"));
		String authorization = req.getHeader("Authorization");
		String token = null;
		Result<Object> result = Result.ok(null);

		if (authorization!= null && authorization.startsWith("Bearer ")) {
			token = authorization.substring(7);
			WebUtil.writeJson(resp, Result.ok(null));
		}

		Integer uid = JwtHelper.getUserId(token);
		// 验证文章的 创建用户 是不是 删除操作用户。查看权限
		Integer pageUid = service.findPageUid(uid);
		if (uid.equals(pageUid)) {
			boolean flag = service.deleteHeadline(hid);
			if(flag){
				result = Result.ok(ResultCodeEnum.SUCCESS);
				WebUtil.writeJson(resp,result);
			}
		}
	}
}
