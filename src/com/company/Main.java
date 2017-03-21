package com.company;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.Serializable;

/**
 * Main class
 **/

public class Main implements Serializable {

    public static List<Customer> customerList = new ArrayList<>();
    // List that contains all accounts in the system
    public static List<Account> totalAccountsInSystem = new ArrayList<>();

    // Types of Accounts in the system
    public static final String ACCOUNT_TYPE_SAVINGS = "savings";
    public static final String ACCOUNT_TYPE_DEPOSIT = "deposit";
    public static final String ACCOUNT_TYPE_CURRENT = "current";

    //The scanner is used to read input data
    private static Scanner scanner = new Scanner(System.in);

    ///////////////////////////////////////////////////////////////////

    // Main method
    public static void main(String[] args) throws Exception {
        // showConsoleMenu();
        customerList = customerList();
        MainView mainView = new MainView();
        mainView.run(args);

        //NewAccountView newAccountview = new NewAccountView();
        //newAccountview.run(args);
    }

    // Main method
    public static void showConsoleMenu() throws Exception {

        customerList = customerList();
        boolean s = true;
        while (s) {

            System.out.println("Welcome!!!");
            System.out.println("A: Open a New Account");
            System.out.println("B: Deposit Money");
            System.out.println("C: Withdraw Money");
            System.out.println("D: Query Account Balance");
            System.out.println("E: Query Account Details");
            System.out.println("F: Query Account Details (without username/password)");
            System.out.println("G: Search all Current Accounts");
            System.out.println("H: Exit");

            boolean exists = false;
            String input = scanner.nextLine();
            Customer newcustomer = new Customer();

            switch (input.toUpperCase()) {
                case "A":
                    System.out.println("A: OPENING ACCOUNT");
                    System.out.println("Enter your username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter your password: ");
                    String password = scanner.nextLine();
                    for (Customer customer : customerList) {
                        if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                            newcustomer = customer;
                            exists = true;
                        }
                    }
                    if (!exists) {
                        System.out.println("Invalid username or password!\nPlease, create a new Customer.");
                        System.out.println("Please, input your name.");
                        String name = scanner.nextLine();
                        System.out.println("Please, input your email address.");
                        String email = scanner.nextLine();
                        System.out.println("Please, input your address.");
                        String address = scanner.nextLine();
                        System.out.println("Please, input your username.");
                        username = scanner.nextLine();
                        System.out.println("Please, input your password.");
                        password = scanner.nextLine();
                        newcustomer = new Customer(name, name, email, address, username, password, new Date());
                        //customerList.add(newcustomer);
                        saveToFile(newcustomer);
                    } else {
                        // Once the customer is created the user is asked to input the account type
                        System.out.println("Please, enter the account type. (current, deposit, savings)");
                        String type = scanner.nextLine();
                        switch (type.toLowerCase()) {
                            case "current":
                                newcustomer.openNewAccount(ACCOUNT_TYPE_CURRENT, 0, 0);
                                saveToFile(newcustomer);
                                System.out.println(newcustomer.getNoOfCurrentAccounts());
                                System.out.println(newcustomer.toString());
                                break;
                            case "deposit":
                                newcustomer.openNewAccount(ACCOUNT_TYPE_DEPOSIT, 0, 0);
                                System.out.println(newcustomer.toString());
                                saveToFile(newcustomer);
                                break;
                            case "savings":
                                System.out.println("How many terms for this account? (1, 2, 3)");
                                int terms = scanner.nextInt();
                                newcustomer.openNewAccount(ACCOUNT_TYPE_SAVINGS, 0, terms);
                                saveToFile(newcustomer);
                                System.out.println(newcustomer.toString());
                                break;
                        }
                    }
                    System.out.println("Press ENTER to continue!");
                    System.in.read();
                    break;

                case "B":
                    System.out.println("B: DEPOSIT MONEY");
                    System.out.println("Enter your username: ");
                    username = scanner.nextLine();
                    System.out.println("Enter your password: ");
                    password = scanner.nextLine();
                    for (Customer customer : customerList) {
                        if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                            newcustomer = customer;
                            exists = true;
                        }
                    }
                    if (exists) {
                        // Once the customer is created the user is asked to input the account type
                        System.out.println("Please, enter the account type to deposit to. (current, deposit, savings)");
                        String type = scanner.nextLine();
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
                                            System.out.print(newcustomer.getCustomerAccounts().get(i).accountID + " ");
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
                    username = scanner.nextLine();
                    System.out.println("Enter your password: ");
                    password = scanner.nextLine();
                    for (Customer customer : customerList) {
                        if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                            newcustomer = customer;
                            exists = true;
                        }
                    }
                    if (exists) {
                        // Once the customer is created the user is asked to input the account type
                        System.out.println("Please, enter the account type to withdraw from. (current, deposit, savings)");
                        String type = scanner.nextLine();
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
                    username = scanner.nextLine();
                    System.out.println("Enter your password: ");
                    password = scanner.nextLine();
                    for (Customer customer : customerList) {
                        if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                            newcustomer = customer;
                            exists = true;
                        }
                    }
                    if (exists) {
                        // Once the customer is created the user is asked to input the account type
                        System.out.println("Please, enter the account type to see its balance. (current, deposit, savings)");
                        String type = scanner.nextLine();
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
                    username = scanner.nextLine();
                    System.out.println("Enter your password: ");
                    password = scanner.nextLine();
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
                    String name = scanner.nextLine();
                    System.out.println("Please, enter your email.");
                    String email = scanner.nextLine();
                    System.out.println("Please, enter your address");
                    String address = scanner.nextLine();
                    for (Customer customer : customerList) {
                        if (customer.getFirstname().equals(name) && customer.getEmail().equals(email) && customer.getAddress().equals(address)) {
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
                        int sumOfAccounts = customer.getNoOfCurrentAccounts() + customer.getNoOfDepositAccount() + customer.getNoOfSavingsAccounts();
                        for (int i = 0; i < sumOfAccounts; i++) {
                            if (customer.getCustomerAccounts().get(i).type.equals("Current")) {
                                double balance = customer.queryAccountBalance(customer.getCustomerAccounts().get(i));
                                if (balance >= 15240) {
                                    System.out.println("Name: " + customer.getFirstname());
                                    System.out.println("Email: " + customer.getEmail());
                                }
                            }
                        }
                    }
                    break;
                case "H":
                    System.out.println("H: EXIT");
                    System.out.println("Exiting...");
                    s = false;
                    break;
            }
        }
    }

    // Method for saving a Customer object to a file
    public static void saveToFile(Customer _customer) throws Exception {
        try {
            File f = new File("customers.bin");
            PrintWriter writer = new PrintWriter(f);
            writer.print("");
            writer.close();
            boolean updatedCustomer = false;
            for(Customer customer : Main.customerList){
                if (customer.equals(_customer)){
                    updatedCustomer = true;
                    customer = _customer;
                }
            }
            if (!updatedCustomer){
                Main.customerList.add(_customer);
            }
            
            
            for(Customer customer : Main.customerList){
                FileOutputStream fileOut = new FileOutputStream("customers.bin",true);
                ObjectOutputStream streamOut = new ObjectOutputStream(fileOut);
                streamOut.writeObject(customer);
                // http://stackoverflow.com/questions/2340106/what-is-the-purpose-of-flush-in-java-streams
                fileOut.flush();
                streamOut.close();
                fileOut.close();
                
            }
            
        }
        catch (EOFException ex) {
            System.out.println("End of file reached.");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Customer details are saved");
    }
   

    // Method for loading Customer objects to a list
    private static List<Customer> customerList() throws Exception {
        List<Customer> customerList = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("customers.bin");
            ObjectInputStream streamIn = new ObjectInputStream(fileIn);

            Customer obj = null;

            while ((obj = (Customer) streamIn.readObject()) != null) {
                Customer customer = obj;
                customerList.add(customer);
                System.out.println(customer.getFirstname());
                System.out.println(customer.getPassword());
                System.out.println("Loading customers finished");
                streamIn = new ObjectInputStream(fileIn);
            }
            streamIn.close();
            fileIn.close();
        }
        catch (EOFException ex) {
            System.out.println("End of file reached.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("All customers loaded");
        return customerList;
    }
 
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