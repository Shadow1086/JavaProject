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
 * {@code @Create} 2026-2026/4/14 22:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadlineQueryVo implements Serializable {
	private String keyWords;            // 搜索关键词
	private Integer type;               // 类型
	private Integer pageNum;            // 第几页
	private Integer pageSize;           // 一页多少条
}
