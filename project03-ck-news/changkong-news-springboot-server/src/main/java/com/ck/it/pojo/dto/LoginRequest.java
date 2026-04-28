package com.ck.it.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.ck.it.pojo.dto
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/28 19:39
 */
@Data
public class LoginRequest {
	@NotBlank(message="用户名不能为空")
	private String username;
	@NotBlank(message = "密码不能为空")
	private String userPwd;
}
