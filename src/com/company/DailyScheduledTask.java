package com.company;
import java.util.List;
import java.util.TimerTask;

/**
 * Daily task 1: If negative balance, increase charging days
 * Daily task 2: Keep track of total monthly balance
 **/

public class DailyScheduledTask extends TimerTask {

    // Class attributes
    private List<Account> accounts;

    // Class constructor, it needs an account list to be instantiated
    // https://docs.oracle.com/javase/tutorial/collections/interfaces/list.html
    public DailyScheduledTask(List<Account> accounts) {
        this.accounts = accounts;
    }

    // We override the run method as in the example here
    // http://www.journaldev.com/1050/java-timer-timertask-example
    @Override
    public void run() {
        for (Account account : accounts) {
            account.showDetails();

            // If there has been an overdraft, we increase "NoOfDayFornegativeBalanace" by 1,
            // so we can charge customer at the end of the month
            if (account.getBalance() < 0) {
                account.setNoOfDayFornegativeBalanace(account.getNoOfDayFornegativeBalanace() + 1);
            }

            // Keep track of "TotalMonthlyBalance" by adding the current balance to it
            account.setTotalMonthlyBalance(account.getTotalMonthlyBalance() + account.getBalance());
        }
    }
}


