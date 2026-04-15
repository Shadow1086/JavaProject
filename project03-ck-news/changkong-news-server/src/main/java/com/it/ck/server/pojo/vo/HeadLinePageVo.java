package com.it.ck.server.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Package: com.it.ck.server.pojo
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/14 22:43
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeadLinePageVo implements Serializable {
	private Integer hid;            // 新闻id
	private String title;           // 标题
	private Integer type;           // 分类id
	private Integer pageViews;      // 浏览量
	private Long pastHours;         // 发布时间 距今 多少小时
	private Integer publisher;      // 发布人id
}
