package com.ck.it.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "新闻分页信息")
public class HeadlinePageVo {
	@Schema(description = "新闻hid",example = "2049366413163573250",requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "hid不能为空")
	private Long hid;

	@Schema(description = "新闻标题", example = "长空新闻发布测试")
	private String title;
	@Schema(description = "新闻类别ID", example = "1")
	private Integer type;
	@Schema(description = "发布者昵称", example = "张三")
	private String nickName;
	@Schema(description = "浏览量", example = "128")
	private Integer pageViews;
	@Schema(description = "距离发布时间的小时数", example = "3")
	private Long pastHour;
}
