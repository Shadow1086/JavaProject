package com.it.ck.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.it.ck.server.pojo
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 18:31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsUser {
	private  Integer uid;
	private String username;
	private String userPwd;
	private String nickName;
}
