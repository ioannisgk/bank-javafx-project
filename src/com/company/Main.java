package com.company;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Customer class
 **/

public class Main {

    // List that contains all accounts in the system
    public static List<Account> totalAccountsInSystem = new ArrayList<>();

    // TYpes of Accounts in the system
    public static final String ACCOUNT_TYPE_SAVINGS = "savings";
    public static final String ACCOUNT_TYPE_DEPOSIT = "deposit";
    public static final String ACCOUNT_TYPE_CURRENT = "current";

    public static void main(String[] args) {

        System.out.println("Welcome!!!");

        System.out.println("1. Creating a customer...");
        Customer customer = new Customer("Ioannis","email@domain.com","Greece","ioannisgk","123456", new Date());
        System.out.println("Customer created!");

        System.out.println("------------ Opening Accounts ------------");

        System.out.println("2. Opening two Current Accounts...");
        customer.openAccount(ACCOUNT_TYPE_CURRENT, 100);
        customer.openAccount(ACCOUNT_TYPE_CURRENT, 50);
        System.out.println("Two Current Accounts opened!");

        System.out.println("3. Opening a 3rd Current Account...");
        customer.openAccount(ACCOUNT_TYPE_CURRENT, 33);
        System.out.println("Current Account NOT opened!");

        System.out.println("4. Opening a Deposit Account...");
        customer.openAccount(ACCOUNT_TYPE_DEPOSIT, 20);
        System.out.println("Deposit Account opened!");

        System.out.println("5. Opening a Savings Account...");
        customer.openAccount(ACCOUNT_TYPE_SAVINGS, 100);
        System.out.println("Deposit Account opened!");

        System.out.println("------------ Act On Accounts ------------");

        System.out.println("6. Withdraw from first Account...");
        customer.withdraw(customer.getCustomerAccounts().get(0), 10);
        System.out.println("Withdrawal completed!");

        System.out.println("7. Deposit to first Account...");
        customer.deposit(customer.getCustomerAccounts().get(0), 15);
        System.out.println("Deposit completed!");

        System.out.println("------------ Show Details of Accounts ------------");

        System.out.println("8. Show Details from first Account...");
        customer.queryAccountDetails(customer.getCustomerAccounts().get(0));
        System.out.println("Details shown!");

        System.out.println("9. Show Details from All Accounts...");
        customer.queryAllAccountDetails();
        System.out.println("All details shown!");

        System.out.println("10. Show Balance from first Account...");
        double balance = customer.queryAccountBalance(customer.getCustomerAccounts().get(0));
        System.out.println("balance" + balance);
        System.out.println("Balance shown!");

        // Create daily and monthly schedulers;
        createSchedulers();
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
        timer.schedule(new MonthlyScheduledTask(totalAccountsInSystem), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)*30);
    }
}