package com.company;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.Serializable;
/**
 * Customer class
 **/

public class Main implements Serializable {

    // List that contains all accounts in the system
    public static List<Account> totalAccountsInSystem = new ArrayList<>();

    // Types of Accounts in the system
    public static final String ACCOUNT_TYPE_SAVINGS = "savings";
    public static final String ACCOUNT_TYPE_DEPOSIT = "deposit";
    public static final String ACCOUNT_TYPE_CURRENT = "current";

    //Variables regarding writing the file
    private static final File FILE_NAME = new File("customers.bin");

    private static final File file = new File("customers.bin");
    private static ObjectInputStream objectInput;
    private static FileInputStream fileInput;
    private static ObjectOutputStream objectOutput;
    private static FileOutputStream fileOutput;

    //A new object of the class Customer is created
    private static Customer customer = new Customer();
    private static List<Customer> customerList = new ArrayList<>();

    //The scanner is used to read input data
    private static Scanner scanner = new Scanner(System.in);

    //Methods used while choosing an option in the menu
    private static void deposit(Customer newcust, int i) throws IOException, ClassNotFoundException {
        System.out.println("Please, input the amount you would like to deposit.");
        double amount = scanner.nextDouble();
        newcust.deposit(newcust.getCustomerAccounts().get(i), amount);
        System.out.println("Press ENTER to continue!");
        System.in.read();
        showMenu();
    }

    private static void withdraw(Customer newcust, int i) throws IOException, ClassNotFoundException {
        System.out.println("Please, input the amount you would like to withdraw.");
        double amount = scanner.nextDouble();
        newcust.withdraw(newcust.getCustomerAccounts().get(i), amount);
        System.out.println("Press ENTER to continue!");
        System.in.read();
        showMenu();
    }

    private static void balance(Customer newcust, int i) throws IOException, ClassNotFoundException {
        double balance = newcust.queryAccountBalance(newcust.getCustomerAccounts().get(i));
        System.out.println("Your current balance is £" + balance);
        System.out.println("Press ENTER to continue!");
        System.in.read();
        showMenu();
    }

    //Method for writing the file - FILE_NAME
    private static void writeFile() throws IOException {
        FileOutputStream fo = new FileOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(fo);
        for (Customer customer : customerList) {
            output.writeObject(customer);
        }
        output.close();
        fo.close();
    }


    //Method for reading the file
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
    }

    //Main
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        showMenu();
    }


    // Example and some uses on Stack Overflow thread here
    // http://stackoverflow.com/questions/9375882/how-i-can-run-my-timertask-everyday-2-pm
    // Documentation http://docs.oracle.com/javase/7/docs/api/java/util/Timer.html#schedule(java.util.TimerTask,%20java.util.Date,%20long)

    //Method for showing the possible choices on the menu
    private static void showMenu() throws IOException, ClassNotFoundException {
        //readFile();
        createSchedulers();
        System.out.println("Welcome!\nPlease, choose an option to continue");
        System.out.println("A: Open a New Account");
        System.out.println("B: Deposit Money");
        System.out.println("C: Withdraw Money");
        System.out.println("D: Query Account Balance");
        System.out.println("E: Query Account Details");
        System.out.println("F: Login");
        showOptions();
    }

    //Method regarding the options connected to the menu
    private static void showOptions() throws IOException, ClassNotFoundException {
        boolean exists = false;
        String input = scanner.next();
        Customer newcust = new Customer();
        switch (input.toUpperCase()) {
            case "A":
                System.out.println("Please, input your name");
                String name = scanner.next();

                //Checks whether a customer exists in the customerList
                for (Customer customer : customerList) {
                    if (customer.getName().equals(name)) {
                        exists = true;
                    }
                }
                if (!exists) {
                    System.out.println("Sorry, this name is not a customer, yet.\nPlease, create a new Customer.");
                    System.out.println("Please, input your name.");
                    name = scanner.next();
                    System.out.println("Please, input your email address.");
                    String email = scanner.next();
                    System.out.println("Please, input your address.");
                    String address = scanner.next();
                    System.out.println("Please, input your username.");
                    String user = scanner.next();
                    System.out.println("Please, input your password.");
                    String pass = scanner.next();
                    customer = new Customer(name, email, address, user, pass, new Date());
                    customerList.add(customer);
                    writeFile();
                } else {
                    System.out.println("Welcome back, " + name + ".");
                }
                System.out.println("Open a New Account");
                //Once the customer is created the user is asked to input the account type
                System.out.println("Please, enter the account type. (current, deposit, savings)");
                String acctype = scanner.next();
                switch (acctype.toLowerCase()) {
                    case "current":
                        customer.openAccount(ACCOUNT_TYPE_CURRENT, 0);
                        break;
                    case "deposit":
                        customer.openAccount(ACCOUNT_TYPE_DEPOSIT, 0);
                        break;
                    case "savings":
                        customer.openAccount(ACCOUNT_TYPE_SAVINGS, 0);
                        break;
                }
                System.out.println("Press ENTER to continue!");
                System.in.read();
                showMenu();
                break;
            case "B":
                System.out.println("Please, input your name");
                name = scanner.next();
                for (Customer customer : customerList) {
                    if (customer.getName().equals(name)) {
                        newcust = customer;
                        exists = true;
                    }
                }
                if (exists) {
                    System.out.println("Deposit Money");
                    System.out.println("Which account do you want to deposit money to(current, deposit, savings)?");
                    //We first ask for a string - account, and then we check each account separately (same for: C and D)
                    String acc = scanner.next();
                    switch (acc.toLowerCase()) {
                        case "current":
                            if (newcust.getNoOfCurrentAccounts() == 1) {
                                for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                    if (newcust.getCustomerAccounts().get(i).type.equals("Current")) {
                                        deposit(newcust, i);
                                    }
                                }

                            } else if (newcust.getNoOfCurrentAccounts() == 2) {
                                System.out.print("You have two current accounts. Please, input an account ID (");
                                for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                    if (newcust.getCustomerAccounts().get(i).type.equals("Current")) {
                                        System.out.print(newcust.getCustomerAccounts().get(i).accountID + " ");
                                    }
                                }
                                System.out.print(").\n");
                                int curid = scanner.nextInt();
                                System.out.println("Please, input the amount you want to deposit");
                                int amount = scanner.nextInt();
                                for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                    if (Integer.parseInt(newcust.getCustomerAccounts().get(i).accountID) == curid) {
                                        newcust.deposit(newcust.getCustomerAccounts().get(i), amount);
                                    }
                                }
                                System.out.println("Press ENTER to continue!");
                                System.in.read();
                                showMenu();
                            }
                            System.out.println("There is no such account.");
                            System.out.println("Press ENTER to continue!");
                            System.in.read();
                            showMenu();
                            break;
                        case "deposit":
                            for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                if (newcust.getCustomerAccounts().get(i).type.equals("Deposit")) {
                                    deposit(newcust, i);
                                }
                            }
                            System.out.println("There is no such account.");
                            System.out.println("Press ENTER to continue!");
                            System.in.read();
                            showMenu();

                            break;
                        case "savings":
                            for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                if (newcust.getCustomerAccounts().get(i).type.equals("Savings")) {
                                    deposit(newcust, i);
                                }
                            }
                            System.out.println("There is no such account.");
                            System.out.println("Press ENTER to continue!");
                            System.in.read();
                            showMenu();
                            break;
                    }
                } else {
                    System.out.println("There is no such customer, please create one.");
                    System.out.println("Press ENTER to continue!");
                    System.in.read();
                    showMenu();
                }
                break;
            case "C":
                System.out.println("Please, input your name");
                name = scanner.next();
                for (Customer customer : customerList) {
                    if (customer.getName().equals(name)) {
                        newcust = customer;
                        exists = true;
                    }
                }
                if (exists) {
                    System.out.println("Deposit Money");
                    System.out.println("Which account do you want to withdraw money from(current, deposit, savings)?");
                    String acc = scanner.next();
                    switch (acc.toLowerCase()) {
                        case "current":
                            if (newcust.getNoOfCurrentAccounts() == 1) {
                                for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                    if (newcust.getCustomerAccounts().get(i).type.equals("Current")) {
                                        withdraw(newcust, i);
                                    }
                                }

                            } else if (newcust.getNoOfCurrentAccounts() == 2) {
                                System.out.print("You have two current accounts. Please, input an account ID (");
                                for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                    if (newcust.getCustomerAccounts().get(i).type.equals("Current")) {
                                        System.out.print(newcust.getCustomerAccounts().get(i).accountID + " ");
                                    }
                                }
                                System.out.print(").\n");
                                int curid = scanner.nextInt();
                                System.out.println("Please, input the amount you want to withdraw");
                                int amount = scanner.nextInt();
                                for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                    if (Integer.parseInt(newcust.getCustomerAccounts().get(i).accountID) == curid) {
                                        newcust.withdraw(newcust.getCustomerAccounts().get(i), amount);
                                    }
                                }
                                System.out.println("Press ENTER to continue!");
                                System.in.read();
                                showMenu();
                            }
                            System.out.println("There is no such account.");
                            System.out.println("Press ENTER to continue!");
                            System.in.read();
                            showMenu();
                            break;
                        case "deposit":
                            for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                if (newcust.getCustomerAccounts().get(i).type.equals("Deposit")) {
                                    withdraw(newcust, i);
                                }
                            }
                            System.out.println("There is no such account.");
                            System.out.println("Press ENTER to continue!");
                            System.in.read();
                            showMenu();

                            break;
                        case "savings":
                            for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                if (newcust.getCustomerAccounts().get(i).type.equals("Savings")) {
                                    withdraw(newcust, i);
                                }
                            }
                            System.out.println("There is no such account.");
                            System.out.println("Press ENTER to continue!");
                            System.in.read();
                            showMenu();
                            break;
                    }
                } else {
                    System.out.println("There is no such customer, please create one.");
                    System.out.println("Press ENTER to continue!");
                    System.in.read();
                    showMenu();
                }
                break;
            case "D":
                System.out.println("Please, enter your name.");
                name = scanner.next();

                for (Customer customer : customerList) {
                    if (customer.getName().equals(name)) {
                        newcust = customer;
                        exists = true;
                    }
                }

                if (exists) {
                    System.out.println("Query Account Balance");
                    System.out.println("Which account do you want to see the balance from(current, deposit, savings)?");
                    String acc = scanner.next();
                    switch (acc.toLowerCase()) {
                        case "current":
                            if (newcust.getNoOfCurrentAccounts() == 1) {
                                for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                    if (newcust.getCustomerAccounts().get(i).type.equals("Current")) {
                                        double balance = newcust.queryAccountBalance(newcust.getCustomerAccounts().get(i));
                                        System.out.println("Your current balance is £" + balance);
                                    }
                                }

                            } else if (newcust.getNoOfCurrentAccounts() == 2) {
                                System.out.print("You have two current accounts. Please, input an account ID (");
                                for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                    if (newcust.getCustomerAccounts().get(i).type.equals("Current")) {
                                        System.out.print(newcust.getCustomerAccounts().get(i).accountID + " ");
                                    }
                                }
                                System.out.print(").\n");
                                int curid = scanner.nextInt();
                                for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                    if (Integer.parseInt(newcust.getCustomerAccounts().get(i).accountID) == curid) {
                                        double balance = newcust.queryAccountBalance(newcust.getCustomerAccounts().get(i));
                                        System.out.println("Your current balance is £" + balance);
                                    }
                                }
                                System.out.println("Press ENTER to continue!");
                                System.in.read();
                                showMenu();
                            }
                            System.out.println("There is no such account.");
                            System.out.println("Press ENTER to continue!");
                            System.in.read();
                            showMenu();
                            break;
                        case "deposit":
                            for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                if (newcust.getCustomerAccounts().get(i).type.equals("Deposit")) {
                                    balance(newcust, i);
                                }
                            }
                            System.out.println("There is no such account.");
                            System.out.println("Press ENTER to continue!");
                            System.in.read();
                            showMenu();

                            break;
                        case "savings":
                            for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                                if (newcust.getCustomerAccounts().get(i).type.equals("Savings")) {
                                    balance(newcust, i);
                                }
                            }
                            System.out.println("There is no such account.");
                            System.out.println("Press ENTER to continue!");
                            System.in.read();
                            showMenu();
                            break;
                    }
                } else {
                    System.out.println("There is no such customer, please create one.");
                    System.out.println("Press ENTER to continue!");
                    System.in.read();
                    showMenu();
                }
                break;
            case "E":
                System.out.println("Query Account Details");
                System.out.println("Please, enter your name.");
                name = scanner.next();
                System.out.println("Please, enter your email.");
                String email = scanner.next();
                System.out.println("Please, enter your address");
                String address = scanner.next();
                for (Customer customer : customerList) {
                    if (customer.getName().equals(name) && customer.getEmail().equals(email) && customer.getAddress().equals(address)) {
                        newcust = customer;
                        exists = true;
                    }
                }
                if (exists) {
                    for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                        newcust.queryAccountDetails(newcust.getCustomerAccounts().get(i));
                    }
                } else {
                    System.out.println("There is no customer with these data.");
                }
                System.out.println("Press ENTER to continue!");
                System.in.read();
                showMenu();
                break;
            case "F":
                System.out.println("LOGIN");
                System.out.println("Please, input your username: ");
                String user = scanner.next();
                System.out.println("Please, input your password: ");
                String pass = scanner.next();
                for (Customer customer : customerList) {
                    if (customer.getUsername().equals(user) && customer.getPassword().equals(pass)) {
                        newcust = customer;
                        exists = true;
                    }
                }
                if (exists) {
                    System.out.println("Username: " + newcust.getUsername());
                    System.out.println("Password: " + newcust.getPassword());
                    for (int i = 0; i < newcust.getNoOfCurrentAccounts() + newcust.getNoOfDepositAccount() + newcust.getNoOfSavingsAccounts(); i++) {
                        newcust.queryAccountDetails(newcust.getCustomerAccounts().get(i));
                    }

                } else {
                    System.out.println("Invalid username or password!");
                }
                System.out.println("Press ENTER to continue!");
                System.in.read();
                showMenu();
                break;
        }
    }

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
