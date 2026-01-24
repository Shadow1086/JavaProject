package com.project01.ledgerSystem.dto;

import java.util.Date;

/**
 * 类名: detail
 * 创建时间: 2026/1/23 00:01
 * 项目描述:
 *
 * @author htLiang
 */
public class Detail {
    private String userName;
    private String userId;
    private String transactionType;
    private double amount;
    private Date transactionTime;

    public Detail() {
    }

    public Detail(String userName, String userId, String transactionType, double amount, Date transactionTime) {
        this.userName = userName;
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionTime = transactionTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Override
    public String toString() {
        return userName + '\t' +
                transactionType + '\t' +
               amount + "\t" +
                transactionTime;
    }
}
