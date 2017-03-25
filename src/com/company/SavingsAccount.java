package com.company;
import java.io.Serializable;
import java.util.Date;

/**
 * SavingsAccount subclass
 * Task 1: On deposit, do not allow the balance to exceed upperLimit
 * Task 2: Do not allow withdrawal for this type of account
 **/

public class SavingsAccount extends Account implements  Serializable {

	// Class attributes
    private int terms;
    private double upperLimit = 15240;

    // Constructor for subclass
    public SavingsAccount(Customer customer, double balance, int terms) {
        super(customer, balance);
        this.terms = terms;
        type = "Savings";
        sortcode = "708090";
        dateOpened = new Date();
        accountID = createID();
        interest = 0.03;
    }

    // Calculate number of days since account was created, example:
    // http://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-in-java
    // long noOfDaysSinceAccountOpened =  Math.abs((account.dateOpened.getTime()-System.currentTimeMillis())/86400000);

    // Overridden method to deposit money
    public void deposit(double amount) {
        if (amount >= 10) {
            if ((amount + balance) <= upperLimit) {
                balance = balance + amount;
                System.out.println("You have made a deposit of £" + amount);
                System.out.println("Your current balance is £" + balance);
            } else {
                System.out.println("Your total balance can not exceed £" + upperLimit + ", please enter a lower amount");
            }
        } else {
            System.out.println("Invalid amount, please enter an amount more than £10");
        }
    }

    // Overridden method to withdraw money
    public void withdraw(double amount) {
        System.out.println("This is an Individual Savings Account, no withdrawals can be made before the end of the term of your account");
    }

    // Print details of CurrentAccount
    public void showDetails() {
        System.out.println("Account Number: " + accountID + "\n" +
                "Account Type: " + type + "\n" +
                "Sort-code: " + sortcode + "\n" +
                "Date opened: " + dateOpened + "\n" +
                "Owner Name: " + getCustomer() + "\n" +
                "Balance: " + balance + "\n" +
                "Interest: " + interest * 100 + "%\n");
    }
}