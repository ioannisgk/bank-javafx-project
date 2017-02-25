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

            long noOfDaysSinceAccountOpened =  Math.abs((account.dateOpened.getTime()-System.currentTimeMillis())/86400000);

            if(account.getBalance() <0){
                account.setNoOfDayForNagativeBalanace(account.getNoOfDayForNagativeBalanace()+1);
            }

            account.setTotalMonthlyBalance(account.getTotalMonthlyBalance()+account.getBalance());

            if(account instanceof SavingsAccount){
                account.setBalance((account.getBalance()*3/36500)+account.getBalance());
            }else if(account instanceof CurrentAccount) {
                //no daily task for current account
            }else if(account instanceof DepositAccount){
                //no daily task for deposite account
            }
        }

    }

}


