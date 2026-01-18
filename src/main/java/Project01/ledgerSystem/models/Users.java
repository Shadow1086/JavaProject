package Project01.ledgerSystem.models;


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

    public Users(int id, String name, String password, double balance) {
        this();
        this.name = name;
        this.password = password;
        this.id = id;
        this.balance = balance;
    }

    public Users(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    
}
