package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {

    //object level properties
    private String name;
    private String email;
    private String address;
    private String userName;
    private String password;
    private Date dateOfBirth;


    /**
     *
     */
    public Customer(){

    }
    /**
     *
     * @param name
     * @param email
     * @param address
     * @param userName
     * @param password
     * @param dateOfBirth
     */
    public Customer(String name, String email, String address,String userName,String password,Date dateOfBirth){
        this.name = name;
        this.email = email;
        this.address = address;
        this.userName = userName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }
    /**
     * number of savings accounts user has
     */
    private int noOfSavingsAccounts;

    /**
     * number of deposit account use has
     */
    private int noOfDepositAccount;

    /**
     * number of current account user has
     */
    private int noOfCurrentAccounts;

    /**
     * list of all accounts customer has
     */
    private List<Account> customerAccounts  = new ArrayList<>(0);

    //class level properties
    /**
     * customer can open max 2 current account
     */
    public static final int MAX_CURRENT_ACCOUNS = 2;

    /**
     * customer can open max 1 deposit account
     */
    public static final int MAX_DEPOSIT_ACCCOUNTS = 1;

    /**
     * customer can open max 1 savings account
     */
    public static final int MAX_SAVINGS_ACCOUNTS  = 1;


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
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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

    /**
     * user can open account with given amount of balance
     * @param accountType  account type user want to open
     * @param balance opening balance
     */
    public void openAccount(String accountType,double balance){
        //if balance is negative then no need to create account
        if(balance <= 0){
            System.out.println("Customer can not create account with negative balance");
            return;
        }
        switch (accountType) {

            //open savings account
            case Main.ACCOUNT_TYPE_SAVINGS:{

                if(this.noOfSavingsAccounts >= MAX_SAVINGS_ACCOUNTS){ //check for max. count
                    System.out.println("Customer can not open more then "+MAX_SAVINGS_ACCOUNTS+" savings account.");
                }else{
                    Account savingsAccounts = new SavingsAccount(this, balance);
                    this.getCustomerAccounts().add(savingsAccounts);
                    this.noOfSavingsAccounts = this.noOfSavingsAccounts+1;
                    Main.totalAccountsInSystem.add(savingsAccounts);
                    System.out.println("Savings account created..");
                }
                break;
            }
            //open current account
            case Main.ACCOUNT_TYPE_CURRENT:{
                if(this.noOfCurrentAccounts >= MAX_CURRENT_ACCOUNS){
                    System.out.println("Customer can not open more then "+MAX_CURRENT_ACCOUNS+" current account.");
                }else{
                    Account currentAccount = new CurrentAccount(this, balance);
                    this.noOfCurrentAccounts = this.noOfCurrentAccounts+1;
                    this.getCustomerAccounts().add(currentAccount);
                    Main.totalAccountsInSystem.add(currentAccount);
                    System.out.println("Current account created..");
                }
                break;
            }
            //open deposit account
            case Main.ACCOUNT_TYPE_DEPOSIT:{
                if(this.noOfDepositAccount >= MAX_DEPOSIT_ACCCOUNTS){
                    System.out.println("Customer can not open more then "+MAX_DEPOSIT_ACCCOUNTS+" deposit account.");
                }else{
                    Account currentAccount = new DepositAccount(this, balance);
                    this.noOfDepositAccount = this.noOfDepositAccount+1;
                    this.getCustomerAccounts().add(currentAccount);
                    Main.totalAccountsInSystem.add(currentAccount);
                    System.out.println("Deposit account created..");
                }
                break;
            }
        }
    }

    /**
     * customer can withdraw amount from given account
     * @param account
     * @param amount
     */
    public void withdraw(Account account, double amount){
        if(account.getBalance()<=amount){
            System.out.println("Your balance is low");
            return;
        }
        account.withdraw(amount);
    }

    /**
     * deposit amount to given account
     * @param account
     * @param amount
     */
    public void deposit(Account account , double amount){
        account.deposit(amount);
    }

    /**
     * give details of given account
     * @param account
     */
    public void queryAccountDetails(Account account){
        account.showDetails();
    }

    /**
     * show details of all accounts of user
     */
    public void queryAllAccountDetails(){
        for (Account account : customerAccounts) {
            account.showDetails();
        }
    }

    /**
     * return balance of given account
     * @param account
     * @return
     */
    public double queryAccountBalance(Account account) {
        return account.getBalance();
    }
}