package com.company;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 *
 * Entry point of application
 *
 */
public class Main {

    //list of all accounts in system
    public static List<Account> totalAccountsInSystem = new ArrayList<>();


    //Type of account system has
    public static final String ACCOUNT_TYPE_SAVINGS = "savings";
    public static final String ACCOUNT_TYPE_DEPOSIT = "deposit";
    public static final String ACCOUNT_TYPE_CURRENT = "current";

    public static void main(String[] args) {

        System.out.println("Welcome!!!");

        //create customer
        Customer customer = new Customer("Alex","Alex@gmail.com","India","alex","123",new Date());

        //open 1 savings account
        customer.openAccount(ACCOUNT_TYPE_SAVINGS, 100);

        //open 2 current account
        customer.openAccount(ACCOUNT_TYPE_CURRENT, 111);
        customer.openAccount(ACCOUNT_TYPE_CURRENT, 22);

        //when user try to open 3rd current account, system will not create account, so below code not going to create account
        customer.openAccount(ACCOUNT_TYPE_CURRENT, 33);

        //open 1 deposit account
        customer.openAccount(ACCOUNT_TYPE_DEPOSIT, 20);

        //withdraw amount from first account
        customer.withdraw(customer.getCustomerAccounts().get(0), 11);

        //deposit amount in first account
        customer.deposit(customer.getCustomerAccounts().get(0), 11);

        //give details of first account
        customer.queryAccountDetails(customer.getCustomerAccounts().get(0));

        //give details of all accounts
        customer.queryAllAccountDetails();

        //give balance of first account
        double balance = customer.queryAccountBalance(customer.getCustomerAccounts().get(0));
        System.out.println("balance"+balance);

        //create daily and monthly schedulers
        createSchedulers();

    }

    /**
     *
     */
    private static void createSchedulers() {
        //create daily scheduler ,so we can process all system account daily
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Timer timer = new Timer();
        timer.schedule(new DailyScheduledTask(totalAccountsInSystem), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // 60*60*24*100 = 8640000ms


        //create monthly scheduler, lso we can process all system accounts in end of every month
        today.set(Calendar.DAY_OF_MONTH, 30);
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);
        timer = new Timer();
        timer.schedule(new DailyScheduledTask(totalAccountsInSystem), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)*30);
    }
}