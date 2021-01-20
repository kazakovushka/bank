import logic.TransactRunner;
import model.Account;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class BankSimple {
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 10;
        int transactionNumber = 100000;

        List<Account> accounts = List.of(new Account(0, "Вася"),
                new Account(1, "Жора"), new Account(2, "Лена"));
        getSum(accounts);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(transactionNumber);

        for (int i = 0; i < transactionNumber; i++) {
            executor.submit(new TransactRunner(latch, accounts));
        }

        latch.await();
        executor.shutdown();
        accounts.forEach(System.out::println);
        getSum(accounts);
    }

    public static void getSum(List<Account> accounts) {
        System.out.println("total sum =" + accounts.stream().map(Account::getBalance)
                .reduce(Integer::sum).get());
    }
}
