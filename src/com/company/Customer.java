package com.company;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Customer class
 * Task 1: It also has a list of Accounts as an attribute to keep track of customer's accounts
 * Task 2: Open a new account only if limits of accounts have not been reached
 * Task 3: The openNewAccount method increases the number of accounts created and adds it to the CustomerAccounts list
 **/

public class Customer implements Serializable {

    // Adding serial version ID
    // http://frequal.com/java/PracticalSerialVersionIdGuidelines.html
    private static final long serialVersionUID = 1L;

	// Class attributes
    private String firstname;
    private String surname;
    private String email;
    private String address;
    private String phone;
    private String username;
    private String password;
    private Date dateOfBirth;
    private int noOfSavingsAccounts = 0;
    private int noOfDepositAccount = 0;
    private int noOfCurrentAccounts = 0;

    public static final int MAX_CURRENT_ACCOUNTS = 2;
    public static final int MAX_DEPOSIT_ACCOUNTS = 1;
    public static final int MAX_SAVINGS_ACCOUNTS = 1;

    public Customer() {

    }

    // Class constructor
    public Customer(String firstname, String surname, String email, String address, String username, String password, Date dateOfBirth) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    // List of all accounts that the customer has opened
    // http://stackoverflow.com/questions/14903145/what-is-the-difference-between-list-and-arraylist (polymorphism)
    private List<Account> customerAccounts = new ArrayList<>(0);

    // Class setters
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setDateOfBirth(Date dob) {
        dateOfBirth = dob;
    }

    // Class getters
    public String getFirstname() {
        return firstname;
    }
    public String getSurname() {
        return surname;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() { return password; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public int getNoOfCurrentAccounts() { return noOfCurrentAccounts; }
    public int getNoOfDepositAccount() {
        return noOfDepositAccount;
    }
    public int getNoOfSavingsAccounts() {
        return noOfSavingsAccounts;
    }
    public List<Account> getCustomerAccounts() {
        return customerAccounts;
    }

    // Method for Customer to open a new Account
    public boolean openNewAccount(String type, double balance, int terms) {
        boolean result = true;
        if (balance < 0) {
            System.out.println("Customer cannot create account with negative balance");
            ConfirmBox.display("","Error", "Customer cannot create account with negative balance");
            result = false;
        }

        switch (type) {

            case Main.ACCOUNT_TYPE_CURRENT: {
                // Open a Current Account and increase "noOfCurrentAccounts" by 1
            	if (this.noOfCurrentAccounts >= MAX_CURRENT_ACCOUNTS) {
                    System.out.println("Customer can not open more than " + MAX_CURRENT_ACCOUNTS + " Current accounts");
                    ConfirmBox.display("","Error", "Customer can not open more than " + MAX_CURRENT_ACCOUNTS + " Current accounts");
                    result = false;
                } else {
                    Account currentAccount = new CurrentAccount(this, balance);
                    this.noOfCurrentAccounts = this.noOfCurrentAccounts + 1;
                    this.getCustomerAccounts().add(currentAccount);
                    //Main.totalAccountsInSystem.add(currentAccount);
                    System.out.println("Current account created..");
                }
                break;
            }
            case Main.ACCOUNT_TYPE_DEPOSIT: {
                // Open a Deposit Account and increase "noOfDepositAccount" by 1
                if (this.noOfDepositAccount >= MAX_DEPOSIT_ACCOUNTS) {
                    System.out.println("Customer can not open more than " + MAX_DEPOSIT_ACCOUNTS + " Deposit Accounts");
                    ConfirmBox.display("","Error", "Customer can not open more than " + MAX_DEPOSIT_ACCOUNTS + " Deposit Accounts");
                    result = false;
                } else {
                    Account depositAccount = new DepositAccount(this, balance);
                    this.noOfDepositAccount = this.noOfDepositAccount + 1;
                    this.getCustomerAccounts().add(depositAccount);
                    //Main.totalAccountsInSystem.add(depositAccount);
                    System.out.println("Deposit account created..");
                }
                break;
            }
            case Main.ACCOUNT_TYPE_SAVINGS: {
                // Open a Savings Account and increase "noOfSavingsAccounts" by 1
                if (this.noOfSavingsAccounts >= MAX_SAVINGS_ACCOUNTS) {
                    System.out.println("Customer can not open more than " + MAX_SAVINGS_ACCOUNTS + " Savings accounts");
                    ConfirmBox.display("","Error", "Customer can not open more than " + MAX_SAVINGS_ACCOUNTS + " Savings accounts");
                    result = false;
                } else {
                    Account savingsAccount = new SavingsAccount(this, balance, terms);
                    this.noOfSavingsAccounts = this.noOfSavingsAccounts + 1;
                    this.getCustomerAccounts().add(savingsAccount);
                    //Main.totalAccountsInSystem.add(savingsAccount);
                    System.out.println("Savings account created...");
                }
                break;
            }
        }
        return result;
    }

    // Method to authenticate customer information
    public boolean equals(Customer other){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dob1 = formatter.format(this.getDateOfBirth());
        String dob2 = formatter.format(this.getDateOfBirth());
        if (this.getFirstname().equals(other.getFirstname())&&
                this.getSurname().equals(other.getSurname()) &&
                dob1.equals(dob2)) {
            return true;
        }
        return false;
    }

    // Method for Customer to deposit to an account
    public void deposit(Account account, double amount) {
        account.deposit(amount);
    }

    // Method for Customer to withdraw from an account
    public void withdraw(Account account, double amount) {
        account.withdraw(amount);
    }

    // Method to show account details from a Customer Account
    public void queryAccountDetails(Account account) {
        account.showDetails();
    }

    // Method to show account details from all Customer Accounts
    public void queryAllAccountDetails() {
        for (Account account : customerAccounts) {
            account.showDetails();
        }
    }

    // Method to show balance of a given account from a Customer Account
    public double queryAccountBalance(Account account) {
        return account.getBalance();
    }

    // Print details of Customer
    public void showDetails() {
        System.out.println("Customer Name: " + firstname + " " + surname + "\n" +
                "Email: " + email + "\n" +
                "Address: " + address + "\n" +
                "Username: " + username + "\n" +
                "Password: " + password + "\n" +
                "Date of Birth: " + dateOfBirth + "\n" +
                "Current Accounts: " + noOfCurrentAccounts + "\n" +
                "Deposit Accounts: " + noOfDepositAccount + "\n" +
                "Savings Accounts: " + noOfSavingsAccounts + "\n");
    }

	@Override
	public String toString() {
		return "Customer [ name = " + firstname + " " + surname + ", email = " + email + ", address = " + address + ", username = " + username
				+ ", password = " + password + ", dateOfBirth = " + dateOfBirth + ", noOfSavingsAccounts = "
				+ noOfSavingsAccounts + ", noOfDepositAccount = " + noOfDepositAccount + ", noOfCurrentAccounts = "
				+ noOfCurrentAccounts + ", customerAccounts = " + customerAccounts + " ]";
	}
}