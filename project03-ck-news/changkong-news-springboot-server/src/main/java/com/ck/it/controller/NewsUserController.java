package com.ck.it.controller;

import com.ck.it.common.Result;
import com.ck.it.pojo.NewsUser;
import com.ck.it.pojo.dto.LoginRequest;
import com.ck.it.service.NewsUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.ck.it.controller
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/28 18:52
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class NewsUserController {
//	@Autowired
	private final NewsUserService service;

	@PostMapping("login")
	public Result<String> login(@Valid @RequestBody LoginRequest user){
		String token = service.login(user);
		return Result.ok(token);
	}

}
