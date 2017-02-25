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
            long noOfDaysSinceAccountOpened =  Math.abs((account.dateOpened.getTime()-System.currentTimeMillis())/86400000);
            double dailyAvgBalance = account.getTotalMonthlyBalance()/30;
            if(account instanceof SavingsAccount){
                //no monthly task for saving accosunt
            }else if(account instanceof CurrentAccount) {
                if(account.getNoOfDayForNagativeBalanace()>0){
                    account.setBalance(account.getBalance()*10);
                }
            }else if(account instanceof DepositAccount){
                account.setBalance(account.getBalance()*1.05);
            }
            account.setNoOfDayForNagativeBalanace(0);//reset for next month
        }

    }

}