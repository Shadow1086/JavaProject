package Project02.CustomerManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        // File file = new File("src/main/resources/Project02/Customer.csv");
        // BufferedWriter bw = null;
        // try {
        // if (!file.exists()) {
        // bw = new BufferedWriter(new FileWriter(file));
        // bw.write("姓名,性别,年龄,电话,邮箱\n");
        // }
        // } catch (IOException ioException) {
        // System.err.println("初始化文件出错");
        // } finally {
        // try {
        // if (bw != null) {
        // bw.close();
        // }
        // } catch (IOException ioException1) {
        // System.err.println("初始化文件的bw资源关闭失败");
        // }
        // ReadCustomer();
        // }
        ReadCustomer();
        mainUI();
        System.out.println(customerList.customerLinkedList.toString());
        customerList.customerLinkedList.keepCustomerCsv();
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
        System.out.print("姓名(输入-1退出):");
        String name = input.readString();
        if(name.equals("-1")){
            return;
        }
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
        if (sex.length() != 1 && (sex != "男" || sex != "女")) {
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
            input.readStr();
            System.out.printf("姓名(%s):", cust.getName());
            String nameNew = input.readStr();

            System.out.printf("性别(%s):", cust.getSex());
            // input.readStr();
            // 正确返回true
            String sexNew = input.readStr();
            boolean sexIsNull = false;
            if (!sexNew.isEmpty()) {
                if (sexNew.length() != 1 && sexNew != "男" || sexNew != "女") {
                    isSuccess = true;
                    System.out.println("性别错误，请重试");
                    break;
                }
            }else{
                sexIsNull = true;
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

            System.out.printf("电话(%s):", cust.getTele());
            String teleNew = input.readStr();

            System.out.printf("邮箱(%s):", cust.getMail());
            String mailNew = input.readStr();
            if(!mailNew.isEmpty()){
                cust.setMail(mailNew);
            }
            
            if(!sexIsNull){
                cust.setSex(sexNew);
            }
            

            if (!mailNew.isEmpty()) {
                cust.setMail(mailNew);
            }
            if(!teleNew.isEmpty()){
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
        if(index==-1){
            return;
        }
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

    /**
     * 读写CSV文件保存客户信息
     * 保存
     */
    public static boolean keepCustomer(Customer customer, boolean isOverride) {
        BufferedWriter bw = null;
        BufferedWriter bw1 = null; // 用来重写整个csv文件
        try {
            File file = new File("src/main/resources/Project02/Customer.csv");
            if (!isOverride) {
                bw1 = new BufferedWriter(new FileWriter(file, false));
                bw1.write("姓名,性别,年龄,电话,邮箱\n");
                bw1.write(customer.toStringForCsv() + "\n");
            } else {
                bw = new BufferedWriter(new FileWriter(file, true));
                bw.write(customer.toStringForCsv() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                System.err.print("bw资源关闭出错");
            }

            try {
                if (bw1 != null) {
                    bw1.close();
                }
            } catch (IOException e) {
                System.err.print("bw资源关闭出错");
            } 
        }
        return true;
    }

    /**
     * 读取用户信息并保存到当前运行中的链表中
     */
    public static void ReadCustomer() {
        BufferedReader br = null;
        try {
            File file = new File("src/main/resources/Project02/Customer.csv");
            br = new BufferedReader(new FileReader(file));
            String customerInfo = null;
            br.readLine();
            while ((customerInfo = br.readLine()) != null) {
                String[] custStr = customerInfo.split(",");
                Customer cust = new Customer(custStr[0], custStr[1], Integer.parseInt(custStr[2]), custStr[3],
                        custStr[4]);
                customerList.customerLinkedList.addlast(cust);
            }

        } catch (IOException e) {
            System.out.println("出错了，请重试");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.err.println("br资源关系出错");
            }
        }
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
