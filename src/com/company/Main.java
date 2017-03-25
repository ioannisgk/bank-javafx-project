package com.company;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.Serializable;

/**
 * Main class: Loads customers from file and starts GUI
 **/

public class Main implements Serializable {

    // Lists for customers and accounts in the system
    public static List<Customer> customerList = new ArrayList<>();
    public static List<Account> totalAccountsInSystem = new ArrayList<>();

    // Types of Accounts in the system
    public static final String ACCOUNT_TYPE_SAVINGS = "savings";
    public static final String ACCOUNT_TYPE_DEPOSIT = "deposit";
    public static final String ACCOUNT_TYPE_CURRENT = "current";

    //The scanner is used to read input data
    //private static Scanner scanner = new Scanner(System.in);

    ///////////////////////////////////////////////////////////////////

    // Main method
    public static void main(String[] args) throws Exception {

        // Load customers
        customerList = customerList();

        // Start GUI
        MainView mainView = new MainView();
        mainView.run(args);

        // Run schedulers
        createSchedulers();
    }

    // Method for saving a Customer object to a file
    public static void saveToFile(Customer newcustomer) throws Exception {
        try {
            File f = new File("customers.bin");
            PrintWriter writer = new PrintWriter(f);
            writer.print("");
            writer.close();
            boolean updatedCustomer = false;
            for (Customer customer : Main.customerList) {
                if (customer.equals(newcustomer)) {
                    updatedCustomer = true;
                    customer = newcustomer;
                }
            }
            if (!updatedCustomer) {
                Main.customerList.add(newcustomer);
            }


            for (Customer customer : Main.customerList) {
                FileOutputStream fileOut = new FileOutputStream("customers.bin", true);
                ObjectOutputStream streamOut = new ObjectOutputStream(fileOut);
                streamOut.writeObject(customer);
                // http://stackoverflow.com/questions/2340106/what-is-the-purpose-of-flush-in-java-streams
                fileOut.flush();
                streamOut.close();
                fileOut.close();

            }

        } catch (EOFException ex) {
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