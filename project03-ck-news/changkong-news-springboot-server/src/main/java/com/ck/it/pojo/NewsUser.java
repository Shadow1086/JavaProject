package com.ck.it.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @TableName news_user
 */
@TableName(value ="news_user")
@Data
@Schema(description = "新闻用户")
public class NewsUser {
	@Schema(description = "用户ID，雪花算法生成", example = "2049366413163573249", accessMode = Schema.AccessMode.READ_ONLY)
	@TableId(value = "uid", type = IdType.ASSIGN_ID)
    private Long uid;

	@Schema(description = "用户名", example = "zhangsan", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "用户名不能为空")
    private String username;
	@Schema(description = "用户密码", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED, accessMode = Schema.AccessMode.WRITE_ONLY)
	@NotBlank(message = "密码不能为空")
    private String userPwd;

	@Schema(description = "用户昵称", example = "张三")
    private String nickName;
}
