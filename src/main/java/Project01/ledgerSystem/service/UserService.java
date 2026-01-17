package Project01.ledgerSystem.service;

import Project01.ledgerSystem.models.Users;

public interface UserService {
    //注册
    public boolean signUp(String name,String password);
    //登陆
    public boolean logIn(String name,String password);
    //  取款
    public boolean withdrawMoney(Users user , double money);
    // 存款
    public boolean saveMoney(Users user,double money);
    //查看明细
    public boolean getInfo(Users user);
}
