package com.ck.it.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Package: com.ck.it.pojo.dto
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 13:24
 */
@Data
@Schema(description = "发布新闻请求参数")
public class RequestPublish {
	@Schema(description = "新闻标题", example = "长空新闻发布测试", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank
	private String title;
	@Schema(description = "新闻正文", example = "这里是新闻正文内容。", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank
	private String article;
	@Schema(description = "新闻类别ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull
	private Integer type;
}
