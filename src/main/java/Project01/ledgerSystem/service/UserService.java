package Project01.ledgerSystem.service;

import Project01.ledgerSystem.models.Users;

public interface UserService {
    //注册
    public boolean signUp(String name,String password);
    //登陆
    public Users logIn(String name,String password);
    //  取款
    public double withdrawMoney(Users user , double money);
    // 存款
    public double saveMoney(Users user,double money);
    //查看明细
    public boolean getInfo(Users user);
}
