package com.ck.it.pojo.vo;

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
public class PageInfoVo<T> {
	private Integer pageSize;

	private Integer currentPage;

	private Integer total;

	private List<T> pageList;
}
