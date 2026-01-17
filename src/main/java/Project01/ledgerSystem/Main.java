package Project01.ledgerSystem;

import java.util.InputMismatchException;
import java.util.Scanner;

import Project01.ledgerSystem.models.Users;
import Project01.ledgerSystem.service.UserServiceImpl;

/**
 * ClassName: Main
 * Description:
 * 
 * 系统的总入口
 * 
 * {@code @Author} Liang-ht
 * {@code @Create} 2025-11-30 17:40:16
 * Version : 1.0
 */
public class Main {
    private static final UserServiceImpl userSer = new UserServiceImpl();

    public static void main(String[] args) {
        boolean isExit = false;
        while (!isExit) {
            Users user = logInUI();
            if (user != null) {
                isExit = mainUI(user);
            } else {
                System.out.println("登录错误");
            }
        }
    }

    public static boolean mainUI(Users user) {
        Scanner input = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("-----------------谷粒记账软件-----------------\n" +
                        "1 收支明细\n" +
                        "2 登记收入\n" +
                        "3 登记支出\n" +
                        "4 返回登录界面\n" +
                        "5 退    出\n" +
                        "请选择(1-5)：");
                int fucNum = input.nextInt();
                switch (fucNum) {
                    case 1:
                        System.out.println("-----------------------收支明细-------------------");
                        break;
                    case 2:
                        System.out.println("-----------------------存款-------------------");
                        System.out.print("请输入存入的金额：");
                        double money = input.nextDouble();
                        if (money > 0) {
                            user.saveMoney(money);
                        }
                        break;
                    case 3:
                        System.out.println("-----------------------取款-------------------");
                        System.out.print("请输入取出的金额：");
                        double withdrawMoney = input.nextDouble();
                        if (withdrawMoney <= user.getBalance() && withdrawMoney > 0) {
                            user.withdrawMoney(withdrawMoney);
                        } else {
                            System.out.println("取出金额不正确，可能是余额不足，请检查");
                        }
                        break;
                    case 4:
                        user = logInUI();
                        break;
                    case 5:
                        System.out.print("确定退出？(y/n):");
                        String twiceConfirm = input.next();
                        if (twiceConfirm.equalsIgnoreCase("y")) {
                            return true;
                        }
                        break;
                    default:
                        System.out.println("输入错误，请重新输入");
                        break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("输入错误，请重试");
            mainUI(user);
        }
        input.close();
        return true;
    }

    public static Users logInUI() {
        Scanner input = new Scanner(System.in);
        // 初始化界面：
        boolean isSuccess = false;
        while (!isSuccess) {
            System.out.print("-----------------登录-------------\n" +
                    "提示：若无账户请在输入用户名时输入0进入注册页面\n" +
                    "请输入用户名：");
            String name = input.next();
            if (name.equals("0")) {
                SignInUI();
                break;
            } else {
                System.out.print("请输入密码：");
                String password = input.next();
                if(userSer.logIn(name, password)){
                    System.out.println("登陆成功");
                }else{
                    System.out.println("登陆失败，请检查用户名密码是否错误");
                }
            }
        }
        return null;
    }

    public static void SignInUI() {
        Scanner input = new Scanner(System.in);
        System.out.print("确定进入注册吗？(y/n)：");
        if (input.next().equalsIgnoreCase("y")) {
            System.out.print("请输入用户名：");
            String name = input.next();
            System.out.print("请输入密码：");
            String password = input.next();
            if (userSer.signUp(name, password)) {
                System.out.println("注册成功，返回至登录界面重新登录");
            } else {
                System.out.println("注册失败，请检查输入格式是否正确");
            }

        } else {
            logInUI();
        }
        input.close();
    }
}