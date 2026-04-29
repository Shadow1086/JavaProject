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

	private String title;
	private Integer type;
	private String nickName;
	private Integer pageViews;
	private Long pastHour;
}
