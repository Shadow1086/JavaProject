package com.ck.it.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Package: com.ck.it.pojo.vo
 * Description:
 * <p>
 * {@code @Author} Liang-ht
 * {@code @Create} 2026-2026/4/29 11:42
 */
@Data
@Schema(description = "新闻详情")
public class DetailVo {
	@Schema(description = "新闻ID", example = "2049366413163573250")
	private Long hid;        /// 新闻id
	@Schema(description = "新闻标题", example = "长空新闻发布测试")
	private String title;       /// 新闻标题
	@Schema(description = "新闻正文", example = "这里是新闻正文内容。")
	private String article;     /// 新闻正文内容
	@Schema(description = "新闻类别名称", example = "科技")
	private String typeName;    /// 新闻所属类别名称
	@Schema(description = "浏览量", example = "128")
	private Integer pageViews;  /// 新闻流量量
	@Schema(description = "距离发布时间的小时数", example = "3")
	private Integer pastHour;  /// 发布的小时数
	@Schema(description = "发布者用户名", example = "zhangsan")
	private String username;    /// 发布的作者用户名
	@Schema(description = "乐观锁版本号，更新新闻时需要带回", example = "1")
	private Integer version;        ///版本号
	@Schema(description = "发布者笔名", example = "张三")
	private String author;      /// 发布的作者笔名
}
