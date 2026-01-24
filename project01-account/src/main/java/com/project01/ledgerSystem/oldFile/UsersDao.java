//package com.project01.ledgerSystem.mapper;
//
//import com.project01.ledgerSystem.models.User;
//
//public interface UsersDao {
//    // 注册
//    boolean signUp(User user);
//    // 登陆
//    User logIn(String name, String password);
//    //取/存 款
//    double withdrawMoney(User user, double money, int funcNum);
//    //获取用户所有收入支出信息
//    String getInfo(User user);
//
//// 工具类
//    // 获得存款余额
//    double getBalance(int user_id);
//    // 更新用户余额
//    boolean resetBalance(int user_id,double money);
//}
