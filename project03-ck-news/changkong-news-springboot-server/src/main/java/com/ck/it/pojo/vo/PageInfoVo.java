package com.ck.it.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Package: com.ck.it.pojo.vo
 * Description:
 * <p>
 *     返回给前端的json中的data
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 00:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "分页查询的页信息")
public class PageInfoVo<T> {
	@Schema(description = "页容量",example = "5",requiredMode =  Schema.RequiredMode.REQUIRED)
	private Integer pageSize;
	@Schema(description = "当前页码",example = "1",requiredMode =  Schema.RequiredMode.REQUIRED)
	private Integer currentPage;
	@Schema(description = "总量",example = "5",requiredMode =  Schema.RequiredMode.AUTO)
	private Integer total;
	@Schema(description = "当前页数据列表",requiredMode =  Schema.RequiredMode.AUTO)
	private List<T> pageList;
}
