package com.ck.it.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Package: com.ck.it.pojo.vo
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 11:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadlinePageVo {
	private Integer hid;
	private String title;
	private Integer type;
	private String nickName;
	private Integer pageViews;
	private Long pastHour;
}
