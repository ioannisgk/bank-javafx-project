package com.company;
import java.util.List;
import java.util.TimerTask;

/**
 * Daily task 1: If customer exceeded the overdraft limit, increase charging days of CurrentAccount
 * Daily task 2: Keep track of total monthly balance of DepositAccount
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

            if (account instanceof CurrentAccount) {
                // If customer exceeded the overdraft limit, we increase "getNoOfDaysForOverdraftLimit" by 1,
                // so we can charge customer at the end of the month
                if (account.getCharging() == true) {
                    account.setNoOfDaysForOverdraftLimit(account.getNoOfDaysForOverdraftLimit() + 1);
                }
            } else if (account instanceof DepositAccount) {
                // Keep track of "TotalMonthlyBalance" by adding the current balance to it
                account.setTotalMonthlyBalance(account.getTotalMonthlyBalance() + account.getBalance());
            }
        }
    }
}