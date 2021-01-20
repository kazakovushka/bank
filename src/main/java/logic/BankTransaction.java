package logic;

import model.Account;

public class BankTransaction {
    public static void transact(Account from, Account to, int payment) {
        from.setBalance(from.getBalance() - payment);
        to.setBalance(to.getBalance() + payment);
        System.out.println("account " + from.getId() + " -----> account " + to.getId() + ", payment = " + payment +
                " thread " + Thread.currentThread().getName());
    }
}
