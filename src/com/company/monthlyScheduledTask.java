package com.company;

import java.util.List;
import java.util.TimerTask;


public class monthlyScheduledTask  extends TimerTask{


    private List<Account> accounts;
    public monthlyScheduledTask(List<Account> accounts) {
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

            /**
             * daily avg. balance
             */
            double dailyAvgBalance = account.getTotalMonthlyBalance()/30;

            if(account instanceof SavingsAccount){
                //no monthly task for saving account
            }else if(account instanceof CurrentAccount) {
                //charge user based on number of day on which user has negative balance
                if(account.getNoOfDayFornegativeBalanace()>0){
                    account.setBalance(account.getBalance()*10);
                }
            }else if(account instanceof DepositAccount){
                //add interest for deposit account
                account.setBalance(account.getBalance()*1.05);
            }

            /**
             * reset number of negative balance days for next month
             */
            account.setNoOfDayFornegativeBalanace(0);//reset for next month

            /**
             * reset total month balance for next month
             */
            account.setTotalMonthlyBalance(0);
        }

    }

}