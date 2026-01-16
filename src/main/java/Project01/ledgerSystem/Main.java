package Project01.ledgerSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Project01.ledgerSystem.models.Users;


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
    public static void main(String[] args) {
        //检查是否拥有依赖文件，若没有则创建，有的话跳过
        BufferedWriter bw = null;
        BufferedWriter bw1 = null;
        try {
            File UserCSV = new File("src/main/resources/Project01/User.csv");
            File AccountDetailCSV = new File("src/main/resources/Project01/AccountDetail.csv");
            bw = new BufferedWriter(new FileWriter(UserCSV));
            if(!UserCSV.exists()){
                bw.write("用户名,密码,账户id");
            }
            bw1 = new BufferedWriter(new FileWriter(AccountDetailCSV));
            if(!AccountDetailCSV.exists()){
                bw1.write("id,时间,金额,类型,余额");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(bw!=null){
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(bw1!=null){
                    bw1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                        user.readFile();
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
                        if (withdrawMoney <= user.getBalance()&& withdrawMoney>0) {
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
                // 开始验证账户密码是否正确
                BufferedReader br = null;
                // 用于存储用户名是否在列表中,如果在csv表中找到了用户名，则为true
                boolean nameFlag = false;
                boolean passwordFlag = true;
                String str;
                try {
                    File UsersCSV = new File("src/main/resources/Project01/User.csv");
                    // if (!UsersCSV.exists()) {
                    //     UsersCSV.getParentFile().mkdir();
                    //     UsersCSV.createNewFile();
                    // }
                    br = new BufferedReader(new FileReader(UsersCSV));
                    br.readLine();
                    while ((str = br.readLine()) != null) {
                        // 将用户名和密码,i，d三个字符串存储到temp中
                        String[] temp = str.split(",");
                        if (temp[0].equals(name)) {
                            // 用户名对上了，检查密码是否正确
                            if (temp[1].equals(password)) {
                                isSuccess = true;
                                Users user = new Users(name, password);
                                user.setId(Integer.parseInt(temp[2]));
                                passwordFlag = false;
                                // //更新余额
                                // user.setBalance(getLastestBalance(String.valueOf(user.getId())));
                                user.setBalance();
                                return user;
                            } else {
                                // 密码不正确
                                System.out.println("密码错误，请重试");
                                passwordFlag = false;
                                break;
                            }
                        }
                    }
                    if (nameFlag == false && passwordFlag == true) {
                        System.out.println("用户名不存在，请重试");
                    }
                } catch (IOException e) {
                    System.out.println("无此文件，已创建");
                } finally {
                    try {
                        if (br != null) {
                            br.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        input.close();
        return null;
    }

    public static void SignInUI() {
        Scanner input = new Scanner(System.in);
        System.out.print("确定进入注册吗？(y/n)：");
        if (input.next().equalsIgnoreCase("y")) {
            System.out.println("--------------注册---------------");
            System.out.print("请输入用户名：");
            String name = input.next();
            // 检查
            BufferedReader br = null;
            try {
                br = new BufferedReader(
                        new FileReader("/Volumes/study/02-java/Project/src/main/resources/Project01/User.csv"));
                br.readLine();
                String data = null;
                while ((data = br.readLine()) != null) {
                    String[] dataStr = data.split(",");
                    if (name.equals(dataStr[0])) {
                        System.out.println("用户名已被注册，请重新输入");
                        SignInUI();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("请输入密码：");
            String password = input.next();
            Users user = new Users(name, password);
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter("src/main/resources/Project01/User.csv", true));
                bw.write(user.toString());
                bw.flush();
                System.out.println("注册成功，返回登录页面");
                logInUI();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            logInUI();
        }
        input.close();
    }
}