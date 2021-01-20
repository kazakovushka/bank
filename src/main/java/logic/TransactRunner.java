package logic;

import model.Account;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class TransactRunner implements Runnable {
    List<Account> accounts;
    CountDownLatch latch;

    public TransactRunner( CountDownLatch latch, List<Account> accounts) {
        this.accounts = accounts;
        this.latch = latch;
    }

    @Override
    public void run() {
        int accountFromNumber = ThreadLocalRandom.current().nextInt(0, accounts.size());
        int accountToNumber = ThreadLocalRandom.current().nextInt(0, accounts.size());
        int payment = 100;
        BankTransaction.checkAndTransact(accounts.get(accountFromNumber), accounts.get(accountToNumber), payment);

        latch.countDown();
        //System.out.println("count down"+latch.getCount());
    }
}
