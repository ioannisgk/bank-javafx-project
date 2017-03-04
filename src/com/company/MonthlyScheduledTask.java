package com.company;
import java.util.List;
import java.util.TimerTask;

/**
 * Monthly task 1: If Current account, charge fee and reset charging days
 * Monthly task 2: If Deposit account, apply monthly interest and reset total monthly balance
 * Monthly task 3: If Savings account, calculate and apply interest
 **/

public class MonthlyScheduledTask extends TimerTask {

    // Class attributes
    private List<Account> accounts;

    // Class constructor, it needs an account list to be instantiated
    // https://docs.oracle.com/javase/tutorial/collections/interfaces/list.html
    public MonthlyScheduledTask(List<Account> accounts) {
        this.accounts = accounts;
    }

    // We override the run method as in the example here
    // http://www.journaldev.com/1050/java-timer-timertask-example
    @Override
    public void run() {
        for (Account account : accounts) {
            account.showDetails();

            if (account instanceof CurrentAccount) {
                // Charge customer based on number of days on which user has negative balance
                if (account.getNoOfDaysForOverdraftLimit() > 0) {
                    double fees = account.getNoOfDaysForOverdraftLimit() * 10;
                    account.setBalance(account.getBalance() - fees);
                }
                // Reset charging days for next month
                account.setNoOfDaysForOverdraftLimit(0);

            } else if (account instanceof DepositAccount) {
                // Calculate monthly average balance
                double monthlyAvgBalance = account.getTotalMonthlyBalance() / 30;
                // Calculate and apply interest to Deposit Account
                double monthlyInterest = account.getInterest() / 12;
                account.setBalance((monthlyAvgBalance * monthlyInterest) + account.getBalance());
                // Reset total monthly balance for next month
                account.setTotalMonthlyBalance(0);

            } else if (account instanceof SavingsAccount) {
                // Calculate and apply interest to Savings Account
                double monthlyInterest = account.getInterest() / 12;
                account.setBalance((account.getBalance() * monthlyInterest) + account.getBalance());
            }
        }
    }
}