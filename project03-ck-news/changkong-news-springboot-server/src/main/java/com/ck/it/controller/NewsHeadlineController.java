package com.ck.it.controller;

import com.ck.it.common.Result;
import com.ck.it.pojo.dto.RequestPublish;
import com.ck.it.service.NewsHeadlineService;
import com.ck.it.service.NewsUserService;
import com.ck.it.util.JwtUtil;
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
public class NewsHeadlineController {
	@Autowired
	private JwtUtil jwt;
	@Autowired
	private NewsHeadlineService headlineService;
	@Autowired
	private NewsUserService userService;

	@PostMapping("publish")
	public Result<String> publish(@RequestBody RequestPublish request, @RequestHeader("Authorization") String token) {
		Long userId = jwt.getUserId(token);
		boolean publish = headlineService.publish(request, userId);
		if (publish){
			return Result.ok(null);
		}
		return Result.fail(null);
	}
}
