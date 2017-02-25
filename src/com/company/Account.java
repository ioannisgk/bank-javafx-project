package com.company;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Account {
    // Class attributes
    protected Date dateOpened;
    protected String sortcode;
    protected Customer customer;
    protected String type;
    protected double balance;
    protected double interest;
    protected String accountID;
    protected static AtomicLong idCounter = new AtomicLong(1000001);
    /**
     * number of day  for negative balance in account
     */
    private int noOfDayForNegativeBalanace = 0;

    /**
     * total monthly balance , so we can calculate avg.balance
     */
    private double totalMonthlyBalance = 0;



    public double getTotalMonthlyBalance() {
        return totalMonthlyBalance;
    }

    public void setTotalMonthlyBalance(double totalMonthlyBalance) {
        this.totalMonthlyBalance = totalMonthlyBalance;
    }

    // Constructor for superclass
    Account(Customer customer, double balance) {
        this.customer = customer;
        this.balance = balance;
        this.interest = 0.03;
    }

    public int getNoOfDayFornegativeBalanace() {
        return noOfDayForNegativeBalanace;
    }

    public void setNoOfDayFornegativeBalanace(int noOfDayFornegativeBalanace) {
        this.noOfDayForNegativeBalanace = noOfDayFornegativeBalanace;
    }

    public void showDetails() {

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
    public Customer getCustomer() { return this.customer; }

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