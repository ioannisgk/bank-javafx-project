package com.company;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Main {

    public static List<Account> accounts = new ArrayList<>();
    public static void main(String[] args) {

        System.out.println("Welcome!!!");

        CurrentAccount acc1 = new CurrentAccount("bob", 0);
        CurrentAccount acc2 = new CurrentAccount("John", 10);
        DepositAccount acc3 = new DepositAccount("Doris", 15);
        SavingsAccount acc4 = new SavingsAccount("Jane", 20);

        System.out.println("New accounts");
        acc1.showDetails();
        acc2.showDetails();
        acc3.showDetails();
        acc4.showDetails();

        //add all accounts in list
        accounts.add(acc1);
        accounts.add(acc2);
        accounts.add(acc3);
        accounts.add(acc4);


        //this will run daily
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Timer timer = new Timer();
        timer.schedule(new DailyScheduledTask(accounts), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // 60*60*24*100 = 8640000ms


        //this will run monthly
        today.set(Calendar.DAY_OF_MONTH, 30);
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);
        timer = new Timer();
        timer.schedule(new DailyScheduledTask(accounts), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)*30);    }
}