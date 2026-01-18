package Project01.ledgerSystem.service;


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
    public Users logIn(String name, String password) {
        if (!name.isEmpty() && name != "" && !password.isEmpty() && password != null) {
            return dao.logIn(name, password);
        }
        return null;
    }

    // 取款
    public double withdrawMoney(Users user, double money) {
        double result = -1;
        if (money > 0 || dao.getBalance(0) >= money) {
            result = dao.withdrawMoney(user, money, -1);
        }
        return result;
    }
    // 存款
    public double saveMoney(Users user, double money) {
        double result =-1;
        if (money > 0) {
            result = dao.withdrawMoney(user, money, 1);
        }
        return result;
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
