package com.company;

public class DepositAccount extends Account {
    // Constructor for subclass
    public DepositAccount(String customer, double balance) {
        super(customer, balance);
        type = "Deposit";
        accountID = createID();
    }

    // Print details of CurrentAccount
    public void showDetails() {
        System.out.println("Account Number: " + accountID + "\n" +
                "Account Type: " + type + "\n" +
                "Owner Name: " + customer + "\n" +
                "Balance: " + balance + "\n" +
                "Interest: " + interest * 100 + "%\n");
    }
}
