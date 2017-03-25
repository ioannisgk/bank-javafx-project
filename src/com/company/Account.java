package com.company;
import java.io.*;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Account superclass
 * Task 1: Special attributes "noOfDaysForOverdraftLimit" and "totalMonthlyBalance" for schedulers use
 * Task 2: Provides general deposit and withdrawal methods which will be overridden if needed
 **/

public class Account implements Serializable {

    // Adding serial version ID
    // http://frequal.com/java/PracticalSerialVersionIdGuidelines.html
	private static final long serialVersionUID = 1L;

	// Class attributes
    protected Date dateOpened;
    protected String sortcode;
    private Customer customer;
    protected String type;
    protected double balance;
    protected double interest;
    protected String accountID;

    // Used to save the state that the customer exceeded the overdraft limit
    protected boolean charging;

    // Used to generate unique AccountIDs, example:
    // http://stackoverflow.com/questions/1389736/how-do-i-create-a-unique-id-in-java
    // private static AtomicLong idCounter = new AtomicLong(1000001);
    private static AtomicLong idCounter = new AtomicLong(getAtomicLongNumber());

    // Used to keep track of total monthly balance
    private double totalMonthlyBalance = 0;

    // Used to keep track of how many days the customer has exceeded his overdraft limit
    private int noOfDaysForOverdraftLimit = 0;

    // Constructor for Account class
    Account(Customer customer, double balance) {
        this.customer = customer;
        this.balance = balance;
    }

    // Class setters
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setTotalMonthlyBalance(double totalMonthlyBalance) {
        this.totalMonthlyBalance = totalMonthlyBalance;
    }
    public void setNoOfDaysForOverdraftLimit(int noOfDaysForOverdraftLimit) {
        this.noOfDaysForOverdraftLimit = noOfDaysForOverdraftLimit;
    }

    // Class getters
    public Customer getCustomer() {
        return customer;
    }
    public String getAccountID() {
        return accountID;
    }
    public Date getDateOpened() {
        return dateOpened;
    }
    public String getSortcode() {
        return sortcode;
    }
    public String getType() {
        return type;
    }
    public double getBalance() {
        return balance;
    }
    public double getInterest() {
        return interest;
    }
    public boolean getCharging() {
        return charging;
    }
    public double getTotalMonthlyBalance() {
        return totalMonthlyBalance;
    }
    public int getNoOfDaysForOverdraftLimit() {
        return noOfDaysForOverdraftLimit;
    }

    // Method to deposit money
    public void deposit(double amount) {
        if (amount >= 10) {
            balance = balance + amount;
            System.out.println("You have made a deposit of £" + amount);
            System.out.println("Your current balance is £" + balance);
            ConfirmBox.display("Message", "You have made a deposit of " + amount + " in your " + this.getType().toLowerCase() + " type of account", 0);
        } else {
            System.out.println("Invalid amount, please enter an amount more than £10");
            ConfirmBox.display("Error", "Invalid amount, please enter an amount more than £10", 1);
        }
    }

    // Method to withdraw money
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
            System.out.println("You have made a withdrawal of £" + amount);
            System.out.println("Your current balance is £" + balance);
            ConfirmBox.display("Message", "You have made a withdraw of " + amount + " in your " + this.getType().toLowerCase() + " type of account", 0);
        } else {
            System.out.println("You have insufficient funds, please enter a smaller amount");
            ConfirmBox.display("Error", "You have insufficient funds, please enter a smaller amount", 1);
        }
    }

    // Method that will be used by subclasses methods
    public void showDetails() {
    	toString();
    }

    // Method to generate unique AccountIDs and save to file so every account has unique ID
    public static String createID() {
        long longValue = idCounter.longValue();
        BufferedWriter out = null;
        try{
            out = new BufferedWriter(new FileWriter("accounts.bin"));
            // Save next value (incremented by 1) to file
            out.write(++longValue + "\n");
            out.close();
        } catch (IOException e) {
            System.out.println("Please add File named accounts.bin on project Location");
            e.printStackTrace();
        }
        return String.valueOf(idCounter.getAndIncrement());
    }

    public static long getAtomicLongNumber(){
        // Load next ID number from file
        int startValue = 1000001;
        long number = (long) startValue;
        try {
            Scanner scanner = new Scanner(new File("accounts.bin"));
            while(scanner.hasNextLong())
            {
                number = scanner.nextLong();
                break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return number;
    }

	@Override
	public String toString() {
		return "Account [ dateOpened = " + dateOpened + ", sortcode = " + sortcode +  ", type = "
				+ type + ", balance = " + balance + ", interest = " + interest + ", accountID = " + accountID + ", charging = "
				+ charging + " ]";
	}
}