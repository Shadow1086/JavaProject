package com.project01.ledgerSystem.mapper;

import com.project01.ledgerSystem.dto.Detail;
import com.project01.ledgerSystem.models.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 类名: UserMapper
 * 创建时间: 2026/1/22 23:21
 * 项目描述:
 *
 * @author htLiang
 */
public interface UserMapper {

    boolean signIn(User user);

    User logIn(@Param("userName")String userName, @Param("userPassword")String userPassword);

    boolean withdrawMoney(@Param("userId")int userId,@Param("money")double money);
    boolean saveMoney(@Param("userId")int userId,@Param("money")double money);


    List<Detail> getInfo(int userId);

    // 工具类
    // 获得存款余额
    @Select("SELECT user_balance FROM user_info WHERE user_id = #{userId}")
    double getBalance(int userId);

    // 更新用户余额
    @Update("UPDATE user_info SET user_balance = #{money} WHERE user_id = #{userId}")
    boolean resetBalance(@Param("userId")int userId,@Param("money") double money);


}
