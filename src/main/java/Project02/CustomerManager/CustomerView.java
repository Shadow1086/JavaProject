package Project02.CustomerManager;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * ClassName: CustomerView
 * Description:
 * 
 * CustomerView为主模块，负责菜单的显示和处理用户操作
 * 
 * {@code @Author} Liang-ht
 * {@code @Create} 2025-12-02 17:20:06
 */
public class CustomerView {
    // 全局只使用一个CustomerList类，主要目的就是保证数据只存在一个customerList中
    private static CustomerList customerList = new CustomerList();

    public static void main(String[] args) {
        mainUI();
    }

    public static void mainUI() {
        boolean isExit = false; // 是否退出，false不退，true退出
        int fucNum;// 用户选择的功能数
        while (!isExit) {
            System.out.print("-----------------拼电商客户管理系统-----------------\n" +

                    " 1 添 加 客 户\n" +
                    " 2 修 改 客 户\n" +
                    " 3 删 除 客 户\n" +
                    " 4 客 户 列 表\n" +
                    " 5 退           出\n" +
                    "请选择(1-5):");
            fucNum = input.readInt();
            switch (fucNum) {
                case 1:
                    addUI();
                    break;
                case 2:
                    changeCustomer();
                    break;
                case 3:
                    removeCustomerUI();
                    break;
                case 4:
                    customerListUI();
                    break;
                case 5:
                    System.out.print("是否退出(y/n):");
                    char chInput = input.readString().charAt(0);
                    if (chInput == 'y' || chInput == 'Y') {
                        isExit = true;
                    }
                    break;
                default:
                    System.out.println("输入错误，请重新输入");
                    break;
            }
        }

    }

    /**
     * 功能一：添加用户
     */
    public static void addUI() {
        System.out.println("---------------------添加客户---------------------");
        System.out.print("姓名：");
        String name = input.readString();
        System.out.print("性别：");
        String sex = input.readString();
        int age = -1;
        try {
            System.out.print("年龄：");
            age = input.readInt();
        } catch (InputMismatchException e) {
            System.out.println("输入格式错误，请重新输入");
            addUI();
        }

        System.out.print("电话：");
        String tele = input.readString();
        System.out.print("邮箱：");
        String mail = input.readString();
        if (sex.length() != 1 && sex != "男" || sex != "女") {
            System.out.println("性别错误，请重试");
            addUI();
        } else if (age <= 0 || age > 120) {
            System.out.println("年龄超出范围，请重试");
            addUI();
        } else {
            Customer cust = new Customer(name, sex, age, tele, mail);
            customerList.addCustomer(cust);
            System.out.println("---------------------添加完成---------------------\n");
        }

    }

    /**
     * 1
     * 
     * 功能二：修改用户信息
     * 
     * @param cust
     */
    public static void changeCustomer() {
        System.out.println("---------------------修改客户---------------------");
        System.out.println("请输入待修改的用户的编号(-1退出):");
        int index = input.readInt();
        if (index == -1) {
            return;
        }
        Customer cust = customerList.getCust(index - 1);
        boolean isSuccess = false;
        do {
            System.out.printf("姓名(%s):", cust.getName());
            String nameNew = input.readStr();

            System.out.printf("性别(%s):", cust.getSex());
            // 正确返回true
            String sexNew = input.readStr();
            if (!sexNew.isEmpty()) {
                if (sexNew.length() != 1 && sexNew != "男" || sexNew != "女") {
                    isSuccess = true;
                    System.out.println("性别错误，请重试");
                    break;
                }
            }

            System.out.printf("年龄(%d):", cust.getAge());
            String ageNewStr = input.readStr();
            if (!ageNewStr.isEmpty()) {
                int ageNew = Integer.parseInt(ageNewStr);
                if (ageNew <= 0 || ageNew > 120) {
                    isSuccess = true;
                    System.out.println("年龄超出范围，请重试");
                    break;
                }
                cust.setAge(ageNew);
            }

            // else{

            // }

            System.out.printf("电话(%s):", cust.getTele());
            String teleNew = input.readStr();

            System.out.printf("邮箱(%s):", cust.getMail());
            String mailNew = input.readStr();
            cust.setMail(mailNew);
            cust.setSex(sexNew);

            if (!mailNew.isEmpty()) {
                cust.setTele(teleNew);
            }
            if (!nameNew.isEmpty()) {
                cust.setName(nameNew);
            }

        } while (isSuccess);

    }

    /**
     * 功能三：删除用户
     */
    public static void removeCustomerUI() {
        System.out.println("---------------------删除客户---------------------");
        System.out.print("请选择待删除客户编号(-1退出):");
        int index = input.readInt();
        System.out.print("确认是否删除(y/n)");
        String confirm = input.readString();
        try {
            if (confirm.equalsIgnoreCase("y")) {
                customerList.removeCustomer(index);
            }
        } catch (Exception e) {
            System.out.println("出错了，请检查输入是否正确");
        }
        System.out.println("---------------------删除完成---------------------\n");
    }

    /**
     * 功能四：输出客户列表
     */
    public static void customerListUI() {

        // 遍历输出用户信息
        customerList.printCustomerInfo();
    }

}

/**
 * 封装Scanner类
 */
class input {
    private static Scanner scanner = new Scanner(System.in);

    public static String readString() {
        return scanner.next();
    }

    public static String readStr() {
        return scanner.nextLine();
    }

    public static int readInt() {
        return scanner.nextInt();
    }

    public static double readDouble() {
        return scanner.nextDouble();
    }
}
