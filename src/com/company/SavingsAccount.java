package com.company;
import java.util.Date;

/**
 * SavingsAccount subclass
 **/

public class SavingsAccount extends Account {
    // Class attributes
    private int terms;

    // Constructor for subclass
    public SavingsAccount(Customer customer, double balance) {
        super(customer, balance);
        type = "Savings";
        sortcode = "708090";
        dateOpened = new Date();
        accountID = createID();
        interest = 0.03;
    }

    // Calculate number of days since account was created, example:
    // http://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-in-java
    // long noOfDaysSinceAccountOpened =  Math.abs((account.dateOpened.getTime()-System.currentTimeMillis())/86400000);

    // Need to add method: public void deposit(double amount)

    // Need to add method: public void withdraw(double amount)

    // Print details of CurrentAccount
    public void showDetails() {
        System.out.println("Account Number: " + accountID + "\n" +
                "Account Type: " + type + "\n" +
                "Sort-code: " + sortcode + "\n" +
                "Date opened: " + dateOpened + "\n" +
                "Owner Name: " + customer + "\n" +
                "Balance: " + balance + "\n" +
                "Interest: " + interest * 100 + "%\n");
    }
}