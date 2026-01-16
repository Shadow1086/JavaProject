package Project01.ledgerSystem.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: Users
 * Description:
 * 
 * 用户类，存储账户信息，ID
 * {@code @Author} Liang-ht
 * {@code @Create} 2025-12-01 13:15:48
 */

public class Users {
    private String name;
    private String password;
    private double balance;
    private int id;

    public Users() {
    }

    public Users(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance() {
        balance = getLastestBalance(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + "," + password + "," + id;
    }

    // 获取时间
    public String getTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(now);
    }

    // 方法：存钱
    public void saveMoney(double money) {
        this.balance += money;
        writeFile(String.valueOf(id) + "," + getTime() + ",+" + money + "," + "存入," + balance + "\n");
        System.out.println("存入 " + money + " 元成功，余额：" + balance);
    }

    // 方法：取钱：
    public void withdrawMoney(double money) {
        if (money < balance) {
            this.balance -= money;
            writeFile(String.valueOf(id) + "," + getTime() + ",-" + money + "," + "取出," + balance + "\n");
            System.out.println("取出 " + money + " 元成功，余额：" + balance);
        }
    }

    // 写入明细
    public void writeFile(String data) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("src/main/resources/Project01/AccountDetail.csv", true));
            bw.write(data);
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
    }

    // 读出明细
    public void readFile() {
        BufferedReader br = null;
        boolean isNull = true;
        try {
            br = new BufferedReader(new FileReader("src/main/resources/Project01/AccountDetail.csv"));
            br.readLine();
            String data = null;
            while ((data = br.readLine()) != null) {
                if (data.startsWith(String.valueOf(id))) {
                    String[] dataStr = data.split(",");
                    System.out.println("id:" + dataStr[0] + "\t时间：" + dataStr[1] + "\t类型：" + dataStr[3] + "\t金额："
                            + dataStr[2] + "\t余额：" + dataStr[4]);
                    isNull = false;
                }
            }
            if (isNull) {
                System.out.println("暂无记录");
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
    }

    public double getLastestBalance(int id) {
        BufferedReader br = null;
        double lastestBalance = 0;
        try {
            br = new BufferedReader(new FileReader("src/main/resources/Project01/AccountDetail.csv"));
            br.readLine();
            String data = null;
            while ((data = br.readLine()) != null) {
                if (data.startsWith(String.valueOf(id))) {
                    String[] dataStr = data.split(",");
                    lastestBalance = Double.parseDouble(dataStr[4]);
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
        return lastestBalance;
    }
}
