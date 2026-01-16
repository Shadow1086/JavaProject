package Project01.ledgerSystem.dao;

import Project01.ledgerSystem.models.Users;

public interface UsersDao {
    // 注册
    boolean signUp(Users user);
    // 登陆
    boolean logIn(String name,String password);
    //取/存 款
    boolean withdrawMoney(Users users , double money,int funcNum);
    //获取用户所有收入支出信息
    StringBuilder[] getInfo(Users users);

// 工具类
    // 获得存款余额
    double getBalance(int user_id);
    // 更新用户余额
    boolean resetBalance(int user_id,double money);
}
