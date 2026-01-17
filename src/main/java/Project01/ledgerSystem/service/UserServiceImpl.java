package Project01.ledgerSystem.service;

import java.sql.SQLException;

import Project01.ledgerSystem.dao.UsersDaoImpl;
import Project01.ledgerSystem.models.Users;

public class UserServiceImpl {
    private static UsersDaoImpl dao = new UsersDaoImpl();

    // 注册
    public boolean signUp(String name, String password) {
        System.out.println("--------------注册---------------");
        Users user = null;
        if (!name.isEmpty() && name != "" && !password.isEmpty() && password != null) {
            user = new Users(name, password);
        }
        user.setId(dao.signUp(user));
        return true;
    }

    // 登陆
    public boolean logIn(String name, String password) {
        if (!name.isEmpty() && name != "" && !password.isEmpty() && password != null) {
            dao.logIn(name, password);
            return true;
        }
        return false;
    }

    // 取款
    public boolean withdrawMoney(Users user, double money) {
        if (money > 0 || dao.getBalance(0) >= money) {
            dao.withdrawMoney(user, money, -1);
            return true;
        }
        return false;
    }
    // 存款
    public boolean saveMoney(Users user, double money) {
        if (money > 0) {
            dao.withdrawMoney(user, money, 1);
            return true;
        }
        return false;
    }
    //查询所有交易明细
    public boolean getInfo(Users user){
        StringBuilder[] str = dao.getInfo(user);
        for(int i = 0;i<str.length;i++){
            System.out.println(str[i]);
        }
        return true;
    }
}
