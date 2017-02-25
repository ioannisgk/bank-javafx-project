package com.company;

import java.util.Date;

public class DepositAccount extends Account {
    // Constructor for subclass
    public DepositAccount(Customer customer, double balance) {
        super(customer, balance);
        type = "Deposit";
        sortcode = "405060";
        dateOpened = new Date();
        accountID = createID();
    }

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