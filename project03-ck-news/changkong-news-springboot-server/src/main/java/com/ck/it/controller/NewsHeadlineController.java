package com.ck.it.controller;

import com.ck.it.common.Result;
import com.ck.it.common.ResultCodeEnum;
import com.ck.it.exception.BusinessException;
import com.ck.it.pojo.NewsHeadline;
import com.ck.it.pojo.dto.RequestPublish;
import com.ck.it.service.NewsHeadlineService;
import com.ck.it.service.NewsUserService;
import com.ck.it.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
	public Result<String> publish(@RequestBody RequestPublish request, @RequestHeader("Authorization") String token) {
		Long userId = jwt.getUserId(token);
		boolean publish = headlineService.publish(request, userId);
		if (publish) {
			return Result.ok(null);
		}
		return Result.fail(null);
	}

	@Operation(summary = "根据id查询新闻", description = "根据新闻的hid查询新闻详情")
	@GetMapping("findHeadlineById")
	public Result<NewsHeadline> findHeadlineById(@RequestParam("hid") Long hid) {
		NewsHeadline headline = headlineService.getById(hid);
		if (headline == null) {
			return Result.fail(null);
		}
		return Result.ok(headline);
	}

	@Operation(summary = "更新新闻", description = "用户更新新闻")
	@PostMapping("update")
	public Result<String> update(@RequestBody NewsHeadline headline) {
		boolean success = headlineService.updateData(headline);
		if (success) {
			return Result.ok(null);
		} else {
			return Result.fail(null);
		}
	}

	@Operation(summary = "删除新闻", description = "删除新闻")
	@DeleteMapping("delete")
	public Result<String> delete(@RequestParam("hid") Long hid,
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
}
