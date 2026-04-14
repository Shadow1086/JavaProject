package com.it.ck.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Package: com.it.ck.server.pojo
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 18:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsHeadline {
	private  Integer hid;
	private  String title;
	private String article;
	private Integer type;
	private Integer publisher;
	private Integer pageViews;
	private Date createTime;
	private Date updateTime;
	private Integer isDeleted;
}
