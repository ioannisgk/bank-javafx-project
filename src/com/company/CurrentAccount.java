package com.company;
import java.io.Serializable;
import java.util.Date;

/**
 * CurrentAccount subclass
 * Task 1: On deposit if overdraft limit is not exceeded, stop counting charging days
 * Task 2: On withdrawal if amount exceeds overdraft limit, allow up to maxlimit and start counting charging days
 **/

public class CurrentAccount extends Account implements Serializable {

    // Adding serial version ID
    // http://frequal.com/java/PracticalSerialVersionIdGuidelines.html
    private static final long serialVersionUID = 1L;

	// Class attributes
    private double overdraft = 200;
    private double chargingFee = 10;
    private double maxlimit = 220;

    // Constructor for subclass
    CurrentAccount(Customer customer, double balance) {
        super(customer, balance);
        type = "Current";
        sortcode = "102030";
        dateOpened = new Date();
        accountID = createID();
        charging = false;
    }

    // Overridden method to deposit money
    public void deposit(double amount) {
        if (amount >= 10) {
            balance = balance + amount;
            if (balance >= -(overdraft)) {
                charging = false;
            }
            System.out.println("You have made a deposit of £" + amount);
            System.out.println("Your current balance is £" + balance);
        } else {
            System.out.println("Invalid amount, please enter an amount more than £10");
        }
    }

    // Overridden method to withdraw money
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
            System.out.println("You have made a withdrawal of £" + amount);
            System.out.println("Your current balance is £" + balance);

        } else if (amount <= balance + overdraft) {
            balance = balance - amount;
            System.out.println("You have made a withdrawal of £" + amount + " with overdraft");
            System.out.println("Your current balance is £" + balance);

        } else if (amount <= balance + maxlimit) {
            charging = true;
            balance = balance - amount;
            System.out.println("You have made a withdrawal of £" + amount + " you have exceeded the overdraft limit");
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
                "Overdraft amount: " + overdraft + "\n" +
                "Overdraft charge: " + chargingFee + "\n" +
                "Balance: " + balance + "\n" +
                "Interest: " + interest * 100 + "%\n");

        customer.showDetails();
    }
}