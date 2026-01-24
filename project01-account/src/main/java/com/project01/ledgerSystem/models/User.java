package com.project01.ledgerSystem.models;


/**
 * ClassName: Users
 * Description:
 * 
 * 用户类，存储账户信息，ID
 * {@code @Author} Liang-ht
 * {@code @Create} 2025-12-01 13:15:48
 */

public class User {
    private String userName;
    private String userPassword;
    private double userBalance;
    private int userId;

    public User() {
    }

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User(String userName, String userPassword, double userBalance) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userBalance = userBalance;
    }

    public User(String userName, String userPassword, double userBalance, int userId) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userBalance = userBalance;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userBalance=" + userBalance +
                ", userId=" + userId +
                '}';
    }
}
