package com.company;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainController implements Serializable {

    private static List<Customer> customerList = new ArrayList<>();
    // List that contains all accounts in the system
    public static List<Account> totalAccountsInSystem = new ArrayList<>();

    // Types of Accounts in the system
    public static final String ACCOUNT_TYPE_SAVINGS = "savings";
    public static final String ACCOUNT_TYPE_DEPOSIT = "deposit";
    public static final String ACCOUNT_TYPE_CURRENT = "current";

    public MainController () {

    }

    public static boolean customerIsValid(String firstname, String surname, String dob) throws Exception {
        boolean customerFound = false;
        Customer newcustomer = new Customer();
        customerList = customerList();
        for (Customer customer : customerList) {
            if (customer.getFirstname().equals(firstname) && customer.getSurname().equals(surname)) {
                newcustomer = customer;
                customerFound = true;
            }
        }
        return customerFound;
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

    // http://stackoverflow.com/questions/22326339/how-create-date-object-with-values-in-java
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
