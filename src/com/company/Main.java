package com.company;

public class Main {

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

        System.out.println("After deposit");
        acc1.deposit(100);
        acc2.deposit(100);
        acc3.deposit(100);
        acc4.deposit(100);
        System.out.println();
        acc1.showDetails();
        acc2.showDetails();
        acc3.showDetails();
        acc4.showDetails();

        System.out.println("After withdrawal");
        acc1.withdraw(1);
        acc2.withdraw(1);
        acc3.withdraw(1);
        acc4.withdraw(1);
        System.out.println();
        acc1.showDetails();
        acc2.showDetails();
        acc3.showDetails();
        acc4.showDetails();

    }
}
