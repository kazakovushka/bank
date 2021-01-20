package logic;


import lombok.SneakyThrows;
import model.Account;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TransactSameTimeRunner extends TransactRunner{
    CountDownLatch readyToStart;
    CountDownLatch waitForOtherBlocker;


    public TransactSameTimeRunner(CountDownLatch readyToStart, CountDownLatch waitForOtherBlocker,
                                  CountDownLatch finished, List<Account> accounts) {
        super(finished, accounts);
        this.waitForOtherBlocker = waitForOtherBlocker;
        this.readyToStart = readyToStart;
    }

    @SneakyThrows
    @Override
    public void run() {
        readyToStart.countDown();
        waitForOtherBlocker.await();
        super.run();
    }
}
