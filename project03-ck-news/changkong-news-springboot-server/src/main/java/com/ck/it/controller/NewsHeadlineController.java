package com.ck.it.controller;

import com.ck.it.common.Result;
import com.ck.it.common.ResultCodeEnum;
import com.ck.it.exception.BusinessException;
import com.ck.it.pojo.NewsHeadline;
import com.ck.it.pojo.dto.RequestPage;
import com.ck.it.pojo.dto.RequestPublish;
import com.ck.it.pojo.vo.HeadlinePageVo;
import com.ck.it.pojo.vo.PageInfoVo;
import com.ck.it.service.NewsHeadlineService;
import com.ck.it.service.NewsUserService;
import com.ck.it.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Package: com.ck.it.controller
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 00:03
 */
@RestController
@RequestMapping("headline")
@CrossOrigin
@Tag(name = "新闻接口", description = "新闻的发布，查询，详情相关接口")
@Slf4j
public class NewsHeadlineController {
	@Autowired
	private JwtUtil jwt;
	@Autowired
	private NewsHeadlineService headlineService;
	@Autowired
	private NewsUserService userService;

	@Operation(summary = "发布新闻", description = "用户登录发布新闻")
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("publish")
	public Result<String> publish(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "发布新闻请求参数", required = true)
			@Valid @RequestBody RequestPublish request,
			@Parameter(description = "登录凭证，格式：Bearer <token>", required = true)
			@RequestHeader("Authorization") String token) {
		Long userId = jwt.getUserId(token);
		boolean publish = headlineService.publish(request, userId);
		if (publish) {
			return Result.ok(null);
		}
		log.info("发布新闻失败");
		return Result.fail(null);
	}

	@Operation(summary = "根据id查询新闻", description = "根据新闻的hid查询新闻详情")
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("findHeadlineById")
	public Result<NewsHeadline> findHeadlineById(
			@Parameter(description = "新闻ID", required = true, example = "2049366413163573250")
			@RequestParam("hid")
			Long hid) {
		NewsHeadline headline = headlineService.getById(hid);
		if (headline == null) {
			return Result.fail(null);
		}
		return Result.ok(headline);
	}

	@Operation(summary = "更新新闻", description = "用户更新新闻")
	@PostMapping("update")
	@SecurityRequirement(name = "bearerAuth")
	public Result<String> update(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "新闻更新内容，hid 和 version 必须来自详情接口", required = true)
			@RequestBody NewsHeadline headline,
			@Parameter(description = "登录凭证，格式：Bearer <token>", required = true)
			@RequestHeader("Authorization") String token) {
		Long userId = jwt.getUserId(token);
		if (!headlineService.headlinePermission(headline.getHid(), userId)) {
			throw new BusinessException(ResultCodeEnum.HEADLINE_NO_PERMISSION);
		}
		boolean success = headlineService.updateData(headline);
		if (success) {
			return Result.ok(null);
		} else {
			return Result.fail(null);
		}
	}

	@Operation(summary = "删除新闻", description = "删除新闻")
	@DeleteMapping("delete")
	@SecurityRequirement(name = "bearerAuth")
	public Result<String> delete(
			@Parameter(description = "新闻ID", required = true, example = "2049366413163573250")
			@RequestParam("hid") Long hid,
			@Parameter(description = "登录凭证，格式：Bearer <token>", required = true)
			@RequestHeader("Authorization") String token) {
		Long userId = jwt.getUserId(token);

		if (headlineService.headlinePermission(hid, userId)) {
			/// 该新闻的创建用户是当前token携带者，有权限
			boolean b = headlineService.removeById(hid);
			if (b) {
				return Result.ok(null);
			} else {
				return Result.fail(null);
			}
		} else {
			throw new BusinessException(ResultCodeEnum.HEADLINE_NO_PERMISSION);
		}
	}

	@Operation(summary = "查询我的新闻分页列表", description = "根据当前登录用户查询自己发布的新闻分页列表")
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("findMyNewsPage")
	public Result<PageInfoVo<HeadlinePageVo>> findMyNewsPage(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "分页查询条件，uid 由后端根据 token 写入", required = true)
			@RequestBody RequestPage request,
			@Parameter(description = "登录凭证，格式：Bearer <token>", required = true)
			@RequestHeader("Authorization") String token) {

		Long userId = jwt.getUserId(token);
		request.setUid(userId);
		return headlineService.findNewsPage(request);
	}
}
