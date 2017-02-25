package com.company;

import java.util.List;
import java.util.TimerTask;


public class DailyScheduledTask extends TimerTask {

    private List<Account> accounts;
    public DailyScheduledTask(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public void run() {
        for (Account account : accounts) {
            account.showDetails();

            /**
             * number of day since account is created
             */
            long noOfDaysSinceAccountOpened =  Math.abs((account.dateOpened.getTime()-System.currentTimeMillis())/86400000);

            /***
             * id current balance is negative then we going to track number of days, so we can charge user at end of month
             */
            if(account.getBalance() <0){
                account.setNoOfDayFornegativeBalanace(account.getNoOfDayFornegativeBalanace()+1);
            }

            /**
             * sum daily balance with existing ,so we can calculate avg. balance in month end
             */
            account.setTotalMonthlyBalance(account.getTotalMonthlyBalance()+account.getBalance());

            if(account instanceof SavingsAccount){
                //calculate interest of savings account
                account.setBalance((account.getBalance()*3/36500)+account.getBalance());
            }else if(account instanceof CurrentAccount) {
                //no daily task for current account
            }else if(account instanceof DepositAccount){
                //no daily task for deposit account
            }
        }

    }

}


