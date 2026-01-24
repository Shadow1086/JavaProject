package com.project01.ledgerSystem.service;

import com.project01.ledgerSystem.models.User;

public interface UserService {
    //注册
    public boolean signIn(String name,String password);
    //登陆
    public User logIn(String name, String password);
    //  取款
    public double withdrawMoney(User user , double money);
    // 存款
    public double saveMoney(User user, double money);
    //查看明细
    public boolean getInfo(User user);
}
