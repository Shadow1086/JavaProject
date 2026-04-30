package com.ck.it.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Package: com.ck.it.pojo.dto
 * Description:
 * <p>
 *     前端传入的查询分页信息
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 00:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "新闻分页查询条件")
public class RequestPage {

	@Schema(description = "标题搜索关键字", example = "Spring Boot")
	private String keyWord;

	@Schema(description = "发布者用户ID，查询我的新闻时由后端从 token 写入", example = "2049366413163573249")
	private Long uid;

	@Schema(description = "新闻类别ID，0 表示查询全部类别", example = "0")
	private Integer type = 0;

	@Schema(description = "每页条数，最小值为 5", example = "5")
	private Integer pageSize = 5;

	@Schema(description = "当前页码，从 1 开始", example = "1")
	private Integer currentPage = 1;

}
