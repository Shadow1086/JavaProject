package com.ck.it.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @TableName news_user
 */
@TableName(value ="news_user")
@Data
public class NewsUser {
	@TableId(value = "uid", type = IdType.ASSIGN_ID)
    private Long uid;

	@NotBlank(message = "用户名不能为空")
    private String username;
	@NotBlank(message = "密码不能为空")
    private String userPwd;

    private String nickName;
}
