package com.company;

import java.util.concurrent.atomic.AtomicLong;

public class Account {
    // Class attributes
    protected String customer;
    protected String type;
    protected double balance;
    protected double interest;
    protected String accountID;
    protected static AtomicLong idCounter = new AtomicLong(1000001);

    // Constructor for superclass
    Account(String customer, double balance) {
        this.customer = customer;
        this.balance = balance;
        this.interest = 0.03;
    }

    // Class setters
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setInterest(double interest) {
        this.interest = interest;
    }

    // Class getters
    public double getBalance() {
        return balance;
    }
    public double getInterest() {
        return interest;
    }
    public String getCustomer() { return customer; }

    // Method to deposit money
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount, please enter an amount more than £10");
            return;
        }
        balance = balance + amount;
        System.out.println("You have made a deposit of £" + amount);
        System.out.println("Your current balance is £" + balance);
    }

    // Method to withdraw money
    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("You have insufficient funds, please enter a smaller amount");
            return;
        }
        balance = balance - amount;
        System.out.println("You have made a withdrawal of £" + amount);
        System.out.println("Your current balance is £" + balance);
    }

    // Method to generate unique AccountIDs
    public static String createID()
    {
        return String.valueOf(idCounter.getAndIncrement());
    }
}
