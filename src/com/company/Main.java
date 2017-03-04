package com.company;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.Serializable;
/**
 * Main class
 **/

public class Main implements Serializable {

    // List that contains all accounts in the system
    public static List<Account> totalAccountsInSystem = new ArrayList<>();

    // Types of Accounts in the system
    public static final String ACCOUNT_TYPE_SAVINGS = "savings";
    public static final String ACCOUNT_TYPE_DEPOSIT = "deposit";
    public static final String ACCOUNT_TYPE_CURRENT = "current";

    //Variables regarding writing the file
    /* private static final File FILE_NAME = new File("customers.bin");
    private static final File file = new File("customers.bin");
    private static ObjectInputStream objectInput;
    private static FileInputStream fileInput;
    private static ObjectOutputStream objectOutput;
    private static FileOutputStream fileOutput;
    */

    // A new object of the class Customer is created
    private static Customer customer = new Customer();
    private static List<Customer> customerList = new ArrayList<>();

    //The scanner is used to read input data
    private static Scanner scanner = new Scanner(System.in);

    ///////////////////////////////////////////////////////////////////

    // Main method
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // showMenu();
        System.out.println("Welcome!!!");
        System.out.println("A: Open a New Account");
        System.out.println("B: Deposit Money");
        System.out.println("C: Withdraw Money");
        System.out.println("D: Query Account Balance");
        System.out.println("E: Query Account Details");
        System.out.println("F: Query Account Details (without username/password)");
        System.out.println("G: Search all Current Accounts");

        boolean exists = false;
        String input = scanner.next();
        Customer newcustomer = new Customer();

        switch (input.toUpperCase()) {
            case "A":
                System.out.println("A: OPENING ACCOUNT");
                System.out.println("Enter your username: ");
                String username = scanner.next();
                System.out.println("Enter your password: ");
                String password = scanner.next();
                for (Customer customer : customerList) {
                    if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                        newcustomer = customer;
                        exists = true;
                    }
                }
                if (!exists) {
                    System.out.println("Invalid username or password!\nPlease, create a new Customer.");
                    System.out.println("Please, input your name.");
                    String name = scanner.next();
                    System.out.println("Please, input your email address.");
                    String email = scanner.next();
                    System.out.println("Please, input your address.");
                    String address = scanner.next();
                    System.out.println("Please, input your username.");
                    username = scanner.next();
                    System.out.println("Please, input your password.");
                    password = scanner.next();
                    newcustomer = new Customer(name, email, address, username, password, new Date());
                    customerList.add(newcustomer);
                    // writeFile();
                } else {
                    // Once the customer is created the user is asked to input the account type
                    System.out.println("Please, enter the account type. (current, deposit, savings)");
                    String type = scanner.next();
                    switch (type.toLowerCase()) {
                        case "current":
                            customer.openNewAccount(ACCOUNT_TYPE_CURRENT, 0, 0);
                            break;
                        case "deposit":
                            customer.openNewAccount(ACCOUNT_TYPE_DEPOSIT, 0, 0);
                            break;
                        case "savings":
                            System.out.println("How many terms for this account? (1, 2, 3)");
                            int terms = scanner.nextInt();
                            customer.openNewAccount(ACCOUNT_TYPE_SAVINGS, 0, terms);
                            break;
                    }
                }
                System.out.println("Press ENTER to continue!");
                System.in.read();
                break;

            case "B":
                System.out.println("B: DEPOSIT MONEY");
                System.out.println("Enter your username: ");
                username = scanner.next();
                System.out.println("Enter your password: ");
                password = scanner.next();
                for (Customer customer : customerList) {
                    if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                        newcustomer = customer;
                        exists = true;
                    }
                }
                if (exists) {
                    // Once the customer is created the user is asked to input the account type
                    System.out.println("Please, enter the account type to deposit to. (current, deposit, savings)");
                    String type = scanner.next();
                    System.out.println("Please, input the amount you would like to deposit.");
                    double amount = scanner.nextDouble();
                    int sumOfAccounts = newcustomer.getNoOfCurrentAccounts() + newcustomer.getNoOfDepositAccount() + newcustomer.getNoOfSavingsAccounts();
                    switch (type.toLowerCase()) {
                        case "current":
                            if (newcustomer.getNoOfCurrentAccounts() == 1) {
                                for (int i = 0; i < sumOfAccounts; i++) {
                                    if (newcustomer.getCustomerAccounts().get(i).type.equals("Current")) {
                                        newcustomer.deposit(newcustomer.getCustomerAccounts().get(i), amount);
                                    }
                                }

                            } else if (newcustomer.getNoOfCurrentAccounts() == 2) {
                                System.out.print("You have two current accounts. Please input an account ID (");
                                for (int i = 0; i < sumOfAccounts; i++) {
                                    if (newcustomer.getCustomerAccounts().get(i).type.equals("Current")) {
                                        System.out.print(newcustomer.getCustomerAccounts().get(i).accountID + " ");
                                    }
                                }
                                System.out.print(").\n");
                                int selectedID = scanner.nextInt();

                                for (int i = 0; i < sumOfAccounts; i++) {
                                    if (Integer.parseInt(newcustomer.getCustomerAccounts().get(i).accountID) == selectedID) {
                                        newcustomer.deposit(newcustomer.getCustomerAccounts().get(i), amount);
                                    }
                                }
                            }
                            break;
                        case "deposit":
                            for (int i = 0; i < sumOfAccounts; i++) {
                                if (newcustomer.getCustomerAccounts().get(i).type.equals("Deposit")) {
                                    newcustomer.deposit(newcustomer.getCustomerAccounts().get(i), amount);
                                }
                            }
                            break;
                        case "savings":
                            for (int i = 0; i < sumOfAccounts; i++) {
                                if (newcustomer.getCustomerAccounts().get(i).type.equals("Savings")) {
                                    newcustomer.deposit(newcustomer.getCustomerAccounts().get(i), amount);
                                }
                            }
                            break;
                    }
                } else {
                    System.out.println("Invalid username or password!");
                }
                System.out.println("Press ENTER to continue!");
                System.in.read();
                break;

            case "C":
                System.out.println("C: WITHDRAW MONEY");
                System.out.println("Enter your username: ");
                username = scanner.next();
                System.out.println("Enter your password: ");
                password = scanner.next();
                for (Customer customer : customerList) {
                    if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                        newcustomer = customer;
                        exists = true;
                    }
                }
                if (exists) {
                    // Once the customer is created the user is asked to input the account type
                    System.out.println("Please, enter the account type to withdraw from. (current, deposit, savings)");
                    String type = scanner.next();
                    System.out.println("Please, input the amount you would like to withdraw.");
                    double amount = scanner.nextDouble();
                    int sumOfAccounts = newcustomer.getNoOfCurrentAccounts() + newcustomer.getNoOfDepositAccount() + newcustomer.getNoOfSavingsAccounts();
                    switch (type.toLowerCase()) {
                        case "current":
                            if (newcustomer.getNoOfCurrentAccounts() == 1) {
                                for (int i = 0; i < sumOfAccounts; i++) {
                                    if (newcustomer.getCustomerAccounts().get(i).type.equals("Current")) {
                                        newcustomer.withdraw(newcustomer.getCustomerAccounts().get(i), amount);
                                    }
                                }

                            } else if (newcustomer.getNoOfCurrentAccounts() == 2) {
                                System.out.print("You have two current accounts. Please input an account ID (");
                                for (int i = 0; i < sumOfAccounts; i++) {
                                    if (newcustomer.getCustomerAccounts().get(i).type.equals("Current")) {
                                        System.out.print(newcustomer.getCustomerAccounts().get(i).accountID + " ");
                                    }
                                }
                                System.out.print(").\n");
                                int selectedID = scanner.nextInt();

                                for (int i = 0; i < sumOfAccounts; i++) {
                                    if (Integer.parseInt(newcustomer.getCustomerAccounts().get(i).accountID) == selectedID) {
                                        newcustomer.withdraw(newcustomer.getCustomerAccounts().get(i), amount);
                                    }
                                }
                            }
                            break;
                        case "deposit":
                            for (int i = 0; i < sumOfAccounts; i++) {
                                if (newcustomer.getCustomerAccounts().get(i).type.equals("Deposit")) {
                                    newcustomer.withdraw(newcustomer.getCustomerAccounts().get(i), amount);
                                }
                            }
                            break;
                        case "savings":
                            for (int i = 0; i < sumOfAccounts; i++) {
                                if (newcustomer.getCustomerAccounts().get(i).type.equals("Savings")) {
                                    newcustomer.withdraw(newcustomer.getCustomerAccounts().get(i), amount);
                                }
                            }
                            break;
                    }
                } else {
                    System.out.println("Invalid username or password!");
                }
                System.out.println("Press ENTER to continue!");
                System.in.read();
                break;

            case "D":
                System.out.println("D: QUERY ACCOUNT BALANCE");
                System.out.println("Enter your username: ");
                username = scanner.next();
                System.out.println("Enter your password: ");
                password = scanner.next();
                for (Customer customer : customerList) {
                    if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                        newcustomer = customer;
                        exists = true;
                    }
                }
                if (exists) {
                    // Once the customer is created the user is asked to input the account type
                    System.out.println("Please, enter the account type to see its balance. (current, deposit, savings)");
                    String type = scanner.next();
                    int sumOfAccounts = newcustomer.getNoOfCurrentAccounts() + newcustomer.getNoOfDepositAccount() + newcustomer.getNoOfSavingsAccounts();
                    switch (type.toLowerCase()) {
                        case "current":
                            if (newcustomer.getNoOfCurrentAccounts() == 1) {
                                for (int i = 0; i < sumOfAccounts; i++) {
                                    if (newcustomer.getCustomerAccounts().get(i).type.equals("Current")) {
                                        double balance = newcustomer.queryAccountBalance(newcustomer.getCustomerAccounts().get(i));
                                        System.out.println("Your current balance is £" + balance);
                                    }
                                }

                            } else if (newcustomer.getNoOfCurrentAccounts() == 2) {
                                System.out.print("You have two current accounts. Please input an account ID (");
                                for (int i = 0; i < sumOfAccounts; i++) {
                                    if (newcustomer.getCustomerAccounts().get(i).type.equals("Current")) {
                                        System.out.print(newcustomer.getCustomerAccounts().get(i).accountID + " ");
                                    }
                                }
                                System.out.print(").\n");
                                int selectedID = scanner.nextInt();

                                for (int i = 0; i < sumOfAccounts; i++) {
                                    if (Integer.parseInt(newcustomer.getCustomerAccounts().get(i).accountID) == selectedID) {
                                        double balance = newcustomer.queryAccountBalance(newcustomer.getCustomerAccounts().get(i));
                                        System.out.println("Your current balance is £" + balance);
                                    }
                                }
                            }
                            break;
                        case "deposit":
                            for (int i = 0; i < sumOfAccounts; i++) {
                                if (newcustomer.getCustomerAccounts().get(i).type.equals("Deposit")) {
                                    double balance = newcustomer.queryAccountBalance(newcustomer.getCustomerAccounts().get(i));
                                    System.out.println("Your current balance is £" + balance);
                                }
                            }
                            break;
                        case "savings":
                            for (int i = 0; i < sumOfAccounts; i++) {
                                if (newcustomer.getCustomerAccounts().get(i).type.equals("Savings")) {
                                    double balance = newcustomer.queryAccountBalance(newcustomer.getCustomerAccounts().get(i));
                                    System.out.println("Your current balance is £" + balance);
                                }
                            }
                            break;
                    }
                } else {
                    System.out.println("Invalid username or password!");
                }
                System.out.println("Press ENTER to continue!");
                System.in.read();
                break;

            case "E":
                System.out.println("E: QUERY ACCOUNT DETAILS");
                System.out.println("Enter your username: ");
                username = scanner.next();
                System.out.println("Enter your password: ");
                password = scanner.next();
                for (Customer customer : customerList) {
                    if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                        newcustomer = customer;
                        exists = true;
                    }
                }
                if (exists) {
                    int sumOfAccounts = newcustomer.getNoOfCurrentAccounts() + newcustomer.getNoOfDepositAccount() + newcustomer.getNoOfSavingsAccounts();
                    for (int i = 0; i < sumOfAccounts; i++) {
                        newcustomer.queryAccountDetails(newcustomer.getCustomerAccounts().get(i));
                    }

                } else {
                    System.out.println("Invalid username or password!");
                }
                System.out.println("Press ENTER to continue!");
                System.in.read();
                break;

            case "F":
                System.out.println("F: QUERY ACCOUNT DETAILS WITHOUT USERNAME/PASS");
                System.out.println("Please, enter your name.");
                String name = scanner.next();
                System.out.println("Please, enter your email.");
                String email = scanner.next();
                System.out.println("Please, enter your address");
                String address = scanner.next();
                for (Customer customer : customerList) {
                    if (customer.getName().equals(name) && customer.getEmail().equals(email) && customer.getAddress().equals(address)) {
                        newcustomer = customer;
                        exists = true;
                    }
                }
                if (exists) {
                    int sumOfAccounts = newcustomer.getNoOfCurrentAccounts() + newcustomer.getNoOfDepositAccount() + newcustomer.getNoOfSavingsAccounts();
                    for (int i = 0; i < sumOfAccounts; i++) {
                        newcustomer.queryAccountDetails(newcustomer.getCustomerAccounts().get(i));
                    }

                } else {
                    System.out.println("Invalid username or password!");
                }
                System.out.println("Press ENTER to continue!");
                System.in.read();
                break;

            case "G":
                System.out.println("G: SEARCH ALL CURRENT ACCOUNTS");
                for (Customer customer : customerList) {
                    int sumOfAccounts = newcustomer.getNoOfCurrentAccounts() + newcustomer.getNoOfDepositAccount() + newcustomer.getNoOfSavingsAccounts();
                    for (int i = 0; i < sumOfAccounts; i++) {
                        if (newcustomer.getCustomerAccounts().get(i).type.equals("Current")) {
                            double balance = newcustomer.queryAccountBalance(newcustomer.getCustomerAccounts().get(i));
                            if (balance >= 15240) {
                                System.out.println("Name: " + newcustomer.getName());
                                System.out.println("Email: " + newcustomer.getEmail());
                            }
                        }
                    }
                }
                break;
        }
    }

    /* //Method for writing the file - FILE_NAME
    private static void writeFile() throws IOException {
        FileOutputStream fo = new FileOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(fo);
        for (Customer customer : customerList) {
            output.writeObject(customer);
        }
        output.close();
        fo.close();
    } */


    /* //Method for reading the file
    private static void readFile() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream(file);
        ObjectInputStream input = new ObjectInputStream(fi);
        try {
            while (true) {
                Customer cust = (Customer) input.readObject();
                customerList.add(cust);
            }
        } catch(EOFException eof){
        }
    } */

    // Example and some uses on Stack Overflow thread here
    // http://stackoverflow.com/questions/9375882/how-i-can-run-my-timertask-everyday-2-pm
    // Documentation http://docs.oracle.com/javase/7/docs/api/java/util/Timer.html#schedule(java.util.TimerTask,%20java.util.Date,%20long)

    private static void createSchedulers() {
        // Create daily scheduler and process all accounts each day
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Timer timer = new Timer();
        timer.schedule(new DailyScheduledTask(totalAccountsInSystem), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));

        // Create monthly scheduler and process all accounts each month
        today.set(Calendar.DAY_OF_MONTH, 30);
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);
        timer = new Timer();
        timer.schedule(new MonthlyScheduledTask(totalAccountsInSystem), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 30);
    }
}
