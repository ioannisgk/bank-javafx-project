package com.company;

import java.util.Date;

public class CurrentAccount extends Account {
    // Class attributes
    private double overdraft = 200;
    private double charge = 0;
    private Date dateCharged;
    private boolean charging = false;

    // Constructor for subclass
    public CurrentAccount(String customer, double balance) {
        super(customer, balance);
        type = "Current";
        sortcode = "102030";
        dateOpened = new Date();
        accountID = createID();
    }

    // Method to deposit money
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount, please enter an amount more than £10");
            return;
        }
        else {
            balance = balance + amount;
            if (balance >= 0) {
                // Overdraft paid back, so don't mark account as charging
                charging = false;

                System.out.println("You have made a deposit of £" + amount);
                System.out.println("Your current balance is £" + balance);
            }
            return;
        }
    }

    // Method to withdraw money
    public void withdraw(double amount) {
        if (amount > balance + overdraft) {
            System.out.println("You have insufficient funds, please enter a smaller amount");
            return;
        }
        else if (amount > balance) {
            balance = balance - amount;

            // Overdraft, so mark account with charging and save the date
            charging = true;
            dateCharged = new Date();

            System.out.println("You have made a withdrawal of £" + amount + " with overdraft");
            System.out.println("Your current balance is £" + balance);
            return;
        }
        else {
            balance = balance - amount;
            System.out.println("You have made a withdrawal of £" + amount);
            System.out.println("Your current balance is £" + balance);
        }
    }

    // Print details of CurrentAccount
    public void showDetails() {
        System.out.println("Account Number: " + accountID + "\n" +
                "Account Type: " + type + "\n" +
                "Sort-code: " + sortcode + "\n" +
                "Date opened: " + dateOpened + "\n" +
                "Overdraft amount: " + overdraft +"\n" +
                "Overdraft charge: " + charge +"\n" +
                "Owner Name: " + customer + "\n" +
                "Balance: " + balance + "\n" +
                "Interest: " + interest * 100 + "%\n");
    }
}