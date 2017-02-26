package com.company;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Customer class
 **/

public class Customer {
    // Class attributes
    private String name;
    private String email;
    private String address;
    private String username;
    private String password;
    private Date dateOfBirth;
    private int noOfSavingsAccounts;
    private int noOfDepositAccount;
    private int noOfCurrentAccounts;

    public static final int MAX_CURRENT_ACCOUNS = 2;
    public static final int MAX_DEPOSIT_ACCCOUNTS = 1;
    public static final int MAX_SAVINGS_ACCOUNTS  = 1;

    // Class constructor
    public Customer(String name, String email, String address, String username, String password, Date dateOfBirth){
        this.name = name;
        this.email = email;
        this.address = address;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    // List of all accounts that the customer has opened
    // http://stackoverflow.com/questions/14903145/what-is-the-difference-between-list-and-arraylist (polymorphism)
    private List<Account> customerAccounts  = new ArrayList<>(0);

    // Getters and setters for Customer class
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String userName) {
        this.username = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public int getNoOfSavingsAccounts() {
        return noOfSavingsAccounts;
    }
    public void setNoOfSavingsAccounts(int noOfSavingsAccounts) {
        this.noOfSavingsAccounts = noOfSavingsAccounts;
    }
    public int getNoOfDepositAccount() {
        return noOfDepositAccount;
    }
    public void setNoOfDepositAccount(int noOfDepositAccount) {
        this.noOfDepositAccount = noOfDepositAccount;
    }
    public int getNoOfCurrentAccounts() {
        return noOfCurrentAccounts;
    }
    public void setNoOfCurrentAccounts(int noOfCurrentAccounts) {
        this.noOfCurrentAccounts = noOfCurrentAccounts;
    }
    public List<Account> getCustomerAccounts() {
        return customerAccounts;
    }
    public void setCustomerAccounts(List<Account> customerAccounts) {
        this.customerAccounts = customerAccounts;
    }

    // Method for Customer to open new account
    public void openAccount(String type, double balance){
        if (balance <= 0) {
            System.out.println("Customer can not create account with negative balance");
        }

        switch (type) {

            case Main.ACCOUNT_TYPE_CURRENT: {
                // Open a Current Account and increase "noOfCurrentAccounts" by 1
                if (this.noOfCurrentAccounts >= MAX_CURRENT_ACCOUNS) {
                    System.out.println("Customer can not open more than " + MAX_CURRENT_ACCOUNS + " Current accounts");
                } else {
                    Account currentAccount = new CurrentAccount(this, balance);
                    this.noOfCurrentAccounts = this.noOfCurrentAccounts + 1;
                    this.getCustomerAccounts().add(currentAccount);
                    Main.totalAccountsInSystem.add(currentAccount);
                    System.out.println("Current account created..");
                }
                break;
            }
            case Main.ACCOUNT_TYPE_DEPOSIT: {
                // Open a Deposit Account and increase "noOfDepositAccount" by 1
                if (this.noOfDepositAccount >= MAX_DEPOSIT_ACCCOUNTS) {
                    System.out.println("Customer can not open more than " + MAX_DEPOSIT_ACCCOUNTS + " Deposit Accounts");
                } else {
                    Account depositAccount = new DepositAccount(this, balance);
                    this.noOfDepositAccount = this.noOfDepositAccount + 1;
                    this.getCustomerAccounts().add(depositAccount);
                    Main.totalAccountsInSystem.add(depositAccount);
                    System.out.println("Deposit account created..");
                }
                break;
            }
            case Main.ACCOUNT_TYPE_SAVINGS: {
                // Open a Savings Account and increase "noOfSavingsAccounts" by 1
                if (this.noOfSavingsAccounts >= MAX_SAVINGS_ACCOUNTS) {
                    System.out.println("Customer can not open more than " + MAX_SAVINGS_ACCOUNTS + " Savings accounts");
                } else {
                    Account savingsAccount = new SavingsAccount(this, balance);
                    this.getCustomerAccounts().add(savingsAccount);
                    this.noOfSavingsAccounts = this.noOfSavingsAccounts + 1;
                    Main.totalAccountsInSystem.add(savingsAccount);
                    System.out.println("Savings account created...");
                }
                break;
            }
        }
    }

    // Method for Customer to deposit to an account
    public void deposit(Account account, double amount){
        account.deposit(amount);
    }

    // Method for Customer to withdraw from an account
    public void withdraw(Account account, double amount) { account.withdraw(amount); }

    // Method to show account details from a Customer Account
    public void queryAccountDetails(Account account){
        account.showDetails();
    }

    //Method to show account details from all Customer Accounts
    public void queryAllAccountDetails(){
        for (Account account : customerAccounts) {
            account.showDetails();
        }
    }

    // Method to show balance of a given account from a Customer Account
    public double queryAccountBalance(Account account) {
        return account.getBalance();
    }
}