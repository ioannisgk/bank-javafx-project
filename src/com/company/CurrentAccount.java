package com.company;
import java.util.Date;

/**
 * CurrentAccount subclass
 **/

public class CurrentAccount extends Account {
    // Class attributes
    private double overdraft = 200;
    private double chargingFee;

    // Constructor for subclass
    public CurrentAccount(Customer customer, double balance) {
        super(customer, balance);
        type = "Current";
        sortcode = "102030";
        chargingFee = 10;
        dateOpened = new Date();
        accountID = createID();
    }

    // Overridden Method to withdraw money
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
            System.out.println("You have made a withdrawal of £" + amount);
            System.out.println("Your current balance is £" + balance);

        } else if (amount <= balance + overdraft) {
            balance = balance - amount;
            System.out.println("You have made a withdrawal of £" + amount + " with overdraft");
            System.out.println("Your current balance is £" + balance);

        } else {
            System.out.println("You have insufficient funds, please enter a smaller amount");
        }
    }

    // Print details of CurrentAccount
    public void showDetails() {
        System.out.println("Account Number: " + accountID + "\n" +
                "Account Type: " + type + "\n" +
                "Sort-code: " + sortcode + "\n" +
                "Date opened: " + dateOpened + "\n" +
                "Overdraft amount: " + overdraft +"\n" +
                "Overdraft charge: " + chargingFee +"\n" +
                "Owner Name: " + customer + "\n" +
                "Balance: " + balance + "\n" +
                "Interest: " + interest * 100 + "%\n");
    }
}