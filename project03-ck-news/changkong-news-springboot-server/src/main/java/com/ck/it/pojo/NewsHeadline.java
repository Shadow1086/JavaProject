package com.ck.it.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @TableName news_headline
 */
@TableName(value = "news_headline")
@Data
@Schema(description = "新闻实体")
public class NewsHeadline {
	@Schema(description = "新闻ID，雪花算法生成", example = "2049366413163573250", requiredMode = Schema.RequiredMode.REQUIRED)
	@TableId(value = "hid", type = IdType.ASSIGN_ID)
	private Long hid;

	@Schema(description = "新闻标题", example = "长空新闻发布测试", requiredMode = Schema.RequiredMode.REQUIRED)
	private String title;

	@Schema(description = "新闻正文", example = "这里是新闻正文内容。", requiredMode = Schema.RequiredMode.REQUIRED)
	private String article;

	@Schema(description = "新闻类别ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer type;

	@Schema(description = "发布者用户ID，由登录用户决定", example = "2049366413163573249", accessMode = Schema.AccessMode.READ_ONLY)
	private Long publisher;

	@Schema(description = "浏览量", example = "128", accessMode = Schema.AccessMode.READ_ONLY)
	private Integer pageViews;

	@Schema(description = "创建时间", example = "2026-04-30T12:00:00.000+00:00", accessMode = Schema.AccessMode.READ_ONLY)
	private Date createTime;

	@Schema(description = "更新时间", example = "2026-04-30T12:30:00.000+00:00", accessMode = Schema.AccessMode.READ_ONLY)
	private Date updateTime;

	@Schema(description = "逻辑删除标记，0 表示未删除，1 表示已删除", hidden = true)
	@TableLogic
	private Integer isDeleted;
	@Schema(description = "乐观锁版本号，更新新闻时需要携带查询详情返回的 version", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
	@Version
	private Integer version;
}
