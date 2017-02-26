package com.company;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Account superclass
 **/

public class Account {
    // Class attributes
    protected Date dateOpened;
    protected String sortcode;
    protected Customer customer;
    protected String type;
    protected double balance;
    protected double interest;
    protected String accountID;

    // Used to generate unique AccountIDs, example:
    // http://stackoverflow.com/questions/1389736/how-do-i-create-a-unique-id-in-java
    private static AtomicLong idCounter = new AtomicLong(1000001);

    // Used to keep track how many days the customer has negative balance
    private int noOfDayForNegativeBalanace = 0;

    // Used to keep track of total monthly balance
    private double totalMonthlyBalance = 0;

    // Constructor for Account class
    Account(Customer customer, double balance) {
        this.customer = customer;
        this.balance = balance;
    }

    // Class setters
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getTotalMonthlyBalance() {
        return totalMonthlyBalance;
    }
    public int getNoOfDayFornegativeBalanace() {
        return noOfDayForNegativeBalanace;
    }

    // Class getters
    public double getBalance() {
        return balance;
    }
    public double getInterest() {
        return interest;
    }
    public void setTotalMonthlyBalance(double totalMonthlyBalance) {
        this.totalMonthlyBalance = totalMonthlyBalance;
    }
    public void setNoOfDayFornegativeBalanace(int noOfDayFornegativeBalanace) {
        this.noOfDayForNegativeBalanace = noOfDayFornegativeBalanace;
    }
    // public Customer getCustomer() { return this.customer; }

    // Method that will be d by subclasses methods
    public void showDetails() {

    }

    // Method to deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
            System.out.println("You have made a deposit of £" + amount);
            System.out.println("Your current balance is £" + balance);
        } else {
            System.out.println("Invalid amount, please enter an amount more than £10");
        }
    }

    // Method to withdraw money
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
            System.out.println("You have made a withdrawal of £" + amount);
            System.out.println("Your current balance is £" + balance);
        } else {
            System.out.println("You have insufficient funds, please enter a smaller amount");
        }
    }

    // Method to generate unique AccountIDs
    public static String createID()
    {
        return String.valueOf(idCounter.getAndIncrement());
    }
}