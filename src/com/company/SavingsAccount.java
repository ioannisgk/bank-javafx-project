package com.company;

public class SavingsAccount extends Account {
    // Constructor for subclass
    public SavingsAccount(String customer, double balance) {
        super(customer, balance);
        type = "Savings";
        accountID = createID();
    }

    // Method to withdraw money (overriden)
    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("You have insufficient funds, please enter a smaller amount");
            return;
        }
        balance = balance - amount - 100;
        System.out.println("You have made a withdrawal of £" + amount);
        System.out.println("Your current balance is £" + balance);
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
