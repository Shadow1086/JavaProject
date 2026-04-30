package com.ck.it.pojo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @TableName news_type
 */
@TableName(value = "news_type")
@Data
@Schema(description = "新闻类别")
public class NewsType {
	@Schema(description = "类别ID", example = "1")
	private Integer tid;

	@Schema(description = "类别名称", example = "科技")
	private String tname;

	@Schema(description = "乐观锁版本号", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
	@Version
	private Integer version;
	@Schema(description = "逻辑删除标记，0 表示未删除，1 表示已删除", hidden = true)
	@TableLogic
	private Integer isDeleted;
}
